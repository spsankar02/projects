package com.Myproject.Bookingsystem.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
@Table(name="booking_seatbooking")
public class seatbooking {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long seatbookingid;
	@OneToOne
	@JoinColumn(name="seat_id")
	private Seat seatId;
	@ManyToOne
	@JoinColumn(name="ticket_id")
	private Ticket ticketId;
	private String bookingstatus;
	private Integer price;

	public Long getSeatbookingid() {
		return seatbookingid;
	}
	public void setSeatbookingid(Long seatbookingid) {
		this.seatbookingid = seatbookingid;
	}
	public String getBookingstatus() {
		return bookingstatus;
	}
	public void setBookingstatus(String bookingstatus) {
		this.bookingstatus = bookingstatus;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Seat getSeatId() {
		return seatId;
	}
	public void setSeatId(Seat seatId) {
		this.seatId = seatId;
	}
	public Ticket getTicketId() {
		return ticketId;
	}
	public void setTicketId(Ticket ticketId) {
		this.ticketId = ticketId;
	}


}
