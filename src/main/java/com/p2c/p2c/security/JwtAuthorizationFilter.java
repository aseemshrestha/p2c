package com.p2c.p2c.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.p2c.p2c.model.Parent;
import com.p2c.p2c.repository.ParentRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@CrossOrigin
public class JwtAuthorizationFilter extends BasicAuthenticationFilter
{
    private final ParentRepository parentRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
         ParentRepository parentRepository)
    {
        super(authenticationManager);
        this.parentRepository = parentRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        String header = request.getHeader(JwtProperties.HEADER_STRING);
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);

    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request)
    {
        String token = request.getHeader(JwtProperties.HEADER_STRING);

        if (token != null) {
            String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()))
                .build()
                .verify(token.replace(JwtProperties.TOKEN_PREFIX, ""))
                .getSubject();

            if (username != null) {
                Parent user = parentRepository.findByUsername(username).get();
                UserPrincipal principal = new UserPrincipal(user);
                UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(username, null, principal.getAuthorities());
                return auth;
            }
            return null;
        }
        return null;
    }
}
