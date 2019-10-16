package com.wba.leap.card.interceptors;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * This class is the HeaderInterceptor that intercepts the channel request and
 * checks for correlationId and hamac
 * 
 *
 */
public class HeaderInterceptor extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		System.out.println("Message arrived");
		System.out.println(request.getParameterMap().toString());
		
		String messageId= request.getHeader("MessageId");

		if(messageId!=null) response.setHeader("MessageId", messageId);		
		response.setHeader("Channel", request.getHeader("Channel"));
		
		response.setContentType(request.getContentType());
		
		filterChain.doFilter(request,response);	
	}
}