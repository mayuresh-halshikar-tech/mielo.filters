package org.mielo.filters.authentication;

import jakarta.servlet.http.HttpServletRequest;
import org.mielo.filters.BaseRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Optional;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;


public abstract class BaseAuthenticationFilter extends BaseRequestFilter {

    @Override
    protected final void filterRequest(HttpServletRequest request) throws HttpStatusCodeException {
        if(!isAlreadyAuthenticated()) {
            filterRequestForAuthentication(request).stream()
                    .map(this::getAuthentication)
                    .peek(getContext()::setAuthentication)
                    .forEach(authentication -> request.getSession(true).setAttribute("SPRING_SECURITY_CONTEXT", getContext()));
        }
    }

    public Authentication getAuthentication(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }


    public boolean isAlreadyAuthenticated() {
        return getContext().getAuthentication() != null;
    }

    protected abstract Optional<UserDetails> filterRequestForAuthentication(HttpServletRequest request) throws HttpStatusCodeException;
}
