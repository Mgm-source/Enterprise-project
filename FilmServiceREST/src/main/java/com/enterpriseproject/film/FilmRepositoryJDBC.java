package com.enterpriseproject.film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

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
    public Collection<Film> findAll() {
        return template.query("SELECT pkid,title,year,director,stars,review FROM films", this::mapFilmRowToFilm);
    }

    @Override
    public Collection<Film> findOne(int id) {
        // TODO Auto-generated method stub
        try {
            return template.query("SELECT pkid,title,year,director,stars,review FROM films WHERE pkid = ?", this::mapFilmRowToFilm, id);
        }
        catch (EmptyResultDataAccessException emptyResultDataAccessException)
        {
            return null;
        } 
    }

    @Override
    public boolean save(Film film) {
        // TODO Auto-generated method stub
        return template.update("INSERT INTO films(title,year,director,stars,review) VALUES (?,?,?,?,?)",
                film.getTitle(),film.getYear(),film.getDirector(),film.getStars(),film.getReview()) > 0;
    }

    Film mapFilmRowToFilm(ResultSet rs , int _n) throws SQLException
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
    public Collection<Film> findOne(String title) {
        try {
            return template.query("SELECT pkid,title,year,director,stars,review FROM films WHERE title = ?", this::mapFilmRowToFilm, title);
        }
        catch (EmptyResultDataAccessException emptyResultDataAccessException)
        {
            return null;
        } 
    }

    @Override
    public boolean update(Film film) {
        // TODO Auto-generated method stub
        return template.update("UPDATE films SET title = ?, year = ?, director = ?, stars = ?, review = ? Where pkid = ?",
                film.getTitle(),film.getYear(),film.getDirector(),film.getStars(),film.getReview(), film.getPkid()) > 0;
    }

    @Override
    public boolean delete(int id) {
        return template.update("DELETE FROM films WHERE pkid =?",id) > 0;
    }
}