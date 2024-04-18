package com.sedapal.tramite.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class FilterSecure
 */
public class FilterSecure implements Filter {

    private FilterConfig filterConfig = null;

    /** Called by the web container to indicate to a filter 
        that it is being placed into service.
    */
    public void init(FilterConfig filterConfig)
    {
        this.filterConfig = filterConfig;
    }

    /** The doFilter method of the Filter is called by the container each 
        time a request/response pair is passed through the chain due to a 
        client request for a resource at the end of the chain. */
    public void doFilter(ServletRequest request, 
                         ServletResponse response, 
                         FilterChain chain)
        throws IOException, ServletException
    {
        boolean authorized = false;
        if (request instanceof HttpServletRequest) {
            HttpSession session = ((HttpServletRequest)request).getSession(false);
            if (session != null) {
                String key = (String) session.getAttribute("key");
                if (key != null)
                    authorized = true;
            }
        }
                
        if (authorized) {
            chain.doFilter(request, response);
            return;
        } else if (filterConfig != null) {
            String login_page = "/index.jsp";
            if (login_page != null && !"".equals(login_page)) {
                filterConfig.getServletContext().getRequestDispatcher(login_page).forward(request, response);               
                return;
            }
        }
        
        throw new ServletException("Unauthorized access, unable to forward to login page");
        
    }

    /** Set the page page the user wanted to go to in the session. */
    void rememberPage(HttpServletRequest request)
    {
        StringBuffer pageWanted = new StringBuffer(request.getServletPath());
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && !"".equals(pathInfo))
            pageWanted.append("/" + pathInfo);
        String queryString = request.getQueryString();
        if (queryString != null && !"".equals(queryString))
            pageWanted.append("?" + queryString);
        HttpSession session = request.getSession();
        session.setAttribute("SecureFilter.pageWanted", pageWanted.toString());
    }
    

    /** Called by the web container to indicate to a filter that it is being taken 
        out of service.
    */
    public void destroy()
    {
        filterConfig = null;
    }}
