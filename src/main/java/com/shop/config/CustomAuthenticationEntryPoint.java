package com.shop.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
       log.info("-------------------CustomAuthenticationEntryPoint----------------------------------");
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        response.sendRedirect("/members/login");
    }
}
