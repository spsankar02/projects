package com.Myproject.Bookingsystem.Service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Myproject.Bookingsystem.Entity.User;
import com.Myproject.Bookingsystem.Repo.Userrepo;

public class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService userService;

    @Mock
    private Userrepo userrepo;
    
    @Mock
    private PasswordEncoder passwordEncoder;

    @SuppressWarnings("deprecation")
	@BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddUser_ValidUser() {
        User user = new User();
        user.setUserName("validUser123");
        user.setPassword("Password1@");
        user.setConfirmPassword("Password1@");
        when(userrepo.save(any(User.class))).thenReturn(user);
        String result = userService.addUser(user);
        verify(userrepo, times(1)).save(user);
        assertEquals("User added successfully", result);
    }

    @Test
    public void testAddUser_InvalidUsername() {
        User user = new User();
        user.setUserName("zoe");
        user.setPassword("Password1@");
        user.setConfirmPassword("Password1@");
        String result = userService.addUser(user);
        verify(userrepo, never()).save(any(User.class));
        assertEquals("Invalid username", result);
    }

    @Test
    public void testAddUser_InvalidPassword() {
        User user = new User();
        user.setUserName("validUser123");
        user.setPassword("weakpassword");
        user.setConfirmPassword("weakpassword");
        String result = userService.addUser(user);
        verify(userrepo, never()).save(any(User.class));
        assertEquals("Invalid password", result);
    }
}
