package auctionhouse.security.jwt;

import auctionhouse.entities.User;
import auctionhouse.repositories.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static auctionhouse.security.WebSecurity.HEADER_STRING;
import static auctionhouse.security.WebSecurity.TOKEN_PREFIX;
import static auctionhouse.security.WebSecurity.SECRET;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);

        if(header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationFilter = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationFilter);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if(token != null){
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            Optional<User> userEntity = this.userRepository.findByUsername(user);
            if(userEntity.isPresent()) {
                return new UsernamePasswordAuthenticationToken(userEntity.get(), null, new ArrayList<>());
            }
            return null;
        }

        return null;
    }
}
