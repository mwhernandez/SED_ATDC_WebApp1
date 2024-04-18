package com.sedapal.tramite.servlets;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletImagen
 */
public class ServletImagen extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletImagen() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 try {

			   //obteniendo el archivo d eimagen.

			   String file = request.getParameter("file");

			    BufferedInputStream in = new BufferedInputStream(new FileInputStream("/var/www/fotos/" + file));

			    // obteniendo el contenido d ela imagen.
			byte[] bytes = new byte[in.available()];

			   in.read(bytes);
			   in.close();

			   // escribiendo el contenido d ela imagen  a la respuesta.
			   response.getOutputStream().write(bytes);

			} catch (IOException e) {

			e.printStackTrace();

			}

	}

}
