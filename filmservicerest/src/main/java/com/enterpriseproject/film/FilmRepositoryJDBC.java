package com.enterpriseproject.film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class FilmRepositoryJDBC implements FilmRepository {

    private final JdbcTemplate template;

    public FilmRepositoryJDBC(JdbcTemplate jdbcTemplate){
        template = jdbcTemplate;
    }

    @Override
    public List<Film> findAll() {
        return template.query("SELECT pkid,title,year,director,stars,review FROM films", this::mapFilmRowToFilm);
    }

    @Override
    public List<Film> findOne(int id) {
        try {
            return template.query("SELECT pkid,title,year,director,stars,review FROM films WHERE pkid = ?", this::mapFilmRowToFilm, id);
        }
        catch (EmptyResultDataAccessException emptyResultDataAccessException)
        {
            return Collections.emptyList();
        } 
    }

    @Override
    public boolean save(Film film) {
        return template.update("INSERT INTO films(title,year,director,stars,review) VALUES (?,?,?,?,?)",
                film.getTitle(),film.getYear(),film.getDirector(),film.getStars(),film.getReview()) > 0;
    }

    Film mapFilmRowToFilm(ResultSet rs , int n) throws SQLException
    {
        return new Film(
        rs.getInt("pkid"),
        rs.getString("title"),
        rs.getInt("year"),
        rs.getString("director"),
        rs.getString("stars"),
        rs.getString("review"));
    }

    @Override
    public List<Film> findOne(String title) {
        try {
            return template.query("SELECT pkid,title,year,director,stars,review FROM films WHERE title = ?", this::mapFilmRowToFilm, title);
        }
        catch (EmptyResultDataAccessException emptyResultDataAccessException)
        {
            return Collections.emptyList();
        } 
    }

    @Override
    public boolean update(Film film) {   
        return template.update("UPDATE films SET title = ?, year = ?, director = ?, stars = ?, review = ? Where pkid = ?",
                film.getTitle(),film.getYear(),film.getDirector(),film.getStars(),film.getReview(), film.getPkid()) > 0;
    }

    @Override
    public boolean delete(int id) {
        return template.update("DELETE FROM films WHERE pkid =?",id) > 0;
    }

    @Override
    public List<Film> findByTitleAndYearAndDirector(String title, int year, String director) {
        try {
            return template.query("SELECT pkid,title,year,director,stars,review FROM films WHERE title = ? and year = ? and director = ?", this::mapFilmRowToFilm, title, year,director);
        }
        catch (EmptyResultDataAccessException emptyResultDataAccessException)
        {
            return Collections.emptyList();
        }
    }
}