package com.enterpriseproject.film.converters;

import java.util.List;

import com.enterpriseproject.film.Film;

public interface Converter {
	
	public String toXML(List<Film> film);
	
	public String toJSON(List<Film> film);
	
	public String toTEXT(List<Film> film);

}
