package com.egil.pts.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListeners implements HttpSessionListener {

	private static int sessionCount;

	public int getActiveSession() {
		return sessionCount;
	}

	public void sessionCreated(HttpSessionEvent e) {
		sessionCount++;
	}

	public void sessionDestroyed(HttpSessionEvent e) {
		sessionCount--;
	}
}