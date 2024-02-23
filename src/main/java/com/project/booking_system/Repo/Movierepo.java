package com.Myproject.Bookingsystem.Repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Myproject.Bookingsystem.Entity.Movie;
@Repository
public interface Movierepo extends JpaRepository<Movie, Long>{


}
