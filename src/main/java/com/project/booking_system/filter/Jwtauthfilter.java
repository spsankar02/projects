package com.Myproject.Bookingsystem.filter;

import java.io.IOException;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Myproject.Bookingsystem.Service.CustomUserDetailsService;
import com.Myproject.Bookingsystem.Service.Jwtservice;
import com.Myproject.Bookingsystem.Service.TokenBlacklistService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Jwtauthfilter extends OncePerRequestFilter{
	public Jwtauthfilter() {
		
	}
	public Jwtauthfilter(Jwtservice jwtservice, Jwtauthfilter jwtauthfilter, TokenBlacklistService tokenblacklistservice) {
		this.tokenblacklistservice=tokenblacklistservice;
		this.jwtauthfilter=jwtauthfilter;
		this.jwtservice=jwtservice;
	}
	@SuppressWarnings("unused")
	private Jwtauthfilter jwtauthfilter;
	@Autowired
	private Jwtservice jwtservice;
	@Autowired
	private CustomUserDetailsService customuserdetailsservice;
	public Jwtauthfilter(CustomUserDetailsService customuserdetailsservice) {
		this.customuserdetailsservice=customuserdetailsservice;
	}
	@Autowired
	private TokenBlacklistService tokenblacklistservice;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader=request.getHeader("Authorization");
		String token=jwtservice.resolveToken(request);
		String username=null;
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			token=authHeader.substring(7);
			username=jwtservice.extractusername(token);
		}
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null
				&& token!=null && !tokenblacklistservice.istokenblacklisted(token)) {
			System.out.println(username);
			System.out.println("hi");
			//String suma=customuserdetailsservice.loadUserByUsername(username);
			//System.out.println(customuserdetailsservice.loadUserByUsername(username));
			UserDetails userdetails=customuserdetailsservice.loadUserByUsername(username);
			if(jwtservice.validatetoken(token,userdetails)) {
				UsernamePasswordAuthenticationToken authtoken=new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
				authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authtoken);
			}
		
		}
		
		filterChain.doFilter(request, response);
	}

}
