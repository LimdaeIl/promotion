package com.book.couponservice.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class UserIdInterceptor implements HandlerInterceptor {

  private static final String USER_ID_HEADER = "X-USER-ID";
  private static final ThreadLocal<Long> currentUserId = new ThreadLocal<>();

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    String userIdStr = request.getHeader(USER_ID_HEADER);

    log.info("userIdStr: {}", userIdStr);
    log.info("currentUserId.set(Long.parseLong(userIdStr)): {}", currentUserId.get());

    if (userIdStr == null || userIdStr.isEmpty()) {
      throw new IllegalStateException("X-USER-ID header is required");
    }
    try {
      currentUserId.set(Long.parseLong(userIdStr));
      return true;
    } catch (NumberFormatException e) {
      throw new IllegalStateException("Invalid X-USER-ID format");
    }
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    currentUserId.remove();
  }

  public static Long getCurrentUserId() {
    Long userId = currentUserId.get();
    if (userId == null) {
      throw new IllegalStateException("User ID not found in current context");
    }
    return userId;
  }
}