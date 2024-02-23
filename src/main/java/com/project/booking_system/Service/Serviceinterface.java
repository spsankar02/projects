package com.Myproject.Bookingsystem.Service;

import java.util.List;




import org.springframework.stereotype.Service;

import com.Myproject.Bookingsystem.Entity.Booking;
import com.Myproject.Bookingsystem.Entity.Movie;
import com.Myproject.Bookingsystem.Entity.Payment;
import com.Myproject.Bookingsystem.Entity.Seat;
import com.Myproject.Bookingsystem.Entity.Show;
import com.Myproject.Bookingsystem.Entity.Theatre;
import com.Myproject.Bookingsystem.Entity.Ticket;
import com.Myproject.Bookingsystem.Entity.User;
import com.Myproject.Bookingsystem.Entity.seatbooking;

@Service
public interface Serviceinterface {
	User createUser(User value);
	Movie createMovie(Movie value);
	Theatre createTheatre(Theatre value);
	Show createShow(Show value);
	Ticket createTicket(Ticket value);
	Seat createSeat(Seat value);
	Booking createBooking(Booking value);
	Payment createPayment(Payment value);
	seatbooking userseatbooking(seatbooking value);


	List<User> findAlluser();
	List<Theatre> findAlltheatre();
	List<Show> findAllshow();
	List<Ticket> findAllticket();
	List<Seat> findAllseat();
	List<Movie> findAllmovie();
	List<Booking> findAllbooking();
	List<Payment> findAllpayment();
	List<Ticket> findPrice();
	List<Booking> findbookingamount();
	List<Payment> findpaymentamount();
	List<seatbooking> findseatbooking();
	List<Show> findbookingseats();
	String deletebooking(Long bookingid);
	User passValue(String username);
	Ticket passvalueforseatbooking(String username);
	Booking passvalueforpayment(String username);
	List<Payment> finduserbookings();
	List<Booking> passvalueforuser(String username);
	User edituser(User value, String username);
	Movie editmovie(Movie value, Long movieId);
	Theatre edittheatre(Theatre value, Long theatreId);
	Show editshow(Show value, Long showId);
	Seat editseat(Seat value, Long seatId);
	void sendPasswordResetEmail(String email);
	void resetPassword(String email, String token, String newPassword);
	//Movie createMovie(Movie value, String username);
	
}
