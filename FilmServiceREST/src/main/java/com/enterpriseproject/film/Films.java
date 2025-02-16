package com.enterpriseproject.film;

import java.util.Collection;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "films")
@XmlAccessorType(XmlAccessType.FIELD)
public class Films {
	
	@XmlElement(name = "Film")
	private Collection<Film> filmlist;
	
	public Films() {}
	
	public void setFilm(Collection<Film> filmlist) {
		this.filmlist = filmlist;
		
	}

	@JacksonXmlElementWrapper(useWrapping = false)
	public Collection<Film> getFilm() {
		return filmlist;
	}

	
}
