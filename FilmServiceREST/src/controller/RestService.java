package controller;

import resources.ActiveMq;
import resources.FilmRest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("resources")
public class RestService extends Application{
	 public Set<Class<?>> getClasses() {
	        Set<Class<?>> s = new HashSet<>();
	        s.add(FilmRest.class);
	        s.add(ActiveMq.class);
	        return s;
	    }
}