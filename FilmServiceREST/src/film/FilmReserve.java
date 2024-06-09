package film;

import java.util.Collection;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "films")
public class FilmReserve {
	
	private Collection<Film> filmlist;
	
	public FilmReserve() {}
	
	public void setFilmList(Collection<Film> filmlist) {
		this.filmlist = filmlist;
		
	}
	@XmlElement(name = "film")
	public Collection<Film> getFilmList() {
		return filmlist;
	}

	
}
