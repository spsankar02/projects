package com.Myproject.Bookingsystem.Service;


import java.util.Date;
import java.time.LocalDate;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Myproject.Bookingsystem.Entity.Booking;
import com.Myproject.Bookingsystem.Entity.Movie;
import com.Myproject.Bookingsystem.Entity.Payment;
import com.Myproject.Bookingsystem.Entity.Seat;
import com.Myproject.Bookingsystem.Entity.Show;
import com.Myproject.Bookingsystem.Entity.Theatre;
import com.Myproject.Bookingsystem.Entity.Ticket;
import com.Myproject.Bookingsystem.Entity.TokenEntity;
import com.Myproject.Bookingsystem.Entity.User;
import com.Myproject.Bookingsystem.Entity.seatbooking;
import com.Myproject.Bookingsystem.Repo.Bookingrepo;
import com.Myproject.Bookingsystem.Repo.Movierepo;
import com.Myproject.Bookingsystem.Repo.Paymentrepo;
import com.Myproject.Bookingsystem.Repo.Seatrepo;
import com.Myproject.Bookingsystem.Repo.Theatrerepo;
import com.Myproject.Bookingsystem.Repo.Ticketrepo;
import com.Myproject.Bookingsystem.Repo.TokenRepository;
import com.Myproject.Bookingsystem.Repo.Userrepo;
import com.Myproject.Bookingsystem.Repo.seatbookingrepo;
import com.Myproject.Bookingsystem.Repo.showrepo;

import jakarta.transaction.Transactional;

@Service
public class Serviceclass implements Serviceinterface {
//    @Autowired
//    private Ticket ticket;
	@Autowired
	private Theatrerepo theatreRepo;
	@Autowired
	private seatbookingrepo seatbookingre;
	@Autowired
	private showrepo showRepo;
	
	@Autowired
	private Ticketrepo ticketrepo;
	
	@Autowired
	private  Userrepo userrepo;
	
	@Autowired
	private  Seatrepo seatrepo;

	@Autowired
	private Movierepo movierepo;
	@Autowired
	private Bookingrepo bookingrepo;
	@Autowired
	private Paymentrepo paymentrepo;
	@Autowired
	private TokenRepository tokenRepository; 
	@Autowired
	private JavaMailSender emailSender;
	@Autowired
	private PasswordEncoder passwordencoder;
	
//	@Autowired
//	private JavaMailSender javaMailSender;
//	@Value("${spring.mail.username}")
//	private String sender;
	
	


//	public Serviceclass(Userrepo userRepoMock, showrepo showRepoMock, Bookingrepo bookingRepoMock) {
//	}
//
//
//	public Serviceclass() {
//	}
//
//
//	public Serviceclass(Object object) {
//	}
//	
//	public Serviceclass(Movierepo movieRepoMock) {
//		
//	}

//	public Serviceclass(Userrepo userrepomock) {
//		this.userrepo=userrepomock;
//	}
//
//
//	public Serviceclass(Theatrerepo theatreRepositoryMock) {
//		this.theatreRepo=theatreRepositoryMock;
//	}
//	public List<String> getUserRoles(String username) {
//	    List<User> users = userrepo.findAllByUserName(username);
//	    List<String> roles = new ArrayList<>();
//
//	    for (User user : users) {
//	        if (user != null && user.getRoles() != null) {
//	            roles.add(user.getRoles());
//	        }
//	    }
//	    
//	    return roles;
//	}

	@Override
	public User createUser(User value) {
	    if (userrepo != null) {
	        return userrepo.save(value);
	    } else {
	        throw new IllegalStateException("User repository is not available");
	    }
	}
	
	User de;
	public User passValue(String username) {
		if(userrepo!=null) {
	        Optional<User> userOptional = userrepo.findByUserName(username);
	        de = userOptional.orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
	        return de;
	    
		}else {
			throw new IllegalArgumentException("User repository is not available");
		}
	}

	@Override
	public Movie createMovie(Movie value) {
	      return movierepo.save(value);
	}

	
	@Override
	public Theatre createTheatre(Theatre value) {
		if(theatreRepo!=null) {
		return theatreRepo.save(value);
		}else {
			throw new IllegalStateException("Theatre repository is not available");
		}
	}
	
	@Override
	public Show createShow(Show value) {
		if(showRepo!=null) {
		return showRepo.save(value);
		}else {
			throw new IllegalStateException("Show repository is not available");
		}
	}
	
	@Override
	public Ticket createTicket(Ticket value) {
		if(ticketrepo!=null) {
		Booking bookinginformations=bookingrepo.findByBookingId(value.getBookingId().getBookingId());
		value.setBookingId(bookinginformations);
		return ticketrepo.save(value);
		}else {
			throw new IllegalStateException("Ticket repository is not available");
		}
	}

	@Override
	public Seat createSeat(Seat value) {
		if(seatrepo!=null) {
		return seatrepo.save(value);
		}else {
			throw new IllegalStateException("Seat repository is not available");
		}
	}
	
//	List<Booking> be;
//	@Override
//	public Booking passvalueforseatticketbooking(String username) {
//		List<User> user=userrepo.findAll();
//		user.forEach(u->{
//			if(u.getUserName().equals(username)) {
//				if (bookingrepo.existsByUserIdUserId(u.getUserId())) {
//	                be = bookingrepo.findByUserId(u.getUserId());
//				}
//			}
//		});
//		return be.get(0);
//	}
	


	@Override
	public Booking createBooking(Booking value) {
	    try {
	        if (de == null || de.getUserId() == null) {
	            throw new IllegalStateException("User ID is null");
	        }
	        
	        if (showRepo == null) {
	            throw new IllegalStateException("Show repository is not available");
	        }

	        Show showInformation = showRepo.findById(value.getShowId().getShowId())
	                .orElseThrow(() -> new IllegalArgumentException("Show not found with ID: " + value.getShowId().getShowId()));

	        LocalDateTime showDateTime = LocalDateTime.of(showInformation.getDate(), showInformation.getTime());
	        LocalDateTime now = LocalDateTime.now();
	        long minutesDifference = ChronoUnit.MINUTES.between(now, showDateTime);

	        if (minutesDifference < -30) {
	            throw new IllegalArgumentException("Booking closed");
	        }

	        value.setUserId(de);
	        value.setShowId(showInformation);
	        Booking savedBooking = bookingrepo.save(value);

	        Ticket ticket = new Ticket();
	        ticket.setBookingId(savedBooking);
	        ticketrepo.save(ticket);

	        return savedBooking;
	    } catch (IllegalStateException | IllegalArgumentException e) {
	        throw new IllegalArgumentException("Error while creating booking: " + e.getMessage());
	        //System.out.println(e);
	    }
	}
	
	

//	@Override
//	public Booking create(Booking value) {
//	    value.setUserId(de.get(0));
//	    Show showInformation = showRepo.findById(value.getShowId().getShowId())
//	            .orElseThrow(() -> new IllegalArgumentException("Show not found with ID: " + value.getShowId().getShowId()));
//	    value.setShowId(showInformation);
//	    LocalDateTime showDateTime = LocalDateTime.of(value.getShowId().getDate(), value.getShowId().getTime());
//	    System.out.println(showDateTime);
//	    System.out.println(LocalDateTime.now());
//	    long hoursDifference = ChronoUnit.HOURS.between(LocalDateTime.now(),showDateTime);
//	    System.out.println(hoursDifference);
//	    if (hoursDifference < 0) {
//	        throw new IllegalArgumentException("Booking closed");
//	    }
//	    return bookingrepo.save(value);
//	}

	
//	@Override
//	public Ticket passvalueforseatbooking(String username) {
//	    List<User> users = userrepo.findAll();
//
//	    users.forEach(user -> {
//	        if (user.getUserName().equals(username)) {
//	            if (bookingrepo.existsByUserIdUserId(user.getUserId())) {
//	                List<Booking> booking = bookingrepo.findAllByUserIdUserId(user.getUserId());
//	                Integer bookingId=booking.stream()
//	                .map(Booking ::getBookingId)
//	                .max(Comparator.naturalOrder()).get();
//	                te = ticketrepo.findAllByticketId(bookingId);
//	            }
//	        }
//	    });
//        return te.get(0);
//	}

	
	List<Ticket> te;
	public Ticket passvalueforseatbooking(String username) {
	    try {
	        Optional<User> userOptional = userrepo.findAll().stream()
	                                    .filter(user -> user.getUserName().equals(username))
	                                    .findFirst();
	        if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            Optional<Booking> latestBookingOptional = bookingrepo.findAllByUserIdUserId(user.getUserId()).stream()
	                                    .max(Comparator.comparing(Booking::getBookingId));
	            if (latestBookingOptional.isPresent()) {
	                Booking latestBooking = latestBookingOptional.get();
	                te = ticketrepo.findByBookingIdBookingId(latestBooking.getBookingId());
	                if (!te.isEmpty()) {
	                    return te.get(0);
	                }
	                else {
	                	return null;
	                }
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	
	@Override
	public seatbooking userseatbooking(seatbooking value) {
	    try {
	        if (value.getSeatId() == null) {
	            throw new IllegalArgumentException("Seat ID is null");
	        }
	        Long seatId = value.getSeatId().getSeatId();
            if (!te.isEmpty()) {
	        Long showId = te.get(0).getBookingId().getShowId().getShowId();
	        List<seatbooking> existingBookings = seatbookingre.findAllBySeatIdSeatIdAndTicketIdBookingIdShowIdShowIdAndBookingstatus(seatId, showId, "Booked");
	        if (!existingBookings.isEmpty()) {
	            throw new IllegalArgumentException("Seat already booked for the specified show");
	        }}
	        Seat seatInformations = seatrepo.findAllBySeatId(value.getSeatId().getSeatId());
	        value.setSeatId(seatInformations);
	        value.setBookingstatus("booking");
	        value.setTicketId(te.get(0));
	        seatbookingre.save(value);
	        List<Seat> seatBooking = seatrepo.findBySeatId(value.getSeatId().getSeatId());
	        Integer normalPrice = te.get(0).getBookingId().getShowId().getTicketPrice();
	        Double specialPrice = normalPrice * 1.2;
	        Integer price = (int) Math.round(specialPrice);
	        seatBooking.forEach(e -> {
	            int newPrice = e.getSeatType().equalsIgnoreCase("First class") ? price : normalPrice;
	            value.setPrice(newPrice);
	            seatbookingre.save(value);
	        });
	        ticketrepo.findAllByticketId(te.get(0).getTicketId()).forEach(ticket -> {
	            List<seatbooking> seats = seatbookingre.findByTicketIdTicketId(te.get(0).getTicketId());
	            int seatCost = seats.stream().mapToInt(seatbooking::getPrice).sum();
	            ticket.setPrice(seatCost);
	            ticket.setSeatCount(seats.size());
	        });
	        bookingrepo.findAllByBookingId(te.get(0).getBookingId().getBookingId()).forEach(booking -> {
	            List<Ticket> tickets = ticketrepo.findByBookingIdBookingId(booking.getBookingId());
	            int ticketCost = tickets.stream().mapToInt(Ticket::getPrice).sum();
	            booking.setTotalCost(ticketCost);
	        });
	        Ticket ticketInformation = ticketrepo.findByTicketId(te.get(0).getTicketId());
	        value.setTicketId(ticketInformation);
	        return seatbookingre.save(value);
	    } catch (IllegalArgumentException e) {
	        throw new IllegalArgumentException("Error while creating seatbooking: " + e.getMessage());

	    }
	}
	
	Booking latestBooking;
	@Override
	public Booking passvalueforpayment(String username) {
	    Optional<User> userOptional = userrepo.findAll().stream()
	            .filter(user -> user.getUserName().equals(username))
	            .findFirst();

	    if (userOptional.isPresent()) {
	        User user = userOptional.get();
	        latestBooking = bookingrepo.findAllByUserIdUserId(user.getUserId()).stream()
	                .max(Comparator.comparing(Booking::getBookingId)).get();
	    }
	            return latestBooking;
	}
	
	@Override
	public Payment createPayment(Payment value) {
	    try {
	        Booking bookingInformation = bookingrepo.findByBookingId(latestBooking.getBookingId());
	        if (bookingInformation == null) {
	            throw new IllegalStateException("Booking information not found");
	        }
	        if (!value.getPaymentStatus().equalsIgnoreCase("paid")) {
	            throw new IllegalStateException("Your payment is not successful");
	        }
	        bookingInformation.setBookingDate(LocalDate.now());
	        bookingrepo.save(bookingInformation);
	        List<seatbooking> seatInformation = seatbookingre.findByTicketIdBookingIdBookingId(latestBooking.getBookingId());
	        seatInformation.forEach(s -> s.setBookingstatus("Booked"));
	        seatbookingre.saveAll(seatInformation);
	        value.setPaymentDate(LocalDate.now());
	        value.setBookingId(bookingInformation);
	        value.setAmount(bookingInformation.getTotalCost());
	        return paymentrepo.save(value);
	    } catch (IllegalStateException e) {
	        throw new IllegalStateException(e.getMessage());
	    }
	}


	
	@Override
	public List<User> findAlluser() {
		return userrepo.findAll();
	}
	
	@Override
	public List<Theatre> findAlltheatre() {
		return theatreRepo.findAll();
	}
	
	@Override
	public List<Show> findAllshow() {
		return showRepo.findAll();
	}
	
	@Override
	public List<Ticket> findAllticket() {
		return ticketrepo.findAll();
	}

	@Override
	public List<Seat> findAllseat() {
		return seatrepo.findAll();
	}

	@Override
	public List<Movie> findAllmovie() {
		return movierepo.findAll();
	}

	@Override
	public List<Booking> findAllbooking() {
		return bookingrepo.findAll();
	}

	@Override
	public List<Payment> findAllpayment() {
		return paymentrepo.findAll();
	}

//	@Override
//	public List<Ticket> findPrice() {
//		List<Ticket> ticket=ticketrepo.findAll();
//		for(Ticket tic:ticket) {
//			List<Seat> seats= seatrepo.findByTicketTicketId(tic.getTicketId());
//			Integer seatCost = 0;
//			for(Seat seat : seats) {
//				seatCost = seatCost + seat.getPrice();
//			}
//			Integer totalPrice = seatCost+ tic.getShow().getTicketPrice()* seats.size(); 
//			tic.setPrice(totalPrice);
//			tic.setSeatCount(seats.size());	
//			ticketrepo.save(tic);
//		}
//		return ticket;
//	}
	
	@Override
	public List<Ticket> findPrice() {
	    return ticketrepo.findAll().stream()
	            .peek(ticket -> {
	                List<seatbooking> seats = seatbookingre.findByTicketIdTicketId(ticket.getTicketId());
	                int seatCost = seats.stream().mapToInt(seatbooking::getPrice).sum();
	                //int totalPrice = seatCost + ticket.getBookingId().getShowId().getTicketPrice() * seats.size();
	                ticket.setPrice(seatCost);
	                ticket.setSeatCount(seats.size());
	            })
	            .map(ticketrepo::save)
	            .collect(Collectors.toList());
	}

	
//	public List<Booking> findbookingamount() {
//		List<Booking> booking=bookingrepo.findAll();
//		for(Booking tics:booking) {
//			List<Ticket> ticket=ticketrepo.findByBookingBookingId(tics.getBookingId());
//			Integer totalprice =0;
//			for(Ticket price:ticket) {
//				totalprice=totalprice+price.getPrice();
//				tics.setTotalCost(totalprice);
//				bookingrepo.save(tics);
//			}
//		}
//		return booking;
//	}
	
	@Override
	public List<Booking> findbookingamount() {
	    List<Booking> bookings = bookingrepo.findAll();
	    List<Payment> payment = paymentrepo.findAll();
	    bookings.forEach(booking -> {
	        List<Ticket> tickets = ticketrepo.findByBookingIdBookingId(booking.getBookingId());
	        int totalCost = tickets.stream()
	                               .mapToInt(Ticket::getPrice)
	                               .sum();
	        booking.setTotalCost(totalCost);
	        boolean ispaid=payment.stream()
	        		.anyMatch(p -> p.getBookingId().getBookingId().equals(booking.getBookingId()) &&
                            p.getPaymentStatus().equalsIgnoreCase("paid"));
	        	if(ispaid) {
	        		booking.setBookingDate(LocalDate.now());
	        	}
		    bookingrepo.save(booking);

	    });
	    return bookings;
	}
	

//	@Override
//	public List<Payment> findpaymentamount() {
//		List<Payment> payment=paymentrepo.findAll();
//		for(Payment payid:payment) {
//			payid.setAmount(payid.getBooking().getTotalCost());
//			paymentrepo.save(payid);
//		}
//		return payment;
//	}
	
	@Override
	public List<Payment> findpaymentamount() {
	    List<Payment> payments = paymentrepo.findAll();

	    payments.forEach(payment -> {
	        payment.setAmount(payment.getBookingId().getTotalCost());
	      boolean isPaid = payments.stream()
                .anyMatch(p -> p.getPaymentStatus().equalsIgnoreCase("paid"));
	    if(isPaid) {
	    	payment.setPaymentDate(LocalDate.now());
	    }
	    });
	    paymentrepo.saveAll(payments);
	    return payments;
	}


//	@Override
//	public List<Seat> findseatbooking() {
//		List<Seat> seat=seatrepo.findAll();
//		for(Seat ticketbookingid:seat) {
//			List<Payment> paymentbookingid=paymentrepo.
//					findByBookingBookingId(ticketbookingid.getTicket().getBooking().getBookingId());
//			//System.out.println(ticketbookingid.getTicket().getTicketId());
//			for(Payment paymentstatus:paymentbookingid) {
//				if(paymentstatus.getPaymentStatus().equalsIgnoreCase("paid")) {
//					ticketbookingid.setStatus("booked");
//					}
//				}
//			seatrepo.save(ticketbookingid);
//			}
//		return seat;
//	}
	
	@Override
	public List<seatbooking> findseatbooking() {
	    List<seatbooking> seats = seatbookingre.findAll();

	    seats.forEach(seat -> {
	        List<Payment> paymentBookingId = paymentrepo.findByBookingIdBookingId(
	                seat.getTicketId().getBookingId().getBookingId());
	        
	        LocalDate showDate = seat.getTicketId().getBookingId().getShowId().getDate();
	        LocalTime showTime = seat.getTicketId().getBookingId().getShowId().getTime();
	        
	        if (showDate.isBefore(LocalDate.now()) && showTime.isBefore(LocalTime.now())) {
	            seat.setBookingstatus("Booking closed");
	        } else {
	            boolean isPaid = paymentBookingId.stream()
	                    .anyMatch(payment -> payment.getPaymentStatus().equalsIgnoreCase("paid"));

	            seat.setBookingstatus(isPaid ? "Booked" : "Not booked");
	        }
	    });

	    seatbookingre.saveAll(seats);
	    return seats;
	}



//	@Override
//	public List<Show> findbookingseats() {
//		List<Show> show=showRepo.findAll();
//		//List<Seat> seat=seatrepo.findAll();
//		for(Show showid:show) {
//			List<Seat> allshowid=seatrepo.findAllByShowShowId(showid.getShowId());
//			  int count=0;
//			for(Seat si:allshowid)
//			{
//				if(si.getStatus().equalsIgnoreCase("booked"))
//				{
//					count=count+1;
//				}
//				//System.out.println(count);
//			}
//			int status=showid.getTheatre().getSeatCapacity()-count;
//			showid.setAvailableSeats(status);
//			showid.setReservedSeats(count);
//			showRepo.save(showid);
//		}
//		return show;
//	}
	
	@Override
	public List<Show> findbookingseats() {
	    List<Show> shows = showRepo.findAll();
	    shows.forEach(show -> {
	        try {
	            List<seatbooking> allShowSeats = seatbookingre.findByTicketIdBookingIdShowIdShowId(show.getShowId());
	            long bookedSeatsCount = allShowSeats.stream()
	                    .filter(seat -> seat.getBookingstatus().equalsIgnoreCase("booked"))
	                    .count();
	            int availableSeats = show.getTheatre().getSeatCapacity() - (int) bookedSeatsCount;
	            show.setAvailableSeats(availableSeats);
	            show.setReservedSeats((int) bookedSeatsCount);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    });
	    return showRepo.saveAll(shows);
	}

	
	
		@Transactional
	    public String deletebooking(Long bookingId) {
	        try {
	            Optional<Booking> optionalBooking = bookingrepo.findById(bookingId);

	            if (optionalBooking.isPresent()) {
	                Booking booking = optionalBooking.get();
	                LocalDateTime showDateTime = LocalDateTime.of(booking.getShowId().getDate(), booking.getShowId().getTime());
	                long hoursDifference = ChronoUnit.HOURS.between(LocalDateTime.now(), showDateTime);
	                if (hoursDifference >= 2) {
	                	paymentrepo.deleteByBookingIdBookingId(bookingId);
	                	seatbookingre.deleteByTicketIdBookingIdBookingId(bookingId);
	                    ticketrepo.deleteByBookingIdBookingId(bookingId);
	                    bookingrepo.deleteById(bookingId);
	                    return "Booking canceled";
	                } else {
	                    throw new IllegalStateException ("Cancellation not allowed within 2 hours of showtime.");
	                }
	            } else {
	                throw new IllegalStateException("Booking ID not found");
	            }
	        } catch (IllegalStateException e) {
	        	throw new IllegalStateException(e.getMessage());
	        }
	    }

		List<Booking> bookings;
		@Override
		public List<Booking> passvalueforuser(String username) {
			 Optional<User> userOptional = userrepo.findAll().stream()
			            .filter(user -> user.getUserName().equals(username))
			            .findFirst();

			    if (userOptional.isPresent()) {
			        User user = userOptional.get();
			        bookings=bookingrepo.findAllByUserIdUserId(user.getUserId());
			    }
			return bookings;
		}
		
		
		@Override
		public List<Payment> finduserbookings() {
		    List<Payment> payments = new ArrayList<>();
		    try {
		    	if(bookings!=null && !bookings.isEmpty()) {
		    		bookings.forEach(s->{
						List<Payment> payment=paymentrepo.findByBookingIdBookingId(s.getBookingId());
						payments.addAll(payment);
					}); 
					return payments;
		    	}
		    	else {
		    		throw new IllegalStateException("User has no bookings");
		    	}
		    }
		    catch(IllegalStateException e) {
		    	throw new IllegalStateException(e.getMessage());
		    }
		}
		

		@Override
		public User edituser(User value,String username) {
			 try {
			        Optional<User> user = userrepo.findByUserName(username);
			        if (user.isPresent()) {
			        	User user1=user.get();
			            Optional.ofNullable(value.getFirstName()).ifPresent(user1::setFirstName);
			            Optional.ofNullable(value.getLastName()).ifPresent(user1::setLastName);
			            Optional.ofNullable(value.getDateOfBirth()).ifPresent(user1::setDateOfBirth);
			            Optional.ofNullable(value.getEmail()).ifPresent(user1::setEmail);
			            Optional.ofNullable(value.getGender()).ifPresent(user1::setGender);
			            Optional.ofNullable(value.getAddress()).ifPresent(user1::setAddress);
			            Optional.ofNullable(value.getPhone()).ifPresent(user1::setPhone);
			            userrepo.save(user1);
			            return user1;
			        } else {
			            throw new IllegalArgumentException("User with Username " + value.getUserName() + " not found");
			        }
			    } catch (IllegalArgumentException e) {
			        throw new IllegalStateException("An unexpected error occurred", e);
			    }
		}
		@Override
		public Movie editmovie(Movie value, Long movieId) {
			try {
				Optional<Movie> movie=movierepo.findById(movieId);
				if(movie!=null) {
					Movie movie1=movie.get();
					Optional.ofNullable(value.getCertificate()).ifPresent(movie1::setCertificate);
					Optional.ofNullable(value.getDuration()).ifPresent(movie1::setDuration);
					Optional.ofNullable(value.getGenre()).ifPresent(movie1::setGenre);
					Optional.ofNullable(value.getLanguages()).ifPresent(movie1::setLanguages);
					Optional.ofNullable(value.getTitle()).ifPresent(movie1::setTitle);
					movierepo.save(movie1);
					return movie1;
				}else {
					throw new IllegalArgumentException("Movie not found");
				}
			}
			catch(IllegalArgumentException e) {
				throw new IllegalStateException("An unexpected error occurred", e);
			}
		}


		@Override
		public Theatre edittheatre(Theatre value, Long theatreId) {
			try {
				Optional<Theatre> theatre=theatreRepo.findById(theatreId);
				if(theatre!=null) {
					Theatre theatre1=theatre.get();
					Optional.ofNullable(value.getTheatreName()).ifPresent(theatre1::setTheatreName);
					Optional.ofNullable(value.getAddress()).ifPresent(theatre1::setAddress);
					Optional.ofNullable(value.getCity()).ifPresent(theatre1::setCity);
					Optional.ofNullable(value.getCountry()).ifPresent(theatre1::setCountry);
					Optional.ofNullable(value.getPostalCode()).ifPresent(theatre1::setPostalCode);
					Optional.ofNullable(value.getSeatCapacity()).ifPresent(theatre1::setSeatCapacity);
					Optional.ofNullable(value.getState()).ifPresent(theatre1::setState);
					Optional.ofNullable(value.getContactNo()).ifPresent(theatre1::setContactNo);
					theatreRepo.save(theatre1);
					return theatre1;
				}else {
					throw new IllegalArgumentException("Theatre not found");
				}
			}
			catch(IllegalArgumentException e) {
				throw new IllegalStateException("An unexpected error occurred", e);
			}
		}

		@Override
		public Show editshow(Show value, Long showId) {
			try {
				Optional<Show> show=showRepo.findById(showId);
				if(show!=null) {
					Show show1=show.get();
					Optional.ofNullable(value.getDate()).ifPresent(show1::setDate);
					Optional.ofNullable(value.getTicketPrice()).ifPresent(show1::setTicketPrice);
					Optional.ofNullable(value.getTime()).ifPresent(show1::setTime);
					Optional.ofNullable(value.getMovie()).ifPresent(show1::setMovie);
					Optional.ofNullable(value.getTheatre()).ifPresent(show1::setTheatre);
					showRepo.save(show1);
					return show1;
				}else {
					throw new IllegalArgumentException("Show not found");
				}
			}
			catch(IllegalArgumentException e) {
				throw new IllegalStateException("An unexpected error occurred", e);
			}		
		}


		@Override
		public Seat editseat(Seat value, Long seatId) {
			try {
				Optional<Seat> seat=seatrepo.findById(seatId);
				if(seat!=null) {
					Seat seat1=seat.get();
					Optional.ofNullable(value.getSeatType()).ifPresent(seat1::setSeatType);
					Optional.ofNullable(value.getSeatNumber()).ifPresent(seat1::setSeatNumber);
					Optional.ofNullable(value.getSeatRow()).ifPresent(seat1::setSeatRow);
					seatrepo.save(seat1);
					return seat1;
				}else {
					throw new IllegalArgumentException("Seat not found");
				}
			}
			catch(IllegalArgumentException e) {
				throw new IllegalStateException("An unexpected error occurred", e);
			}		
		}


		public void sendPasswordResetEmail(String email) {
		    User user = userrepo.findByEmail(email);
		    if (user == null) {
		        throw new IllegalArgumentException("User with email " + email + " not found");
		    }
		    try {
		        String token = generateToken();
		        TokenEntity tokenEntity = new TokenEntity();
		        tokenEntity.setUserId(user);
		        tokenEntity.setToken(token);
		        tokenEntity.setExpiryDate(getExpiryDate());
		        tokenRepository.save(tokenEntity);
		        String resetLink = "http://yourdomain.com/resetPassword?token=" + token;
		        sendEmail(email, "Password Reset", "To reset your password, click the link below:\n" + resetLink);
		    } catch (IllegalArgumentException e) {
		        throw new IllegalStateException("Error sending password reset email", e);
		    }
		}


		    private String generateToken() {
		    	 String uuid = UUID.randomUUID().toString();
				return uuid;
		    }

		    private Date getExpiryDate() {
		    	  Calendar calendar = Calendar.getInstance();
		          calendar.add(Calendar.HOUR_OF_DAY, 1); 
		          return (Date) calendar.getTime();
		    }

		    private void sendEmail(String to, String subject, String text) {
 		        SimpleMailMessage message = new SimpleMailMessage();
		        message.setTo(to);
		        message.setSubject(subject);
		        message.setText(text);

		        emailSender.send(message);
		    }


		    @Override
		    public void resetPassword(String email, String token, String newPassword) {
		        User user = userrepo.findByEmail(email);
		        if (user == null) {
		            throw new IllegalArgumentException("User with email " + email + " not found");
		        }
		        
		        TokenEntity tokenEntity = tokenRepository.findByToken(token);
		        if (tokenEntity == null || !tokenEntity.getUserId().equals(user) || tokenEntity.isExpired()) {
		            throw new IllegalArgumentException("Invalid or expired reset token");
		        }
		        
		        if (!isPasswordValid(newPassword)) {
		            throw new IllegalArgumentException("Invalid password format. Password must be at least 8 characters long and contain at least one digit and one special character.");
		        }
		        
		        user.setPassword(passwordencoder.encode(newPassword));
		        userrepo.save(user);
		        tokenRepository.delete(tokenEntity);
		    }

		    private boolean isPasswordValid(String newPassword) {
		        //Password must be at least 8 characters long and contain at least one digit and one special character
		        String passwordRegex = "^(?=.*[0-9])(?=.*[!@#$%^&*])(?=\\S+$).{8,}$";
		        return Pattern.matches(passwordRegex,newPassword);
		    }
		    
			
		

//	public List<Seat> userseatbooking1() {
//		List<Seat> seats=seatrepo.findAll();
//		seats.forEach(s->{
//			if(s.getStatus().equalsIgnoreCase("booking")) {
//				List<Ticket> tickets=ticketrepo.findAll();
//				Ticket selectedticket=tickets.stream()
//						.filter(se->se.getPrice().equals(0))
//						.findFirst().get();
//				s.setTicket(selectedticket);
//				System.out.println(selectedticket);
//				tickets.stream()
//	            .peek(ticket -> {
//	                List<Seat> seatss = seatrepo.findByTicketTicketId(ticket.getTicketId());
//	                int seatCost = seatss.stream().mapToInt(Seat::getPrice).sum();
//	                int totalPrice = seatCost + ticket.getBooking().getShow().getTicketPrice() * seatss.size();
//	                ticket.setPrice(totalPrice);
//	                ticket.setSeatCount(seatss.size());
//	            })
//	            .map(ticketrepo::save)
//	            .collect(Collectors.toList());
//			}
//			
//		});
//		seatrepo.saveAll(seats);
//		return seats;
//	}

//	@Override
//	public List<Seat> userseatbooking(){
//		List<Seat> seats=seatrepo.findAll();
//		seats.forEach(s->{
//			if(s.getStatus().equalsIgnoreCase("booking")) {
//				ticket.setShow(s.getShow());
//				ticket.setPrice(null);
//			}
//		
//		}}

//	@Override
//	public String register(User user) {
//		String statement="";
//		if(user.getPassword().equals(user.getConfirmPassword())) {
//			userrepo.save(user);
//			statement="registered";
//		}
//		else {
//			statement="password match not found";
//		}
//		return statement;
//	}
//
//	@Override
//	public String login(Userpojo user) {
//		return null;
//	}
//
//	@Override
//	public User save(Userpojo userpojo) {
//		return null;
//	}

//	@Override
//	public String sendsimplemail(notification details) {
//		
//		try {
//		    SimpleMailMessage mailMessage = new SimpleMailMessage();
//		    mailMessage.setFrom(sender);
//		    mailMessage.setTo(details.getRecipient());
//		    mailMessage.setText(details.getMsgBody());
//		    mailMessage.setSubject(details.getSubject());
//		    
//		    javaMailSender.send(mailMessage);
//		    return "Message sent successfully...";
//		}
//		catch(Exception e){
//			return "Error while sending mail...";
//		}
//	}

//	@Override
//	public String login(Userpojo userpojo) {
//		Optional<User> user=userrepo.findByUserName(userpojo.getUserName());
//		String statement="";
//		if(user.get().getUserName().equals(userpojo.getUserName()))
//		{
//			if(user.get().getPassword().equals(userpojo.getPassword())) {
//				statement="Login success";
//			}
//			else {
//				statement="invalid password";
//			}
//		}
//		else {
//			statement="invalid userid";
//		}
//		return statement;
//	}
}

