package com.Myproject.Bookingsystem.Service;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import com.Myproject.Bookingsystem.Entity.Booking;
import com.Myproject.Bookingsystem.Entity.Show;
import com.Myproject.Bookingsystem.Entity.Theatre;
import com.Myproject.Bookingsystem.Entity.TokenEntity;
import com.Myproject.Bookingsystem.Entity.User;
import com.Myproject.Bookingsystem.Entity.seatbooking;
import com.Myproject.Bookingsystem.Repo.Theatrerepo;
import com.Myproject.Bookingsystem.Repo.seatbookingrepo;
import com.Myproject.Bookingsystem.Repo.showrepo;
import com.Myproject.Bookingsystem.Repo.Ticketrepo;
import com.Myproject.Bookingsystem.Repo.TokenRepository;
import com.Myproject.Bookingsystem.Repo.Userrepo;
import com.Myproject.Bookingsystem.Repo.Bookingrepo;
import com.Myproject.Bookingsystem.Repo.Paymentrepo;


class ServiceclassTest5 {
    @Mock
    private Theatrerepo theatrerepo;
    @Mock
    private showrepo showRepo;
    @Mock
    private seatbookingrepo seatbookingre;
    @Mock
    private Bookingrepo bookingrepo;
    @Mock
    private Paymentrepo paymentrepo;
    @Mock
    private Ticketrepo ticketrepo;
    @Mock
    private Userrepo userrepo;
    @Mock
    private TokenRepository tokenRepo;
    @Mock
    private JavaMailSender emailSender;



    @InjectMocks
    private Serviceclass service;
    
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	 @Test
	    void createTheatre_TheatreRepoNotNull_SuccessfullyCreatesTheatre() {	        
	        Theatre theatreToSave = new Theatre();
	        theatreToSave.setTheatreId(7000000L);
	        theatreToSave.setTheatreName("Example Theatre");
	        theatreToSave.setAddress("123 Main St");
	        theatreToSave.setCity("Example City");
	        theatreToSave.setState("Example State");
	        theatreToSave.setPostalCode(12345);
	        theatreToSave.setCountry("Example Country");
	        theatreToSave.setContactNo(1234567890);
	        theatreToSave.setSeatCapacity(1000);
	        when(theatrerepo.save(any(Theatre.class))).thenReturn(theatreToSave);
	        Theatre result = service.createTheatre(theatreToSave);
	        verify(theatrerepo, times(1)).save(theatreToSave);
	        assertEquals(result.getTheatreId(), theatreToSave.getTheatreId());
	    }

	    @Test
	    void createTheatre_TheatreRepoIsNull_ThrowsIllegalStateException() {
	        when(theatrerepo.save(any(Theatre.class))).thenThrow(new IllegalArgumentException("Theatre repository is not available"));
	        verify(theatrerepo, never()).save(any(Theatre.class)); 
	    }
	    
	    @Test
	    void testFindBookingSeats() {
	        Show show1 = new Show();
	        show1.setShowId(1L); 
	        Theatre theatre = new Theatre();
	        theatre.setSeatCapacity(100); 
	        show1.setTheatre(theatre);
	        seatbooking seat1 = new seatbooking();
	        seat1.setBookingstatus("booked");
	        seatbooking seat2 = new seatbooking();
	        seat2.setBookingstatus("booked");
	        List<seatbooking> bookedSeats = Arrays.asList(seat1, seat2);
	        when(showRepo.findAll()).thenReturn(Collections.singletonList(show1));
	        when(seatbookingre.findByTicketIdBookingIdShowIdShowId(anyLong())).thenReturn(bookedSeats);
	        List<Show> result = service.findbookingseats();
	        assertEquals(1, result.size());
	        Show updatedShow = result.get(0);
	        assertEquals(98, updatedShow.getAvailableSeats()); 
	        assertEquals(2, updatedShow.getReservedSeats()); 
	    }
	    
	    @Test
	    public void testDeleteBooking_Cancelled() {
	        Booking booking = new Booking();
	        booking.setBookingId(1L);
	        Show show = new Show();
	        show.setDate(LocalDate.now()); 
	        show.setTime(LocalTime.now().plusHours(3));
	        booking.setShowId(show);
	        when(bookingrepo.findById(1L)).thenReturn(Optional.of(booking));
	        String result = service.deletebooking(1L);
	        assertEquals("Booking canceled", result);
	        verify(paymentrepo).deleteByBookingIdBookingId(1L);
	        verify(seatbookingre).deleteByTicketIdBookingIdBookingId(1L);
	        verify(ticketrepo).deleteByBookingIdBookingId(1L);
	        verify(bookingrepo).deleteById(1L);
	    }
	    
	    @Test
	    public void testDeleteBooking_NotAllowed() {
	        Booking booking = new Booking();
	        booking.setBookingId(1L);
	        Show show = new Show();
	        show.setDate(LocalDateTime.now().plusHours(1).toLocalDate()); 
	        show.setTime(LocalTime.now().plusHours(1));
	        booking.setShowId(show);
	        when(bookingrepo.findById(1L)).thenReturn(Optional.of(booking));
	        String result = service.deletebooking(1L);
	        assertEquals("Cancellation not allowed within 2 hours of showtime.", result);
	        verify(paymentrepo, never()).deleteByBookingIdBookingId(1L);
	        verify(seatbookingre, never()).deleteByTicketIdBookingIdBookingId(1L);
	        verify(ticketrepo, never()).deleteByBookingIdBookingId(1L);
	        verify(bookingrepo, never()).deleteById(1L);
	    }
	    
	    @Test
	    public void testDeleteBooking_NotFound() {
	        when(bookingrepo.findById(1L)).thenReturn(Optional.empty());
	        String result = service.deletebooking(1L);
	        assertEquals("Booking ID not found", result);
	        verify(paymentrepo, never()).deleteByBookingIdBookingId(1L);
	        verify(seatbookingre, never()).deleteByTicketIdBookingIdBookingId(1L);
	        verify(ticketrepo, never()).deleteByBookingIdBookingId(1L);
	        verify(bookingrepo, never()).deleteById(1L);
	    }
	    
	    @Test
	    void testEditUser_UserExists() {
	        String username = "existingUser";
	        User existingUser = new User();
	        existingUser.setUserName(username);
	        existingUser.setFirstName("John");
	        existingUser.setLastName("Doe");
	        User editedUser = new User();
	        editedUser.setFirstName("Jane");
	        editedUser.setLastName("Smith");
	        when(userrepo.findByUserName(username)).thenReturn(Optional.of(existingUser));
	        when(userrepo.save(existingUser)).thenReturn(existingUser);
	        User result = service.edituser(editedUser, username);
	        assertEquals("Jane", result.getFirstName());
	        assertEquals("Smith", result.getLastName());
	        verify(userrepo).save(existingUser);
	    }
	    
	    @Test
	    void testEditUser_UserDoesNotExist() {
	        String username = "nonExistingUser";
	        when(userrepo.findByUserName(username)).thenReturn(Optional.empty());
	        assertThrows(IllegalStateException.class, () -> {
	            service.edituser(new User(), username);
	        });
	    }
	    
	    @Test
	    void testEditUser_ExceptionThrown() {
	        String username = "existingUser";
	        when(userrepo.findByUserName(username)).thenThrow(new RuntimeException("Database connection error"));
	        assertThrows(IllegalStateException.class, () -> {
	            service.edituser(new User(), username);
	        });
	    }
	    
	    @Test
	    public void testSendPasswordResetEmail_UserFound() {
	        String email = "user@example.com";
	        User user = new User();
	        user.setEmail(email);
	        when(userrepo.findByEmail(email)).thenReturn(user);
	        service.sendPasswordResetEmail(email);
	        verify(tokenRepo, times(1)).save(any(TokenEntity.class));
	    }
	    
	    @Test
	    public void testSendPasswordResetEmail_UserNotFound() {
	        String email = "nonexistent@example.com";
	        when(userrepo.findByEmail(email)).thenReturn(null);
	        assertThrows(IllegalArgumentException.class, () -> {
                service.sendPasswordResetEmail(email);
            });
	    }
}
