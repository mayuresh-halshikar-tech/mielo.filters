package org.mielo.filters.authentication;

public interface CookieAuthenticationFilterSupport extends AuthenticationFilterSupport {
    String getCookieName();
}
