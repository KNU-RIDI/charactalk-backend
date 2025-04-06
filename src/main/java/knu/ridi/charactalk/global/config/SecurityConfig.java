package knu.ridi.charactalk.global.config;

import knu.ridi.charactalk.auth.service.GoogleOAuth2UserService;
import knu.ridi.charactalk.auth.controller.AuthorizationRequestRedirectResolver;
import knu.ridi.charactalk.auth.controller.OAuth2LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final GoogleOAuth2UserService userService;
    private final OAuth2LoginSuccessHandler loginSuccessHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AuthorizationRequestRedirectResolver authorizationRequestRedirectResolver;

    public static final List<String> clients = List.of(
        "http://localhost:3000",
        "https://charactalk.site"
    );

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-resources",
                    "/v3/api-docs/**",
                    "/actuator/**",
                    "/oauth2/**",
                    "/auth/**",
                    "/login/**"
                )
                .permitAll()
                .anyRequest()
                .hasAnyRole("USER")
            )
            .oauth2Login(oauth2 -> oauth2.redirectionEndpoint(redirection ->
                    redirection.baseUri("/login/oauth2/code/{registrationId}"))
                .userInfoEndpoint(userInfoEndpoint ->
                    userInfoEndpoint.userService(userService)
                )
                .loginProcessingUrl("/auth/login")
                .authorizationEndpoint(authorization ->
                    authorization.authorizationRequestResolver(authorizationRequestRedirectResolver)
                )
                .successHandler(loginSuccessHandler))
            .logout(config -> config.logoutSuccessUrl("/"))
            .exceptionHandling(httpSecurityExceptionHandling ->
                httpSecurityExceptionHandling.authenticationEntryPoint(authenticationEntryPoint));
      return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(clients);
        configuration.setAllowedMethods(Arrays.asList(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PATCH.name(),
            HttpMethod.PUT.name(),
            HttpMethod.DELETE.name()
        ));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
