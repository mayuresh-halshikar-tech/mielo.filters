package org.mielo.filters.requestparam;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.mielo.filters.BaseRequestFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import static java.lang.String.format;

@RequiredArgsConstructor
@Component
public class RequestParamFilter extends BaseRequestFilter implements Ordered {

    private final RequestParamFilterSupport support;

    @Override
    protected void filterRequest(HttpServletRequest request) throws HttpStatusCodeException {
        String value = getParamValue(request, support.getParameterName())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, format("Parameter '%s' is required", support.getParameterName())));
        support.consumeValue(value);
    }

    @Override
    public int getOrder() {
        return support.getOrder();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return support.shouldNotFilter(request.getServletPath());
    }
}
