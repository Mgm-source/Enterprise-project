package wdsl;

import java.util.Collection;

import film.ConverterFactory;
import film.Film;
import models.FilmDao;

public class FilmWSDL {
	
	public String getFilmbyTitle(String title) {
		
		FilmDao filmDao =  FilmDao.getDao();
		
		Collection<Film> collection = filmDao.retrieveFilm(title);
		
		return ConverterFactory.getFormat("xml", collection);
			
	}
	
	/**
	 * Uses the request parameter to get a film with the specified id.  
	 * 
	 * @param parameters Uses the key with the value year
	 * @param filmDB An instance of the DAO talks to the SQL server 
	 * @return A collection of films that were the result a MYSQL query select 
	 */
	public String getFilmByID(int id) {
		
		 FilmDao filmDao =  FilmDao.getDao();
		
		Collection<Film> collection = filmDao.retrieveFilmByID(id);
		
		return ConverterFactory.getFormat("xml", collection);
		
	}
	
	/**
	 * Uses the request parameter to get films with the matching year.  
	 * 
	 * @param parameters Uses the key with the value year
	 * @param filmDB An instance of the DAO talks to the SQL server 
	 * @return A collection of films that were the result a MYSQL query select 
	 */
	public String getFilmByYear(int year) {
		
		FilmDao filmDao =  FilmDao.getDao();
		
		Collection<Film> collection = filmDao.retrieveFilm(year);
		
		return ConverterFactory.getFormat("xml", collection);
		
	}
		
	/**
	 *  Gets all the films in the DB
	 * 
	 * @param filmDB filmDB An instance of the DAO talks to the SQL server
	 * @return xml of all the films in the database. 
	 */
	public String getAllFilms() {
		
		FilmDao filmDao =  FilmDao.getDao();
		
		Collection<Film> collection = filmDao.listFilm();
		
		return ConverterFactory.getFormat("xml", collection);
	}
	
	
	/**
	 *  Inserts a film to the database
	 * 
	 * @param film
	 * @return Boolean where the operation has been carried out
	 */
	public Boolean insertFilm(Film film) {
		
		FilmDao filmDao =  FilmDao.getDao();
		
		filmDao.insertFilm(film);
		
		if(filmDao.getOperation()==1)
			
		{
			return true;
		}
		
		return false;
		
	}
	
	/**
	 *  Deletes a film to the database
	 * 
	 * @param id
	 * @return Boolean where the operation has been carried out
	 */
	public Boolean deleteFilm(int id) {
		
		FilmDao filmDao =  FilmDao.getDao();
		
		filmDao.deleteFilm(id);
		
		if(filmDao.getOperation()==1)
			
		{
			return true;
		}
		
		return false;
		
	}
	
	
	/**
	 *  Updates a film to the database
	 * 
	 * @param film
	 * @return Boolean where the operation has been carried out
	 */
	public Boolean updateFilm(Film film) {
		
		FilmDao filmDao =  FilmDao.getDao();
		
		filmDao.updateFilm(film);
		
		if(filmDao.getOperation()==1)
			
		{
			return true;
		}
		
		return false;
		
	}
	
	

}
