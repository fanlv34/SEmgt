package com.semgt.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;



public class DefaultHandlerExceptionResolver implements HandlerExceptionResolver {
	private static Logger log = LoggerFactory.getLogger(DefaultHandlerExceptionResolver.class);

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView mv = new ModelAndView();
		if(ex instanceof SeException) {
			log.info("Got a SeException! _errorCode:"+ ((SeException)ex).getErrorCode() 
					+ "  _errorMessage:"+((SeException)ex).getErrorMessage());
			mv.addObject("_errorCode", ((SeException)ex).getErrorCode());
			mv.addObject("_errorMessage", ((SeException)ex).getErrorMessage());
		} else {
			log.info("Got a Exception! _errorMessage:" + ex.toString());
			mv.addObject("_errorMessage", ex.toString());
		}
		return mv;
	}

}
