package com.egil.pts.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mchange.v2.c3p0.impl.DefaultConnectionTester;

/**
 * 
 * Call back implementation of ComboPooledDataSource for database errors. Please
 * see dao.xml for configuration parameters.
 * 
 * @author yuva.chandolu
 * 
 */
public class DatabaseConnectionTest  extends DefaultConnectionTester{

	private static final long serialVersionUID = 1L;

	protected Logger logger = LogManager.getLogger("PTSLOGGER");

	// had to make this static given Combo pool implementation is
	// creating two instances of this: one for activeCheckConnection()
	// and one for statusOnException()
	public static int counter = 0;

	
	public int activeCheckConnection(Connection conn, String query,
			Throwable[] thrs) {

		// reset counter
		counter = 0;
		return super.activeCheckConnection(conn, query, thrs);

	}

	public int statusOnException(Connection conn, Throwable thr, String query,
			Throwable[] thrs) {

		int result = super.statusOnException(conn, thr, query, thrs);
		if (result != DefaultConnectionTester.CONNECTION_IS_OKAY) {
			logError(conn, thr);
		}
		// logger.debug("Database connection pool is working fine");
		return result;

	}// end statusOnException()

	private void logError(Connection conn, Throwable thr) {

		++counter;
		// send only 6 alarms for database errors
		if (counter > 6) {
			return;
		}

		int error_code = 0;

		if (thr instanceof SQLException) {
			SQLException exc = (SQLException) thr;
			error_code = exc.getErrorCode();
		}

		logger.error("Database connection " + conn + " is broken, error is "
				+ thr.getMessage()
				+ ((error_code > 0) ? ", error_code=" + error_code : ""));


	}

}// end class