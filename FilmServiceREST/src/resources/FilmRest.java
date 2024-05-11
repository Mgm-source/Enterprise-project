package resources;

import java.util.Collection;

import film.Film;
import film.FilmConverter;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import models.FilmDao;

@Path("/Films")
public class FilmRest {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	FilmDao filmDb = FilmDao.getDao();
	FilmConverter converter = new FilmConverter();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllFilms() {
		Collection<Film> film = filmDb.listFilm();
		if (!film.isEmpty()) {
			return Response.ok().entity(film).build();
		}

		return Response.status(404).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getAllFilmsXML() {
		Collection<Film> film = filmDb.listFilm();
		if (!film.isEmpty()) {
			return Response.ok().entity(converter.toXML(film)).build();
		}

		return Response.status(404).build();
	}

	@GET
	@Produces("text/csv")
	public Response getAllFilmsCSV() {
		Collection<Film> film = filmDb.listFilm();

		if (!film.isEmpty()) {

			return Response.ok().entity(converter.toTEXT(film)).build();
		}

		return Response.status(404).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response insertFilm(@FormParam("title") String title, @FormParam("year") int year,
			@FormParam("director") String director, @FormParam("stars") String stars,
			@FormParam("review") String review) {
		Film film = filmDb.createFilm(title, year, director, stars, review);
		filmDb.insertFilm(film);
		if (filmDb.getOperation() == 1) {
			return Response.created(uriInfo.getAbsolutePath()).entity(film).build();
		}
		return Response.status(404).build();

	}

	@Path("{id}")
	public FilmResource getFilm(@PathParam("id") int id) {
		return new FilmResource(uriInfo, request, id);
	}

}
