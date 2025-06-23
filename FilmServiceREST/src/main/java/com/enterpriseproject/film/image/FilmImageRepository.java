package com.enterpriseproject.film.image;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface FilmImageRepository extends CrudRepository<FilmImage,Long> {

    Optional<FilmImage> findByFilmId(Integer filmId);
}
