package com.enterpriseproject.film;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enterpriseproject.controller.FilmResource;

@Component
public class FilmProcessorBean {

    private static final Logger logger = LoggerFactory.getLogger(FilmResource.class);

    @Autowired
    FilmRepository filmRepository;
    
    public void process(Films films)
    {
        films.getfilmList().forEach( (film) -> {

            List<Film> dbFilms = filmRepository.findByTitleAndYearAndDirector(film.getTitle(), film.getYear(), film.getDirector());
            
            if(dbFilms != null && dbFilms.isEmpty())
            {
                filmRepository.save(film);
                logger.info("Film added:\n" + film.toString());
            }
            else
            {
                logger.info("Film not added;\n" + film.toString());
            }
        });
    }
}
