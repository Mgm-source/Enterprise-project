package com.enterpriseproject.film;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Film {
	private int pkid;
	private String title;
	private int year;
	private String director;
	private String stars;
	private String review;
	
	public Film() {}

	public int getPkid() {
		return pkid;
	}

	public void setPkid(int pkid) {
		this.pkid = pkid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getStars() {
		return stars;
	}

	public void setStars(String stars) {
		this.stars = stars;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
	
	public Film(int pkid, String title, int year, String director, String stars, String review) {
		this.pkid=pkid;
		this.title=title;
		this.year=year;
		this.director=director;
		this.stars=stars;
		this.review=review;
	}
	
	public Film(String title, int year, String director, String stars, String review) {
		this.title=title;
		this.year=year;
		this.director=director;
		this.stars=stars;
		this.review=review;
	}

	@Override
	public String toString() {
		return "Film [pkid=" + pkid + ", title=" + title + ", year=" + year + ", director=" + director + ", stars="
				+ stars + ", review=" + review + "]";
	}

}
