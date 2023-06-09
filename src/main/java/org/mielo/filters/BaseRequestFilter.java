package org.mielo.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Optional;

public abstract class BaseRequestFilter extends OncePerRequestFilter {

    @Override
    protected final void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterRequest(request);
            filterChain.doFilter(request, response);
        } catch (HttpStatusCodeException fex) {
            response.setStatus(fex.getStatusCode().value());
            response.getWriter().write(fex.getMessage());
        }
    }

    protected abstract void filterRequest(HttpServletRequest request) throws HttpStatusCodeException;

    protected Optional<String> getParamValue(HttpServletRequest request, String paramName) {
        return Optional.ofNullable(request.getParameter(paramName));
    }

    protected Optional<String> getHeaderValue(HttpServletRequest request, String paramName) {
        return Optional.ofNullable(request.getHeader(paramName));
    }

    protected Optional<Cookie> getCookie(HttpServletRequest request, String cookieName) {
        return Optional.ofNullable(WebUtils.getCookie(request, cookieName));
    }
}
