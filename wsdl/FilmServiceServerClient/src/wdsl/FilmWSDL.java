/**
 * FilmWSDL.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package wdsl;

public interface FilmWSDL extends java.rmi.Remote {
    public java.lang.String getFilmByID(int id) throws java.rmi.RemoteException;
    public java.lang.String getFilmbyTitle(java.lang.String title) throws java.rmi.RemoteException;
    public java.lang.String getFilmByYear(int year) throws java.rmi.RemoteException;
    public boolean updateFilm(film.Film film) throws java.rmi.RemoteException;
    public boolean insertFilm(film.Film film) throws java.rmi.RemoteException;
    public boolean deleteFilm(int id) throws java.rmi.RemoteException;
    public java.lang.String getAllFilms() throws java.rmi.RemoteException;
}
