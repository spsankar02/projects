package com.Myproject.Bookingsystem.Controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Myproject.Bookingsystem.Entity.Booking;
import com.Myproject.Bookingsystem.Entity.Movie;
import com.Myproject.Bookingsystem.Entity.Payment;
import com.Myproject.Bookingsystem.Entity.Seat;
import com.Myproject.Bookingsystem.Entity.Show;
import com.Myproject.Bookingsystem.Entity.Theatre;
import com.Myproject.Bookingsystem.Entity.Ticket;
import com.Myproject.Bookingsystem.Entity.User;
import com.Myproject.Bookingsystem.Entity.seatbooking;
import com.Myproject.Bookingsystem.Service.Serviceinterface;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name="Authorization")
@RequestMapping("/api/booking_system")
public class Mycontroller {	
	@Autowired
	private Serviceinterface service;

	@PostMapping("/user")
	public User method(@RequestBody User value){		
		return service.createUser(value);
	}
	

	
	@PostMapping("/theatre")
	public Theatre method(@RequestBody Theatre value) {
		return service.createTheatre(value);
	}
	
	@PostMapping("/show")
	public Show method(@RequestBody Show value) {
		return service.createShow(value);
	}
	

	@PostMapping("/seat")
	public Seat method(@RequestBody Seat value) {
		return service.createSeat(value);
	}
	
	
	@PostMapping("/getuser")
	public List<User> method1(){
		return service.findAlluser();
	}
	
	@GetMapping("/gettheatre")
	public List<Theatre> method2(){
		return service.findAlltheatre();
	}
	
	@GetMapping("/getshow")
	public List<Show> method3(){
		return service.findAllshow();
	}
	
	@GetMapping("/getticket")
	public List<Ticket> method4(){
		return service.findAllticket();
	}
	
	@GetMapping("/getseat")
	public List<Seat> method6(){
		return service.findAllseat();
	}
	
	@GetMapping("/getmovie")
	public List<Movie> method7(){
		return service.findAllmovie();
	}
	
	@GetMapping("/getbooking")
	public List<Booking> method8(){
		return service.findAllbooking();
		
	}
	
	@GetMapping("/getpayment")
	public List<Payment> method9(){
		return service.findAllpayment();
	}
	@PostMapping("/findprice")
	public List<Ticket> method10(){
		return service.findPrice();
	}
	@PostMapping("/findbookingamount")
	public List<Booking> method11(){
		return service.findbookingamount();
	}
	@PostMapping("/findpaymentamount")
	public List<Payment> method12(){
		return service.findpaymentamount();
	}
	@PostMapping("/seatbooking")
	public List<seatbooking> method13(){
		return service.findseatbooking();
	}

	@DeleteMapping("/cancelbooking/{bookingid}")
	public ResponseEntity<Object> method15(@PathVariable Long bookingid){
		try {
		return ResponseEntity.ok().body(service.deletebooking(bookingid));}
		catch(IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
	}
	
	

	
//	@PostMapping("/sendemail")
//	public String sendmail(@RequestBody notification details) {
//		String status=service.sendsimplemail(details);
//		return status;
//	}
//		
}
