package com.Myproject.Bookingsystem.Repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Myproject.Bookingsystem.Entity.Booking;
@Repository
public interface Bookingrepo extends JpaRepository<Booking, Long>{

	Booking findByBookingId(Long long1);

	void save(List<Booking> bookings);

	Object findBybookingId(boolean existsByUserId);

	boolean existsByUserIdUserId(Long userId);

	Booking findByUserIdUserId(Long integer);

	List<Booking> findByUserId(Long userId);

	List<Booking> findAllByBookingId(Long bookingId);

	List<Booking> findAllByUserIdUserId(Long userId);

	List<Booking> findAllByUserId(Long userId);



}
