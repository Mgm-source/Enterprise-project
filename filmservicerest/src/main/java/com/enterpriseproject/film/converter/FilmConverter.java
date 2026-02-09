package com.enterpriseproject.film.converter;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import com.enterpriseproject.film.Film;
import com.enterpriseproject.film.Films;
import com.google.gson.Gson;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class FilmConverter implements Converter {
	

		private static final Logger logger = LoggerFactory.getLogger(FilmConverter.class);

    @Override
	public String toXML(List<Film> film) {

	        try {
	        	
	        	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    	    DocumentBuilder builder = factory.newDocumentBuilder();
	    	    Document document = builder.newDocument();
	        	
	        	
	    	    JAXBContext context = JAXBContext.newInstance(Films.class);
	            Marshaller m = context.createMarshaller();
	            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	            
	            Films filmReserve = new Films();
	            filmReserve.setFilms(film);
	            
	            m.marshal(filmReserve, document);
	            
	            DOMImplementationLS ls = (DOMImplementationLS) DOMImplementationRegistry.newInstance().getDOMImplementation("LS");
		        LSSerializer serializer = ls.createLSSerializer();
		        LSOutput output = ls.createLSOutput();
		        Writer writer = new StringWriter();
		        output.setEncoding("UTF-8");
		        output.setCharacterStream(writer);
		        serializer.write(document, output);
		        
		        return writer.toString();

	        } catch (JAXBException| ParserConfigurationException | ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException mPCE) {
				logger.debug("Context Misc",mPCE);
			}
	        
	        return null;
	        
	       
	    }

	@Override
	public String toJSON(List<Film> film) {
		return new Gson().toJson(film);
	}

	@Override
	public String toTEXT(List<Film> film) {
		
		Iterator<Film> iterator = film.iterator();
		StringBuilder sb = new StringBuilder();
		csvColumns("pkid,year,title,stars,review,director",sb);
		
		while(iterator.hasNext()) {
			Film nextfilm = iterator.next();
			sb.append("\n");
			sb.append(nextfilm.getPkid());
			sb.append(",");
			sb.append(nextfilm.getYear());
			sb.append(",");
			sb.append("\"").append(nextfilm.getTitle()).append("\"");
			sb.append(",");
			sb.append("\"").append(nextfilm.getStars()).append("\"");
			sb.append(",");
			sb.append("\"").append(nextfilm.getReview()).append("\"");
			sb.append(",");
			sb.append("\"").append(nextfilm.getDirector()).append("\"");
			
		}
		
		return sb.toString();
	}

	private StringBuilder csvColumns(String columns, StringBuilder sb) {
		return sb.append(columns);
		
	}
}
