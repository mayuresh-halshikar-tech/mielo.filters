package org.mielo.filters.authentication;

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
public class BearerTokenAuthenticationFilter extends BaseAuthenticationFilter implements Ordered {

    private final BearerTokenAuthenticationFilterSupport support;

    @Override
    protected Optional<UserDetails> filterRequestForAuthentication(HttpServletRequest request) throws HttpStatusCodeException {
        Optional<UserDetails> authorization = getHeaderValue(request, "Authorization")
                .map(this::extractToken)
                .map(support::authenticate);
        if(authorization.isEmpty()) {
            log.warn("******** No bearer token header received.");
        }
        return authorization;
    }

    private String extractToken(String bearer) {
        return bearer != null && bearer.startsWith("Bearer ") ? bearer.substring(7) : null;
    }


    @Override
    public int getOrder() {
        return support.getOrder();
    }
}
