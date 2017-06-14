package com.semgt.http;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RequestResolver {
	boolean accept(HttpServletRequest request);

	Map resolve(HttpServletRequest request, HttpServletResponse response)
			throws Exception;
}
