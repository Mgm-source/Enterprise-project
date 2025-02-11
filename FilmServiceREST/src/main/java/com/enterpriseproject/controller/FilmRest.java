package com.enterpriseproject.controller; 

import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enterpriseproject.film.Film;
import com.enterpriseproject.film.FilmConverter;
import com.enterpriseproject.models.FilmDao;

@RestController
@RequestMapping(value = "Films")
public class FilmRest {

	FilmDao filmDb = FilmDao.getDao();
	FilmConverter converter = new FilmConverter();

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllFilms() {
		Collection<Film> film = filmDb.listFilm();
		if (!film.isEmpty()) {
			return ResponseEntity.ok(converter.toJSON(film));
		}

		return ResponseEntity.status(404).build();
	}

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> getAllFilmsXML() {
		Collection<Film> film = filmDb.listFilm();
		if (!film.isEmpty()) {
			return ResponseEntity.ok(converter.toXML(film));
		}

		return ResponseEntity.status(404).build();
	}

	@GetMapping(produces = "text/csv")
	public ResponseEntity<String> getAllFilmsCSV() {
		Collection<Film> film = filmDb.listFilm();

		if (!film.isEmpty()) {

			return ResponseEntity.ok(converter.toTEXT(film));
		}

		return ResponseEntity.status(404).build();
	}

	@GetMapping(path = "/{name}",produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> getFilmXML(@PathVariable String name) {
    	Collection<Film> film = FilmDao.getDao().retrieveFilm(name);
    	if(!film.isEmpty())
    	{
    		return ResponseEntity.ok().body(converter.toXML(film));
    	}
    	
    	return ResponseEntity.status(404).build();
    }
    
	@GetMapping(path = "/{name}", produces = "text/csv")
	public ResponseEntity<String> getFilmCSV(@PathVariable String name) {
    	Collection<Film> film = FilmDao.getDao().retrieveFilm(name);
    	if(!film.isEmpty())
    	{
    		return ResponseEntity.ok().body(converter.toTEXT(film));
    	}
    	
    	return ResponseEntity.status(404).build();
    } 

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> insertFilm(@RequestParam String title, @RequestParam int year,
			@RequestParam String director, @RequestParam String stars,
			@RequestParam String review) {
		Film film = filmDb.createFilm(title, year, director, stars, review);
		filmDb.insertFilm(film);
		

		if (filmDb.getOperation() == 1) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(404).build();

	}

}
