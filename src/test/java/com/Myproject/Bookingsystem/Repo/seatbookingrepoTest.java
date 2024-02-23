package com.Myproject.Bookingsystem.Repo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.Myproject.Bookingsystem.Entity.Seat;
import com.Myproject.Bookingsystem.Entity.Ticket;
import com.Myproject.Bookingsystem.Entity.seatbooking;

class seatbookingrepoTest {
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

	 	@Mock
	    private seatbookingrepo seatBookingRepoMock;


	    @Test
	    void testFindByTicketIdBookingIdShowIdShowId() {
	        Long showId = 1L;
	        List<seatbooking> seatBookings = new ArrayList<>();
	        when(seatBookingRepoMock.findByTicketIdBookingIdShowIdShowId(showId)).thenReturn(seatBookings);
	        List<seatbooking> result = seatBookingRepoMock.findByTicketIdBookingIdShowIdShowId(showId);
	        assertEquals(seatBookings, result);
	    }

	    @Test
	    void testFindByTicketIdTicketId() {
	        Long ticketId = 1L;
	        List<seatbooking> seatBookings = new ArrayList<>();
	        when(seatBookingRepoMock.findByTicketIdTicketId(ticketId)).thenReturn(seatBookings);
	        List<seatbooking> result = seatBookingRepoMock.findByTicketIdTicketId(ticketId);
	        assertEquals(seatBookings, result);
	    }

	    @Test
	    void testExistsBySeatIdAndTicketId() {
	        Seat seat = new Seat();
	        Ticket ticket = new Ticket();
	        when(seatBookingRepoMock.existsBySeatIdAndTicketId(seat, ticket)).thenReturn(true);
	        assertTrue(seatBookingRepoMock.existsBySeatIdAndTicketId(seat, ticket));
	    }

	    @Test
	    void testDeleteByTicketIdBookingIdBookingId() {
	        Long bookingId = 1L;
	        doNothing().when(seatBookingRepoMock).deleteByTicketIdBookingIdBookingId(bookingId);
	        seatBookingRepoMock.deleteByTicketIdBookingIdBookingId(bookingId);
	        verify(seatBookingRepoMock, times(1)).deleteByTicketIdBookingIdBookingId(bookingId);
	    }
	    
}
