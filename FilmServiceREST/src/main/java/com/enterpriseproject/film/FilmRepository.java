package com.enterpriseproject.film;

import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository  {
    Iterable<Film> findAll();

    Film findOne(int id);

    Film save(Film film);

}