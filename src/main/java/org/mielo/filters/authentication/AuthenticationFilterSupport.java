package org.mielo.filters.authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.HttpStatusCodeException;

public interface AuthenticationFilterSupport {
    Integer getOrder();
    UserDetails authenticate(String token) throws HttpStatusCodeException;
}
