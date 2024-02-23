package com.Myproject.Bookingsystem.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.Myproject.Bookingsystem.Entity.Booking;
import com.Myproject.Bookingsystem.Entity.Payment;
import com.Myproject.Bookingsystem.Entity.User;
import com.Myproject.Bookingsystem.Repo.Bookingrepo;
import com.Myproject.Bookingsystem.Repo.Paymentrepo;
import com.Myproject.Bookingsystem.Repo.Userrepo;

class ServiceclassTest6 {
	   	@Mock
	    private Userrepo userrepo;
	    @Mock
	    private Bookingrepo bookingrepo;
	    @Mock
	    private Paymentrepo paymentrepo;
	    @InjectMocks
	    private Serviceclass service;
	    private List<Booking> bookings;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		    bookings = new ArrayList<>();
	        User user = new User();
	        user.setUserId(1L);
	        user.setUserName("testUser");
	        when(userrepo.findAll()).thenReturn(List.of(user));
	        when(bookingrepo.findAllByUserIdUserId(1L)).thenReturn(bookings);
	}


	@Test
	void testPassvalueforuser() {
        List<Booking> result = service.passvalueforuser("testUser");
        assertEquals(bookings, result);
        List<Booking> result2 = service.passvalueforuser("nonExistingUser");
        assertEquals(0, result2.size());
    }

	@Test
	void testFinduserbookings() {
		bookings.add(new Booking());
        List<Payment> expectedPayments = new ArrayList<>();
        when(paymentrepo.findByBookingIdBookingId(1L)).thenReturn(expectedPayments);
        List<Payment> result = service.finduserbookings();
        System.out.println("expectedPayments"+expectedPayments.size()+" result"+result.size());
        assertEquals(expectedPayments, result);
        bookings = null;
        List<Payment> result2 = service.finduserbookings();
        assertEquals(0, result2.size());
    }

}
