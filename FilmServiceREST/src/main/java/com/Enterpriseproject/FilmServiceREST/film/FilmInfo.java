package film;
import java.util.Collection;

public interface FilmInfo {
	
	/**
	 * Inserts a new film record.
	 * @param film that needs to be film inserted.
	 */
	 public void insertFilm (Film film);
	 
		/**
		 * Updates a film record
		 * @param film that needs to be film updated.
		 */
	 public void updateFilm (Film film);
		/**
		 * 
		 *  Deletes a film record
		 * @param id of film that needs to be film deleted.
		 */
	 public void deleteFilm(int id);
	 
		/**
		 * Gets a collection of films
		 * Returns a collection of nothing if nothing is found
		 * @return collection of films
		 */
	 public Collection<Film> listFilm ();
	 
		/**
		 * Gets a specified film.
		 * Returns a collection of nothing if nothing is found
		 * @param title the title of the film 
		 * @return collection of a film or films
		 */
	 public Collection<Film> retrieveFilm(String title);
	 

}