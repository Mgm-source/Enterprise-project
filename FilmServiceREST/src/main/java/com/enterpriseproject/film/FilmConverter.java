package com.enterpriseproject.film;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import com.google.gson.Gson;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class FilmConverter implements Converter {
	
	public FilmConverter() {}
	// option save as a dom document
	public String toXML(Collection<Film> film) {

	        try {
	        	
	        	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    	    DocumentBuilder builder = factory.newDocumentBuilder();
	    	    Document document = builder.newDocument();
	        	
	        	
	    	    JAXBContext context = JAXBContext.newInstance(FilmReserve.class);
	            Marshaller m = context.createMarshaller();
	            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	            
	            FilmReserve filmReserve = new FilmReserve();
	            filmReserve.setFilmList(film);
	            
	            m.marshal(filmReserve, document);
	            
	            DOMImplementationLS ls = (DOMImplementationLS) DOMImplementationRegistry.newInstance().getDOMImplementation("LS");
		        LSSerializer serializer = ls.createLSSerializer();
		        LSOutput output = ls.createLSOutput();
		        Writer writer = new StringWriter();
		        output.setEncoding("UTF-8");
		        output.setCharacterStream(writer);
		        serializer.write(document, output);
		        
		        String xml = writer.toString();

		        return xml;

	        } catch (JAXBException JXE) {
	            JXE.printStackTrace();
	        } catch (ParserConfigurationException | ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException PCE) {
				PCE.printStackTrace();
			}
	        
	        return null;
	        
	       
	    }

	@Override
	public String toJSON(Collection<Film> film) {
	        String json = new Gson().toJson(film);
		return json;
	}

	@Override
	public String toTEXT(Collection<Film> film) {
		
		Iterator<Film> iterator = film.iterator();
		StringBuffer sb = new StringBuffer();
		csvColumns("id,year,title,stars,review,director",sb);
		
		while(iterator.hasNext()) {
			Film nextfilm = iterator.next();
			sb.append("\n");
			sb.append(nextfilm.getId());
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

	private StringBuffer csvColumns(String columns, StringBuffer sb) {
		return sb.append(columns);
		
	}
		
		
	}



	
