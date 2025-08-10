package com.enterpriseproject.film;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Films")
@XmlAccessorType(XmlAccessType.FIELD)
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
