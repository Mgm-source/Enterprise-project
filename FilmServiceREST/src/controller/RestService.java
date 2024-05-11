package controller;

import java.util.HashSet;
import java.util.Set;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import resources.ActiveMq;
import resources.FilmRest;

@ApplicationPath("resources")
public class RestService extends Application{
	
	@Override
	 public Set<Class<?>> getClasses() {
	        Set<Class<?>> s = new HashSet<>();
	        s.add(FilmRest.class);
	        s.add(ActiveMq.class);
	        return s;
	    }
}