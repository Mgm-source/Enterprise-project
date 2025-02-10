package com.enterpriseproject.controller; 

import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enterpriseproject.film.Film;
import com.enterpriseproject.film.FilmConverter;
import com.enterpriseproject.models.FilmDao;

@RestController
@RequestMapping(value = "Films/{id}")
public class FilmResource {

	FilmDao filmDb = FilmDao.getDao();
	FilmConverter converter = new FilmConverter();

	@DeleteMapping
	public ResponseEntity<String> deleteFilm(@PathVariable int id) {

		filmDb.deleteFilm(id);
    	if(filmDb.getOperation() == 1) 
    	{
    		 return ResponseEntity.noContent().build();
    	}
    	return ResponseEntity.status(404).build();
	}

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> getFilmXML(@PathVariable String id) {
    	Collection<Film> film = FilmDao.getDao().retrieveFilm(id);
    	if(!film.isEmpty())
    	{
    		return ResponseEntity.ok().body(converter.toXML(film));
    	}
    	
    	return ResponseEntity.status(404).build();
    }
    
	@GetMapping(produces = "text/csv")
	public ResponseEntity<String> getFilmCSV(@PathVariable String id) {
    	Collection<Film> film = FilmDao.getDao().retrieveFilm(id);
    	if(!film.isEmpty())
    	{
    		return ResponseEntity.ok().body(converter.toTEXT(film));
    	}
    	
    	return ResponseEntity.status(404).build();
    } 

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getFilm(@PathVariable int id) {
    	Collection<Film> film = FilmDao.getDao().retrieveFilmByID(id);
    	if(!film.isEmpty())
    	{
    		return ResponseEntity.ok().body(converter.toJSON(film));
    	}
    	
    	return ResponseEntity.status(404).build();
    }

	
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> updateFilm( @PathVariable int id, @RequestParam String title, @RequestParam int year, 
			@RequestParam String director, @RequestParam String stars,
			@RequestParam String review) {
		Film film = filmDb.createFilm(id, title, year, director, stars, review);
		filmDb.updateFilm(film);
    	if(filmDb.getOperation() == 1) 
    	{
    		return ResponseEntity.noContent().build();
    	}
    	return ResponseEntity.status(404).build();
		
	}

	/* @PUT
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response updateImage(@FormParam("img") InputStream is,@FormDataParam("img") FormDataContentDisposition fileDetails)
	{
		String devPath = "C:\\Users\\Munashe\\dump\\";
		try {
			FileOutputStream out = new FileOutputStream(devPath+fileDetails.getFileName());
			
			byte[] bytes = new byte[1024];

			filmDb.insertImageMeta(id,fileDetails.getFileName(),fileDetails.getType(),"");
			
			
			try {
				int read;
				while((read = is.read(bytes)) != -1)
				{
					out.write(bytes,0,read);
				}

				out.flush();
				out.close();

				return Response.ok().build();

			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return Response.status(404).build();
	}
    
*/
}
