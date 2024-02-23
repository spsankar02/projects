package com.Myproject.Bookingsystem.Entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
@Table(name="booking_booking")
public class Booking {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="booking_id")
	private Long bookingId;
	@Column(name="booking_date")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate bookingDate;
	@Column(name="total_cost")
	private Integer totalCost;
	@ManyToOne
	@JoinColumn(name="user_id")
	private User userId;
	@ManyToOne
	@JoinColumn(name="show_id")
	private Show showId;
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}
	public LocalDate getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(LocalDate localDate) {
		this.bookingDate = localDate;
	}
	public Integer getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Integer totalCost) {
		this.totalCost = totalCost;
	}
	public User getUserId() {
		return userId;
	}
	public void setUserId(User userId) {
		this.userId = userId;
	}
	public Show getShowId() {
		return showId;
	}
	public void setShowId(Show showId) {
		this.showId = showId;
	}
	
}
