package com.Myproject.Bookingsystem.Repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Myproject.Bookingsystem.Entity.Seat;
import com.Myproject.Bookingsystem.Entity.Ticket;
import com.Myproject.Bookingsystem.Entity.seatbooking;
@Repository
public interface seatbookingrepo extends JpaRepository<seatbooking, Long>{

	List<seatbooking> findByTicketIdBookingIdShowIdShowId(Long showId);
	
	List<seatbooking> findByTicketIdTicketId(Long ticketId);

	boolean existsBySeatIdAndTicketId(Seat seatId, Ticket ticket);

	List<seatbooking> findByTicketIdBookingIdBookingId(Long bookingId);

	List<seatbooking> findAllBySeatIdSeatIdAndTicketIdBookingIdShowIdShowIdAndBookingstatus(Long seatId,
			Long showId, String string);

	void deleteByTicketIdBookingIdBookingId(Long bookingId);





}
