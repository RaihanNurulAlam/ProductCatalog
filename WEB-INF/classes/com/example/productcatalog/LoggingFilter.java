package com.example.productcatalog;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/LoggingFilter")
public class LoggingFilter extends HttpFilter implements Filter {
 
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		// filter req
		
		HttpServletRequest req = (HttpServletRequest) request;
		System.out.println("LOG >>>> " + new Date() + " >>>> IP : " + request.getRemoteAddr());
		System.out.println("PATH >>>> " + req.getServletPath());
		// pass the request along the filter chain
		chain.doFilter(request, response);
	
	
		//filter resp
	}
}
