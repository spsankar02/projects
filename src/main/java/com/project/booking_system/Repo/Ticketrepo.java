package com.Myproject.Bookingsystem.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Myproject.Bookingsystem.Entity.Ticket;
@Repository
public interface Ticketrepo extends JpaRepository<Ticket, Long>{

	List<Ticket> findByBookingIdBookingId(Long ticketId);

	List<Ticket> findAllByticketId(Long ticketId);

	boolean existsByBookingId(Object findByUserIdUserId);

	Ticket findByTicketId(Long ticketId);

	void deleteByBookingIdBookingId(Long bookingId);

	List<Ticket> findByticketId(Long bookingId);
}
