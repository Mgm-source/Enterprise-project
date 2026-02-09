package com.enterpriseproject.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
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

    private final FilmImageStorageService filmImageStorageService;

    public FilmImageController(FilmImageStorageService filmImageStorageService) {
        this.filmImageStorageService = filmImageStorageService;
    }

    @GetMapping
    public ResponseEntity<Resource> getFilmImage(@PathVariable int id) {

        try {

            Resource resource = filmImageStorageService.loadImage(id);

             MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

            if (resource.getFilename() != null) {
                // Guess the MIME type using the file extension
                Path path = Paths.get(resource.getFilename());
                String mimeType = Files.probeContentType(path);
                if (mimeType != null) {
                    mediaType = MediaType.parseMediaType(mimeType);
                }
            }

            return ResponseEntity.ok()
                    .header("Content-Disposition", "inline; filename=\"" + resource.getFilename() + "\"")
                    .contentLength(resource.contentLength())
                    .contentType(mediaType)
                    .body(resource);

        } catch (IOException ex) {
            logger.debug("IOException", ex);
        } catch (FilmImageNotFoundException finfe) {
            logger.debug("FilmImageNotFoundException", finfe);
        }

        return ResponseEntity.status(404).build();
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(@PathVariable int id, @RequestPart("img") MultipartFile file) {

        try {
            if (filmImageStorageService.uploadToDisk(
                    new FilmImage(id, UUID.randomUUID().toString() + "-" + file.getOriginalFilename()),
                    file.getBytes())) {

                return ResponseEntity.ok().build();
            }

        } catch (IOException e) {
            logger.debug("Failed to retrieve uploaded file", e);
        }

        return ResponseEntity.status(500).build();
    }
}
