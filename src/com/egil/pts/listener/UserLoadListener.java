package com.egil.pts.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class UserLoadListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();
        HashMap<String, String> userMap = new LinkedHashMap<String, String>(); 
		try {
			Properties props = new Properties();
			String userFile = servletContext.getInitParameter("user");
			InputStream inStream = servletContext.getResourceAsStream( userFile );

			props.load( inStream );
			for (Object key : props.keySet()) {
				userMap.put(key.toString(), props.getProperty(key.toString()));
			}
			try {
				// __Release resource
				inStream.close();
			} catch (IOException teIO) {
			}
			servletContext.setAttribute("userMap", userMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
