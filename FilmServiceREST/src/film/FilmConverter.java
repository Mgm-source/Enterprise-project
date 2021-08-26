package film;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import com.google.gson.Gson;

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
	        } catch (ParserConfigurationException PCE) {
				PCE.printStackTrace();
			} catch (ClassNotFoundException CFE) {
				CFE.printStackTrace();
			} catch (InstantiationException IE) {
				IE.printStackTrace();
			} catch (IllegalAccessException IAE) {
				IAE.printStackTrace();
			} catch (ClassCastException CCE) {
				CCE.printStackTrace();
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
			sb.append("\""+nextfilm.getTitle()+"\"");
			sb.append(",");
			sb.append("\""+nextfilm.getStars()+"\"");
			sb.append(",");
			sb.append("\""+nextfilm.getReview()+"\"");
			sb.append(",");
			sb.append("\""+nextfilm.getDirector()+"\"");
			
		}
		
		return sb.toString();
	}

	private StringBuffer csvColumns(String columns, StringBuffer sb) {
		return sb.append(columns);
		
	}
		
		
	}



	
