package com.egil.pts.actions;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.egil.pts.modal.ReportQueryMapper;
import com.egil.pts.util.GenericExcel;

@Controller("daoReportAction")
@Scope("prototype")

public class DaoReportAction extends PTSAction {
	private static final long serialVersionUID = 1L;

	private String initalHit;
	private String queryNeeded;
	private List<Map<String, Object>> queryReturnResult;
	private List<String> headers = new ArrayList<String>();
	private String fileName;
	private String fileInputName;
	private String gridName;
	private String fileBased;
	private String viewBased;
	private String allowMasterCrud;
	private static String tempfileName = "";
	private String filename;
	private String downloadFile;
	private InputStream inputStream;
	private long contentLength;
	private static Map<String, ReportQueryMapper> reportQueries;
	private String selectedReport;
	private static Map<Long, String> projectManagersMapObj = new LinkedHashMap<Long, String>();
	private String projectManager;
	private String queryKeyWoard;
	private String queryOperation;
	private String queryDecription;
	private boolean showQueryEdit;
	private Map<Long, String> pillarsMapObj = new LinkedHashMap<Long, String>();
	private InputStream fileInputStream;
	private String project;
	private String release;
	private String type;
	private String reportType;
	private String yearType;

	public static void zipFiles(List<String> files) {

		FileOutputStream fos = null;
		ZipOutputStream zipOut = null;
		FileInputStream fis = null;
		String file = "/applications/tomcat/webapps/pts/summaryexport/REPORT.zip";
		try {
			fos = new FileOutputStream(file);
			if (new java.io.File(file).exists()) {
				new java.io.File(file).delete();
			}
			zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
			for (String filePath : files) {
				File input = new File(filePath);
				fis = new FileInputStream(input);
				ZipEntry ze = new ZipEntry(input.getName());
				System.out.println("Zipping the file: " + input.getName());
				zipOut.putNextEntry(ze);
				byte[] tmp = new byte[4 * 1024];
				int size = 0;
				while ((size = fis.read(tmp)) != -1) {
					zipOut.write(tmp, 0, size);
				}
				zipOut.flush();
				fis.close();
			}
			zipOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (Exception ex) {

			}
		}
	}

	private static final int buffersize = 8048;

	public static void updateFile(File tarFile, List<File> flist) throws IOException {
		try { // get a temp file
			File tempFile = File.createTempFile("Report.zip", null,
					new File("/applications/tomcat/webapps/pts/summaryexport/"));
			// delete it, otherwise you cannot rename your existing tar to it.
			if (tempFile.exists()) {
				tempFile.delete();
			}

			if (!tarFile.exists()) {
				tarFile.createNewFile();
			}

			boolean renameOk = tarFile.renameTo(tempFile);
			if (!renameOk) {
				throw new RuntimeException(
						"could not rename the file " + tarFile.getAbsolutePath() + " to " + tempFile.getAbsolutePath());
			}
			byte[] buf = new byte[buffersize];

			TarArchiveInputStream tin = new TarArchiveInputStream(new FileInputStream(tempFile));

			OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(tarFile.toPath()));
			TarArchiveOutputStream tos = new TarArchiveOutputStream(outputStream);
			tos.setLongFileMode(TarArchiveOutputStream.LONGFILE_POSIX);

			// read from previous version of tar file
			ArchiveEntry entry = tin.getNextEntry();
			while (entry != null) {// previous file have entries
				String name = entry.getName();
				boolean notInFiles = true;
				for (File f : flist) {
					if (f.getName().equals(name)) {
						notInFiles = false;
						break;
					}
				}
				if (notInFiles) {
					// Add TAR entry to output stream.
					if (!entry.isDirectory()) {
						tos.putArchiveEntry(new TarArchiveEntry(name));
						// Transfer bytes from the TAR file to the output file
						int len;
						while ((len = tin.read(buf)) > 0) {
							tos.write(buf, 0, len);
						}
					}
				}
				entry = tin.getNextEntry();
			}
			// Close the streams
			tin.close();// finished reading existing entries
			// Compress new files

			for (int i = 0; i < flist.size(); i++) {
				if (flist.get(i).isDirectory()) {
					continue;
				}
				InputStream fis = new FileInputStream(flist.get(i));
				TarArchiveEntry te = new TarArchiveEntry(flist.get(i), flist.get(i).getName());
				// te.setSize(flist[i].length());
				tos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
				tos.setBigNumberMode(2);
				tos.putArchiveEntry(te); // Add TAR entry to output stream.

				// Transfer bytes from the file to the TAR file
				int count = 0;
				while ((count = fis.read(buf, 0, buffersize)) != -1) {
					tos.write(buf, 0, count);
				}
				tos.closeArchiveEntry();
				fis.close();
			}
			// Complete the TAR file
			tos.close();
			tempFile.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String goGetReportforLM() {
		// get Queries from dao
		try {
			pillarsMapObj.clear();
			serviceManager.getNetworkCodeReportService().getPillarsMap(pillarsMapObj, null, true);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		reportQueries = serviceManager.getPtsReportsService().getReportMapper((String) session.get("username"),
				(Long) session.get("userId"));
		reportType = null;
		queryNeeded = null;
		selectedReport = null;
		return SUCCESS;
	}

	public String goDownloadReportforLM() {
		FileInputStream inputStream = null;
		OutputStream outStream = null;
		try {
			boolean process = false;
			List<File> files = new ArrayList<>();
			if (selectedReport != null && selectedReport.equalsIgnoreCase("EFFORT_TOTAL")) {
				List<String> args = new ArrayList<String>();
				if (yearType != null && yearType.equalsIgnoreCase("DEFAULT")) {

					for (int i = 2018; i <= Calendar.getInstance().getWeekYear(); i++) {
						args.clear();
						args.add(i + "");
						args.add("YEARLY");
						args.add("-1");
						TestCon.process(args.toArray(new String[args.size()]));

						args.clear();
						args.add(i + "");
						args.add("MONTHLY");
						args.add("-1");
						TestCon.process(args.toArray(new String[args.size()]));

					}

					for (int i = 2018; i <= Calendar.getInstance().getWeekYear(); i++) {
						files.add(new File(
								"/applications/tomcat/webapps/pts/summaryexport/RICO_NW_UTILIZATION_REPORT_YEARLY_"+i+".xlsx"));
						files.add(new File(
								"/applications/tomcat/webapps/pts/summaryexport/RICO_NW_UTILIZATION_REPORT_MONTHLY_"+i+".xlsx"));
					}
					process = true;

				} else if (getType() != null && getType().equalsIgnoreCase("MONTH")) {
					if (yearType != null && yearType.equalsIgnoreCase("DEFAULT")) {

						for (int i = 2018; i <= Calendar.getInstance().getWeekYear(); i++) {
							args.clear();
							args.add(i + "");
							args.add("MONTHLY");
							args.add("-1");
							TestCon.process(args.toArray(new String[args.size()]));

						}

						for (int i = 2018; i <= Calendar.getInstance().getWeekYear(); i++) {
							files.add(new File(
									"/applications/tomcat/webapps/pts/summaryexport/RICO_NW_UTILIZATION_REPORT_MONTHLY_"+i+".xlsx"));
						}
						process = true;

					} else {
						args.clear();
						args.add(yearType);
						args.add("MONTHLY");
						args.add("-1");
						TestCon.process(args.toArray(new String[args.size()]));

						files.add(new File(
								"/applications/tomcat/webapps/pts/summaryexport/RICO_NW_UTILIZATION_REPORT_MONTHLY_"
										+ yearType + ".xlsx"));
					}
				} else {

					if (yearType != null && yearType.equalsIgnoreCase("DEFAULT")) {

						for (int i = 2018; i <= Calendar.getInstance().getWeekYear(); i++) {
							args.add(i + "");
							args.add("YEARLY");
							args.add("-1");
							TestCon.process(args.toArray(new String[args.size()]));

						}

						for (int i = 2018; i <= Calendar.getInstance().getWeekYear(); i++) {
							files.add(new File(
									"/applications/tomcat/webapps/pts/summaryexport/RICO_NW_UTILIZATION_REPORT_YEARLY_"+i+".xlsx"));
						}
						process = true;

					} else {
						args.clear();
						args.add(yearType);
						args.add("YEARLY");
						args.add("-1");
						TestCon.process(args.toArray(new String[args.size()]));

						files.add(new File(
								"/applications/tomcat/webapps/pts/summaryexport/RICO_NW_UTILIZATION_REPORT_YEARLY_"
										+ yearType + ".xlsx"));
					}

				}

				if (process) {

					ServletContext context = ServletActionContext.getServletContext();
					updateFile(new File("/applications/tomcat/webapps/pts/summaryexport/Report.zip"), files);
					File downloadFile = new File("/applications/tomcat/webapps/pts/summaryexport/Report.zip");

					inputStream = new FileInputStream(downloadFile);

					response.setContentLength((int) downloadFile.length());
					response.setContentType(
							context.getMimeType("/applications/tomcat/webapps/pts/summaryexport/Report.zip"));

					// response header details
					String headerKey = "Content-Disposition";
					String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
					response.setHeader(headerKey, headerValue);

					// response
					outStream = response.getOutputStream();
					IOUtils.copy(inputStream, outStream);
				} else {

					ServletContext context = ServletActionContext.getServletContext();

					File downloadFile = (files.get(0));

					inputStream = new FileInputStream(downloadFile);

					response.setContentLength((int) downloadFile.length());
					response.setContentType(context.getMimeType(files.get(0).getName()));

					// response header details
					String headerKey = "Content-Disposition";
					String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
					response.setHeader(headerKey, headerValue);

					// response
					outStream = response.getOutputStream();
					IOUtils.copy(inputStream, outStream);
				}
				queryNeeded = null;
				selectedReport = null;
				return SUCCESS;
			} else {

				// HttpServletResponse response = ServletActionContext.getResponse();
				reportQueries = serviceManager.getPtsReportsService().getReportMapper((String) session.get("username"),
						(Long) session.get("userId"));
				String sql = (reportQueries.get(selectedReport).getQuery());
				if (projectManager != null) {
					sql = sql.replaceAll("&sp", projectManager);
				}
				queryNeeded = (sql);

				SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HHmmss");

				String filePath = getText("rico.summary.export.path");
				File dir = new File(filePath);

				if (!dir.exists()) {

					@SuppressWarnings("unused")
					boolean result = dir.mkdirs();
				}

				tempfileName = "REPORT.xlsx";
				String fileName = filePath + tempfileName;
				LinkedHashMap<String, String> excelColHeaders = new LinkedHashMap<String, String>();
				getHeaders().clear();
				queryReturnResult = serviceManager.getPtsReportsService().getQueryResultData(
						getQueryNeeded().toUpperCase(), getProject(),
						(!selectedDate.equalsIgnoreCase(selectedToDate)) ? getWeekStarting(selectedDate) : null,
						(!selectedDate.equalsIgnoreCase(selectedToDate)) ? getWeekEnding(selectedToDate) : null,
						getRelease(), getType(), getReportType());
				int rowSize = queryReturnResult.size();
				if (rowSize > 0) {
					if (queryReturnResult != null) {
						queryReturnResult.get(0).forEach((k, v) -> {
							getHeaders().add(k);
						});
					}

					setColumnHeaders(getHeaders(), excelColHeaders);

					String[] colHeaders = new String[excelColHeaders.size()];
					String[] colHeaderKeys = new String[excelColHeaders.size()];
					int colHeaderSize = excelColHeaders.size();
					int colCount = 0;
					Iterator<String> colIterator = excelColHeaders.keySet().iterator();

					while (colIterator.hasNext()) {
						colHeaderKeys[colCount] = colIterator.next();
						colHeaders[colCount] = excelColHeaders.get(colHeaderKeys[colCount]);
						colCount++;
					}

					GenericExcel excel = new GenericExcel(fileName);

					String[][] activityRecords = new String[rowSize + 1][colHeaderSize];

					int row = 0, col = 0;

					for (String header : getHeaders()) {
						activityRecords[row][col] = header;
						col++;
					}
					row++;
					for (Map<String, Object> s : queryReturnResult) {
						col = 0;
						Collection<Object> data = s.values();
						for (Object o : data) {
							activityRecords[row][col] = o + "";
							col++;
						}
						row++;
					}
					excel.writeSheet(
							(getGridName() == null || (getGridName() != null && getGridName().isEmpty())) ? tempfileName
									: getGridName(),
							activityRecords);
					excel.setCustomRowStyleForHeader(0, 0, IndexedColors.GREY_40_PERCENT.index, 30);

					boolean status = excel.writeWorkbook();

					if (status) {

						ServletContext context = ServletActionContext.getServletContext();

						File downloadFile = new File(fileName);

						inputStream = new FileInputStream(downloadFile);

						response.setContentLength((int) downloadFile.length());
						response.setContentType(context.getMimeType(fileName));

						// response header details
						String headerKey = "Content-Disposition";
						String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
						response.setHeader(headerKey, headerValue);

						// response
						outStream = response.getOutputStream();
						IOUtils.copy(inputStream, outStream);
					}
					// pushFileToClient(fileName, getFileName(fileName));
				} else {
					addActionError("");
					return SUCCESS;
				}
			}
		} catch (Throwable e) {
			addActionError("Some Thing Went Wrong!!!!!!");
			e.printStackTrace();
		} finally {
			try {
				if (null != inputStream)
					inputStream.close();
				if (null != inputStream)
					outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		// pushFileToClient(getText("rico.summary.export.path") + tempfileName,
		// tempfileName);
		queryNeeded = null;
		selectedReport = null;
		return SUCCESS;
	}

	public String getCustomDaoResults() {
		tempfileName = "";
		try {
			if (queryNeeded != null && queryNeeded.equalsIgnoreCase("on")) {
				queryNeeded = queryNeeded.replaceAll("on,", "");
			} else {
				queryNeeded = (queryNeeded != null && queryNeeded.substring(0, 3).equalsIgnoreCase("on,"))
						? queryNeeded.replaceAll("on,", "")
						: queryNeeded;
			}
			// get Queries from dao
			reportQueries = serviceManager.getPtsReportsService().getReportMapper((String) session.get("username"),
					(Long) session.get("userId"));

			if (reportQueries != null && reportQueries.size() > 0) {
				showQueryEdit = reportQueries.get("ALL").getAllowdResources()
						.contains(((String) session.get("username")));
				reportQueries.remove("ALL");

			} else {
				showQueryEdit = true;
			}

			// call manager map
			goProjectManagersMap();

			if (initalHit == null || (initalHit != null && initalHit.isEmpty()))
				return SUCCESS;

			if (selectedReport != null && (selectedReport != null && !selectedReport.isEmpty())) {
				String sql = (reportQueries.get(selectedReport).getQuery());
				if (projectManager != null) {
					sql = sql.replaceAll("&sp", projectManager);
				}

				queryNeeded = (sql);
			}

			if (getQueryNeeded() == null || (getQueryNeeded() != null && getQueryNeeded().isEmpty())) {
				addActionError("Please Select Which type wou want View / File Based and and please do input query...");
				return ERROR;
			}

			if (getAllowMasterCrud() == null || (getAllowMasterCrud() != null && !getAllowMasterCrud().isEmpty()))
				if (getQueryNeeded().toUpperCase().contains("UPDATE")
						|| getQueryNeeded().toUpperCase().contains("INSERT")
						|| getQueryNeeded().toUpperCase().contains("DELETE FROM")
						|| getQueryNeeded().toUpperCase().contains("DROP")) {
					addActionError("No Crud Operations are allowed");
					return ERROR;
				}
			if (getQueryNeeded().contains("&sp") && projectManager != null) {
				setQueryNeeded(getQueryNeeded().replaceAll("&sp", projectManager));
			}
			if (getQueryNeeded() != null && !getQueryNeeded().isEmpty()) {
				queryReturnResult = serviceManager.getPtsReportsService()
						.getQueryResultData(getQueryNeeded().toUpperCase());

				if (queryReturnResult == null || (queryReturnResult != null && queryReturnResult.size() <= 0)) {
					addActionError("Unable to fetch Data for Given Query");
					return ERROR;
				}
				getHeaders().clear();
				if (queryReturnResult != null) {
					queryReturnResult.get(0).forEach((k, v) -> {
						getHeaders().add(k);
					});
				}

				if (getAllowMasterCrud() == null || (getAllowMasterCrud() != null && getAllowMasterCrud().isEmpty()))
					if (getHeaders().contains("PASSWORD")) {
						addActionError("Sensitive data cant be shown");
						queryReturnResult.clear();
						getHeaders().clear();
						return ERROR;
					}

				if (fileBased != null) {

					SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HHmmss");
					String timeStamp = df.format(new Date());

					String filePath = getText("rico.summary.export.path");
					File dir = new File(filePath);

					if (!dir.exists()) {
						@SuppressWarnings("unused")
						boolean result = dir.mkdirs();
					}

					if (getFileInputName() == null || (getFileInputName() != null && getFileInputName().isEmpty())) {
						addActionError("Please Enter valid file name to download...");
						return ERROR;
					}
					tempfileName = getFileInputName() + timeStamp + ".xlsx";
					String fileName = filePath + tempfileName;
					LinkedHashMap<String, String> excelColHeaders = new LinkedHashMap<String, String>();

					setColumnHeaders(getHeaders(), excelColHeaders);

					String[] colHeaders = new String[excelColHeaders.size()];
					String[] colHeaderKeys = new String[excelColHeaders.size()];
					int colHeaderSize = excelColHeaders.size();
					int colCount = 0;
					Iterator<String> colIterator = excelColHeaders.keySet().iterator();

					while (colIterator.hasNext()) {
						colHeaderKeys[colCount] = colIterator.next();
						colHeaders[colCount] = excelColHeaders.get(colHeaderKeys[colCount]);
						colCount++;
					}

					GenericExcel excel = new GenericExcel(fileName);

					int rowSize = queryReturnResult.size();
					String[][] activityRecords = new String[rowSize + 1][colHeaderSize];

					int row = 0, col = 0;

					for (String header : getHeaders()) {
						activityRecords[row][col] = header;
						col++;
					}
					row++;
					for (Map<String, Object> s : queryReturnResult) {
						col = 0;
						Collection<Object> data = s.values();
						for (Object o : data) {
							activityRecords[row][col] = o + "";
							col++;
						}
						row++;
					}
					excel.writeSheet(
							(getGridName() == null || (getGridName() != null && getGridName().isEmpty())) ? tempfileName
									: getGridName(),
							activityRecords);
					excel.setCustomRowStyleForHeader(0, 0, IndexedColors.GREY_40_PERCENT.index, 30);

					boolean status = excel.writeWorkbook();

					if (status) {
						pushFileToClient(getFileName(fileName), fileName);
					} else {
						addActionError("Some Thing Went Wrong");
						return ERROR;
					}

					// pushFileToClient(getText("rico.summary.export.path") + tempfileName,
					// tempfileName);

					// downloadDaoQueryData()
					// pushFileToClient(fileName, tempfileName);
				}
				addActionError("Query Parsed...");
				return SUCCESS;
			} else {
				addActionError("No Data...");
				return SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Some Thing Went Wrong!!!!!!");
			return ERROR;
		} catch (Throwable e) {
			e.printStackTrace();
			addActionError("Some Thing Went Wrong!!!!!!");
			return ERROR;
		} finally {

		}
	}

	public String downloadDaoQueryData() {
		if (getQueryNeeded() != null && !getQueryNeeded().isEmpty()) {
			try {
				if (selectedReport != null && (selectedReport != null && !selectedReport.isEmpty())) {
					String sql = (reportQueries.get(selectedReport).getQuery());
					if (projectManager != null) {
						sql = sql.replaceAll("&sp", projectManager);
					}

					queryNeeded = (sql);

					SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HHmmss");
					String timeStamp = df.format(new Date());

					String filePath = getText("rico.summary.export.path");
					File dir = new File(filePath);

					if (!dir.exists()) {
						@SuppressWarnings("unused")
						boolean result = dir.mkdirs();
					}

					if (getFileInputName() == null || (getFileInputName() != null && getFileInputName().isEmpty())) {
						addActionError("Please Enter valid file name to download...");
						return ERROR;
					}
					tempfileName = getFileInputName() + timeStamp + ".xlsx";
					String fileName = filePath + tempfileName;
					LinkedHashMap<String, String> excelColHeaders = new LinkedHashMap<String, String>();
					getHeaders().clear();
					queryReturnResult = serviceManager.getPtsReportsService()
							.getQueryResultData(getQueryNeeded().toUpperCase());
					if (queryReturnResult != null) {
						queryReturnResult.get(0).forEach((k, v) -> {
							getHeaders().add(k);
						});
					}

					setColumnHeaders(getHeaders(), excelColHeaders);

					String[] colHeaders = new String[excelColHeaders.size()];
					String[] colHeaderKeys = new String[excelColHeaders.size()];
					int colHeaderSize = excelColHeaders.size();
					int colCount = 0;
					Iterator<String> colIterator = excelColHeaders.keySet().iterator();

					while (colIterator.hasNext()) {
						colHeaderKeys[colCount] = colIterator.next();
						colHeaders[colCount] = excelColHeaders.get(colHeaderKeys[colCount]);
						colCount++;
					}

					GenericExcel excel = new GenericExcel(fileName);

					int rowSize = queryReturnResult.size();
					String[][] activityRecords = new String[rowSize + 1][colHeaderSize];

					int row = 0, col = 0;

					for (String header : getHeaders()) {
						activityRecords[row][col] = header;
						col++;
					}
					row++;
					for (Map<String, Object> s : queryReturnResult) {
						col = 0;
						Collection<Object> data = s.values();
						for (Object o : data) {
							activityRecords[row][col] = o + "";
							col++;
						}
						row++;
					}
					excel.writeSheet(
							(getGridName() == null || (getGridName() != null && getGridName().isEmpty())) ? tempfileName
									: getGridName(),
							activityRecords);
					excel.setCustomRowStyleForHeader(0, 0, IndexedColors.GREY_40_PERCENT.index, 30);

					boolean status = excel.writeWorkbook();

					if (status) {
						pushFileToClient(getFileName(fileName), fileName);
					} else {
						addActionError("");
						return ERROR;
					}
				} else {
					addActionError("Please slecet query to generate data ");
					return SUCCESS;
				}
			} catch (IOException e) {
				addActionError("Some Thing Went Wrong!!!!!!");
				e.printStackTrace();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			addActionError("No Data...");
			return SUCCESS;
		}
		// pushFileToClient(getText("rico.summary.export.path") + tempfileName,
		// tempfileName);

		return SUCCESS;
	}

	private void setColumnHeaders(List<String> headers, LinkedHashMap<String, String> excelColHeaders) {
		headers.forEach(h -> {
			excelColHeaders.put(h, h);
		});
	}

	public String managerCustomDaoResults() {

		reportQueries = serviceManager.getPtsReportsService().getReportMapper((String) session.get("username"),
				(Long) session.get("userId"));
		if (queryOperation != null && queryOperation.equalsIgnoreCase("A")) {
			if (selectedReport != null) {
				queryNeeded = reportQueries.get(selectedReport).getQuery();
				queryDecription = reportQueries.get(selectedReport).getDescription();
				queryKeyWoard = reportQueries.get(selectedReport).getKeywoard();
			}
		} else if (queryOperation != null && !queryOperation.isEmpty() && queryDecription != null
				&& !queryDecription.isEmpty() && queryKeyWoard != null && !queryKeyWoard.isEmpty()
				&& queryNeeded != null && !queryNeeded.isEmpty()) {
			boolean status = serviceManager.getPtsReportsService().updateReportMapper(queryOperation, queryDecription,
					queryKeyWoard, queryNeeded);
			if (status) {
				addActionError("Operation Succesfull...");
				return SUCCESS;
			} else {
				addActionError("Some Thing Went Wrong!!!!!!");
				return ERROR;
			}
		}

		return SUCCESS;
	}

	public void executeManagerUtilData() {

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

		Long midnight = LocalDateTime.now().until(LocalDate.now().plusDays(20).atStartOfDay(), ChronoUnit.MINUTES);
		scheduler.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				serviceManager.getUserUtilizationService().saveUserUtilizationForMonth();
			}
		}, midnight, 1440, TimeUnit.MINUTES);

	}

	@SkipValidation
	public String goProjectManagersMap() {
		try {
			serviceManager.getUserService().getSuperviosrs(projectManagersMapObj);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String executeManagerUtilDataMonthNow() {

		serviceManager.getUserUtilizationService().saveUserUtilizationForMonth();
		return SUCCESS;
	}

	public String getQueryNeeded() {
		return queryNeeded;
	}

	public void setQueryNeeded(String queryNeeded) {
		this.queryNeeded = queryNeeded;
	}

	public String getInitalHit() {
		return initalHit;
	}

	public void setInitalHit(String initalHit) {
		this.initalHit = initalHit;
	}

	public List<Map<String, Object>> getQueryReturnResult() {
		return queryReturnResult;
	}

	public void setQueryReturnResult(List<Map<String, Object>> queryReturnResult) {
		this.queryReturnResult = queryReturnResult;
	}

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileInputName() {
		return fileInputName;
	}

	public void setFileInputName(String fileInputName) {
		this.fileInputName = fileInputName;
	}

	public String getGridName() {
		return gridName;
	}

	public void setGridName(String gridName) {
		this.gridName = gridName;
	}

	public String getFileBased() {
		return fileBased;
	}

	public void setFileBased(String fileBased) {
		this.fileBased = fileBased;
	}

	public String getViewBased() {
		return viewBased;
	}

	public void setViewBased(String viewBased) {
		this.viewBased = viewBased;
	}

	public String getAllowMasterCrud() {
		return allowMasterCrud;
	}

	public void setAllowMasterCrud(String allowMasterCrud) {
		this.allowMasterCrud = allowMasterCrud;
	}

	public static String getTempfileName() {
		return tempfileName;
	}

	public static void setTempfileName(String tempfileName) {
		DaoReportAction.tempfileName = tempfileName;
	}

	public String getDownloadFile() {
		return downloadFile;
	}

	public void setDownloadFile(String downloadFile) {
		this.downloadFile = downloadFile;
	}

	public InputStream getInputStream() throws IOException {
		String path = ServletActionContext.getServletContext().getRealPath(fileName);
		/**
		 * Method 1 File file = new File (path); return new File InputStream (file);
		 * Method 2 FileUtils.openInputStream(file);
		 */
		File file = new File(path);
		return FileUtils.openInputStream(file);
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public Map<String, ReportQueryMapper> getReportQueries() {
		return reportQueries;
	}

	public void setReportQueries(Map<String, ReportQueryMapper> reportQueries) {
		this.reportQueries = reportQueries;
	}

	public String getSelectedReport() {
		return selectedReport;
	}

	public void setSelectedReport(String selectedReport) {
		this.selectedReport = selectedReport;
	}

	public Map<Long, String> getProjectManagersMapObj() {
		return projectManagersMapObj;
	}

	public void setProjectManagersMapObj(Map<Long, String> projectManagersMapObj) {
		this.projectManagersMapObj = projectManagersMapObj;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public boolean isShowQueryEdit() {
		return showQueryEdit;
	}

	public void setShowQueryEdit(boolean showQueryEdit) {
		this.showQueryEdit = showQueryEdit;
	}

	public String getQueryKeyWoard() {
		return queryKeyWoard;
	}

	public void setQueryKeyWoard(String queryKeyWoard) {
		this.queryKeyWoard = queryKeyWoard;
	}

	public String getQueryOperation() {
		return queryOperation;
	}

	public void setQueryOperation(String queryOperation) {
		this.queryOperation = queryOperation;
	}

	public String getQueryDecription() {
		return queryDecription;
	}

	public void setQueryDecription(String queryDecription) {
		this.queryDecription = queryDecription;
	}

	public Map<Long, String> getPillarsMapObj() {
		return pillarsMapObj;
	}

	public void setPillarsMapObj(Map<Long, String> pillarsMapObj) {
		this.pillarsMapObj = pillarsMapObj;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYearType() {
		return yearType;
	}

	public void setYearType(String yearType) {
		this.yearType = yearType;
	}

}
