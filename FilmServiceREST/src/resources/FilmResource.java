package resources;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import film.Film;
import film.FilmConverter;
import models.FilmDao;

public class FilmResource {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	// Film ID
	int id;
	
	FilmDao filmDb = FilmDao.getDao();
	FilmConverter converter = new FilmConverter();

	public FilmResource(UriInfo uriInfo, Request request, int id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
	
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFilm(){
    	filmDb.deleteFilm(id);
    	if(filmDb.getOperation() == 1) 
    	{
    		 return Response.noContent().build();
    	}
    	return Response.status(404).build();
    	
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateFilm( @FormParam("title") String title, @FormParam("year") int year, 
			@FormParam("director") String director, @FormParam("stars") String stars,
			@FormParam("review") String review) {
		Film film = filmDb.createFilm(id, title, year, director, stars, review);
		filmDb.updateFilm(film);
    	if(filmDb.getOperation() == 1) 
    	{
    		return Response.noContent().build();
    	}
    	return Response.status(404).build();
		
	}
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilm(){
    	Collection<Film> film = filmDb.retrieveFilmByID(id);
    	if(film.size() > 0)
    	{
    		return Response.ok().entity(film).build();
    	}
    	
    	return Response.status(404).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getFilmXML(){
    	Collection<Film> film = filmDb.retrieveFilmByID(id);
    	if(film.size() > 0)
    	{
    		return Response.ok().entity(converter.toXML(film)).build();
    	}
    	
    	return Response.status(404).build();
    }
    
    @GET
    @Produces("text/csv")
    public Response getFilmCSV(){
    	Collection<Film> film = filmDb.retrieveFilmByID(id);
    	if(film.size() > 0)
    	{
    		return Response.ok().entity(converter.toTEXT(film)).build();
    	}
    	
    	return Response.status(404).build();
    }
}
