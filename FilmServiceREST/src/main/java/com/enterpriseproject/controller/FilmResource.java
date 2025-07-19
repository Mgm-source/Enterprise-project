package com.enterpriseproject.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enterpriseproject.film.Film;
import com.enterpriseproject.film.FilmRepository;
import com.enterpriseproject.film.Films;

@RestController
@RequestMapping(value = "Films/id/{id}")
public class FilmResource {

	FilmRepository filmRepository;

	@Value("${ImageService.location}")
	private String location;

	private static final Logger logger = LoggerFactory.getLogger(FilmResource.class);

	public FilmResource(FilmRepository filmRepository) {
		this.filmRepository = filmRepository;
	}

	@DeleteMapping
	public ResponseEntity<String> deleteFilm(@PathVariable int id) {

		if (filmRepository.delete(id)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.status(404).build();
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Film>> getFilmJSON(@PathVariable int id) {
		List<Film> film = filmRepository.findOne(id);

		if (film != null) {
			return ResponseEntity.ok(film);
		}

		return ResponseEntity.status(404).build();
	}

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<Films> getFilmXML(@PathVariable int id) {
		List<Film> film = filmRepository.findOne(id);

		if (film != null) {
			Films filmReserve = new Films();
			filmReserve.setFilmList(film);
			return ResponseEntity.ok(filmReserve);
		}

		return ResponseEntity.status(404).build();
	}

	@GetMapping(produces = "text/csv")
	public ResponseEntity<List<Film>> getFilmCSV(@PathVariable int id) {
		List<Film> film = filmRepository.findOne(id);

		if (film != null) {
			return ResponseEntity.ok(film);
		}

		return ResponseEntity.status(404).build();
	}

	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> updateFilm(@PathVariable int id, @RequestParam String title, @RequestParam int year,
			@RequestParam String director, @RequestParam String stars,
			@RequestParam String review) {

		if (filmRepository.update(new Film(id, title, year, director, stars, review))) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.status(404).build();

	}

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> updateImage(@PathVariable int id, @RequestPart("img") MultipartFile file) {
		try (FileOutputStream out = new FileOutputStream(location + file.getOriginalFilename())) {

			try {

				byte[] contentbtyes = file.getBytes();

				out.write(contentbtyes);

				out.flush();
				out.close();

				return ResponseEntity.ok().build();

			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ResponseEntity.status(404).build();
	}

}
