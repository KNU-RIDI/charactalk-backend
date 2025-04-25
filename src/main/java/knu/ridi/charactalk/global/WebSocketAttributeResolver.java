package knu.ridi.charactalk.global;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Component
public class WebSocketAttributeResolver implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(
        final ServerHttpRequest request,
        final ServerHttpResponse response,
        final WebSocketHandler wsHandler,
        final Map<String, Object> attributes
    ) {
        final URI uri = request.getURI();
        final MultiValueMap<String, String> params = UriComponentsBuilder.fromUri(uri)
            .build()
            .getQueryParams();
        attributes.putAll(params.asSingleValueMap());
        return true;
    }

    @Override
    public void afterHandshake(
        final ServerHttpRequest request,
        final ServerHttpResponse response,
        final WebSocketHandler wsHandler,
        final Exception exception
    ) {
    }
}
