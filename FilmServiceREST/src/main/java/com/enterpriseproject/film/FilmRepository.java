package com.enterpriseproject.film;

import java.util.Collection;

import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository  {
    Collection<Film> findAll();

    Collection<Film> findOne(int id);
    Collection<Film> findOne(String title);
    boolean update(Film film);
    boolean save(Film film);
    boolean delete(int id);

}

