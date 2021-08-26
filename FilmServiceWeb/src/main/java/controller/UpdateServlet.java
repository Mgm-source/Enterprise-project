package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.FilmDao;

/**
 * Servlet implementation class InsertServlet
 */
@WebServlet("/Update")
public class UpdateServlet extends HttpServlet implements ParameterCheck {
	private static final long serialVersionUID = 1L;
	private static final String ERROR_JSP = "WEB-INF/jsp/error.jsp";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map<String, String[]> parameters = request.getParameterMap();
		Map<String, Boolean> setParamters = acceptedParams();
		FilmDao filmDB = FilmDao.getDao(); 
		
		// checking if there are parameters 
		if(parameters.isEmpty()) { 
			
			// shows the error page
			getErrorPage(request, response, "No Parameters");
			
		}
		// checks the parameters sent with the parameters that are set my acceptedParams
		// if there's a match that specific key's value will be set to true
		checkParameters(parameters, setParamters);
		
		
		if(setParamters.get("error")) {
			
			//shows a page displaying an error
			getErrorPage(request, response, "Invaild Parameters");
			
			setParamters.replace("error", false);
			
			// Removes the error key-value "error" for vaildation and put its back again after its done 
		} else if(setParamters.remove("error", false)) {
			
			if(setParamters.containsValue(false)) {
				// displays error message
				getErrorPage(request, response, "Missing Parameters");
			}
			// adding the entry back
			setParamters.put("error", false);
		}
			// when there is no errors or (additional parameters that arent recognised) the servlet updates a film
			if(setParamters.get("error").equals(false))
			{
				
				int id = Integer.valueOf(request.getParameter("id"));
				String title = request.getParameter("title");
				int year = Integer.valueOf(request.getParameter("year"));
				String director = request.getParameter("director");
				String stars = request.getParameter("stars");
				String review =request.getParameter("review");
			
				filmDB.updateFilm(filmDB.createFilm(id, title, year, director, stars, review));
				
				// checks if the sql server did the operation and sends a response based on that
				if (filmDB.getOperation() == 1) {
					
					response.setStatus(200);
				}
				else {
					response.setStatus(500);
				}
			}
			
	
	
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
	
	@Override
	public Map<String, Boolean> acceptedParams() {
	// map with the accept parameters
		Map<String, Boolean> map = new HashMap<>();
		
		map.put("title",false);
		map.put("director",false);
		map.put("year",false);
		map.put("review",false);
		map.put("stars",false);
		map.put("id",false);
		map.put("error",false);
		
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
