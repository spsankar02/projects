package com.Myproject.Bookingsystem.Service;
import static org.hamcrest.CoreMatchers.is;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.Myproject.Bookingsystem.Controller.Usercontroller;
import com.Myproject.Bookingsystem.Entity.Booking;
import com.Myproject.Bookingsystem.Entity.Movie;
import com.Myproject.Bookingsystem.Entity.Show;
import com.Myproject.Bookingsystem.Entity.Ticket;
import com.Myproject.Bookingsystem.Entity.User;
import com.Myproject.Bookingsystem.Entity.seatbooking;
import com.Myproject.Bookingsystem.Repo.Userrepo;
import com.Myproject.Bookingsystem.pojo.Userpojo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
@WebMvcTest(Usercontroller.class)
@ExtendWith(MockitoExtension.class)
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
	@MockBean
	private Jwtservice jwtService;
	@MockBean
	private Userpojo authrequest;

	@MockBean
	private AuthenticationManager authenticationManager;

	@BeforeEach
	void setup() throws Exception {
	    authrequest = new Userpojo();
	    authrequest.setUserName("testUser");
	    authrequest.setPassword("testPassword"); 
	    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
	            .thenReturn(new UsernamePasswordAuthenticationToken(authrequest.getUserName(), authrequest.getPassword(), Collections.emptyList()));    
	    UserDetails userDetails = createUserDetailsWithAuthorities();
	    when(userDetailsService.loadUserByUsername(authrequest.getUserName())).thenReturn(userDetails);
	    when(jwtService.generatetoken(authrequest.getUserName())).thenReturn("sampleToken");
	}

	private UserDetails createUserDetailsWithAuthorities() {
	    List<GrantedAuthority> authorities = new ArrayList<>();
	    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); 
	    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
	    return new org.springframework.security.core.userdetails.User("testUser", "testPassword", authorities);
	}
	private List<GrantedAuthority> extractAuthoritiesFromUserDetails() {
	    UserDetails userDetails = userDetailsService.loadUserByUsername(authrequest.getUserName());
	    return new ArrayList<>(userDetails.getAuthorities());
	}

	private String obtainAccessToken(String username, String password) throws Exception {
	    authrequest.setUserName(username);
	    authrequest.setPassword(password);
	    ResultActions result = mockMvc.perform(post("/auth/generatetoken")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(asJsonString(authrequest))
	            .accept(MediaType.APPLICATION_JSON));
	    String token = result.andReturn().getResponse().getHeader("Authorization");
	    return token.substring(7);
	}

	 private String asJsonString(final Object obj) {
	 try {
		 ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.registerModule(new JavaTimeModule()); 
	        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); 
	        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); 

	        return objectMapper.writeValueAsString(obj);    
	        } catch (Exception e) {
         throw new RuntimeException(e);
     	}
	 }

    @Test
    public void testWelcomeApi() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/welcome"))
                .andExpect(MockMvcResultMatchers.status().isOk())
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
    	 ObjectMapper objectMapper = new ObjectMapper();
    	 String userJson = objectMapper.writeValueAsString(user);

       when(userDetailsService.addUser(any(User.class))).thenReturn("User added successfully");
        mockMvc.perform(post("/auth/adduser")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(userJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("User added successfully"));

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

        ObjectMapper objectMapper = new ObjectMapper();
        String user1Json = objectMapper.writeValueAsString(user1);
        String user2Json = objectMapper.writeValueAsString(user2);

        when(userDetailsService.addUser(any(User.class))).thenReturn("User added successfully");
        mockMvc.perform(post("/auth/adduser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user1Json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("User added successfully"));

        when(userDetailsService.addUser(any(User.class))).thenReturn("Username already exists");
        mockMvc.perform(post("/auth/adduser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user2Json)
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
        
        String token = obtainAccessToken(authrequest.getUserName(), authrequest.getPassword());

        mockMvc.perform(post("/auth/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .with(user(authrequest.getUserName()).authorities(extractAuthoritiesFromUserDetails()))
                .content(asJsonString(movie))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId", is(1)))
                .andExpect(jsonPath("$.title", is("Test Movie")))
                .andExpect(jsonPath("$.certificate", is("U")))
                .andExpect(jsonPath("$.languages", is("English")))
                .andExpect(jsonPath("$.genre", is("Action")))
                .andExpect(jsonPath("$.duration", is("2 hours")));

        verify(service, times(1)).createMovie(any(Movie.class));
    }
    
    
    @Test
    void testCreateBooking() throws Exception {
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
        String token = obtainAccessToken(authrequest.getUserName(), authrequest.getPassword());
        mockMvc.perform(post("/auth/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .with(user(authrequest.getUserName()).authorities(extractAuthoritiesFromUserDetails()))
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
        String token = obtainAccessToken(authrequest.getUserName(), authrequest.getPassword());
        mockMvc.perform(post("/auth/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .with(user(authrequest.getUserName()).authorities(extractAuthoritiesFromUserDetails()))
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
        String token = obtainAccessToken(authrequest.getUserName(), authrequest.getPassword());

        mockMvc.perform(post("/auth/userseatbooking")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .with(user(authrequest.getUserName()).authorities(extractAuthoritiesFromUserDetails()))
                .content(asJsonString(seatBooking)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seatId").value(seatBooking.getSeatId()));
                //.andExpect(jsonPath("$.bookingstatus").value("booking"));

    }
}