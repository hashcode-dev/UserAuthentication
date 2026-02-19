package com.hashcode.userauthentication.configuration;

import com.hashcode.userauthentication.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Getter
public class AuthTokenFilter extends OncePerRequestFilter {

    private JWTUtil jwtUtil;
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public AuthTokenFilter(JWTUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    public AuthTokenFilter() {

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            String username = getJwtUtil().getUsernameFromToken(jwt);
            UserDetails userDetails = getUserDetailsService().loadUserByUsername(username);

            if (jwt != null && getJwtUtil().validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: [{}]", e);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.equals("/api/auth/signup") || path.equals("/api/auth/signin") || path.equals("/api/auth/roles") || path.equals("/api/auth/createUserRoles");
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
