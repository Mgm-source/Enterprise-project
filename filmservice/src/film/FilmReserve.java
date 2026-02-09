package film;

import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
// parent element
@XmlRootElement(namespace = "xml.jaxb.model")
public class FilmReserve {
	
	public FilmReserve() {}
	
	private Collection<Film> filmlist;
	
	public void setFilmList(Collection<Film> filmlist) {
		this.filmlist = filmlist;
		
	}
	@XmlElement(name = "film")
	public Collection<Film> getFilmList() {
		return filmlist;
	}

	
}
