package com.nhnacademy.shoppingmall.common.filter;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@WebFilter(
        filterName = "loginCheckFilter",
        urlPatterns = "/*"
)
public class LoginCheckFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //todo#10 /mypage/ 하위경로의 접근은 로그인한 사용자만 접근할 수 있습니다.
        String requestUri = req.getRequestURI();
        if(requestUri.contains("/mypage/")){
            HttpSession session = req.getSession(false);
            if(session == null || session.getAttribute("user") == null){
                res.sendRedirect(req.getServletPath() + "/login.do");
                return;
            }
        }

        chain.doFilter(req, res);

    }
}