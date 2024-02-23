package com.Myproject.Bookingsystem.Entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookingTest {

    private Booking booking;
    private User user;
    private Show show;

    @BeforeEach
    void setUp() {
    	
        booking = new Booking();
        
        user = mock(User.class);
        show = mock(Show.class);
    }

    @Test
    void testGettersAndSetters() {
    	
        booking.setBookingId(1L);
        booking.setBookingDate(LocalDate.of(2024, 2, 8));
        booking.setTotalCost(100);
        booking.setUserId(user);
        booking.setShowId(show);

        assertEquals(1L, booking.getBookingId());
        assertEquals(LocalDate.of(2024, 2, 8), booking.getBookingDate());
        assertEquals(100, booking.getTotalCost());
        assertEquals(user, booking.getUserId());
        assertEquals(show, booking.getShowId());
    }
}
