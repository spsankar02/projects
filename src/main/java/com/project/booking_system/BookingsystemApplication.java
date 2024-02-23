package com.Myproject.Bookingsystem;

import org.springframework.boot.SpringApplication;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;




@SpringBootApplication
@ComponentScan("com.Myproject.Bookingsystem")
public class BookingsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingsystemApplication.class, args);
	}

}
