package com.enterpriseproject.controller;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enterpriseproject.film.image.FilmImage;
import com.enterpriseproject.film.image.FilmImageNotFoundException;
import com.enterpriseproject.film.image.FilmImageStorageService;

@RestController
@RequestMapping(value = "Films/image/{id}")
public class FilmImageController {

    private static final Logger logger = LoggerFactory.getLogger(FilmImageController.class);

    private FilmImageStorageService filmImageStorageService;

    @Autowired
    public FilmImageController(FilmImageStorageService filmImageStorageService) {
        this.filmImageStorageService = filmImageStorageService;
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> getFilmImage(@PathVariable int id) {

        try {

            InputStreamResource resource = filmImageStorageService.loadImage(id);

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + resource.getFilename() + "\"")
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (IOException ex) {
            logger.debug("IOException", ex);
        } catch (FilmImageNotFoundException FINFE) {
            logger.debug("FilmImageNotFoundException", FINFE);
        }

        return ResponseEntity.status(404).build();
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(@PathVariable int id, @RequestPart("img") MultipartFile file) {

        try {
            if (filmImageStorageService.uploadToDisk(new FilmImage(id, file.getOriginalFilename()), file.getBytes())) {

                return ResponseEntity.ok().build();
            }

        } catch (IOException e) {
            logger.debug("Failed to retrieve uploaded file", e);
        }

        return ResponseEntity.status(500).build();
    }
}
