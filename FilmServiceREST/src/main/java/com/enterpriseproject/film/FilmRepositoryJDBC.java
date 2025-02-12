package com.enterpriseproject.film;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FilmRepositoryJDBC implements FilmRepository {

    private final JdbcTemplate template;

    @Autowired
    public FilmRepositoryJDBC(JdbcTemplate jdbcTemplate){
        template = jdbcTemplate;
    }

    @Override
    public Iterable<Film> findAll() {
        return template.query("SELECT pkid,title,year,director,stars,review FROM FILMS", this::mapFilmRowToFilm);
    }

    @Override
    public Film findOne(int id) {
        // TODO Auto-generated method stub
        return template.queryForObject("SELECT * FROM films WHERE pkid = ?", this::mapFilmRowToFilm, id);
    }

    @Override
    public Film save(Film film) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    Film mapFilmRowToFilm(ResultSet rs , int num) throws SQLException
    {
        return new Film(
        rs.getInt("pkid"),
        rs.getString("title"),
        rs.getInt("year"),
        rs.getString("director"),
        rs.getString("stars"),
        rs.getString("review"));
    }
}