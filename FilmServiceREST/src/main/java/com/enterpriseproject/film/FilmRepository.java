package com.enterpriseproject.film;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository  {
    List<Film> findAll();

    List<Film> findOne(int id);
    List<Film> findOne(String title);
    boolean update(Film film);
    boolean save(Film film);
    boolean delete(int id);

}

