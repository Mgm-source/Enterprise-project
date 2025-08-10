package com.enterpriseproject.film.converters;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;

import com.enterpriseproject.film.Film;

public class CsvHttpConverter extends AbstractHttpMessageConverter<List<Film>> {

	 private final FilmConverter converter = new FilmConverter();

	public CsvHttpConverter()
	{
		super(new MediaType("text", "csv"));
	}

	@Override
	protected void writeInternal(@SuppressWarnings("null") List<Film> films, @SuppressWarnings("null") HttpOutputMessage outputMessage) {

        	try (OutputStreamWriter writer = new OutputStreamWriter(outputMessage.getBody())){
			writer.write(converter.toTEXT(films));
		} catch (IOException e) {

			throw new ConverterException("Error writing CSV output", e);
		}
	}

	@SuppressWarnings("null")
	@Override
	protected List<Film> readInternal(@SuppressWarnings("null") Class<? extends List<Film>> clazz, @SuppressWarnings("null") HttpInputMessage inputMessage) {
    		throw new UnsupportedOperationException("CSV input not supported");
	}
	
	@Override
	protected boolean supports(@SuppressWarnings("null") Class<?> clazz) {
		return List.class.isAssignableFrom(clazz);
	}
}	
