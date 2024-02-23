package com.Myproject.Bookingsystem.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import com.Myproject.Bookingsystem.Repo.seatbookingrepo;
import com.Myproject.Bookingsystem.Repo.Bookingrepo;
import com.Myproject.Bookingsystem.Repo.Paymentrepo;
import com.Myproject.Bookingsystem.Repo.Userrepo;

class ServiceclassTest7 {
	 
    @Mock
    private Userrepo userrepo;    
    @Mock
    private Bookingrepo bookingrepo;
    @Mock
    private Paymentrepo paymentrepo;  
    @Mock
    private seatbookingrepo seatbookingre;
    @InjectMocks
    private Serviceclass service;
    private Booking latestBooking;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		 	User user = new User();
	        user.setUserId(1L);
	        user.setUserName("testUser");
	        when(userrepo.findAll()).thenReturn(List.of(user));
	        latestBooking = new Booking();
	        latestBooking.setBookingId(1L);
	        when(bookingrepo.findAllByUserIdUserId(1L)).thenReturn(List.of(latestBooking));
	}


	@Test
	void testPassvalueforpayment() {
	     Booking result = service.passvalueforpayment("testUser");
	     assertEquals(latestBooking, result);
	}

	@Test
	void testCreatePayment() {
		Booking mockBooking = new Booking();
	    mockBooking.setBookingId(1L); 
	    service.latestBooking = mockBooking;
	    Payment payment = new Payment();
	    payment.setPaymentStatus("paid");
        when(bookingrepo.findByBookingId(anyLong())).thenReturn(mockBooking);
	    when(seatbookingre.findByTicketIdBookingIdBookingId(1L)).thenReturn(new ArrayList<>());
	    Payment result = service.createPayment(payment);
	    assertEquals(LocalDate.now(), result.getPaymentDate());
	    assertEquals(LocalDate.now(), result.getBookingId().getBookingDate());
	}

}
