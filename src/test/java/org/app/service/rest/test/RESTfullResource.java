package org.app.service.rest.test;

import java.io.StringWriter;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.util.GenericType;


public class RESTfullResource <T extends Object> {
	private static Logger logger = Logger.getLogger(RESTfullResource.class.getName());
	private enum HTTP_METHOD {GET, POST, PUT, DELETE};
	private String basePath;
	// for simple DTO
	private Class<T> entityResourceClass;
	// for collection DTO
	private GenericType resourceGenericType;
	private String mediaType;
	private ClientRequest request;
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	public Class<T> getEntityResourceClass() {
		return entityResourceClass;
	}
	public void setEntityResourceClass(Class entityResourceClass) {
		this.entityResourceClass = entityResourceClass;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	// Constructor pentru cereri generice (fara tipizare raspuns interpretat ca simple String)
	public RESTfullResource(String basePath) {
		this.basePath = basePath;
		this.entityResourceClass = (Class<T>) String.class;
	}		
	// Constructor pentru Reourse simple
	public RESTfullResource(String basePath, Class entityResourceClass, String mediaType) {
		this.basePath = basePath;
		if(entityResourceClass != null)
			this.entityResourceClass = entityResourceClass;
		else
			this.entityResourceClass = (Class<T>) String.class;
		this.mediaType = mediaType;
	}
	// Constructor pentru Resurse colectii
	public RESTfullResource(String basePath,String mediaType, GenericType genericType) {
		this(basePath, null, mediaType);
		this.resourceGenericType = genericType;
	}
	
	/* HTTP Methods */
	public T get() throws Exception{
		return (T) this.invokeResourceRequest(HTTP_METHOD.GET, null);
	}
	public T put(Object entity) throws Exception{
		return (T) this.invokeResourceRequest(HTTP_METHOD.PUT, entity);
	}	
	public T post(Object entity) throws Exception{
		return (T) this.invokeResourceRequest(HTTP_METHOD.POST, entity);
	}	
	public void delete(Object entity) throws Exception{
		this.invokeResourceRequest(HTTP_METHOD.DELETE, entity);
	}	
	public void delete() throws Exception{
		this.invokeResourceRequest(HTTP_METHOD.DELETE, null);
	}	
	
	/* Internals */
	@SuppressWarnings("unchecked")
	
	private  T invokeResourceRequest(HTTP_METHOD requestType, Object entity) throws Exception{
		// Creare cerere
		this.request = new ClientRequest(this.basePath);
		if(this.mediaType != null)
			this.request.accept(this.mediaType);			
		if(this.request == null)
			throw new Exception("Failed REST: REST request object not initialized!");
		logger.info("DEBUG resource request " + request.getUri());
		// Cererile GET nu trebuie sa aiba body
		if(entity != null && !requestType.equals(HTTP_METHOD.GET))
			this.request.body(this.mediaType, mapEntityToMediaType(entity));
		// Trimitere cerere si receptionare raspuns
		ClientResponse<T> response = null;
		if(requestType.equals(HTTP_METHOD.GET)){
			response = this.request.get();
			this.request.clear();
		}
		if(requestType.equals(HTTP_METHOD.PUT))
			response = this.request.put();
		if(requestType.equals(HTTP_METHOD.POST))
			response = this.request.post();
		if(requestType.equals(HTTP_METHOD.DELETE))
			response = this.request.delete();
		// Verificare Status raspuns
		int responseCode = response.getResponseStatus().getStatusCode();
		logger.info("DEBUG resource RESPONSE --- CODE: " + responseCode);
		if(requestType.equals(HTTP_METHOD.GET)){
			if(responseCode != 200){
		        throw new RuntimeException("Failed with HTTP error code : " + responseCode);
		    }			
		}
		// Decodificare raspuns
	    try{
	    	// pentru colectii
	    	if (this.resourceGenericType != null)
	    		return (T) response.getEntity(this.resourceGenericType);
	    	// pentru tipuri DTO simple
		    if (response.getEntity(this.entityResourceClass) != null)
		    	return (T) response.getEntity(this.entityResourceClass);
	    }catch(Exception ex){
	    	ex.printStackTrace();
	    	logger.info("DEBUG resource request ERROR PARSING RESPONSE " + ex.getMessage());
	    }
	    return null;		
	}
	
	private String mapEntityToMediaType(Object entity) throws Exception{
		StringWriter writer = new StringWriter();
	    JAXBContext jaxbContext = JAXBContext.newInstance(entity.getClass());
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	    jaxbMarshaller.marshal(entity, writer);
	    return writer.getBuffer().toString();
	}
}