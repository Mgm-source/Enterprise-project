/**
 * FilmWSDLServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package wdsl;

public class FilmWSDLServiceLocator extends org.apache.axis.client.Service implements wdsl.FilmWSDLService {

    public FilmWSDLServiceLocator() {
    }


    public FilmWSDLServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public FilmWSDLServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for FilmWSDL
    private java.lang.String FilmWSDL_address = "http://localhost:8080/FilmServiceServer/services/FilmWSDL";

    public java.lang.String getFilmWSDLAddress() {
        return FilmWSDL_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String FilmWSDLWSDDServiceName = "FilmWSDL";

    public java.lang.String getFilmWSDLWSDDServiceName() {
        return FilmWSDLWSDDServiceName;
    }

    public void setFilmWSDLWSDDServiceName(java.lang.String name) {
        FilmWSDLWSDDServiceName = name;
    }

    public wdsl.FilmWSDL getFilmWSDL() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(FilmWSDL_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getFilmWSDL(endpoint);
    }

    public wdsl.FilmWSDL getFilmWSDL(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            wdsl.FilmWSDLSoapBindingStub _stub = new wdsl.FilmWSDLSoapBindingStub(portAddress, this);
            _stub.setPortName(getFilmWSDLWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setFilmWSDLEndpointAddress(java.lang.String address) {
        FilmWSDL_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (wdsl.FilmWSDL.class.isAssignableFrom(serviceEndpointInterface)) {
                wdsl.FilmWSDLSoapBindingStub _stub = new wdsl.FilmWSDLSoapBindingStub(new java.net.URL(FilmWSDL_address), this);
                _stub.setPortName(getFilmWSDLWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("FilmWSDL".equals(inputPortName)) {
            return getFilmWSDL();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://wdsl", "FilmWSDLService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://wdsl", "FilmWSDL"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("FilmWSDL".equals(portName)) {
            setFilmWSDLEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
