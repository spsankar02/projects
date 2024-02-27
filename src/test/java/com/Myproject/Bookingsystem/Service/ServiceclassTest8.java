package com.Myproject.Bookingsystem.Service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.Myproject.Bookingsystem.Controller.Usercontroller;
import com.Myproject.Bookingsystem.Entity.Booking;
import com.Myproject.Bookingsystem.Entity.Movie;
import com.Myproject.Bookingsystem.Entity.Payment;
import com.Myproject.Bookingsystem.Entity.Show;
import com.Myproject.Bookingsystem.Entity.Theatre;
import com.Myproject.Bookingsystem.Entity.Ticket;
import com.Myproject.Bookingsystem.Entity.User;
import com.Myproject.Bookingsystem.Entity.seatbooking;
import com.Myproject.Bookingsystem.Repo.Userrepo;
import com.Myproject.Bookingsystem.pojo.Userpojo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
@WebMvcTest(Usercontroller.class)
public class ServiceclassTest8 {
	@Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomUserDetailsService userDetailsService;
	@MockBean
	private Serviceclass service;
	@MockBean
	private UserDetails userDetail;
	@MockBean
    private Userrepo userrepo;
	@InjectMocks
	private Jwtservice jwtService;
	@MockBean
	private Userpojo authrequest;
	@MockBean
	private AuthenticationManager authenticationManager;

	private UserDetails createUserDetailsWithUserRole() {
	    List<GrantedAuthority> authorities = new ArrayList<>();
	    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
	    return new org.springframework.security.core.userdetails.User("testUser", "testPassword", authorities);
	}

	private UserDetails createUserDetailsWithAdminRole() {
	    List<GrantedAuthority> authorities = new ArrayList<>();
	    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
	    return new org.springframework.security.core.userdetails.User("testAdmin", "testPassword", authorities);
	}
	private List<GrantedAuthority> extractAuthoritiesFromUserDetails(String username) {
	    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	    if ("testUser".equals(username)) {
	        return new ArrayList<>(userDetails.getAuthorities());
	    } else if ("testAdmin".equals(username)) {
	        List<GrantedAuthority> authorities = new ArrayList<>();
	        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
	        return authorities;
	    } else {
	        return Collections.emptyList(); 
	    }
	}
	
	private String obtainAccessToken(String username, String password) throws Exception {
	    authrequest = new Userpojo();
	    authrequest.setUserName(username);
	    authrequest.setPassword(password);
	    System.out.println("token: "+username);
	    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(new UsernamePasswordAuthenticationToken(authrequest.getUserName(), authrequest.getPassword(), Collections.emptyList()));
	    UserDetails userDetailsUser = createUserDetailsWithUserRole();
	    UserDetails userDetailsAdmin = createUserDetailsWithAdminRole();
	    if(authrequest.getUserName().contains("testUser")) {
	    when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetailsUser);}
	    else {when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetailsAdmin);}
	    ResultActions result = mockMvc.perform(post("/auth/generatetoken")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(asJsonString(authrequest))
	            .accept(MediaType.APPLICATION_JSON));
	    String token = result.andReturn().getResponse().getHeader("Authorization");
	    System.out.println(token);
	    return token.substring(7);
	}

	 private String asJsonString(final Object obj) {
	 try {
		 ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.registerModule(new JavaTimeModule()); 
	        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); 

	        return objectMapper.writeValueAsString(obj);    
	        } catch (Exception e) {
         throw new RuntimeException(e);
     	}
	 }

    @Test
    public void testWelcomeApi() throws Exception {
        mockMvc.perform(get("/auth/welcome"))
                .andExpect(status().isOk())
                .andReturn();
    }
    
    @Test
    public void testRegister_ValidUser_ReturnsSuccessMessage() throws Exception {
        User user = new User();
        user.setUserName("testing");
        user.setPassword("@Testing02");
        user.setConfirmPassword("@Testing02");
        user.setEmail("junit@gmail.com");
        user.setDateOfBirth(new Date());
        user.setGender("M");
        user.setPhone("1234567890");
        user.setAddress("123 Main St.");
        user.setRoles("ROLE_USER");
    	

       when(userDetailsService.addUser(any(User.class))).thenReturn("User added successfully");
        mockMvc.perform(post("/auth/adduser")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(asJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("User added successfully"))
                .andExpect(jsonPath("$", Matchers.is("User added successfully"))); 

        verify(userDetailsService, times(1)).addUser(any(User.class));
        verifyNoMoreInteractions(userDetailsService);
    }
    
    @Test
    public void testRegister_DuplicateUsername_ReturnsErrorMessage() throws Exception {
        User user1 = new User();
        user1.setUserName("testing");
        user1.setPassword("@Testing02");
        user1.setConfirmPassword("@Testing02");
        user1.setEmail("junit@gmail.com");
        user1.setDateOfBirth(new Date());
        user1.setGender("M");
        user1.setPhone("1234567890");
        user1.setAddress("123 Main St.");
        user1.setRoles("ROLE_USER");

        User user2 = new User();
        user2.setUserName("testing");
        user2.setPassword("@Testing03");
        user2.setConfirmPassword("@Testing03");
        user2.setEmail("junit2@gmail.com");
        user2.setDateOfBirth(new Date());
        user2.setGender("M");
        user2.setPhone("1234567891");
        user2.setAddress("123 Main St.");
        user2.setRoles("ROLE_USER");

        when(userDetailsService.addUser(any(User.class))).thenReturn("User added successfully");
        mockMvc.perform(post("/auth/adduser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user1))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("User added successfully"));

        when(userDetailsService.addUser(any(User.class))).thenReturn("Username already exists");
        mockMvc.perform(post("/auth/adduser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user2))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Username already exists"));

        verify(userDetailsService, times(2)).addUser(any(User.class));
        verifyNoMoreInteractions(userDetailsService);
    }
    
	
    @Test
    public void testCreateMovie() throws Exception {
        Movie movie = new Movie();
        movie.setMovieId(1L);
        movie.setTitle("Test Movie");
        movie.setCertificate("U");
        movie.setLanguages("English");
        movie.setGenre("Action");
        movie.setDuration("2 hours");

        when(service.createMovie(any(Movie.class))).thenReturn(movie);
        
        String token = obtainAccessToken("testAdmin", "password");

        mockMvc.perform(post("/auth/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .with(user(authrequest.getUserName()).authorities(extractAuthoritiesFromUserDetails(authrequest.getUserName())))
                .content(asJsonString(movie))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Movie details created successfully"));
        verify(service, times(1)).createMovie(any(Movie.class));
    }
    
    @Test
    public void testCreateMovie_UnauthorizedAccess() throws Exception {
        Movie movie = new Movie();
        movie.setMovieId(1L);
        movie.setTitle("Test Movie");
        movie.setCertificate("U");
        movie.setLanguages("English");
        movie.setGenre("Action");
        movie.setDuration("2 hours");
        when(service.createMovie(any(Movie.class))).thenReturn(movie);
        String token = obtainAccessToken("testUser", "password");

        mockMvc.perform(post("/auth/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .with(user(authrequest.getUserName()).authorities(extractAuthoritiesFromUserDetails(authrequest.getUserName())))
                .content(asJsonString(movie))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        verify(service, never()).createMovie(any(Movie.class));
    }

    
    @Test
    public void testCreateBooking() throws Exception {
        Booking bookingRequest = new Booking();
        bookingRequest.setBookingId(1L);
        bookingRequest.setBookingDate(LocalDate.of(2024, 2, 22));
        bookingRequest.setTotalCost(100);
        User user = new User();
        user.setUserId(1L);
        when(service.passValue(anyString())).thenReturn(user);
        bookingRequest.setUserId(user);
        Show show = new Show();
        show.setShowId(1L); 
        bookingRequest.setShowId(show);

        when(service.createBooking(any(Booking.class))).thenReturn(bookingRequest);
        String token = obtainAccessToken("testUser", "password");
        mockMvc.perform(post("/auth/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .with(user(authrequest.getUserName()).authorities(extractAuthoritiesFromUserDetails(authrequest.getUserName())))
                .content(asJsonString(bookingRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.bookingId").value(1L))
                .andExpect(jsonPath("$.bookingDate").value(bookingRequest.getBookingDate().toString()))
                .andExpect(jsonPath("$.totalCost").value(bookingRequest.getTotalCost()))
                .andExpect(jsonPath("$.userId.userId").value(bookingRequest.getUserId().getUserId()))
                .andExpect(jsonPath("$.showId.showId").value(bookingRequest.getShowId().getShowId()));

        verify(service, times(1)).createBooking(any(Booking.class));
    }
    

    @Test
    public void testCreateBooking_BookingClosed() throws Exception {
        Booking bookingRequest = new Booking();
        bookingRequest.setShowId(new Show());
        
        when(service.createBooking(any(Booking.class))).thenThrow(new IllegalArgumentException("Error while creating booking: Booking closed"));
        String token = obtainAccessToken("testUser","password");
        mockMvc.perform(post("/auth/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .with(user(authrequest.getUserName()).authorities(extractAuthoritiesFromUserDetails(authrequest.getUserName())))
                .content(asJsonString(bookingRequest)))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Booking closed"));
    }
    
    @Test
    public void testCreateseatbooking() throws Exception {
        seatbooking seatBooking = new seatbooking();
        Ticket ticket = new Ticket();
        seatBooking.setPrice(100);       
        when(service.passvalueforseatbooking(any(String.class))).thenReturn(ticket);
        when(service.userseatbooking(any(seatbooking.class))).thenReturn(seatBooking);
        String token = obtainAccessToken("testUser", "password");

        mockMvc.perform(post("/auth/userseatbooking")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .with(user(authrequest.getUserName()).authorities(extractAuthoritiesFromUserDetails(authrequest.getUserName())))
                .content(asJsonString(seatBooking)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seatId").value(seatBooking.getSeatId()));
    }
    
    @Test
    public void testBookingClosed() throws Exception {

        seatbooking seatBooking = new seatbooking();
        Ticket ticket = new Ticket();
        seatBooking.setPrice(100);       
        when(service.passvalueforseatbooking(any(String.class))).thenReturn(ticket);
        when(service.userseatbooking(any(seatbooking.class)))
        .thenThrow(new IllegalArgumentException("Error while creating seatbooking: Seat already booked for the specified show"));
        String token = obtainAccessToken("testUser", "password");

        mockMvc.perform(post("/auth/userseatbooking")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .with(user(authrequest.getUserName()).authorities(extractAuthoritiesFromUserDetails(authrequest.getUserName())))
                .content(asJsonString(seatBooking))) 
                .andExpect(status().isForbidden()); 
    }
    
    @Test
    public void testPaymentSuccess() throws Exception {
        Payment payment = new Payment();
        when(service.passvalueforpayment("username")).thenReturn(new Booking());
        payment.setPaymentStatus("paid");
        when(service.createPayment(any(Payment.class))).thenReturn(payment);
        String token = obtainAccessToken("testUser", "password");
        mockMvc.perform(post("/auth/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .with(user(authrequest.getUserName()).authorities(extractAuthoritiesFromUserDetails(authrequest.getUserName())))
                .content(asJsonString(payment)))                 
        		.andExpect(status().isOk())
                .andExpect(content().string("Your payment is successfull and your seat is booked"));
    }
    
    @Test
    public void testPaymentFailure() throws Exception {
        Payment payment = new Payment();
        when(service.passvalueforpayment("username")).thenReturn(new Booking());
        payment.setPaymentStatus("not paid");
        when(service.createPayment(any())).thenThrow(new IllegalStateException("Your payment is not successful"));
        String token = obtainAccessToken("testUser", "password");

        mockMvc.perform(post("/auth/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .with(user(authrequest.getUserName()).authorities(extractAuthoritiesFromUserDetails(authrequest.getUserName())))
                .content(asJsonString(payment)))                 
        		.andExpect(status().isInternalServerError()) 
                .andExpect(content().string("Error creating payment: Your payment is not successful")); 
    }
    
    @Test
    public void testFindBookingSeats() throws Exception {
        List<Show> shows = new ArrayList<>();
        Show show = new Show();
        shows.add(show);
        when(service.findbookingseats()).thenReturn(shows);
        String token = obtainAccessToken("testAdmin", "password");
        mockMvc.perform(post("/auth/findbookingseats")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .with(user(authrequest.getUserName()).authorities(extractAuthoritiesFromUserDetails(authrequest.getUserName())))
                .accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$[0].showId").value(show.getShowId()))
        		.andExpect(jsonPath("$[0].availableSeats").value(show.getAvailableSeats()))
        		.andExpect(jsonPath("$[0].reservedSeats").value(show.getReservedSeats()));
        verify(service, times(1)).findbookingseats();
    }
    
    @Test
    public void testFindBookingSeats_AccessDenied() throws Exception {
    	String token=obtainAccessToken("testUser","password");
    	mockMvc.perform(post("/auth/findbookingseats")
              .contentType(MediaType.APPLICATION_JSON)
              .header("Authorization", "Bearer " + token)
              .with(user(authrequest.getUserName()).authorities(extractAuthoritiesFromUserDetails(authrequest.getUserName())))
              .accept(MediaType.APPLICATION_JSON))
    		  .andExpect(status().isForbidden());
    }
    
    @Test
    public void testEditUser_Success() throws Exception {
        User user = new User();

        when(service.edituser(any(User.class), anyString())).thenReturn(user);
    	String token=obtainAccessToken("testUser","password");
        mockMvc.perform(post("/auth/edituser")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .with(user(authrequest.getUserName()).authorities(extractAuthoritiesFromUserDetails(authrequest.getUserName())))
                .content(asJsonString(user)))
                .andExpect(status().isOk());
    }
     
    @Test
    public void testEditUser_UserNotFound() throws Exception {
        User user = new User();
        when(service.edituser(any(User.class), anyString())).thenThrow(new IllegalStateException("User not found"));
        String token=obtainAccessToken("testUser","password");
        mockMvc.perform(post("/auth/edituser")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .with(user(authrequest.getUserName()).authorities(extractAuthoritiesFromUserDetails(authrequest.getUserName())))
                .content(asJsonString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User not found"));
    }
    
    @Test
    public void testEditTheatre_Success() throws Exception {
        Theatre theatre = new Theatre();
        Long theatreId = 1L;

        when(service.edittheatre(any(Theatre.class), anyLong())).thenReturn(theatre);
        String token=obtainAccessToken("testAdmin","password");
        mockMvc.perform(post("/auth/edittheatre/{theatreId}", theatreId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .with(user(authrequest.getUserName()).authorities(extractAuthoritiesFromUserDetails(authrequest.getUserName())))
                .content(asJsonString(theatre)))
                .andExpect(status().isOk());
    }

    @Test
    public void testEditTheatre_TheatreNotFound() throws Exception {
        Theatre theatre = new Theatre();
        Long theatreId = 1L;

        when(service.edittheatre(any(Theatre.class), anyLong())).thenThrow(new IllegalStateException("Theatre not found"));
        String token=obtainAccessToken("testAdmin","password");
        mockMvc.perform(post("/auth/edittheatre/{theatreId}", theatreId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .with(user(authrequest.getUserName()).authorities(extractAuthoritiesFromUserDetails(authrequest.getUserName())))
                .content(asJsonString(theatre)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Theatre not found"));
    }
    
    @Test
    public void testCancelBooking_Success() throws Exception {
        Long bookingId = 1L;
        when(service.deletebooking(anyLong())).thenReturn("Booking canceled");
        String token=obtainAccessToken("testUser","password");
        
        mockMvc.perform(delete("/api/booking_system/cancelbooking/{bookingid}", bookingId)
        		.contentType(MediaType.APPLICATION_JSON)
        		.header("Authorization", "Bearer " + token)
        		.with(user(authrequest.getUserName()).authorities(extractAuthoritiesFromUserDetails(authrequest.getUserName())))
        		.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Booking canceled"));
    }

    @Test
    public void testCancelBooking_BookingNotFound() throws Exception {
        Long bookingId = 1L;
        when(service.deletebooking(anyLong())).thenThrow(new IllegalStateException("Booking ID not found"));
        String token=obtainAccessToken("testUser","password");
        
        mockMvc.perform(delete("/api/booking_system/cancelbooking/{bookingid}", bookingId)
        		.contentType(MediaType.APPLICATION_JSON)
        		.header("Authorization", "Bearer " + token)
        		.with(user(authrequest.getUserName()).authorities(extractAuthoritiesFromUserDetails(authrequest.getUserName())))
        		.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Booking ID not found"));
    }

    @Test
    public void testCancelBooking_CancellationNotAllowed() throws Exception {
        Long bookingId = 1L;

        when(service.deletebooking(anyLong())).thenThrow(new IllegalStateException("Cancellation not allowed within 2 hours of showtime."));
        String token=obtainAccessToken("testUser","password");
        
        mockMvc.perform(delete("/api/booking_system/cancelbooking/{bookingid}", bookingId)
        		.contentType(MediaType.APPLICATION_JSON)
        		.header("Authorization", "Bearer " + token)
        		.with(user(authrequest.getUserName()).authorities(extractAuthoritiesFromUserDetails(authrequest.getUserName())))
        		.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Cancellation not allowed within 2 hours of showtime."));
    }
       
}