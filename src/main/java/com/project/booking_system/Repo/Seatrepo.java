package com.Myproject.Bookingsystem.Repo;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Myproject.Bookingsystem.Entity.Seat;

@Repository
public interface Seatrepo extends JpaRepository<Seat, Long>{

	//List<Seat> findByTicketTicketId(Integer ticketId);

	//List<Seat> findByShowShowId(Integer showId);

	List<Seat> findBySeatId(Long integer);

	Seat findAllBySeatId(Long seatId);


	//List<Seat> findAllById(Integer ticketId);

	//List<Seat> findAllByShowShowId(Integer showId);
	//List<Seat> findByTicketBookingShowShowId(Integer showId);

	//List<Seat> findByTicketBookingBookingId(Integer bookingid);


}
