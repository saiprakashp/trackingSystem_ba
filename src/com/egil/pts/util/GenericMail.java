package com.egil.pts.util;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GenericMail {

	private static String SUBJECT = "RICO - Reminder to clock time in PTS";
	private static String CONTENT = "This is a generic reminder to clock time in PTS for this week...";
	private static String MAIL_FROM = "PTSadmin@one.verizon.com";

	public static void sendMail(String supervisorMail, List<String> toList,
			List<String> resourceNameList) throws Throwable {

		try {
			// System.setProperty("java.net.preferIPv4Stack" , "true");
			Properties props = new Properties();// System.getProperties();
			// props.put("mail.smtp.auth", "true");
			/*
			 * props.put("mail.smtp.starttls.enable", "true");
			 * props.put("java.net.preferIPv4Stack", "true");
			 */
			props.put("mail.smtp.host", "smtp.vzbi.com");
			// props.put("mail.smtp.user", "sudhakar.x.enuganti@verizon.com");
			/*
			 * props.put("mail.smtp.socketFactory.class",
			 * "javax.net.ssl.SSLSocketFactory");
			 * props.put("mail.smtp.socketFactory.fallback", "true");
			 */

			// props.put("email.enabled", "true");
			// props.put("email.timeout", "10000");

			// props.put("mail.smtp.port", "25");

			Session session = Session.getDefaultInstance(props, null);

			MimeMessage message = new MimeMessage(session);
			//toList.add(supervisorMail);
			InternetAddress[] addressTo = new InternetAddress[toList.size()];
			for (int i = 0; i < toList.size(); i++) {
				addressTo[i] = new InternetAddress(toList.get(i));
			}

			InternetAddress addrToCC = new InternetAddress(supervisorMail);
			if(supervisorMail != null && !supervisorMail.equals("")) {
				if (!supervisorMail.trim().contains("majeed.a.mohammed")
						&& !supervisorMail.trim().contains("dolly.babb")
						&& !supervisorMail.trim().contains("charles.delacey")) {
					toList.add(supervisorMail);
				}
				message.setFrom(new InternetAddress(supervisorMail));
			} else {
				message.setFrom(new InternetAddress(MAIL_FROM));
				message.setRecipient(Message.RecipientType.CC, addrToCC);
			}
			message.setRecipients(Message.RecipientType.TO, addressTo);
			message.setSubject(SUBJECT);
			message.setContent(CONTENT, "text/plain;charset=utf-8");
			Transport.send(message);
			System.out.println("Done");

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}

	}
	
	public static void sendMail(String supervisorMail, Map<String, List<String>> toList,
			List<String> resourceNameList) throws Throwable {

		try {
			// System.setProperty("java.net.preferIPv4Stack" , "true");
			Properties props = new Properties();// System.getProperties();
			// props.put("mail.smtp.auth", "true");
			/*
			 * props.put("mail.smtp.starttls.enable", "true");
			 * props.put("java.net.preferIPv4Stack", "true");
			 */
			props.put("mail.smtp.host", "smtp.vzbi.com");
			// props.put("mail.smtp.user", "sudhakar.x.enuganti@verizon.com");
			/*
			 * props.put("mail.smtp.socketFactory.class",
			 * "javax.net.ssl.SSLSocketFactory");
			 * props.put("mail.smtp.socketFactory.fallback", "true");
			 */

			// props.put("email.enabled", "true");
			// props.put("email.timeout", "10000");

			// props.put("mail.smtp.port", "25");

			Session session = Session.getDefaultInstance(props, null);

			MimeMessage message = null; 
					
			for (String to : toList.keySet()) {
				message = new MimeMessage(session);

				InternetAddress addressTo = new InternetAddress(to);

				InternetAddress addrToCC = new InternetAddress(supervisorMail);
				message.setFrom(new InternetAddress(MAIL_FROM));

				message.setRecipient(Message.RecipientType.TO, addressTo);
				message.setRecipient(Message.RecipientType.CC, addrToCC);
				message.setSubject(SUBJECT);
				String content = "This is a generic reminder to a RICO employee for completing the time sheets for the following week(s) ";
				String dates = "";
				for(String dt : toList.get(to)) {
					dates = dates + dt + ", ";
				}
				content = content + dates.substring(0,dates.lastIndexOf(", "));
				message.setContent(content, "text/plain;charset=utf-8");
				Transport.send(message);
			}
			System.out.println("Done");

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}

	}

	public static void main(String args[]) {
		getReourceMailId();
	}

	public static void getReourceMailId() {
		try {
			Map<String, List<String>> emailMap = new LinkedHashMap<String, List<String>>();
			Map<String, List<String>> supEmailResNameMap = new LinkedHashMap<String, List<String>>();
			List<String> toList = new ArrayList<String>();
			List<String> resourceNameList = new ArrayList<String>();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			SqlHelper sqlhelper = new SqlHelper();
			sqlhelper.getConnection("1000");
			String qryStr = "select resource_name, resource_email, supervisor_email, effort from (select u.name as resource_name, COALESCE(sum((ts.mon_hrs+ts.tue_hrs+ts.wed_hrs+ts.thu_hrs+ts.fri_hrs+ts.sat_hrs+ts.sun_hrs)),0.0) as effort, u.EMAIL as resource_email, "
					+ " (select email from PTS_USER where id=us.SUPERVISOR_ID) as supervisor_email "
					+ " from PTS_USER u inner join PTS_USER_SUPERVISOR us on us.user_id=u.id INNER JOIN PTS_USER_TYPES ut on u.USER_TYPE_ID=ut.id "
					+ " LEFT OUTER JOIN PTS_USER_TIMESHEET ts on ts.user_id=u.id and (ts.WEEKENDING_DATE = '"
					+ format.format(getWeekEnding())
					+ "' ) "
					+ " LEFT OUTER JOIN PTS_NETWORK_CODES nc on ts.NETWORK_CODE_ID=nc.id LEFT OUTER JOIN  PTS_ACTIVITY_CODES ac ON ac.id=ts.ACTIVITY_CODE_ID "
					+ " INNER JOIN PTS_USER_ROLE ur on ur.USER_ID=u.id "
					+ " INNER JOIN PTS_ROLES r on ur.ROLE_ID=r.id and r.ROLE_NAME <> 'ADMIN' "
					+ " WHERE   u.status not in ('OffBoard','No Show', 'Open','Selected')  group by us.SUPERVISOR_ID, ts.WEEKENDING_DATE, u.id) temptable WHERE EFFORT = 0.0 and resource_email is not null group by supervisor_email,resource_email ";

			ResultSet resourceMailSet = sqlhelper.getResultSetWithQuery(qryStr);

			while (resourceMailSet.next()) {

				String supervisorMailId = resourceMailSet
						.getString("supervisor_email");
				String resourceMailId = resourceMailSet
						.getString("resource_email");
				String resourceName = resourceMailSet
						.getString("resource_name");
				if ((supervisorMailId != null && !supervisorMailId.trim()
						.equals(""))) {
					if ((!supervisorMailId.trim().contains("admin")
							&& supervisorMailId.trim().contains("verizon"))) {
						if (emailMap != null
								&& !emailMap.containsKey(supervisorMailId
										.trim()))
							toList = new ArrayList<String>();
						resourceNameList = new ArrayList<String>();
						emailMap.put(supervisorMailId.trim(), toList);
						supEmailResNameMap.put(supervisorMailId.trim(), resourceNameList);
					}
				}

				if ((supervisorMailId != null
						&& !supervisorMailId.trim().equals("") && !supervisorMailId
						.trim().contains("admin"))) {
					if ((resourceMailId != null && !resourceMailId.trim().equals(""))
							&& (!resourceMailId.trim().contains("admin")
									&& !resourceMailId.trim().contains("majeed.a.mohammed")
									&& !resourceMailId.trim().contains("dolly.babb")
									&& !resourceMailId.trim().contains("charles.delacey")
									&& resourceMailId.trim().contains("verizon"))) {
						emailMap.get(supervisorMailId).add(resourceMailId);
					}
				}

				supEmailResNameMap.get(supervisorMailId).add(resourceName);

				/*
				 * if (toList.size() > 0) { sendMail(toList); }
				 */

			}

			for (String key : emailMap.keySet()) {
				sendMail(key, emailMap.get(key), supEmailResNameMap.get(key));
			}

			for (String key : emailMap.keySet()) {
				System.out.println("Supervisor >>" + key);
				for (String value : emailMap.get(key)) {
					System.out.println(" \t " + value);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	private static Date getWeekEnding() {
		Date weekEnd = null;
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String selectedDate = df.format(new Date());
			// Get calendar set to current date and time
			Calendar c = Calendar.getInstance();
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			if (selectedDate != null && !selectedDate.equals("")) {
				c.setTime(format.parse(selectedDate));
			}
			// Set the calendar to monday of the current week
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

			// Print dates of the current week starting on Monday
			// DateFormat df = new SimpleDateFormat("EEEE dd/MM/yyyy");
			weekEnd = format.parse(format.format(c.getTime()));

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return weekEnd;
	}
	
	public static void sendPasswordMail(String mailid, String password) throws Throwable {

		try {
			// System.setProperty("java.net.preferIPv4Stack" , "true");
			Properties props = new Properties();// System.getProperties();
			// props.put("mail.smtp.auth", "true");
			/*
			 * props.put("mail.smtp.starttls.enable", "true");
			 * props.put("java.net.preferIPv4Stack", "true");
			 */
			props.put("mail.smtp.host", "smtp.vzbi.com");
			// props.put("mail.smtp.user", "sudhakar.x.enuganti@verizon.com");
			/*
			 * props.put("mail.smtp.socketFactory.class",
			 * "javax.net.ssl.SSLSocketFactory");
			 * props.put("mail.smtp.socketFactory.fallback", "true");
			 */

			// props.put("email.enabled", "true");
			// props.put("email.timeout", "10000");

			// props.put("mail.smtp.port", "25");

			Session session = Session.getDefaultInstance(props, null);

			MimeMessage message = new MimeMessage(session);

			InternetAddress addressTo = new InternetAddress(mailid);
			
			message.setFrom(addressTo);

			message.setRecipient(Message.RecipientType.TO, addressTo);
			
			message.setSubject("PTS System Generated Password");
			message.setContent("Your Ricotracker temporary password is : " + password+". Please do not disclose password with anyone.", "text/plain;charset=utf-8");
			Transport.send(message);
			System.out.println("Done");

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}

	}
	
	public static void sendMailResourceUtilization(String supervisorMail, String ccId, String bodyText, String selectedMonth)
			throws Throwable {

		try {
			// System.setProperty("java.net.preferIPv4Stack" , "true");
			Properties props = new Properties();// System.getProperties();
			// props.put("mail.smtp.auth", "true");
			/*
			 * props.put("mail.smtp.starttls.enable", "true");
			 * props.put("java.net.preferIPv4Stack", "true");
			 */
			props.put("mail.smtp.host", "smtp.vzbi.com");
			// props.put("mail.smtp.user", "sudhakar.x.enuganti@verizon.com");
			/*
			 * props.put("mail.smtp.socketFactory.class",
			 * "javax.net.ssl.SSLSocketFactory");
			 * props.put("mail.smtp.socketFactory.fallback", "true");
			 */

			// props.put("email.enabled", "true");
			// props.put("email.timeout", "10000");

			// props.put("mail.smtp.port", "25");

			Session session = Session.getDefaultInstance(props, null);

			MimeMessage message = null;

			message = new MimeMessage(session);

			InternetAddress addressTo = new InternetAddress(supervisorMail);
			message.setRecipient(Message.RecipientType.TO, addressTo);
			
			if (ccId != null && !ccId.isEmpty()) {
				InternetAddress addrToCC = new InternetAddress(ccId);
				message.setRecipient(Message.RecipientType.CC, addrToCC);
				message.setFrom(new InternetAddress(ccId));
			} else {
				message.setFrom(new InternetAddress(MAIL_FROM));
			}
			message.setSubject("Resource Utilization Discrepency for" +selectedMonth + " Month.");
			message.setContent(bodyText, "text/html;charset=utf-8");
			Transport.send(message);
			System.out.println("Done" + bodyText);

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}

	}
}
