package knu.ridi.charactalk.auth.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class ResponseEntityAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException
    ) throws IOException {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = "로그인이 필요합니다.";

        ResponseEntity<String> responseEntity = ResponseEntity.status(status).body(message);

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        HttpHeaders headers = responseEntity.getHeaders();
        headers.forEach((key, value) -> {
            String headerValue = String.join(",", value);
            response.addHeader(key, headerValue);
        });

        try (OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8)) {
            objectMapper.writeValue(writer, responseEntity.getBody());
        }
    }
}
