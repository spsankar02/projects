package com.Myproject.Bookingsystem.Service;



import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.Myproject.Bookingsystem.Entity.Booking;
import com.Myproject.Bookingsystem.Entity.Show;
import com.Myproject.Bookingsystem.Entity.User;
import com.Myproject.Bookingsystem.Repo.Userrepo;
import com.Myproject.Bookingsystem.Repo.showrepo;

class ServiceclassTest4 {
	@Mock
    private Userrepo userrepo;
	@Mock
    private showrepo showRepo;
	@InjectMocks
    private Serviceclass service;

    private User de;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void setUp() {
    	MockitoAnnotations.initMocks(this);
    	de=new User();
    	when(userrepo.findByUserName("testUser")).thenReturn(Optional.of(de));
    }
    @Test
    void testPassValue_UserFound() {
        User result = service.passValue("testUser");
        assertEquals(de, result);
    }
    
    @Test
    void testPassValue_UserNotFound() {
    	String username="nonexistinguser";
    	when(userrepo.findByUserName(username)).thenReturn(Optional.empty());
    	IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.passValue(username);
        });
        assertTrue(exception.getMessage().contains("User not found with username:"));
    }
    
    @Test
    void testCreateBooking_ShowNotFound() {
        Booking booking = new Booking();
        booking.setShowId(new Show());
        when(showRepo.findById(any())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> {
            service.createBooking(booking);
        });
    }
 

}
