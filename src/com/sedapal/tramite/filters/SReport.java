package com.sedapal.tramite.filters;

import java.io.IOException;
import javax.crypto.SecretKey;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.sedapal.tramite.nova.util.DesEncrypter;
import com.sedapal.tramite.util.Constantes;

/**
 * Servlet Filter implementation class SReport
 */
public class SReport implements Filter {

	/**
	 * Default constructor.
	 */
	public SReport() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = ((HttpServletRequest)request);
		String codigo = req.getParameter("cod");
		System.out.println("Parametro:" + codigo);
		SecretKey key = Constantes.key;

	    // Create encrypter/decrypter class
	    DesEncrypter encrypter = new DesEncrypter(key);

	    // Encrypt
	    //String encrypted = encrypter.encrypt(codigo);
        //System.out.println(encrypted);
	    // Decrypt
	    String decrypted = encrypter.decrypt(codigo);
	    System.out.println("Desencriptado:" + decrypted);
	    request.setAttribute("rCod", codigo);
	    //regla....validar!!!
	    chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
