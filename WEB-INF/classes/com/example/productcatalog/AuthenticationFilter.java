package com.example.productcatalog;

import java.io.IOException;
import java.util.Date;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName =  "/AuthenticationFilter", urlPatterns = "/frontoffice/*", dispatcherTypes = DispatcherType.REQUEST)
public class AuthenticationFilter extends HttpFilter implements Filter {
 
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		// filter req
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String loginContext = (String) session.getAttribute("loginContext");
		if(loginContext == null) {
			request.setAttribute("message", "[Filter] Anda belum login atau session anda expired");
			request.getRequestDispatcher("/WEB-INF/jsp/login-form.jsp").forward(request, response);;
			} else {
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
//		System.out.println("LOG >>>> " + new Date() + " >>>> IP : " + request.getRemoteAddr());
//		System.out.println("PATH >>>> " + req.getServletPath());
	}
}
