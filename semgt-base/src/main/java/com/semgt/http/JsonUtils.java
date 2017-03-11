package com.semgt.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Time;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtils {
	public JsonUtils()
    {
    }

    public static byte[] encode(Object object, String encoding)
        throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        encode(object, ((OutputStream) (out)), encoding);
        return out.toByteArray();
    }

    public static void encode(Object object, OutputStream out, String encoding)
        throws IOException
    {
        JsonGenerator json = null;
        json = mapper.getJsonFactory().createJsonGenerator(out, JsonEncoding.valueOf(encoding));
        json.writeObject(object);
        json.flush();    
        if(json != null)
            try
            {
                json.close();
            }
            catch(IOException ioexception) { }
    }

    public static String encode(Object object)
        throws IOException
    {
        StringWriter out = new StringWriter();
        encode(object, ((Writer) (out)));
        return out.toString();
    }

    public static void encode(Object object, Writer out)
        throws IOException
    {
        JsonGenerator json = null;
        json = mapper.getJsonFactory().createJsonGenerator(out);
        json.writeObject(object);
        json.flush();
        if(json != null)
            try
            {
                json.close();
            }
            catch(IOException ioexception) { }
    }

    public static Object decode(Reader json, Class klass)
        throws JsonParseException, IOException
    {
        JsonParser parser = null;
        Object object;
        parser = mapper.getJsonFactory().createJsonParser(json);
        object = parser.readValueAs(klass);
        if(parser != null)
            try
            {
                parser.close();
            }
            catch(IOException ioexception) { }
        return object;
    }

    public static Object decode(InputStream json, Class klass)
        throws JsonParseException, IOException
    {
        JsonParser parser = null;
        Object object;
        parser = mapper.getJsonFactory().createJsonParser(json);
        object = parser.readValueAs(klass);
        if(parser != null)
            try
            {
                parser.close();
            }
            catch(IOException ioexception) { }
        return object;
    }

    public static Object decode(String json, Class klass)
        throws JsonParseException, IOException
    {
        JsonParser parser = null;
        Object object;
        parser = mapper.getJsonFactory().createJsonParser(json);
        object = parser.readValueAs(klass);
        if(parser != null)
            try
            {
                parser.close();
            }
            catch(IOException ioexception) { }
        return object;
    }

    public static Object decode(byte json[], Class klass)
        throws JsonParseException, IOException
    {
        JsonParser parser = null;
        Object object;
        parser = mapper.getJsonFactory().createJsonParser(json);
        object = parser.readValueAs(klass);
        if(parser != null)
            try
            {
                parser.close();
            }
            catch(IOException ioexception) { }
        return object;
    }

    static String abbreviate(String str, int len)
    {
        if(str == null || str.length() < len)
            return str;
        else
            return (new StringBuilder(String.valueOf(str.substring(0, len)))).append(" ...").toString();
    }

    public static boolean isJSON(HttpServletRequest request)
    {
        if(!"POST".equals(request.getMethod()))
            return false;
        String contentType = request.getContentType();
        return contentType != null && contentType.startsWith("application/json");
    }

    private static final ObjectMapper mapper;
    private static ThreadLocal dfmt = new ThreadLocal() {

        protected Object initialValue()
        {
        	return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

    };

    static 
    {
        mapper = new ObjectMapper();
        mapper.getSerializationConfig().setDateFormat(new DateFormat() {

            public StringBuffer format(java.util.Date date, StringBuffer toAppendTo, FieldPosition fieldPosition)
            {
                String str = null;
                if((date instanceof Date) || (date instanceof Time))
                    str = date.toString();
                else
                    str = ((DateFormat)JsonUtils.dfmt.get()).format(date);
                return toAppendTo.append(str);
            }

            public java.util.Date parse(String source, ParsePosition pos)
            {
                return null;
            }
           
            {
                setCalendar(Calendar.getInstance());
                setNumberFormat(NumberFormat.getInstance());
            }
        });
        mapper.getDeserializationConfig().set(org.codehaus.jackson.map.DeserializationConfig.Feature.USE_BIG_DECIMAL_FOR_FLOATS, true);
    }
}
