package resources;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.UUID;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

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

	@PUT
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateImage(@FormParam("img") InputStream is,@FormDataParam("img") FormDataContentDisposition fileDetails)
	{
		String devPath = "C:\\Users\\Munashe\\dump\\";
		try {
			FileOutputStream out = new FileOutputStream(devPath+fileDetails.getFileName());

			int read = 0;

			byte[] bytes = new byte[1024];
	
			try {
				while((read = is.read(bytes)) != -1)
				{
					out.write(bytes,0,read);
				}

				out.flush();
				out.close();

				return Response.ok(bytes).build();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(404).build();
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
