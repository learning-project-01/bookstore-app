package com.example.bookstoreapp.filters;

import com.example.bookstoreapp.auth.AuthService;
import com.example.bookstoreapp.exceptions.AppRuntimeException;
import com.example.bookstoreapp.utils.AppUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@Slf4j
public class AuthenticationFilter implements Filter {

  private static final String[] PUBLIC_URI_PATTERNS = {"/api/**",
      "/user/login",
      "/user/signup",
      "/health",
      "/swagger-ui/**",
      "/catalogItems/**"
  };

  public static final String X_AUTH_TOKEN = "X-AUTH-TOKEN";

  private AuthService authService;

  public AuthenticationFilter(AuthService authService) {
    this.authService = authService;
  }

  private boolean isPreflightRequest(HttpServletRequest request){
    log.warn("preflight request: {}", request.getRequestURI());
    return request.getMethod().equals("OPTIONS");
  }
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    /*
     The status code was missing on 401 error and
     the pre-flight request was also failing when the status code sent by server is not 2XX.
     As the status code is missing the frontend is not able to extract the status code on error
     */
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
    response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, X-AUTH-TOKEN");

    log.info("filter executed for url: {}", request.getRequestURL());
    log.info("filter executed for uri: {}", request.getRequestURI());
    if (isPreflightRequest(request) || isPublicUri(request.getRequestURI())) {
      filterChain.doFilter(servletRequest, servletResponse);
      return;
    }
    boolean userContext = setUserContext(request, response);
    if(userContext) {
      filterChain.doFilter(servletRequest, servletResponse);
    }

  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    Filter.super.init(filterConfig);
  }

  @Override
  public void destroy() {
    Filter.super.destroy();
  }

  private boolean isPublicUri(String requestUri) {
    for (String pattern : PUBLIC_URI_PATTERNS) {
      boolean matched = AppUtils.matchesPattern(pattern, requestUri);
      if (matched) {
        return true;
      }
    }
    return false;
  }

  private boolean setUserContext(HttpServletRequest request, HttpServletResponse response) {
    String token = request.getHeader(X_AUTH_TOKEN);
    try {
      authService.setUserContext(token);
      return true;
    } catch (AppRuntimeException e) {
      log.error("error while setting user context: ", e);
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
    return false;
  }
}
