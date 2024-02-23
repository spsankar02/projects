package com.Myproject.Bookingsystem.Service;

import java.security.Key;




import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@SuppressWarnings("deprecation")
@Service
public class Jwtservice {
	
	public static final String SECRET="5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
	public String generatetoken(String userName) {
		Map<String, Object> claims=new HashMap<>();
		return createtoken(claims, userName);
	}

	private String createtoken(Map<String, Object> claims, String userName) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userName)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() +1000*60*30))
				.signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey() {
		byte[] keybytes=Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keybytes);
	}

	public String extractusername(String token) {
		return extractclaim(token, Claims::getSubject);
	}

	private <T> T extractclaim(String token, Function<Claims,T> claimsResolver) {
		final Claims claims=extractAllclaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllclaims(String token) {
		return Jwts
				.parser()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	

	public boolean validatetoken(String token, UserDetails userdetails) {
		final String username=extractusername(token);
		return (username.equals(userdetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractclaim(token, Claims::getExpiration);
	}
	
	  public String resolveToken(HttpServletRequest request) {
	        final String authorizationHeader = request.getHeader("Authorization");
	        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            return authorizationHeader.substring(7); 
	        }
	        return null;
	   }
	  
	public UsernamePasswordAuthenticationToken getAuthentication(String token,UserDetails userdetails) {
		String username=extractusername(token);
		if(username!=null) {
			return new UsernamePasswordAuthenticationToken(username,"",userdetails.getAuthorities());
		}
		return null;
	}


	    

}
