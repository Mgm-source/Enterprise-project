package controller;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import resources.FilmRest;

@ApplicationPath("resources")
public class RestService extends Application{

	 public Set<Class<?>> getClasses() {
	        Set<Class<?>> s = new HashSet<Class<?>>();
	        s.add(FilmRest.class);
	        return s;
	    }
	 
	 

}
