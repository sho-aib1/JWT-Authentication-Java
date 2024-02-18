package com.jwtauth.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userservice;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //get token

        String requestToken = request.getHeader("Authorization");
        String username = null;
        String token = null;
     /*   Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            System.out.println(headerNames.nextElement());
        }
*/

        // Bearer 2352523sdgsg
        if (requestToken != null && requestToken.startsWith("Bearer ")) {
            token = requestToken.substring(7);

            try {
                //get user from token
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get Jwt token");
            } catch (ExpiredJwtException e) {
                System.out.println("Jwt token has expired");
            } catch (MalformedJwtException e) {
                System.out.println("invalid jwt");
            }
        } else {
            System.out.println("Jwt token does not begin with Bearer");
        }
        //validate token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userservice.loadUserByUsername(username);
            if (this.jwtTokenHelper.validateToken(token, userDetails)) {

                //load user associated from token
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //set spring security
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            } else {
                System.out.println("Invallid JWT Token");
            }
        } else {
            System.out.println("username is null or context is not null");
        }


        filterChain.doFilter(request, response);


    }
}
