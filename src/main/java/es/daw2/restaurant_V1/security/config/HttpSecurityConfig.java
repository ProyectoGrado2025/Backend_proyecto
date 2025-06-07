package es.daw2.restaurant_V1.security.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import es.daw2.restaurant_V1.security.config.filters.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf( csrfConfig -> csrfConfig.disable() )
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests( authReqConfig -> buildRequestMatchers(authReqConfig) )
                .build();
    }

    private void buildRequestMatchers(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
        /*
         * Autorización de endpoints para el ADMINISTRADOR
         */
        authReqConfig.requestMatchers(HttpMethod.GET,"/admin/**")
                .hasRole("ADMINISTRATOR");
        authReqConfig.requestMatchers(HttpMethod.POST,"/admin/**")
                .hasRole("ADMINISTRATOR");
        authReqConfig.requestMatchers(HttpMethod.PUT,"/admin/**")
                .hasRole("ADMINISTRATOR");
        authReqConfig.requestMatchers(HttpMethod.DELETE,"/admin/**")
                .hasRole("ADMINISTRATOR");
        /*
         * Autorización de endpoints para TRABAJADORES
         */
        authReqConfig.requestMatchers(HttpMethod.GET,"/worker/**")
                .hasRole("WORKER");
        authReqConfig.requestMatchers(HttpMethod.POST,"/worker/**")
                .hasRole("WORKER");
        authReqConfig.requestMatchers(HttpMethod.PUT,"/worker/**")
                .hasRole("WORKER");
        authReqConfig.requestMatchers(HttpMethod.DELETE,"/worker/**")
                .hasRole("WORKER");
        
        /*
         * Autorización de endpoints públicos para CLIENTES
         */
        authReqConfig.requestMatchers(HttpMethod.GET, "/client/**").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST, "/client/**").permitAll();
        authReqConfig.requestMatchers(HttpMethod.PUT, "/client/**").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET, "/stats/**").permitAll();
        authReqConfig.anyRequest().authenticated();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(Arrays.asList("*"));
        // config.setAllowedOrigins(Arrays.asList(""));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
