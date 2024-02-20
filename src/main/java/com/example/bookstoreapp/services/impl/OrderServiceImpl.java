package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.entities.OrderEntity;
import com.example.bookstoreapp.exceptions.AppRuntimeException;
import com.example.bookstoreapp.models.Address;
import com.example.bookstoreapp.models.Cart;
import com.example.bookstoreapp.models.Order;
import com.example.bookstoreapp.repositories.OrderEntityRepository;
import com.example.bookstoreapp.services.*;
import com.example.bookstoreapp.utils.IdGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserContextService userContextService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private OrderEntityRepository orderEntityRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private CatalogItemService catalogItemService;

    @Override
    public Order createOrder(Order order) {
        Cart cart = cartItemService.doCheckout(userContextService.getUserId());
        LocalDateTime localDateTime = LocalDateTime.now();

        Address addressForShipping = addressService.findById(order.getAddressId());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonAddress;
        if (addressForShipping == null) throw new AppRuntimeException("No address exists for the user");
        try {
            jsonAddress = objectMapper.writeValueAsString(addressForShipping);
        } catch (JsonProcessingException e) {
            throw new AppRuntimeException(e.getMessage());
        }

        order.setOrderId(IdGenerator.getLongId());
        order.setCustomerId(userContextService.getUserId());
        order.setOrderTime(localDateTime);
        order.setTotalAmount(cart.getTotal());
        order.setShippingAddress(jsonAddress);

        cart.getItems().forEach(cartItem -> {
            int purchaseQuantity = cartItem.getQuantity();
            int stockQuantity = cartItem.getStockQuantity() - purchaseQuantity;
            if (purchaseQuantity > stockQuantity)
                throw new AppRuntimeException("Not enough stock available for this order");
            cartItem.setStockQuantity(stockQuantity);
            catalogItemService.update(cartItem.getId(), cartItem);
        });

        OrderEntity orderEntity = order.toEntity();
        orderEntity = orderEntityRepository.save(orderEntity);
        return order.fromEntity(orderEntity);
    }

    @Override
    public Order orderSummary(Long orderId) {

        Optional<OrderEntity> optionalOrderEntity = orderEntityRepository.findById(orderId);
        Order order = new Order();

        if (optionalOrderEntity.isPresent()) {
            OrderEntity orderEntity = optionalOrderEntity.get();
            order.setOrderId(orderEntity.getOrderId());
            order.setCustomerId(orderEntity.getCustomerId());
            order.setAddressId(orderEntity.getAddressId());
            order.setOrderTime(orderEntity.getOrderTime());
            order.setTotalAmount(orderEntity.getTotalAmount());
            order.setShippingAddress(orderEntity.getShippingAddress());
        } else throw new AppRuntimeException("Order doesn't exist");

        return order;
    }

    @Override
    public List<Order> allOrders(Long customerId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> criteriaQuery = criteriaBuilder.createQuery(OrderEntity.class);
        Root<OrderEntity> root = criteriaQuery.from(OrderEntity.class);

        Predicate predicate = criteriaBuilder.equal(root.get("customerId"), userContextService.getUserId());
        criteriaQuery.where(predicate);

        List<OrderEntity> orderEntityList = entityManager.createQuery(criteriaQuery).getResultList();

        List<Order> orders = orderEntityList.stream()
                .map(e -> {
                    return new Order().fromEntity(e);
                })
                .toList();

        return orders;
    }
}
