package webpagemanagementsystem.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import webpagemanagementsystem.security.JwtAccessDeniedHandler;
import webpagemanagementsystem.security.JwtAuthenticationEntryPoint;
import webpagemanagementsystem.security.filter.CustomJwtFilter;

@Configuration
@EnableWebSecurity // 기본적인 웹보안을 활성화하겠다
@EnableMethodSecurity // @PreAuthorize 어노테이션 사용을 위해 선언
@RequiredArgsConstructor
public class SecurityConfig {

  private final CorsFilter corsFilter;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity, CustomJwtFilter customJwtFilter) throws Exception {
    httpSecurity
        .csrf(AbstractHttpConfigurer::disable) // token을 사용하는 방식이기 때문에 csrf를 disable
        .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

        .headers(headerConfig -> headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
        .sessionManagement(httpSecuritySessionManagementConfigurer ->
            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authorizeHttpRequests(registry -> // api path
            registry.requestMatchers("/user/**").permitAll()
        )
        .authorizeHttpRequests(registry -> // api path
            registry.requestMatchers("/graphiql/**").permitAll()
                .requestMatchers("/graphql/**").permitAll()
        )
        .authorizeHttpRequests(registry ->
            registry.anyRequest().authenticated()) // 나머지 경로는 jwt 인증 해야함
        .exceptionHandling(exceptionConfig ->
            exceptionConfig.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
        )
        .addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }
}
