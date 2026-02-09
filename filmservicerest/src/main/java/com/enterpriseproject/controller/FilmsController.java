package com.enterpriseproject.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enterpriseproject.film.Film;
import com.enterpriseproject.film.FilmRepository;
import com.enterpriseproject.film.Films;

@RestController
@RequestMapping(value = "Films")
public class FilmsController {

    FilmRepository filmRepository;

    public FilmsController(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Film>> getAllFilms() {
        List<Film> film = filmRepository.findAll();
        if (film != null) {
            return ResponseEntity.ok(film);
        }

        return ResponseEntity.status(404).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Films> getAllFilmsXML() {
        List<Film> film = filmRepository.findAll();

        if (film != null) {
            Films filmReserve = new Films();
            filmReserve.setFilms(film);
            return ResponseEntity.ok(filmReserve);
        }

        return ResponseEntity.status(404).build();
    }

    @GetMapping(produces = "text/csv")
    public ResponseEntity<List<Film>> getAllFilmsCSV() {
        List<Film> film = filmRepository.findAll();

        if (film != null) {
            return ResponseEntity.ok(film);
        }

        return ResponseEntity.status(404).build();
    }

    @GetMapping(path = "/{name}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Films> getFilmXML(@PathVariable String name) {
        List<Film> film = filmRepository.findOne(name);

        if (film != null) {
	    Films filmReserve = new Films();
            filmReserve.setFilms(film);
            return ResponseEntity.ok(filmReserve);
        }

        return ResponseEntity.status(404).build();
    }

    @GetMapping(path = "/{name}", produces = "text/csv")
    public ResponseEntity<List<Film>> getFilmCSV(@PathVariable String name) {
        List<Film> film = filmRepository.findOne(name);
        

        if (film != null) {
            return ResponseEntity.ok(film);
        }

        return ResponseEntity.status(404).build();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> insertFilm(@RequestParam String title, @RequestParam int year,
            @RequestParam String director, @RequestParam String stars,
            @RequestParam String review) {

        Film film = new Film(title, year, director, stars, review);

        if (filmRepository.save(film)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(404).build();

    }

}
