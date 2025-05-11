package com.enterpriseproject.film;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;


public class CsvHttpConverter extends AbstractHttpMessageConverter<List<Film>> {

	 private final FilmConverter converter = new FilmConverter();

	public CsvHttpConverter()
	{
		super(new MediaType("text", "csv"));
	}

	@Override
	protected void writeInternal(List<Film> films, HttpOutputMessage outputMessage) {

        	try (OutputStreamWriter writer = new OutputStreamWriter(outputMessage.getBody())){
			writer.write(converter.toTEXT(films));
		} catch (IOException e) {

			throw new RuntimeException("Error writing CSV output", e);
		}
	}

	@Override
	protected List<Film> readInternal(Class<? extends List<Film>> clazz, HttpInputMessage inputMessage) {
    		throw new UnsupportedOperationException("CSV input not supported");
	}
	
	@Override
	protected boolean supports(Class<?> clazz) {
		return List.class.isAssignableFrom(clazz);
	}
}	
