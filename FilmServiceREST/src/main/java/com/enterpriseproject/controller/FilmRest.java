package com.enterpriseproject.controller; 

import java.net.URI;
import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.enterpriseproject.film.Film;
import com.enterpriseproject.film.FilmConverter;
import com.enterpriseproject.models.FilmDao;

@RestController
@RequestMapping(value = "/")
public class FilmRest {

	FilmDao filmDb = FilmDao.getDao();
	FilmConverter converter = new FilmConverter();

	@GetMapping(value = "/Films", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllFilms() {
		Collection<Film> film = filmDb.listFilm();
		if (!film.isEmpty()) {
			return ResponseEntity.ok(converter.toJSON(film));
		}

		return ResponseEntity.status(404).build();
	}

	@GetMapping(value = "/Films", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> getAllFilmsXML() {
		Collection<Film> film = filmDb.listFilm();
		if (!film.isEmpty()) {
			return ResponseEntity.ok(converter.toXML(film));
		}

		return ResponseEntity.status(404).build();
	}

	@GetMapping(value = "/Films", produces = "text/csv")
	public ResponseEntity<String> getAllFilmsCSV() {
		Collection<Film> film = filmDb.listFilm();

		if (!film.isEmpty()) {

			return ResponseEntity.ok(converter.toTEXT(film));
		}

		return ResponseEntity.status(404).build();
	}

	@PostMapping(value = "/Films", produces = MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> insertFilm(@RequestParam(value="title") String title, @RequestParam(value="year") int year,
			@RequestParam(value="director") String director, @RequestParam(value="stars") String stars,
			@RequestParam(value="review") String review) {
		Film film = filmDb.createFilm(title, year, director, stars, review);
		filmDb.insertFilm(film);
		
		URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(film.getId())
                    .toUri();

		String json = "{title:" + "\"" + film.getTitle() + "\"" + "}";

		if (filmDb.getOperation() == 1) {
			return ResponseEntity.created(location).body(json);
		}
		return ResponseEntity.status(404).build();

	}
/*
	@GetMapping("/Films/{id}")
	public FilmResource getFilm(@RequestParam(value ="id") int id) {
		return new FilmResource(location, request, id);
	}
*/
}
