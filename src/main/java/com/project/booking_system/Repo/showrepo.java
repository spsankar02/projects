package com.Myproject.Bookingsystem.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Myproject.Bookingsystem.Entity.Show;
@Repository
public interface showrepo extends JpaRepository<Show, Long> {
	Show findByshowId(Show show);
	boolean existsByShowId(Long showId);
	List<Show> findByShowId(Long showId) ;
	Show findAllByshowId(Show show);
	


	
}
