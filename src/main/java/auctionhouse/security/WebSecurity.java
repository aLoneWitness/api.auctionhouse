package auctionhouse.security;

import auctionhouse.repositories.UserRepository;
import auctionhouse.security.jwt.JWTAuthenticationFilter;
import auctionhouse.security.jwt.JWTAuthorizationFailureHandler;
import auctionhouse.security.jwt.JWTAuthorizationFilter;
import auctionhouse.security.services.UserDetailsServiceImpl;
import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;


@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    public static final String SECRET = "c35312fb3a7e05b7a44db2326bd29040";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/register";
    public static final String LOGIN_URL = "/login";
    public static final String WEBSOCKET_URL = "/ws/**";
    public static final String WEBSOCKET_INFO = "/ws/info";

    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public WebSecurity(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.GET, WEBSOCKET_URL).permitAll()
                .antMatchers(HttpMethod.POST, WEBSOCKET_URL).permitAll()
                .antMatchers(HttpMethod.GET, WEBSOCKET_INFO).permitAll()
                .antMatchers(HttpMethod.POST, WEBSOCKET_INFO).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), userRepository))
                .exceptionHandling()
                .authenticationEntryPoint(new JWTAuthorizationFailureHandler())
                .and()
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
//        source.registerCorsConfiguration("/**", config.applyPermitDefaultValues());
        config.addAllowedOrigin("http://localhost:8080");
        config.setExposedHeaders(Arrays.asList("Authorization"));
        config.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config.applyPermitDefaultValues());



        return source;
    }

}
