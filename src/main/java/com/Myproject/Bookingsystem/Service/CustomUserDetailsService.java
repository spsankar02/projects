package com.Myproject.Bookingsystem.Service;

import java.util.Optional;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Myproject.Bookingsystem.Entity.User;
import com.Myproject.Bookingsystem.Repo.Userrepo;


@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private Userrepo userrepo;
	
	@Autowired
	private PasswordEncoder passwordencoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException,NullPointerException{
		Optional<User> user=userrepo.findByUserName(username);
		return user.map(CustomUserDetail::new)
			.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
	}
	
	   public String addUser(User user) {
	        String statement="";
			try {
	        if (isUsernameValid(user.getUserName())) 
	        {
	        	if(isPasswordValid(user.getPassword(), user.getConfirmPassword())) 
	        	{	    	        
	                      user.setPassword(passwordencoder.encode(user.getPassword()));
	                      userrepo.save(user);
	                      statement = "User added successfully";           
		        } 
	        	else {
		            statement = "Invalid password";
		        }
	        }
	        else {
	            statement = "Invalid username";
	        }}
	        catch (Exception e) {
                statement = "Error adding user: " + e.getMessage();
            }
	        return statement;
	    }


		private boolean isUsernameValid(String username) {
	        //User name must be alphanumeric and between 4 to 20 characters
	        String usernameRegex = "^[a-zA-Z0-9]{4,20}$";
	        return Pattern.matches(usernameRegex, username);
	    }

	    private boolean isPasswordValid(String password, String confirmPassword) {
	        //Password must be at least 8 characters long and contain at least one digit and one special character
	        String passwordRegex = "^(?=.*[0-9])(?=.*[!@#$%^&*])(?=\\S+$).{8,}$";
	        return password.equals(confirmPassword) && Pattern.matches(passwordRegex, password);
	    }
	    
}

