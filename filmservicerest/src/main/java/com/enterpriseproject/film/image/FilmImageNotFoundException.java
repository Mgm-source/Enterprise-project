package com.enterpriseproject.film.image;

public class FilmImageNotFoundException extends RuntimeException {
    
    FilmImageNotFoundException(String message)
    {
        super(message);
    }
}
