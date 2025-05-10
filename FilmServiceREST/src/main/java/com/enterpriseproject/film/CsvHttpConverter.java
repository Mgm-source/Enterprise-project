package com.enterpriseproject.film;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.MediaType;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.lang.UnsupportedOperationException;
import java.io.IOException;


public class CsvHttpConverter extends AbstractHttpMessageConverter<Collection<Film>> {

	 private final FilmConverter converter = new FilmConverter();

	public CsvHttpConverter()
	{
		super(new MediaType("text", "csv"));
	}

	@Override
	protected void writeInternal(Collection<Film> films, HttpOutputMessage outputMessage) {

        	try {
			OutputStreamWriter writer = new OutputStreamWriter(outputMessage.getBody());
			writer.write(converter.toTEXT(films));
		} catch (IOException e) {

			throw new RuntimeException("Error writing CSV output", e);
		}
	}

	@Override
	protected Collection<Film> readInternal(Class<? extends Collection<Film>> clazz, HttpInputMessage inputMessage) {
    		throw new UnsupportedOperationException("CSV input not supported");
	}
	
	@Override
	protected boolean supports(Class<?> clazz) {
		return Films.class.isAssignableFrom(clazz);
	}
}	
