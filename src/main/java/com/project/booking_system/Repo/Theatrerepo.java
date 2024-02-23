package com.Myproject.Bookingsystem.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Myproject.Bookingsystem.Entity.Theatre;
@Repository
public interface Theatrerepo extends JpaRepository<Theatre, Long>{

}
