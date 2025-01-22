package com.enterpriseproject.film;

import java.util.Collection;

public interface Converter {
	
	public String toXML(Collection<Film> film);
	
	public String toJSON(Collection<Film> film);
	
	public String toTEXT(Collection<Film> film);

}
