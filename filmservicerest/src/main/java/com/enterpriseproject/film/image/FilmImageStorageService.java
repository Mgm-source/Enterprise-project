package com.enterpriseproject.film.image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

@Service
public class FilmImageStorageService {

    FilmImageRepository filmImageRepository;

    private static final Logger logger = LoggerFactory.getLogger(FilmImageStorageService.class);

    @Value("${ImageService.location}")
    private String location;

    public FilmImageStorageService(FilmImageRepository filmImageRepository) {
        this.filmImageRepository = filmImageRepository;
    }

    public FileSystemResource loadImage(int id)  {

        Optional<FilmImage> image = filmImageRepository.findByFilmId(id);

        if (image.isPresent()) {
            Path filmPath = Path.of(location + image.get().getPath());

            logger.info("Image location {} ", filmPath);

            return new FileSystemResource(filmPath);
        }

        throw new FilmImageNotFoundException("Film not found with " + id);
    }

    public boolean uploadToDisk(FilmImage newImage, byte[] bytes) {

        File file = new File(location + newImage.getPath());

        try (FileOutputStream out = new FileOutputStream(file)) {

            out.write(bytes);
            out.flush();

            Optional<FilmImage> image = filmImageRepository.findByFilmId(newImage.getFilmId());

            if (image.isPresent()) {
                image.get().setPath(newImage.getPath());
                filmImageRepository.save(image.get());
            } else {
                filmImageRepository.save(newImage);
            }

            return true;

        } catch (FileNotFoundException e) {
            logger.debug("Issue with File Location", e);
        } catch (IOException e) {
            logger.debug("File not saved", e);
        }
        return false;
    }
}
