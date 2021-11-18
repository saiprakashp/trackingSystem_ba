package com.egil.pts.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.egil.pts.constants.PTSConstants;

/**
 * @author v549566
 * 
 */
public class SqlHelper {
	private String connectionUrl = "";
	private String connectionUserID = "";
	private String connectionPwd = "";
	private Connection con;
	private Statement stmt;
	private Properties connectionProperties = new Properties();
	private String filename = PTSConstants.DBCONFIGFILENAMEURL;

	private void loadDbProperties() {
		FileInputStream fileInputStream = null;
		try {
			Properties ptsConfigs = new Properties();
			ptsConfigs.load(new FileInputStream(
					PTSConstants.CSSOP_CONFIG_FILE_URL));

			fileInputStream = new FileInputStream(filename);
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(fileInputStream);
			Element rootElement = document.getDocumentElement();
			NodeList nodes = rootElement
					.getElementsByTagName(PTSConstants.DBCONFIGPROPERTY);
			int n = nodes.getLength();
			for (int i = 0; i < n; i++) {
				Node node = nodes.item(i);
				Node requiredNode = node.getAttributes().getNamedItem(
						PTSConstants.DBCONFIGNAME);
				Node requiredNodeValue = node.getAttributes().getNamedItem(
						PTSConstants.DBCONFIGVALUE);
				if (requiredNode != null) {
					if (requiredNode.getNodeValue() != null
							&& requiredNode.getNodeValue().toString()
									.equals(PTSConstants.DBCONFIGJDBCURL)
							&& requiredNodeValue != null) {
						connectionUrl = requiredNodeValue.getTextContent();
					}
					if (requiredNode.getNodeValue() != null
							&& requiredNode.getNodeValue().toString()
									.equals(PTSConstants.DBCONFIGUSER)
							&& requiredNodeValue != null) {
						connectionUserID = requiredNodeValue.getTextContent();
					}
					if (requiredNode.getNodeValue() != null
							&& requiredNode.getNodeValue().toString()
									.equals(PTSConstants.DBCONFIGJPWD)
							&& requiredNodeValue != null) {
						connectionPwd = DesEncrypter
								.decrypt(
										requiredNodeValue.getTextContent(),
										ptsConfigs
												.getProperty(PTSConstants.USER_KEY_FOR_PWD_ENCRYPT_OR_DECRYPT));
					}
				}
			}

		} catch (Exception exception) {
			throw new RuntimeException(exception);
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			}
		}

	}

	private void loadProperties(String rowPreFetchValue) {
		loadDbProperties();
		connectionProperties.put(PTSConstants.DBCONFIGUSER,
				connectionUserID);
		connectionProperties.put(PTSConstants.DBCONFIGJPASSWORD,
				connectionPwd);
		connectionProperties.put(PTSConstants.DBCONFIGDEFAULTROWPREFETCH,
				rowPreFetchValue);
	}

	public boolean getConnection(String rowPreFetchValue) throws Exception {
		try {
			Class.forName(PTSConstants.DBORACLEDRIVER);
			loadProperties(rowPreFetchValue);
			con = DriverManager.getConnection(connectionUrl,
					connectionProperties);

		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	public void closeConnection() throws Exception {
		if (stmt != null)
			stmt.close();
		if (con != null)
			con.close();
	}

	public ResultSet getResultSetWithQuery(String query) throws Exception {

		stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet resultSet = stmt.executeQuery(query);
		return resultSet;
	}

}
