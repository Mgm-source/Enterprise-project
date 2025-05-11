package com.enterpriseproject.film;

import java.util.List;

public interface Converter {
	
	public String toXML(List<Film> film);
	
	public String toJSON(List<Film> film);
	
	public String toTEXT(List<Film> film);

}
