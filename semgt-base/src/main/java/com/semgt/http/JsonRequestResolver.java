package com.semgt.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("jsonRequestResolver")
public class JsonRequestResolver implements RequestResolver {
	protected Logger log;

	public JsonRequestResolver() {
		log = LoggerFactory.getLogger(getClass());
	}

	public boolean accept(HttpServletRequest request) {
		return JsonUtils.isJSON(request);
	}

	public Map resolve(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Map result = null;
			byte data[] = readData(request.getInputStream(),
					request.getContentLength());
			if(data.length > 0) {
				result = (Map) JsonUtils.decode(new ByteArrayInputStream(data),
						java.util.Map.class);
				if (log.isDebugEnabled())
					log.debug((new StringBuilder("parse json:")).append(result)
							.toString());
			}
			return result;
		} catch (JsonParseException e) {
			throw new Exception("json parse error", e);
		} catch (IOException e) {
			throw new Exception("json io error", e);
		}
	}

	protected byte[] readData(InputStream in, int length) throws IOException {
		byte buf[] = new byte[length];
		int off;
		int len;
		for (off = 0; off < length; off += len) {
			len = in.read(buf, off, length - off);
			if (len == -1)
				break;
		}

		if (off < length)
			throw new IOException(
					(new StringBuilder("data not enough,expires "))
							.append(length).append("bytes,but ").append(off)
							.append(" bytes at fact").toString());
		else
			return buf;
	}
}
