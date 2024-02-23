package com.Myproject.Bookingsystem.Repo;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Myproject.Bookingsystem.Entity.User;
@Repository
public interface Userrepo extends JpaRepository<User, Long>{

	Optional<User> findByUserName(String string);

	List<User> findByUserId(Long userId);

	List<User> findAllByUserName(String username);

	User findByEmail(String email);


}
