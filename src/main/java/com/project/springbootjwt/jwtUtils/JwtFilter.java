package com.project.springbootjwt.jwtUtils;

import com.project.springbootjwt.exception.CustomException;
import com.project.springbootjwt.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger( JwtFilter.class );

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);

        String username = null;
        try {
            username = jwtTokenProvider.getUsernameFromToken(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (token != null && jwtTokenProvider.validateToken(token, userDetails)) {
                    Authentication authentication = getAuthentication(userDetails);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }catch (ExpiredJwtException e) {
            logger.info("JWT Token has expired");
        }catch (IllegalArgumentException e) {
            logger.info("Unable to get JWT token");
        }


        filterChain.doFilter(request, response);
    }
    private Authentication getAuthentication(UserDetails userDetails){
        return new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities() );
    }
    private String resolveToken(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7, header.length());
        }
        return null;
    }
}
