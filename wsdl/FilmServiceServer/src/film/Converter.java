package film;

import java.util.Collection;

public interface Converter {
	/**
	 *  Takes in a collection of films
	 * 
	 * @param film
	 * @return A string with xml formated data
	 */
	public String toXML(Collection<Film> film);
	
	/**
	 *  Takes in a collection of films
	 * 
	 * @param film
	 * @return A string with json formated data
	 */
	public String toJSON(Collection<Film> film);
	
	/**
	 *  Takes in a collection of films
	 * 
	 * @param film
	 * @return A string with csv formated data
	 */
	public String toTEXT(Collection<Film> film);

}
