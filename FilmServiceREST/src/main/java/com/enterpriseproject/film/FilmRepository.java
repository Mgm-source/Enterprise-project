package com.enterpriseproject.film;

public interface FilmRepository  {
    Iterable<Film> findAll();

    Film findOne(int id);

    Film save(Film film);

}