package com.Myproject.Bookingsystem.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.Myproject.Bookingsystem.Entity.User;
import com.Myproject.Bookingsystem.Repo.Userrepo;

class ServiceclassTest3 {


    @Mock
    private Userrepo userrepo;

    @InjectMocks
    private Serviceclass service;
    
    @SuppressWarnings("deprecation")
	@BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    void passValue_UserExists_ReturnsUser() {	
        String username = "sanji";
        User expectedUser = new User(); 
        when(userrepo.findByUserName(username)).thenReturn(Optional.of(expectedUser));
        User actualUser = service.passValue(username);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void passValue_UserDoesNotExist_ThrowsIllegalArgumentException() {
        String username = "franky";
        when(userrepo.findByUserName(username)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> {
            service.passValue(username);
        });
    }

    @Test
    void passValue_UserRepositoryNotAvailable_ThrowsIllegalArgumentException() {
        String username = "Luffy";
        assertThrows(IllegalArgumentException.class, () -> {
            service.passValue(username);
        });
    }
}
