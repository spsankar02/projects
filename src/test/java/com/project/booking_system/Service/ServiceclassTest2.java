package com.Myproject.Bookingsystem.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.Myproject.Bookingsystem.Entity.Movie;
import com.Myproject.Bookingsystem.Repo.Movierepo;

class ServiceclassTest2 {

    @Mock
    private Movierepo movierepo;

    @InjectMocks
    private Serviceclass service;

    @SuppressWarnings("deprecation")
	@BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

  
    @Test
    void testCreateMovie_Success() {
    	Movie movie = new Movie();
        movie.setMovieId(50000L);
        movie.setTitle("Example Movie");
        movie.setCertificate("PG-13");
        movie.setLanguages("English");
        movie.setGenre("Action");
        movie.setDuration("2 hours");
        when(movierepo.save(any(Movie.class))).thenReturn(movie);
        Movie createdMovie = service.createMovie(movie);        
        assertNotNull(createdMovie);
        verify(movierepo, times(1)).save(movie); 
    }


    @Test
    void testCreateMovie_RepositoryNotAvailable() {
    when(movierepo.save(any(Movie.class))).thenThrow(new IllegalArgumentException("Movie repository is not available"));
    verify(movierepo, never()).save(any(Movie.class)); 
    }

}
