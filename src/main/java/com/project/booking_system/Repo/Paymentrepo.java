package com.Myproject.Bookingsystem.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Myproject.Bookingsystem.Entity.Payment;
@Repository
public interface Paymentrepo extends JpaRepository<Payment, Long>{

	List<Payment> findByBookingIdBookingId(Long bookingId);

	void deleteByBookingIdBookingId(Long bookingId);

}
