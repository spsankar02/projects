package com.Myproject.Bookingsystem.Controller;


import java.util.List;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Myproject.Bookingsystem.Entity.Booking;
import com.Myproject.Bookingsystem.Entity.Movie;
import com.Myproject.Bookingsystem.Entity.Payment;
import com.Myproject.Bookingsystem.Entity.Seat;
import com.Myproject.Bookingsystem.Entity.Show;
import com.Myproject.Bookingsystem.Entity.Theatre;
import com.Myproject.Bookingsystem.Entity.Ticket;
import com.Myproject.Bookingsystem.Entity.User;
import com.Myproject.Bookingsystem.Entity.seatbooking;
import com.Myproject.Bookingsystem.Service.CustomUserDetailsService;
import com.Myproject.Bookingsystem.Service.Jwtservice;
import com.Myproject.Bookingsystem.Service.Serviceinterface;
import com.Myproject.Bookingsystem.Service.TokenBlacklistService;
import com.Myproject.Bookingsystem.pojo.Userpojo;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name="Authorization")
@RequestMapping("/auth")
public class Usercontroller {
	private String result;
	private String username;
	@Autowired
    private Serviceinterface service;
	@Autowired
	private AuthenticationManager authenticationmanager;
	@Autowired
	private CustomUserDetailsService userservice;
	@Autowired
	private Jwtservice jwtservice;
	@Autowired
	private TokenBlacklistService tokenblacklistservice;
	



	@GetMapping("/welcome")
	public String welcome() {
		return "welcome";
	}
	@PostMapping("/adduser")
	public String register(@RequestBody User user) {
		return userservice.addUser(user);
	}
	@PostMapping("/user/userprofile")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String userprofile() {
			return "welcome to the userprofile";
	}
	@PostMapping("/admin/adminprofile")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String adminprofile() {
		return "welcome to the adminprofile";
	}
	@PostMapping("/ticket")
	public Ticket method(@RequestBody Ticket value) {
		return service.createTicket(value);
	}
	@PostMapping("/movie")
	public ResponseEntity<Movie>  method(@RequestBody Movie value){
		return  ResponseEntity.ok()
				.body(service.createMovie(value));		
	}
	@PostMapping("/booking")
	public ResponseEntity<Object> method(@RequestBody Booking value) {
	    try {
	        service.passValue(username);
	        Booking createdBooking = service.createBooking(value);
	        return ResponseEntity.ok().body(createdBooking);
	    } catch (IllegalStateException | IllegalArgumentException e) {
	        if ("Error while creating booking: Booking closed".equals(e.getMessage())) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Booking closed");
	        } else {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
	        }
	    }
	}

	
	@PostMapping("/userseatbooking")
	public seatbooking method16(@RequestBody seatbooking value){
		service.passvalueforseatbooking(username);
		return service.userseatbooking(value);
	}
	
	@PostMapping("/payment")
	public Payment method(@RequestBody Payment value) {
		service.passvalueforpayment(username);
		return service.createPayment(value);
	}
	@PostMapping("/findbookingseats")
	public ResponseEntity<List<Show>> method14(){
		return ResponseEntity.ok()
			.body(service.findbookingseats());
	}

	
	@PostMapping("/finduserbookings")
	public List<Payment> method21(){
		service.passvalueforuser(username);
		return service.finduserbookings();
	}
	
	@PostMapping("/edituser")
	public User method22(@RequestBody User value) {
		return service.edituser(value,username);
	}
	
	@PostMapping("/editmovie/{movieId}")
	public Movie method23(@RequestBody Movie value,@PathVariable Long movieId) {
		return service.editmovie(value,movieId);
	}
	
	@PostMapping("/edittheatre/{theatreId}")
	public Theatre method24(@RequestBody Theatre value,@PathVariable Long theatreId) {
		return service.edittheatre(value,theatreId);
	}
	
	@PostMapping("/editshow/{showId}")
	public Show method25(@RequestBody Show value,@PathVariable Long showId) {
		return service.editshow(value,showId);
	}
	
	@PostMapping("/editseat/{seatId}")
	public Seat method26(@RequestBody Seat value,@PathVariable Long seatId) {
		return service.editseat(value,seatId);
	}


    @PostMapping("/forgotPassword")
    public ResponseEntity<Object> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isEmpty()) {
            return new ResponseEntity<>("Email address is required", HttpStatus.BAD_REQUEST);
        }
        try {
            service.sendPasswordResetEmail(email);
            return new ResponseEntity<>("Password reset link sent successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
       }
    


        @PostMapping("/resetPassword")
        public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> details ) 
        {
            try {
            	String email=details.get("email");
            	String token=details.get("token");
            	String newPassword=details.get("newPassword");
            	service.resetPassword(email, token, newPassword);
                return ResponseEntity.ok("Password reset successfully");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
    

        @PostMapping("/generatetoken")
        public ResponseEntity<String> authenticatAndGetToken(@RequestBody Userpojo authrequest) {
            try {
                authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(
                        authrequest.getUserName(), authrequest.getPassword()));
                result = jwtservice.generatetoken(authrequest.getUserName());
        		username=jwtservice.extractusername(result);
                return ResponseEntity.ok()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + result)
                        .body(result);
            } catch (AuthenticationException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid username or password");
            }
        }

	
	@PostMapping("/logout")
	public String logout() {
		@SuppressWarnings("unused")
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String token=result;
		System.out.println("Token is: "+token);
		if(token!=null) {
			tokenblacklistservice.blacklisttoken(token);
		}
		return "logout successfully";
	}
	
	
}
