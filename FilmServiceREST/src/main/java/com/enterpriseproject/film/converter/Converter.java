package com.enterpriseproject.film.converter;

import java.util.List;

import com.enterpriseproject.film.Film;

public interface Converter {
	
	public String toXML(List<Film> film);
	
	public String toJSON(List<Film> film);
	
	public String toTEXT(List<Film> film);

}
