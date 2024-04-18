package com.sedapal.tramite.servicios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sedapal.tramite.beans.CredencialesAPI;
import com.sedapal.tramite.beans.RemitenteBPM;
import com.sedapal.tramite.beans.TiposDocumentosBean;
import com.sedapal.tramite.dao.EntranteDAO;
import com.sedapal.tramite.dao.ICredencialesApiDAO;
import com.sedapal.tramite.nova.util.CasillaElectronicaResponse;
import com.sedapal.tramite.nova.util.DocumentoDTO;
import com.sedapal.tramite.nova.util.UsuarioExternoResponse;

public class ServiciosAPI implements IServiciosAPI {

	private ICredencialesApiDAO credencialesApiDao;
	private CredencialesAPI credenciales;
	private EntranteDAO entranteDAO;
	
	private List<TiposDocumentosBean> listTipoDoc;
	 
	 
	
	
	public List<TiposDocumentosBean> getListTipoDoc() {
		return listTipoDoc;
	}



	public void setListTipoDoc(List<TiposDocumentosBean> listTipoDoc) {
		this.listTipoDoc = listTipoDoc;
	}



	public EntranteDAO getEntranteDAO() {
		return entranteDAO;
	}



	public void setEntranteDAO(EntranteDAO entranteDAO) {
		this.entranteDAO = entranteDAO;
	}



	public ICredencialesApiDAO getCredencialesApiDao() {
		return credencialesApiDao;
	}



	public void setCredencialesApiDao(ICredencialesApiDAO credencialesApiDao) {
		this.credencialesApiDao = credencialesApiDao;
	}



	private String obtenerToken() {
		Gson gson = new Gson();
		String respuesta;
		credenciales = new CredencialesAPI();
		credenciales= credencialesApiDao.obtener();
		 CloseableHttpClient httpclient = HttpClients.createDefault();
		 
//		 HttpPost httpPost = new HttpPost("https://sedapal.indenova.net/PortalEmpleado/api/rest/security/v1/authentication/authenticate");
//		 httpPost.setHeader("Accept", "*/*");
//		 httpPost.setHeader("Content-type", "application/json");
//		 httpPost.setHeader("esigna-auth-api-key", "a0eba7045aa8b4ba9b17");
//		 httpPost.setHeader("Authorization", "Basic MjI5YzBjYzM5YmYzMGQ3NThiMDRmMDUwMzQyYTk2Y2E6NTQ1ZDUzYTY5YWIyNDM1MGZkZjhkZjY2OTk4ODQ5M2JjYzdkMjFkZmE0YjBlMjdkMGU2ZTg1MWE1NGRmODU5NQ==");
		 
		 HttpPost httpPost = new HttpPost(credenciales.getUrl());
		 httpPost.setHeader("Accept", "*/*");
		 httpPost.setHeader("Content-type", "application/json");
		 httpPost.setHeader("esigna-auth-api-key", credenciales.getKey());
		 httpPost.setHeader("Authorization", credenciales.getAuthorization());
		 System.setProperty("https.protocols", "TLSv1");
		 try {
		 CloseableHttpResponse  responseBody ;
		 // INI - TLSv1.2
		 SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
		 sslContext = SSLContexts.custom().useProtocol("TLSv1.2").build();
		 httpclient = HttpClientBuilder.create().setSslcontext(sslContext).build();
		// FIN - TLSv1.2
		 responseBody = httpclient.execute(httpPost);	
		 StatusLine sl = responseBody.getStatusLine();   
			 if (sl.getStatusCode() != 404) {
				 //String response = gson.fromJson(responseBody.getEntity().toString(), String.class);
				 String response = EntityUtils.toString(responseBody.getEntity());
				 respuesta = response;
	    	 }else {
	    		 System.out.println("Error al llamar al servicio de obtener token: "+ sl.getStatusCode() );
	    		 respuesta = null;
	    	 }
		 }catch(Exception e) {
			 System.out.println("Error al llamar al servicio de obtener token: "+ e.getMessage() );
			 respuesta = null;
		 }
		 return respuesta;
	}

	@Override
	public boolean consultaCasilla(DocumentoDTO documento, List<TiposDocumentosBean> td) {
		UsuarioExternoResponse remitente = new UsuarioExternoResponse();
		remitente=this.consultarPorTipoDocumento(documento.getTipo_documento(),documento.getNumero_documento(), td);
		if (remitente != null) {
			if (remitente.getCasilla().equals("1")) {
				return true;
			} 
		}
		return false;
	}

	private UsuarioExternoResponse consultarPorTipoDocumento(String codTipoDocumento, String numeroDocumento, List<TiposDocumentosBean> td) {
		// TODO Auto-generated method stub
		this.setListTipoDoc(td);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		 UsuarioExternoResponse respuesta = new UsuarioExternoResponse();
		String ruta;
		String token=this.obtenerToken();
		String abrevTipoDocumento = this.getAbrevTipoDocumento(codTipoDocumento);
		ruta=credencialesApiDao.getRuta(4).concat(abrevTipoDocumento).concat("/").concat(numeroDocumento);
		System.out.println(ruta);
		 Gson gson = new Gson();
		 respuesta = null;
		 try {
		HttpGet httpPost = new HttpGet(ruta);
		httpPost.setHeader("Accept", "*/*");
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setHeader("esigna-auth-api-key", credenciales.getKey());
		httpPost.setHeader("Authorization","Bearer "+ token);
		System.setProperty("https.protocols", "TLSv1");
		 try {
			 CloseableHttpResponse  responseBody ;
			// INI - TLSv1.2
			 SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
			 sslContext = SSLContexts.custom().useProtocol("TLSv1.2").build();
			 httpclient = HttpClientBuilder.create().setSslcontext(sslContext).build();
			// FIN - TLSv1.2
			 responseBody = httpclient.execute(httpPost);	
			 StatusLine sl = responseBody.getStatusLine();   
			
				 if (sl.getStatusCode() == 200) {
					 String jsonString = EntityUtils.toString(responseBody.getEntity());
					 System.out.println(jsonString);
					 respuesta= gson.fromJson(jsonString, UsuarioExternoResponse.class);		
		    	 }else {
		    		  if (sl.getStatusCode() == 400) {		    			
		    			System.out.println("No existe resultado consultarPorTipoDocumento()");
		    		  }
		    		  if (sl.getStatusCode() == 401) {
			    			System.out.println("Error de acceso no autorizado consultarPorTipoDocumento()");
			    		  }
		    		  if (sl.getStatusCode() == 500) {
			    			System.out.println("Error procesando peticion consultarPorTipoDocumento()");
			    		  }
		    	 }
			 }catch(Exception e) {
				 System.out.println("Error al llamar al servicio de obtener token: "+ e.getMessage() );
			 }
		 }catch(Exception e) {
			 System.out.println("Error en ruta : " + ruta);
			 System.out.println(e.getMessage());				 
		 }
		 return respuesta;
	}
	
	@Override
	public RemitenteBPM actualizarPorTipoDocumento(String codTipoDocumento, String numeroDocumento, List<TiposDocumentosBean> td) {
		// TODO Auto-generated method stub
		 RemitenteBPM nuevoRemitente = null;
		 UsuarioExternoResponse respuesta = new UsuarioExternoResponse();
		 respuesta=null;
		 respuesta = consultarPorTipoDocumento( codTipoDocumento,  numeroDocumento, td);
		 if (respuesta!=null) {
			 nuevoRemitente = registraRemitenteBPM(respuesta);	 
		 }	 

		 return nuevoRemitente;
	}
	
	public String enviarNotificacionBPM2(CasillaElectronicaResponse ce) {
		try {
		// Configurar la URL del servicio API y abrir la conexión
		Gson gson = new Gson();
		String jsonDocumentoRequest = gson.toJson(ce);
		String ruta;
		String token=this.obtenerToken();
		ruta=credencialesApiDao.getRuta(5);
	    
		 String parametro1 = jsonDocumentoRequest; // Aquí se define el valor del parámetro

	        // Construir el cuerpo de la petición
	        HttpEntity httpEntity = MultipartEntityBuilder.create()
	                .addTextBody("data", parametro1)
	                .build();

	        // Crear la conexión HTTP y establecer los encabezados
	        HttpPost httpPost = new HttpPost(ruta);
	        httpPost.setEntity(httpEntity);
	        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer "+token);
	        httpPost.setHeader("esigna-auth-api-key", credenciales.getKey());
	        System.setProperty("https.protocols", "TLSv1");
	        
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	        // INI - TLSv1.2
			 SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
			 sslContext = SSLContexts.custom().useProtocol("TLSv1.2").build();
			 httpClient = HttpClientBuilder.create().setSslcontext(sslContext).build();
			// FIN - TLSv1.2
	        
	        // Ejecutar la solicitud y obtener la respuesta
	        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

	            // Leer la respuesta
	            HttpEntity responseEntity = response.getEntity();
	            StatusLine sl = response.getStatusLine();   
	            if (responseEntity != null) {
	                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(responseEntity.getContent(), StandardCharsets.UTF_8))) {
	                    StringBuilder responseBuilder = new StringBuilder();
	                    String line;
	                    while ((line = bufferedReader.readLine()) != null) {
	                        responseBuilder.append(line).append("\n");
	                    }
	                    JsonObject jsonObject = gson.fromJson(responseBuilder.toString(), JsonObject.class);
	                    String code = jsonObject.get("code").getAsString();
	                    String message = jsonObject.get("message").getAsString();
	                    System.out.println("code: " + code);	                   
	                    System.out.println("message: " + message);
	                    return message;
	                }
	            }
	        }

    
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	    return "";
	  }
	
	//a usarse cuando se requiere JSON
	@Override
	public String enviarNotificacionBPM(CasillaElectronicaResponse ce) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String ruta;
		String token=this.obtenerToken();
		String response="";
		
		ruta=credencialesApiDao.getRuta(5);
		System.out.println(ruta);
		
		Gson gson = new Gson();
		 try {
		HttpPost httpPost = new HttpPost(ruta);
		httpPost.setHeader("Accept", "*/*");		 
		httpPost.setHeader("esigna-auth-api-key", credenciales.getKey());
		 httpPost.setHeader("Authorization","Bearer "+ token);
		 System.setProperty("https.protocols", "TLSv1");
		 try {
			 
		 	 String jsonDocumentoRequest = gson.toJson(ce);
		 	StringEntity stringEntity;
		 	stringEntity = new StringEntity(jsonDocumentoRequest, "UTF-8");
		 	System.out.println(jsonDocumentoRequest);
		 	

			httpPost.setEntity(stringEntity);
		 	httpPost.setHeader("Content-type", "application/json");
		 	
		 	
			 CloseableHttpResponse  responseBody ;
			 
			// INI - TLSv1.2
			 SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
			 sslContext = SSLContexts.custom().useProtocol("TLSv1.2").build();
			 httpclient = HttpClientBuilder.create().setSslcontext(sslContext).build();
			// FIN - TLSv1.2
			
			 responseBody = httpclient.execute(httpPost);	
			 StatusLine sl = responseBody.getStatusLine();   
			  
				 if (sl.getStatusCode() == 200) {
					  response = gson.fromJson(responseBody.getEntity().toString(), String.class);
					  System.out.println("enviado ok ");
					  System.out.println(response);
		    	 }else {
		    		 response="error";
		    		 	
		    		 HttpEntity entity = responseBody.getEntity();
		    	        String errorResponse = entity != null ? EntityUtils.toString(entity) : "";
		    	        System.out.println("Error Response: " + errorResponse);
		    		 
		    	        System.out.println(sl.getStatusCode());
		    		 System.out.println(sl.getReasonPhrase());
		    	 }
			 }catch(Exception e) {
				 System.out.println("Error al llamar al servicio de obtener token: "+ e.getMessage() );
			 }finally {
				  try {
					  httpclient.close();
				    } catch (IOException e) {
				        e.printStackTrace();
				    }
			 }
		 }catch(Exception e) {
			 System.out.println("Error en ruta : " + ruta);
			 System.out.println(e.getMessage());				 
		 }
		 return response;
 		
 	}

	 private RemitenteBPM registraRemitenteBPM(UsuarioExternoResponse response) {
		 RemitenteBPM remitente = new RemitenteBPM();
		 remitente.setCorreo(response.getMail());
		 remitente.setDireccion(response.getDireccion());
		 remitente.setIndCasilla(Integer.parseInt(response.getCasilla()));
		 remitente.setNombre(response.getNombreCompleto());
		 remitente.setNroDocumento(response.getNumeroDocumento());
		 remitente.setTipoDocumento(this.getCodTipoDocumento(response.getTipoDocumento()));
		 Integer correlativo;
		 correlativo = entranteDAO.registraRemitentesBPM(remitente);
		 if (correlativo > 0) {
			 remitente.setNcorrelativo(new Long(correlativo));
			 return remitente;
		 }
		 return null;
	 }
	 
	 
	 
	 
	private String getAbrevTipoDocumento(String codigoTipoDocumento) {
		  String abrev="";
			  for (int i=0;i<this.listTipoDoc.size();i++) {
				  if (this.listTipoDoc.get(i).getCodigo().equals(codigoTipoDocumento)) {
					  abrev=this.listTipoDoc.get(i).getAbreviatura();
				  }
			  }
		return abrev;	  
	}
	private String getCodTipoDocumento(String abreviatura) {
		  String cod="";
			  for (int i=0;i<this.listTipoDoc.size();i++) {
				  if (this.listTipoDoc.get(i).getAbreviatura() != null) {
					  if (this.listTipoDoc.get(i).getAbreviatura().equals(abreviatura)) {
						  cod=this.listTipoDoc.get(i).getCodigo();
					  }
				  }
				
			  }
		return cod;	  
	}
	public CredencialesAPI getCredenciales() {
		return credenciales;
	}



	public void setCredenciales(CredencialesAPI credenciales) {
		this.credenciales = credenciales;
	}

	
	
}
