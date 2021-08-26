package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import dao.ConnectFactory;
import film.Film;
import film.FilmInfo;

	public class FilmDao implements FilmInfo{
		// initalizing global instance var
		private static FilmDao instance;
		
		private int operation = 0;
		
		private FilmDao() {}
		// Singleton FilmDao 
		
		public static FilmDao getDao() {
			if(instance == null) {
				instance = new FilmDao();
			}
			return instance;
		}
		
		/**
		 * Creates a new film
		 * 
		 * @param id
		 * @param title
		 * @param year
		 * @param director
		 * @param stars
		 * @param review
		 * @return Film
		 */
		public Film createFilm(int id, String title, int year, String director, String stars, String review) {
			// This method returns a film using the constructor to insert data.
			return new Film(id, title, year, director, stars, review);
		}
		
		public Film createFilm(String title, int year, String director, String stars, String review) {
			// This method returns a film using the constructor to insert data.
			return new Film(title, year, director, stars, review);
		}
		
		@Override
		public void insertFilm(Film film) {
			String sql= "INSERT INTO films(title,year,director,stars,review) VALUES (?,?,?,?,?) ";
			try {
				// Gets the instance of the connection and uses it to create a statement that assigns the film objects properties 
				PreparedStatement statement = ConnectFactory.getInstance().connect().prepareStatement(sql);
				statement.setString(1, film.getTitle());
				statement.setInt(2, film.getYear());
				statement.setString(3, film.getDirector());
				statement.setString(4, film.getStars());
				statement.setString(5, film.getReview());
			
				setOperation(statement.executeUpdate());
			
			} catch(SQLException SQE) {
				SQE.printStackTrace();
			}
				
		}

		@Override
		public void updateFilm(Film film) {
			String sql=	"UPDATE films SET title = ?, year = ?, director = ?, stars = ?, review = ? Where id = ?";
			try {
				// Gets the instance of the connection and uses it to create a statement that assigns the film objects properties 
				PreparedStatement statement = ConnectFactory.getInstance().connect().prepareStatement(sql);
				statement.setString(1, film.getTitle());
				statement.setInt(2, film.getYear());
				statement.setString(3, film.getDirector());
				statement.setString(4, film.getStars());
				statement.setString(5, film.getReview());
				statement.setInt(6, film.getId());
			
				setOperation(statement.executeUpdate());
			
			} catch(SQLException SQE) {
				SQE.printStackTrace();
			}
			
		}

		@Override
		public void deleteFilm(int id) {
			String sql = "DELETE FROM films WHERE ID =?";
			try {
				// Gets the instance of the connection and uses it to create a statement that deletes a film 
				PreparedStatement statement = ConnectFactory.getInstance().connect().prepareStatement(sql);
				statement.setInt(1,id);
				setOperation(statement.executeUpdate());
				
			} catch(SQLException SQE) {
				SQE.printStackTrace();
				// Do not need to explicitly close the connection, the dispose function defaults when leaving blocks
			}
	
		}
		
		@Override
		public Collection<Film> listFilm() {
			ArrayList<Film> list = new ArrayList<Film>();
			String sql = "SELECT * FROM films";
			try {
				// Gets the instance of the connection and uses it to create a statement that gets all the films in the db 
				PreparedStatement statement = ConnectFactory.getInstance().connect().prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery();
				while(resultSet.next()) {
					int id = resultSet.getInt("id");
					String title = resultSet.getString("title");
					int year = resultSet.getInt("year");
					String director = resultSet.getString("director");
					String stars = resultSet.getString("stars");
					String review = resultSet.getString("review");
					
					Film film = createFilm(id,title,year,director,stars,review);
					list.add(film);
				}
				
			} catch(SQLException SQE) {
				SQE.printStackTrace();
			}
			// Do not need to explicitly close the connection, the dispose function defaults when leaving blocks
			return list;
		}
		
		public Collection<Film> listFilm(int page) {
			if(page>0) {
				page = page + 10;
			}else {
				page = 0;
			}
			
			ArrayList<Film> list = new ArrayList<Film>();
			String sql = "SELECT * FROM films limit 15 offset ?";
			try {
				// Gets the instance of the connection and uses it to create a statement that gets all the films in the db 
				PreparedStatement statement = ConnectFactory.getInstance().connect().prepareStatement(sql);
				statement.setInt(1,page);
				ResultSet resultSet = statement.executeQuery();
				while(resultSet.next()) {
					int id = resultSet.getInt("id");
					String title = resultSet.getString("title");
					int year = resultSet.getInt("year");
					String director = resultSet.getString("director");
					String stars = resultSet.getString("stars");
					String review = resultSet.getString("review");
					
					Film film = createFilm(id,title,year,director,stars,review);
					list.add(film);
				}
			} catch(SQLException SQE) {
				SQE.printStackTrace();
			}
			// Do not need to explicitly close the connection, the dispose function defaults when leaving blocks
			return list;
		}
		
		@Override
		public Collection<Film> retrieveFilm(String title) {
			ArrayList<Film> list = new ArrayList<Film>();
			title = title.toUpperCase();
			String sql = "SELECT * FROM films WHERE title LIKE ?";
			try {
				/* Gets the instance of the connection and uses it to create a statement that gets all the films
				   in the database that are similar to the title
				*/
				PreparedStatement statement = ConnectFactory.getInstance().connect().prepareStatement(sql);
				statement.setString(1, "%" + title + "%");
				ResultSet resultSet = statement.executeQuery();
				while(resultSet.next()) {
					int id = resultSet.getInt("id");
					title = resultSet.getString("title");
					int year = resultSet.getInt("year");
					String director = resultSet.getString("director");
					String stars = resultSet.getString("stars");
					String review = resultSet.getString("review");
					
					Film film = createFilm(id,title,year,director,stars,review);
					list.add(film);
				}
				
		} catch(SQLException SQE) {
			SQE.printStackTrace();
			// Do not need to explicitly close the connection, the dispose function defaults when leaving blocks
			}
			return list;
		}
		
		 /**
			 * Gets a specified film.
			 * Returns a collection of nothing if nothing is found
			 * @param year
			 * @return requested film or films in a collection
			 */
		public Collection<Film> retrieveFilm(int year) {
			ArrayList<Film> list = new ArrayList<Film>();
			String sql = "SELECT * FROM films WHERE year = ?";
			try {
				/* Gets the instance of the connection and uses it to create a statement that gets all the films
			 		in the database that are from a specified year
				*/
				PreparedStatement statement = ConnectFactory.getInstance().connect().prepareStatement(sql);
				statement.setInt(1,year);
				ResultSet resultSet = statement.executeQuery();
				while(resultSet.next()) {
					int id = resultSet.getInt("id");
					String title = resultSet.getString("title");
					year = resultSet.getInt("year");
					String director = resultSet.getString("director");
					String stars = resultSet.getString("stars");
					String review = resultSet.getString("review");
					
					Film film = createFilm(id,title,year,director,stars,review);
					list.add(film);
		}
			} catch(SQLException SQE) {
				SQE.printStackTrace();
				// Do not need to explicitly close the connection, the dispose function defaults when leaving blocks
			}
			return list;
		}

		
		
		public Collection<Film> retrieveFilmByID(int id) {
			ArrayList<Film> list = new ArrayList<Film>();
			String sql = "SELECT * FROM films WHERE id = ?";
			try {
				/* Gets the instance of the connection and uses it to create a statement that gets all the films
			 		in the database that are from a specified year
				*/
				PreparedStatement statement = ConnectFactory.getInstance().connect().prepareStatement(sql);
				statement.setInt(1,id);
				ResultSet resultSet = statement.executeQuery();
				while(resultSet.next()) {
					id = resultSet.getInt("id");
					String title = resultSet.getString("title");
					int year = resultSet.getInt("year");
					String director = resultSet.getString("director");
					String stars = resultSet.getString("stars");
					String review = resultSet.getString("review");
					
					Film film = createFilm(id,title,year,director,stars,review);
					list.add(film);
					}
				} catch(SQLException SQE) {
					SQE.printStackTrace();
					// Do not need to explicitly close the connection, the dispose function defaults when leaving blocks
					}
			return list;
			}

		public int getOperation() {
			return operation;
		}

		private void setOperation(int operation) {
			this.operation = operation;
		}
		}
	