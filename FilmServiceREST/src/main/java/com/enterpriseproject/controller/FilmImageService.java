package com.enterpriseproject.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enterpriseproject.film.image.FilmImage;
import com.enterpriseproject.film.image.FilmImageRepository;

@RestController
@RequestMapping(value = "Films/image/{id}")
public class FilmImageService {

    FilmImageRepository filmImageRepository;

    private static final Logger logger = LoggerFactory.getLogger(FilmImageService.class);

    @Value("${ImageService.location}")
    private String location;

    public FilmImageService(FilmImageRepository filmImageRepository) {
        this.filmImageRepository = filmImageRepository;
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> getFilmImage(@PathVariable int id) {

        try {
            Optional<FilmImage> image = filmImageRepository.findByFilmId(id);

            if (image.isPresent()) {
                Path filmPath = Path.of(location + image.get().getPath());

                logger.info("Image location ",filmPath);

                InputStreamResource resource = new InputStreamResource(Files.newInputStream(filmPath));

                return ResponseEntity.ok()
                        .header("Content-Disposition", "attachment; filename=\"" + filmPath.getFileName() + "\"")
                        .contentLength(Files.size(filmPath))
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            }

        } catch (IOException ex) {
            logger.debug("Image IOException",ex);
        }

        return ResponseEntity.status(404).build();
    }
}
