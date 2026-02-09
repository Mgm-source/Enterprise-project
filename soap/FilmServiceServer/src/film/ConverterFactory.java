package film;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

public class ConverterFactory {
	
	/**
	 * one thread to execute at any given time just in case.
	 * 
	 *  Get a method form a implemented Converter by specifying a format 
	 *  if a format can't be found then runtime error gets thrown
	 * 
	 * @param format
	 * @param films
	 * @return formated data
	 */
	public static synchronized String getFormat(String format, Collection<Film> films) {
		
		try {
			Method converterMethod = FilmConverter.class.getMethod("to"+format.toUpperCase(), Collection.class);
			
			FilmConverter converterInstance = new FilmConverter();
			
			String converted = (String) converterMethod.invoke(converterInstance, films);
			
			return converted;
			
		} catch (NoSuchMethodException NSME) {
			NSME.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		throw new RuntimeException("Failed to format film");
	}
	
}
