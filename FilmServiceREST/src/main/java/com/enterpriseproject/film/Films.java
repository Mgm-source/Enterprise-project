package com.enterpriseproject.film;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import jakarta.xml.bind.annotation.XmlElement;

public class Films {
	
	@JacksonXmlElementWrapper(useWrapping = false)
	@XmlElement(name = "film")
	private List<Film> filmlist;
	
	public void setFilmList(List<Film> filmlist) {
		this.filmlist = filmlist;
		
	}
	
	public List<Film> getFilmList() {
		return filmlist;
	}

	
}
