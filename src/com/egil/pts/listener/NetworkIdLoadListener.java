package com.egil.pts.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class NetworkIdLoadListener  implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();
        HashMap<String, String> networkIdMap = new LinkedHashMap<String, String>();
        HashMap<String, String> assignedNetworkIds = new LinkedHashMap<String, String>(); 
		try {
			Properties props = new Properties();
			String networkIdFile = servletContext.getInitParameter("network_ids");
			InputStream inStream = servletContext.getResourceAsStream( networkIdFile );

			props.load( inStream );
			for (Object key : props.keySet()) {
				networkIdMap.put(key.toString(), props.getProperty(key.toString()));
			}
			try {
				// __Release resource
				inStream.close();
			} catch (IOException teIO) {
			}
			servletContext.setAttribute("networkIdMap", networkIdMap);
			
			props = new Properties();
			String assignedNetworkIdFile = servletContext
					.getInitParameter("assigned_network_ids");
			inStream = servletContext
					.getResourceAsStream(assignedNetworkIdFile);

			props.load(inStream);
			for (Object key : props.keySet()) {
				assignedNetworkIds.put(key.toString(), props.getProperty(key.toString()));
			}
			
			try {
				// __Release resource
				inStream.close();
			} catch (IOException teIO) {
			}
			servletContext.setAttribute("assignedNetworkIds", assignedNetworkIds);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
