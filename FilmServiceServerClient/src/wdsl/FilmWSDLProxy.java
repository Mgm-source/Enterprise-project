package wdsl;

public class FilmWSDLProxy implements wdsl.FilmWSDL {
  private String _endpoint = null;
  private wdsl.FilmWSDL filmWSDL = null;
  
  public FilmWSDLProxy() {
    _initFilmWSDLProxy();
  }
  
  public FilmWSDLProxy(String endpoint) {
    _endpoint = endpoint;
    _initFilmWSDLProxy();
  }
  
  private void _initFilmWSDLProxy() {
    try {
      filmWSDL = (new wdsl.FilmWSDLServiceLocator()).getFilmWSDL();
      if (filmWSDL != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)filmWSDL)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)filmWSDL)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (filmWSDL != null)
      ((javax.xml.rpc.Stub)filmWSDL)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public wdsl.FilmWSDL getFilmWSDL() {
    if (filmWSDL == null)
      _initFilmWSDLProxy();
    return filmWSDL;
  }
  
  public java.lang.String getFilmByID(int id) throws java.rmi.RemoteException{
    if (filmWSDL == null)
      _initFilmWSDLProxy();
    return filmWSDL.getFilmByID(id);
  }
  
  public java.lang.String getFilmbyTitle(java.lang.String title) throws java.rmi.RemoteException{
    if (filmWSDL == null)
      _initFilmWSDLProxy();
    return filmWSDL.getFilmbyTitle(title);
  }
  
  public java.lang.String getFilmByYear(int year) throws java.rmi.RemoteException{
    if (filmWSDL == null)
      _initFilmWSDLProxy();
    return filmWSDL.getFilmByYear(year);
  }
  
  public boolean updateFilm(film.Film film) throws java.rmi.RemoteException{
    if (filmWSDL == null)
      _initFilmWSDLProxy();
    return filmWSDL.updateFilm(film);
  }
  
  public boolean insertFilm(film.Film film) throws java.rmi.RemoteException{
    if (filmWSDL == null)
      _initFilmWSDLProxy();
    return filmWSDL.insertFilm(film);
  }
  
  public boolean deleteFilm(int id) throws java.rmi.RemoteException{
    if (filmWSDL == null)
      _initFilmWSDLProxy();
    return filmWSDL.deleteFilm(id);
  }
  
  public java.lang.String getAllFilms() throws java.rmi.RemoteException{
    if (filmWSDL == null)
      _initFilmWSDLProxy();
    return filmWSDL.getAllFilms();
  }
  
  
}