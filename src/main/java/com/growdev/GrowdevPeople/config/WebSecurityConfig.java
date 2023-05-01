package com.growdev.GrowdevPeople.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig { // não precisamos mais extender.
    @Autowired
    RsaKeyProperties rsaKeyProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors()
                .and()
                .csrf().disable()
                .authorizeHttpRequests(authorizeConfig -> authorizeConfig
                        .requestMatchers("/api/login", "/api/users/post", "/api/jobs/get", "/api/jobs/filter", "/api/company/get","/resend/confirm-email","/email/reset-password/","/confirm-email","/reset-password")
                        .permitAll()

                        // COMPANY
                        .requestMatchers("/api/company/post").hasAnyAuthority("GROWDEV")
                        .requestMatchers("/api/company/get/all", "/api/company/get/paged").permitAll()

                        // KEYWORD
                        .requestMatchers("/api/keyword/get/all", "/api/keyword/get/paged").permitAll()
                        .requestMatchers("/api/keyword/post").hasAnyAuthority("GROWDEV")

                        // CURRICULUM

                        // JOB
                        .requestMatchers("/api/jobs/post", "/api/jobs/update", "/api/jobs/find/responsible").hasAnyAuthority("PARCEIRO", "GROWDEV")
                        .requestMatchers("/api/jobs/add/talent").hasAnyAuthority("PARCEIRO", "CANDIDATO", "GROWDEV")
                        .requestMatchers("/api/jobs/get/search").permitAll()

                        // ROLE
                        .requestMatchers("/api/roles/*").hasAuthority("GROWDEV")

                        // TALENT BANK
                        .requestMatchers("/api/talent-bank/get", "/api/talent-bank/filter", "/api/talent-bank/find/byjob")
                        .hasAnyAuthority("PARCEIRO", "GROWDEV")
                        .requestMatchers("api/find/talent-bank", "api/find/talent-bank/get/search", "/api/talent-bank/{email}", "/api/talent-bank/post", "/api/talent-bank/add-to-job")
                        .hasAnyAuthority("CANDIDATO", "PARCEIRO", "GROWDEV")

                        // USER
                        .requestMatchers("/api/users/get", "api/users/get/partners").hasAuthority("GROWDEV")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((ex) -> ex
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler()))
                .httpBasic(Customizer.withDefaults()) //uso do login padrão do spring security
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeyProperties.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeyProperties.publicKey()).privateKey(rsaKeyProperties.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter authConverter = new JwtAuthenticationConverter();
        authConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return authConverter;
    }
}