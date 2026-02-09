package controller;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import film.ConverterFactory;
import film.Film;
import models.FilmDao;

/**
 * Servlet implementation class FilmServlet
 */
@WebServlet("/Films")
public class FilmServlet extends HttpServlet implements ParameterCheck {
	
	private static final long serialVersionUID = 1L;
	
	// Location of JSP files that contain HTML for display
	private static final String  SEARCH_JSP = "WEB-INF/jsp/search.jsp";
	private static final String FORM_JSP = "WEB-INF/jsp/form.jsp";
	private static final String CARD_JSP = "WEB-INF/jsp/cards.jsp";
	
	// Where all the formated data goes
	private static final String FORMAT_JSP = "WEB-INF/jsp/Results/format.jsp";
	
	// error page
	private static final String ERROR_JSP = "WEB-INF/jsp/error.jsp";
	

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FilmServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Initialising variables 
		Map<String, String[]> parameters = request.getParameterMap();
		Map<String, Boolean> setParamters = acceptedParams();
		
		// checking if there are parameters 
		if(parameters.isEmpty()) { 
			
			// shows the error page
			getErrorPage(request, response, "No Parameters");
			
		}
		
		
		// checks the parameters sent with the parameters that are set my acceptedParams
		// if there's a match that specific key's value will be set to true
		checkParameters(parameters,setParamters);
		
		// If the key-value is true then the code belown will execute as a result
		// shows a page displaying an error
		if(setParamters.get("error")) {
			
			getErrorPage(request, response, "Invaild Parameters");
			
			setParamters.replace("error", false);
		}
		
		
		// if the key-value {page} is true then the code below  will execute as a result
		// because the checkParamater found it in the request parameter 
		if(setParamters.get("page")) {
			
			showPage(request, response);
			
			setParamters.replace("page", false);
			
		}
		
		// if the key-value {page} is false then the code below will execute as a result
		// this is only done this way because the criteria specifies that the default return is json
		if(setParamters.get("format").equals(false)) {
			
			// If the key-value {title} is true then the code below will execute as a result
			// shows a page with  films represented in JSON format
			if(setParamters.get("title")) {
				
				showTitle(request, response);
			
				setParamters.replace("title", false);
			}
			
			// If the key-value {year} is true then the code below will execute as a result
			// shows a page with films represented in JSON format
			if(setParamters.get("year")) {
				
				showYear(request, response);
				
				setParamters.replace("year", false);
			}
			
			// If the key-value {id} is true then the code below will execute as a result
			// shows a page with  films represented in JSON format
			if(setParamters.get("id")) {
				
				showID(request, response);
				
				setParamters.replace("id", false);
				
			}
			
		}
		// if the key-value {format} is true then the code below as a result
		if(setParamters.get("format")) {
			
			// Gets the current format {xml,json,text}
			// the if statement below checks if the parameter contain the the specified parameters 
			// if not an error gets shown
			String format = parameters.get("format")[0];
			
			if(format.equals("json")) {
				
				
				if(setParamters.get("title")) {
					
					showTitle(request, response);
					
					setParamters.replace("title", false);
					
				}
				
				if(setParamters.get("year")) {
					
					showYear(request, response);
					
					setParamters.replace("year", false);
				}
				
				if(setParamters.get("id")) {
					
					showID(request, response);
					
					setParamters.replace("id", false);
					
				}
				
				// shows all the films in the database in the database
				// json formated
				if(parameters.size() == 1) {
					
					showAllFilms(request, response);
					
				}
				
			}
			
			else if(format.equals("xml")) {
				
				if(setParamters.get("title")) {
										
					showTitleXML(request, response);
					
					setParamters.replace("title", false);
					
				}
				
				if(setParamters.get("year")) {
					
					showYearXML(request, response);
					
					setParamters.replace("year", false);
				}
				
				if(setParamters.get("id")) {
					
					showIDXML(request, response);
					
					setParamters.replace("id", false);
					
				}
				
				// shows all the films in the database in the database
				// xml formated
				if(parameters.size() == 1) {
					
					showAllFilmsXML(request, response);
					
				}
				
			}
			
			else if(format.equals("text")) {
				
				if(setParamters.get("title")) {
					
					showTitleTEXT(request, response);
					
					setParamters.replace("title", false);
				}
				
				if(setParamters.get("year")) {
					
					showYearTEXT(request, response);
					
					setParamters.replace("year", false);
				}
				
				if(setParamters.get("id")) {
					
					showIDTEXT(request, response); 
					
					setParamters.replace("id", false);
					
				}
				
				// shows all the films in the database in the database
				// formated csv
				if(parameters.size() == 1) {
					
					showAllFilmsTEXT(request, response);
					
				}
				
			}
			
			else {
				// if the parameter is not recognised error shows with the parameter that caused the error
				getErrorPage(request, response, "format "+format);
			}
			
			setParamters.replace("format", false);
			
		}
		
		// if the key-value {card} is true then the code below as a result
		if(setParamters.get("card")) {
			
			showCard(request, response);

			setParamters.replace("card", false);
		}
		
		// if the key-value {form} is true then the code below as a result
		if(setParamters.get("form")) {
			
			showForm(request, response);

			setParamters.replace("form", false);
		}
		
		// if the key-value {form} is true then the code below as a result
		if(setParamters.get("search")) {
			
			showSearch(request, response);
			
			setParamters.replace("search", false);
		}
		
	}

	/**
	 * Shows all the films in the database formated in CSV or sets the page to be 404 if the database has no films.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showAllFilmsTEXT(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Collection<Film> films = getAllFilms();
		
		if(films.size() > 0 )	{	
			
		showText(request, response, films);
		
		} else {
			
			response.setStatus(404);
		}
	}

	/**
	 * Shows all the films in the database with the specified id formated in CSV or sets the page to be 404 if the database has no films.
	 * @note : should only be one film on the jsp page. 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showIDTEXT(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Collection<Film> films = getById(request);
		
		if(films.size() > 0 )	{	
		
		showText(request, response, films);
		
		} else {
			
			response.setStatus(404);
		}
	}

	/**
	 * Shows all the films in the database with the specified year formated in CSV or sets the page to be 404 if the database has no films.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showYearTEXT(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Collection<Film> films = getByYear(request);
		
		if(films.size() > 0 )	{	
		
		showText(request, response, films);
		
		} else {
			
			response.setStatus(404);
		}
	}

	/**
	 * Shows all the films in the database with the specified title formated in CSV or sets the page to be 404 if the database has no films.
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showTitleTEXT(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Collection<Film> films = getByTitle(request);
		
		if(films.size() > 0 )	{	
		
		showText(request, response, films);
		
		} else {
			
			response.setStatus(404);
		}
	}

	/**
	 * Shows all the films in the database formated in xml or sets the page to be 404 if the database has no films.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showAllFilmsXML(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Collection<Film> films = getAllFilms();
		
		if(films.size() > 0 )	{	
			
			showXML(request, response, films);
			
		} else {
			
			response.setStatus(404);
			
		}
	}

	/**
	 * Shows all the films in the database with the specified id formated in xml or sets the page to be 404 if the database has no films.
	 * @note : should only be one film on the jsp page. 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showIDXML(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Collection<Film> films = getById(request);
		
		if(films.size() > 0 )	{	
			
			showXML(request, response, films);
			
		} else {
			
			response.setStatus(404);
			
		}
	}

	/**
	 * Shows all the films in the database with the specified year formated in xml or sets the page to be 404 if the database has no films.
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showYearXML(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Collection<Film> films = getByYear(request);
		
		if(films.size() > 0 )	{	
			
			showXML(request, response, films);
			
		} else {
			
			response.setStatus(404);
			
		}
	}

	/**
     * Shows all the films in the database with the specified title formated in xml or sets the page to be 404 if the database has no films.
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showTitleXML(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Collection<Film> films = getByTitle(request);
		
		if(films.size() > 0 )	{	
			
			showXML(request, response, films);
			
		} else {
			
			response.setStatus(404);
			
		}
	}

	/**
	 * Shows all the films in the database formated in json or sets the page to be 404 if the database has no films.
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showAllFilms(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Collection<Film> films = getAllFilms();
		
		if(films.size() > 0 )	{	
			
			showJson(request, response, films);
		
		} else {
			
			response.setStatus(404);
		}
	}

	/**
	 * Shows all the films in the database with the specified id formated in json or sets the page to be 404 if the database has no films.
	 * @note : should only be one film on the jsp page. 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showID(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Collection<Film> films = getById(request);
		
		if(films.size() > 0 )	{	
			
			showJson(request, response, films);
			
		} else {
			
			response.setStatus(404);
			
		}
	}

	/**
	 * Shows all the films in the database with the specified year formated in json or sets the page to be 404 if the database has no films.
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showYear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Collection<Film> films = getByYear(request);
		
		if(films.size() > 0 )	{	
			
			showJson(request, response, films);
			
		} else {
			
			response.setStatus(404);
			
		}
	}

	/**
	 * Shows all the films in the database with the specified title formated in json or sets the page to be 404 if the database has no films.
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showTitle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Collection<Film> films = getByTitle(request);
		
		if(films.size() > 0 )	{	
			
			showJson(request, response, films);
			
		} else {
			
			response.setStatus(404);
			
		}
	}

	/**
	 * Shows all the films in the database with the specified title formated in json or sets the page to be 404 if the database has no films.
	 * @note : should only be 15 films on the jsp page
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// shows a page with 15 films represented in JSON format
		Collection<Film> films = getFilmByPage(request);
		
		if(films.size() > 0 )	{	
		
		showJson(request, response, films);
		
		} else {
			
			response.setStatus(404);
		}
	}

	/**
	 * 	
	 * Uses the request parameter to get films with the matching title.  
	 * Uses the DAO to get the film
	 * 
	 * 
	 * @param request
	 */
	private Collection<Film> getByTitle(HttpServletRequest request) {
		
		// Getting instance
		FilmDao filmDB = FilmDao.getDao();
		
		String title = request.getParameter("title");
		
		Collection<Film> collection = filmDB.retrieveFilm(title);
		
		return collection;
		
}
	/**
	 * 	
	 * Uses the request parameter to get films with the matching year.  
	 * Uses the DAO to get the film
	 * 
	 * @param request
	 */
	private Collection<Film> getByYear(HttpServletRequest request) {
		
		// Getting instance
		FilmDao filmDB = FilmDao.getDao();
		
		int year = Integer.valueOf(request.getParameter("year"));
		
		Collection<Film> collection = filmDB.retrieveFilm(year);
		
		return collection;
		
	}
	/**
	 * 	
	 * Uses the request parameter to get films with the matching id.  
	 * Uses the DAO to get the film
	 * 
	 * @param request
	 */
	private Collection<Film> getById(HttpServletRequest request) {
		
		// Getting instance
		FilmDao filmDB = FilmDao.getDao();
		
		int id = Integer.valueOf(request.getParameter("id"));
		
		Collection<Film> collection = filmDB.retrieveFilmByID(id);
		
		return collection;
		
	}
	

	/**
	 * Shows the JSP page for Search inputs
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showSearch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// shows the search jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher(SEARCH_JSP);
		dispatcher.forward(request, response);
	}

	/**
	 * Shows the JSP page for Form inputs
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// shows the form jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher(FORM_JSP);
		dispatcher.forward(request, response);
	}

	/**
	 * Shows the JSP page for Card
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showCard(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// shows the card jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher(CARD_JSP);
		dispatcher.forward(request, response);
	}

	/**
	 *  Converts a film collection to Text formated data then forwards the formatted data to 
	 *  a specified JSP page. Uses the ConverterFactory to do the formatting of the data.
	 * 
	 * @param request 
	 * @param response
	 * @param films 
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showText(HttpServletRequest request, HttpServletResponse response, Collection<Film> films)
			throws ServletException, IOException {
		String text = ConverterFactory.getFormat("text", films);
		
		request.setAttribute("films", text);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(FORMAT_JSP);
		dispatcher.forward(request, response);
	}

	/**
	 * Converts a film collection to XML formated data then forwards the formatted data to 
	 * a specified JSP page. Uses the ConverterFactory to do the formatting of the data.
	 * @param request
	 * @param response
	 * @param films
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showXML(HttpServletRequest request, HttpServletResponse response, Collection<Film> films)
			throws ServletException, IOException {
		String xml = ConverterFactory.getFormat("xml", films);
		
		request.setAttribute("films", xml);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(FORMAT_JSP);
		dispatcher.forward(request, response);
	}

	/**
	 * Converts a film collection to Json formated data then forwards the formatted data to 
	 * a specified JSP page. Uses the ConverterFactory to do the formatting of the data.
	 * 
	 * @param request
	 * @param response
	 * @param films
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showJson(HttpServletRequest request, HttpServletResponse response, Collection<Film> films)
			throws ServletException, IOException {
		String json = ConverterFactory.getFormat("json", films);
		
		request.setAttribute("films", json);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(FORMAT_JSP);
		dispatcher.forward(request, response);
	}
	
	@Override
	public void checkParameters(Map<String, String[]> requestParam, Map<String, Boolean> definedParam) {
		requestParam.forEach((key, value) -> { 
			
			if(definedParam.containsKey(key)){
				
				definedParam.replace(key, true);
				
			} else {
				
				definedParam.replace("error", true);
			}

		});
	}
	
	/**
	 * Uses the request parameter to get 15 films  (page 0 == 15 films) and so on.  
	 * Uses an (or the) instance of the DAO to take to the SQL server 
	 *  
	 * @return A collection of films that were the result a MYSQL query select 
	 */
	private Collection<Film> getFilmByPage(HttpServletRequest request) {
		
		FilmDao filmDB = FilmDao.getDao();
		
		int page = Integer.parseInt(request.getParameter("page"));
		
		Collection<Film> collection = filmDB.listFilm(page);
		
		return collection;
		
	}
	
	/**
	 *  Gets all the films in the DB
	 * 
	 * @return Collection of all the films in the database. 
	 */
	private Collection<Film> getAllFilms() {
		
		FilmDao filmDB = FilmDao.getDao();
		
		Collection<Film> collection = filmDB.listFilm();
		
		return collection;
	}
	
	@Override
	public Map<String, Boolean> acceptedParams() {
	// map with the accept parameters
		Map<String, Boolean> map = new HashMap<>();
		
		map.put("format",false);
		map.put("title",false);
		map.put("year",false);
		map.put("id",false);
		map.put("page",false);
		map.put("form",false);	
		map.put("card",false);
		map.put("search",false);
		map.put("error", false);
		
		return map;
	}
	
	/**
	 * Forwards to the jsp page for errors 
	 * 
	 * @param request
	 * @param response
	 * @param token Gets displayed on the jsp page for context if needed  
	 * @throws ServletException
	 * @throws IOException
	 */
	private void getErrorPage(HttpServletRequest request, HttpServletResponse response, String token)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setStatus(404);
		request.setAttribute("error", token);
		RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_JSP);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}


