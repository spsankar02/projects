package com.Myproject.Bookingsystem.Entity;

import jakarta.persistence.Column;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
@Table(name="booking_seat")
public class Seat {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="seat_id")
	private Long seatId;
	@Column(name="seat_row")
	private Integer seatRow;
	@Column(name="seat_number")
	private Integer seatNumber;
	@Column(name="seat_type")
	private String seatType;
	//private String seatstatus;
	//	@Column(name="status")
//	private String status;
//	@Column(name="price")
//	private int price;
//	@ManyToOne
//	@JoinColumn(name="show_id")
//	public Show show;
//	@ManyToOne
//	@JoinColumn(name="ticket_id")
//	public Ticket ticket;
//	public Ticket getTicket() {
//		return ticket;
//	}
//	public void setTicket(Ticket ticket) {
//		this.ticket = ticket;
//	}
//	public Show getShow() {
//		return show;
//	}
//	public void setShow(Show show) {
//		this.show = show;
//	}
	public Long getSeatId() {
		return seatId;
	}
	public void setSeatId(Long seatId) {
		this.seatId = seatId;
	}
	public int getSeatRow() {
		return seatRow;
	}
	public void setSeatRow(Integer seatRow) {
		this.seatRow = seatRow;
	}
	public int getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}
	public String getSeatType() {
		return seatType;
	}
	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}
//	public String getSeatstatus() {
//		return seatstatus;
//	}
//	public void setSeatstatus(String seatstatus) {
//		this.seatstatus = seatstatus;
//	}
	
//	public String getStatus() {
//		return status;
//	}
//	public void setStatus(String status) {
//		this.status = status;
//	}
//	public int getPrice() {
//		return price;
//	}
//	public void setPrice(int price) {
//		this.price = price;
//	}

	
}
