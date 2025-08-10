package com.enterpriseproject.film;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class FilmProcessorBeanTest {

    @InjectMocks
    private FilmProcessorBean filmProcessorBean;

    @Mock
    private FilmRepository filmRepository;

    @Test
    void testProcess_filmNotInDb_savesFilm() {
        // Given
        Film film = new Film();
        film.setTitle("Inception");
        film.setYear(2010);
        film.setDirector("Christopher Nolan");

        // Mock: Film not found in DB
        when(filmRepository.findByTitleAndYearAndDirector(film.getTitle(), film.getYear(), film.getDirector()))
            .thenReturn(Collections.emptyList());

        // When
        filmProcessorBean.process(film);

        // Then
        verify(filmRepository).save(film);
    }

    @Test
    void testProcess_filmAlreadyInDb_doesNotSaveFilm() {
        // Given
        Film film = new Film();
        film.setTitle("Inception");
        film.setYear(2010);
        film.setDirector("Christopher Nolan");

        // Mock: Film already exists
        when(filmRepository.findByTitleAndYearAndDirector(film.getTitle(), film.getYear(), film.getDirector()))
            .thenReturn(List.of(film));

        // When
        filmProcessorBean.process(film);

        // Then
        verify(filmRepository, never()).save(any(Film.class));
    }

    @Test
    void testProcess_filmRepoReturnsNull_doesNotSaveFilm() {
        // Given
        Film film = new Film();
        film.setTitle("Inception");
        film.setYear(2010);
        film.setDirector("Christopher Nolan");

        // Mock: Film not found in DB
        when(filmRepository.findByTitleAndYearAndDirector(film.getTitle(), film.getYear(), film.getDirector()))
            .thenReturn(null);

        // When
        filmProcessorBean.process(film);

        // Then
        verify(filmRepository, never()).save(any(Film.class));
    }
}
