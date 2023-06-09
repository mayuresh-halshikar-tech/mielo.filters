package org.mielo.filters.requestparam;

import org.springframework.web.client.HttpStatusCodeException;

public interface RequestParamFilterSupport {
    int getOrder();
    boolean shouldNotFilter(String path);
    void consumeValue(String paramValue) throws HttpStatusCodeException;
    String getParameterName();
}
