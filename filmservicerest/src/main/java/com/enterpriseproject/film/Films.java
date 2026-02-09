package com.enterpriseproject.film;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
@JacksonXmlRootElement(localName = "Films")
public class Films {
	
	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "Film")  
	private List<Film> films ;
	
	public void setFilms(List<Film> films ) {
		this.films  = films ;
		
	}
	
	public List<Film> getFilms() {
		return films ;
	}

	
}
