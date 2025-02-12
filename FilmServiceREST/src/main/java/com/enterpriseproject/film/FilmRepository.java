package com.enterpriseproject.film;

import java.util.Collection;

import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository  {
    Collection<Film> findAll();

    Film findOne(int id);

    Film save(Film film);

}