package com.jwt.security.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwt.security.service.MyUserDetailsService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtility jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		/*
		 * Authorization=bearer eyJhbGciOiJIUzI1NiJ9.
		 * eyJzdWIiOiJmb28iLCJleHAiOjE1Nzg3NjQ0OTAsImlhdCI6MTU3ODcyODQ5MH0.
		 * 6ans7hmCxal1uwzL6f6rOcdMoNYTqhFolvoYgzjmC_s
		 */
		final String authorizationHeader = request.getHeader("Authorization");

		String username = null;
		String jwt = null;

		if (null != authorizationHeader && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);//Bearer.size()+1=7
			username = jwtUtil.extractUsername(jwt);
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			if (jwtUtil.validateToken(jwt, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(
						userDetails, 
						null, 
						userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(
						new WebAuthenticationDetailsSource()
						.buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			// TEST	UserDetails UserDetailsFromCOntext = (UserDetails)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
			}
		}
		chain.doFilter(request, response);
	}

}
