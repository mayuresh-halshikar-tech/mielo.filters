package org.mielo.filters.authentication;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class CookieAuthenticationFilter  extends BaseAuthenticationFilter implements Ordered {

    private final CookieAuthenticationFilterSupport support;

    @Override
    protected Optional<UserDetails> filterRequestForAuthentication(HttpServletRequest request) throws HttpStatusCodeException {
        Optional<UserDetails> authorization = getCookie(request, support.getCookieName())
                .map(Cookie::getValue)
                .map(support::authenticate);
        if(authorization.isEmpty()) {
            log.warn("******** No cookie received with name '" + support.getCookieName() + "'");
        }
        return authorization;
    }

    @Override
    public int getOrder() {
        return support.getOrder();
    }
}
