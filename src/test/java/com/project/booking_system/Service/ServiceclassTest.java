package com.Myproject.Bookingsystem.Service;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.Myproject.Bookingsystem.Entity.User;
import com.Myproject.Bookingsystem.Repo.Userrepo;
class ServiceclassTest {

    @Mock
    private Userrepo userrepo;

    @InjectMocks
    private Serviceclass service;

    @SuppressWarnings("deprecation")
	@BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @AfterEach
    void tearDown() throws Exception {
    }
    @Test
    void testCreateUser_NullInput() {
    when(userrepo.save(any(User.class))).thenThrow(new IllegalArgumentException("User repository is not available"));
     verify(userrepo, never()).save(any(User.class)); 
    }

    @Test
    void testCreateUser() {	
        User mockUser = new User();
        mockUser.setUserId(1L);
        mockUser.setUserName("testuser");        
        when(userrepo.save(any(User.class))).thenReturn(mockUser);
        User result = service.createUser(mockUser);
        verify(userrepo, times(1)).save(mockUser);
        assertEquals(mockUser.getUserId(), result.getUserId());
        assertEquals(mockUser.getUserName(), result.getUserName());
    }
}
