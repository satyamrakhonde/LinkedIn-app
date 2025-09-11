package com.codingshuttle.linkedin.notification_service.notification_service.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
//Why do we use HandlerInterceptor?
//
//We use it when we want to apply cross-cutting logic around controller execution without modifying every controller.
//
//Some common use cases:
//
//Authentication & Authorization
//
//Check if a request contains a valid token (JWT, session, etc.) before reaching the controller.
//
//Example: If the user is not logged in, stop the request and return 401 Unauthorized.
//
//        Logging & Auditing
//
//Log incoming requests (URI, method, headers, execution time).
//
//Track who called which API for auditing purposes.
//
//Request Modification
//
//Add or modify request attributes before passing them to controllers.
//
//Example: Attach a userId extracted from a JWT to the request.
//
//Response Modification
//
//Modify response headers or data after controller execution.
//
//Example: Add custom headers like X-Request-Id.
//
//        Performance Monitoring
//
//Capture request processing time and log slow requests.

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userId = request.getHeader("X-User-Id");

        if(userId != null) {
            UserContextHolder.setCurrentUserId(Long.valueOf(userId));
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContextHolder.clear();
    }
}
