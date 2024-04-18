package com.sedapal.tramite.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletReporte
 */
public class ServletReporte extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletReporte() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String codigo = (String)request.getAttribute("rCod");
		System.out.println("Codigo leido por el Servlet:"+ codigo);
		//index2.jsp pagina de reporte
		HttpSession session = request.getSession();
		session.setAttribute("sCod", codigo);
		request.getRequestDispatcher("index2.jsp").forward(request, response);
		out.println("<h2>OK, REPORTE VISTO !!!</h2>");
		out.println("<h2>"+codigo+"</h2>");
		out.close();
	}

}
