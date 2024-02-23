package com.Myproject.Bookingsystem.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.Myproject.Bookingsystem.Service.CustomUserDetailsService;
import com.Myproject.Bookingsystem.Service.Jwtservice;
import com.Myproject.Bookingsystem.Service.TokenBlacklistService;
import com.Myproject.Bookingsystem.filter.Jwtauthfilter;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@SecurityScheme(
	    name = "Authorization",
	    type = SecuritySchemeType.HTTP,
	    scheme = "bearer",
	    bearerFormat = "JWT",
	    in = SecuritySchemeIn.HEADER
	)

public class SecurityConfig {

	@Autowired
	private TokenBlacklistService tokenblacklistservice;
	@Autowired
	private Jwtauthfilter jwtauthfilter;
	@Autowired
	private Jwtservice jwtservice;
	
	@Bean
	@Qualifier("userdetailsservice")
	public UserDetailsService userdetailservice() {
		return new CustomUserDetailsService();
	}
	@Bean
	public static PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}  
	@Bean
	public SecurityFilterChain securityfilterchain(HttpSecurity http) throws Exception{
		 return http.csrf(AbstractHttpConfigurer::disable)
					.cors(AbstractHttpConfigurer::disable)
					   .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
					   .requestMatchers("/auth/welcome","/auth/generatetoken","/auth/adduser",
							   "/auth/forgotPassword","/auth/resetPassword","/auth/roles",
							   "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
					   .requestMatchers("/api/booking_system/seatbooking","/auth/booking","/auth/ticket","/auth/userseatbooking",
							   "/api/booking_system/findprice","/auth/payment","/auth/finduserbookings"
							   ,"/api/booking_system/cancelbooking/{bookingid}","/auth/edituser").hasAuthority("ROLE_USER")
					   .requestMatchers("api/booking_system/getuser","api/booking_system/seat","/auth/editmovie/{movieId}"
							   ,"auth/findbookingseats","/auth/edittheatre/{theatreId}"
							   ,"/auth/editshow/{showId}","/auth/editseat/{seatId}",
							   "/api/booking_system/theatre","/auth/movie").hasAuthority("ROLE_ADMIN")
					   .anyRequest().authenticated())
					   .sessionManagement()
					   .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					   .and()
					   .authenticationProvider(authenticationProvider())
					   .addFilterBefore(jwtauthfilter,
							   UsernamePasswordAuthenticationFilter.class)
					   .build();					 
	}
	  @Bean
	    public AuthenticationProvider authenticationProvider() { 
	        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); 
	        authenticationProvider.setUserDetailsService(userdetailservice()); 
	        authenticationProvider.setPasswordEncoder(passwordencoder()); 
	        return authenticationProvider; 
	    } 
	  

	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { 
	        return config.getAuthenticationManager(); 
	    } 
	    
	    @Bean
	    public Jwtauthfilter jwttokenfilter() {
	    	return new Jwtauthfilter(jwtservice,jwtauthfilter,tokenblacklistservice);
	    }
	
}
