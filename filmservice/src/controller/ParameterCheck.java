package controller;

import java.util.Map;

public interface ParameterCheck {
	
	/**
	 * Checks the request parameter map with another map if a key value exist then the key-value will be set to
	 * true for the map thats being referenced through  
	 * 
	 * @param requestParam request parameter key is used to do the checking
	 * @param definedParam user defined map to check with
	 */
	public void checkParameters(Map<String, String[]> requestParam, Map<String, Boolean> definedParam);
	
	/**
	 * All default key-values are false (or should be) 
	 * 
	 * @return A map with the specified paramters in a map
	 */
	public Map<String, Boolean> acceptedParams();
	
	

}