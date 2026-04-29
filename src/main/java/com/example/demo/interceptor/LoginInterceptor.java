package com.example.demo.interceptor;

import com.example.demo.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");

        if (user == null) {
            response.sendRedirect("/login");
            return false;
        }

        String uri = request.getRequestURI();
        String role = user.getRole();

        // Role-based access control
        if (uri.startsWith("/admin") && !"ADMIN".equals(role)) {
            response.sendRedirect("/login");
            return false;
        }
        if (uri.startsWith("/teacher") && !"TEACHER".equals(role) && !"ADMIN".equals(role)) {
            response.sendRedirect("/login");
            return false;
        }
        if (uri.startsWith("/student") && !"STUDENT".equals(role)) {
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }
}
