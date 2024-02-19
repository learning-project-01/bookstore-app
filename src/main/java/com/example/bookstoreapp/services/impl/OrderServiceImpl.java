package com.example.bookstoreapp.services.impl;

import com.example.bookstoreapp.entities.OrderEntity;
import com.example.bookstoreapp.exceptions.AppRuntimeException;
import com.example.bookstoreapp.models.Address;
import com.example.bookstoreapp.models.Cart;
import com.example.bookstoreapp.models.Order;
import com.example.bookstoreapp.repositories.CatalogItemEntityRepository;
import com.example.bookstoreapp.repositories.OrderEntityRepository;
import com.example.bookstoreapp.services.*;
import com.example.bookstoreapp.utils.IdGenerator;
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
    CatalogItemEntityRepository catalogItemEntityRepository;
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
        order.setOrderId(IdGenerator.getLongId());
        order.setCustomerId(userContextService.getUserId());
        LocalDateTime localDateTime = LocalDateTime.now();
        order.setOrderTime(localDateTime);

        Cart cart = cartItemService.doCheckout(userContextService.getUserId());
        order.setTotalAmount(cart.getTotal());

        Address addressForShipping = addressService.findById(order.getAddressId());
        ObjectMapper objectMapper = new ObjectMapper();

        if (addressForShipping != null) {
            try {
                String jsonAddress = objectMapper.writeValueAsString(addressForShipping);
                order.setShippingAddress(jsonAddress);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else throw new AppRuntimeException("No address exists for the user");

        OrderEntity orderEntity = order.toEntity();
        orderEntity = orderEntityRepository.save(orderEntity);

//        if (orderEntity != null && orderEntity.getCustomerId() == userContextService.getUserId()) {
//
//            List<CartItem> cartItems = cart.getItems();
//            for (CartItem cartITEM : cartItems
//            ) {
//                int quantityTobeReduced =  cartITEM.getQuantity();
//                long id = cartITEM.getCartId();
//
//                CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//                CriteriaQuery<CartItemEntity> criteriaQuery = criteriaBuilder.createQuery(CartItemEntity.class);
//                Root<CartItemEntity> root = criteriaQuery.from(CartItemEntity.class);
//                criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
//                List<CartItemEntity> resultList = entityManager.createQuery(criteriaQuery).getResultList();
//                CartItemEntity cartItemEntity = resultList.get(0);
//
//                //  CartItemEntity cartItemEntity = entityManager.find(CartItemEntity.class, id);
//                long catalogItemId = cartItemEntity.getCatalogItemId();
//                CatalogItem catalogItem = catalogItemService.findById(catalogItemId);
//                catalogItem.setStockQuantity(catalogItem.getStockQuantity()-quantityTobeReduced);
//                CatalogItemEntity catalogItemEntity = catalogItem.toEntity();
//                catalogItemEntity = catalogItemEntityRepository.save(catalogItemEntity);
//            }
//
//
//        } else throw new AppRuntimeException("The order is not placed correctly");

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
