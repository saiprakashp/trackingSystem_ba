package com.egil.pts.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Generic Excel spreadsheet manipulation activities.
 * 
 * @author ronald.e.johnson
 * @date May 8, 2009
 */
public class GenericExcel {
	public SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mm:ss.SSS");
	private String filename;
	private String excelFormatType;
	private Workbook workbook;

	boolean isMultiSheetExcel;
	public int ROWS_FOR_PULLDOWN_MENUS = 10000;
	public static final String BULK_UPLOAD_FILE_EXTENSION = ".xls";
	public static final String BULK_UPLOAD_FILE_EXTENSION_XLSX = ".xlsx";
	public static final String FILE_SEPERATOR = File.separator;

	static {
		System.setProperty("java.awt.headless", "true");
		// System.out.println(java.awt.GraphicsEnvironment.isHeadless());
	}

	public GenericExcel(String filename) {
		this.filename = filename;
		if (this.filename != null && this.filename.substring(filename.length() - 5, filename.length()).toLowerCase()
				.equals(BULK_UPLOAD_FILE_EXTENSION_XLSX))
			this.excelFormatType = BULK_UPLOAD_FILE_EXTENSION_XLSX;
		else
			this.excelFormatType = BULK_UPLOAD_FILE_EXTENSION;

	}

	public GenericExcel(String filename, boolean isMultiSheetExcel) {
		this.filename = filename;
		this.isMultiSheetExcel = isMultiSheetExcel;
		if (this.filename.contains(BULK_UPLOAD_FILE_EXTENSION_XLSX)) {
			this.excelFormatType = BULK_UPLOAD_FILE_EXTENSION_XLSX;
			this.workbook = new XSSFWorkbook();
		} else {
			this.excelFormatType = BULK_UPLOAD_FILE_EXTENSION;
			this.workbook = new HSSFWorkbook();
		}
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getExcelFormatType() {
		return excelFormatType;
	}

	public void setExcelFormatType(String excelFormatType) {
		this.excelFormatType = excelFormatType;
	}

	/**
	 * Read a spreadsheet from an Excel file.
	 * 
	 * @param filename String containing the name of the Excel file.
	 * @return Workbook containing the spreadsheet.
	 */
	public void readWorkbook() {
		readWb();
	}

	/**
	 * Read a spreadsheet from an Excel file.
	 * 
	 * @param filename String containing the name of the Excel file.
	 * @return Workbook containing the spreadsheet.
	 */
	private Workbook readWb() {
		try {
			if (this.excelFormatType != null && this.excelFormatType.equals(BULK_UPLOAD_FILE_EXTENSION_XLSX)) {
				workbook = WorkbookFactory.create(new FileInputStream(filename));
			} else {
				POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filename));
				workbook = new HSSFWorkbook(fs);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			workbook = null;
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		return workbook;
	}

	/**
	 * Write an Excel spreadsheet to a file.
	 * 
	 * @param workbook Workbook containing the spreadsheet.
	 * @param filename String containing the name of the file
	 */
	public boolean writeWorkbook() {
		try {
			BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(filename));
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			return true;
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * Return number of sheets in workbook.
	 * 
	 * @param workbook Workbook containing the spreadsheet.
	 */
	public int getNumberOfSheets() {
		workbook = readWb();
		return workbook.getNumberOfSheets();
	}

	/**
	 * Return number of rows in a sheet.
	 * 
	 * @param sheetNumber Number of the sheet in the workbook.
	 */
	public int getNumberOfRowsInSheet(int sheetNumber) {
		workbook = readWb();
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		return sheet.getPhysicalNumberOfRows();
	}

	/**
	 * Read an Excel spreadsheet into a two-dimensional String array.
	 * 
	 * @param workbook    An Excel workbook.
	 * @param sheetNumber Number of the sheet in the workbook.
	 * @return Two dimensional String array.
	 */
	public String[][] readSheet(int sheetNumber) throws Exception {
		workbook = readWb();
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		return readSheet(sheet);
	}

	public String[][] readSheet(int sheetNumber, String excelFormatType) throws Exception {
		if (excelFormatType != null && excelFormatType.length() > 0) {
			this.excelFormatType = excelFormatType;
		}
		workbook = readWb();
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		return readSheet(sheet);
	}

	/**
	 * Read an Excel spreadsheet into a two-dimensional String array.
	 * 
	 * @param workbook  An Excel workbook.
	 * @param sheetName Name of the sheet in the workbook.
	 * @return Two dimensional String array.
	 */
	public String[][] readSheet(String sheetName) throws Exception {
		if (sheetName == null)
			return null;
		workbook = readWb();
		Sheet sheet = workbook.getSheet(sheetName);
		return readSheet(sheet);
	}

	/**
	 * Read an Excel spreadsheet into a two-dimensional String array. Any rows that
	 * are empty are ignored and not added to the array.
	 * 
	 * @param sheet Sheet containing the sheet in the workbook.
	 * @return Two dimensional String array.
	 */
	private String[][] readSheet(Sheet sheet) throws Exception {
		try {
			NumberFormat nf = NumberFormat.getNumberInstance(Locale.ROOT);
			nf.setParseIntegerOnly(true);
			if (sheet.getPhysicalNumberOfRows() <= 0) {
				throw new Exception("Please select a valid excel");
			}

			int totalRows = sheet.getPhysicalNumberOfRows();
			Row row = sheet.getRow(sheet.getFirstRowNum());
			int totalCols = row.getPhysicalNumberOfCells();
			String[][] records = new String[totalRows][totalCols];
			Boolean[] emptyRows = new Boolean[totalRows];
			int emptyRowCount = 0;
			for (int i = 0; i < totalRows; i++) {
				row = sheet.getRow(i);
				emptyRows[i] = true;
				for (int j = 0; j < totalCols; j++) {
					if (row.getCell(j) != null) {
						if (row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC
								|| row.getCell(j).getCellType() == Cell.CELL_TYPE_FORMULA) {
							if (row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC
									&& DateUtil.isCellDateFormatted(row.getCell(j))) {
								records[i][j] = row.getCell(j).toString();
							} else {
								String regex = "(?<=[\\d])(,)(?=[\\d])";
								Pattern p = Pattern.compile(regex);
								Matcher m = p.matcher(nf.format(row.getCell(j).getNumericCellValue()));
								records[i][j] = m.replaceAll("");
							}
						} else {
							records[i][j] = row.getCell(j).toString();
						}
						if (records[i][j] != null && !records[i][j].equals(""))
							emptyRows[i] = false;
					} else {
						records[i][j] = "";
					}
				}
				if (emptyRows[i] == true) {
					emptyRowCount++;
					emptyRows[i] = true;
				}
			}

			// Copy the non-empty rows into a new string array and return that
			// one
			String[][] nonEmptyRows = new String[totalRows - emptyRowCount][totalCols];
			int nonEmptyRowCount = 0;
			for (int i = 0; i < totalRows; i++) {
				if (!emptyRows[i]) {
					nonEmptyRows[nonEmptyRowCount] = records[i];
					nonEmptyRowCount++;
				}
			}
			return nonEmptyRows;
		}

		catch (Throwable throwable) {
			throw new Exception("Please select a valid excel", throwable);
		}
	}

	/**
	 * Create a bold title for the first row of the spreadsheet
	 */
	public void setRowFont(int sheetNumber, int rowNumber, int fontStyle) {
		Font font = workbook.createFont();

		if (fontStyle == 1)
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		else
			font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		font.setFontName("Calibri");
		CellStyle style = workbook.createCellStyle();
		style.setFont(font);

		Sheet sheet = workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(rowNumber);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			row.getCell(i).setCellStyle(style);
		}
	}

	public void setCustomRowStyle(int sheetNumber, int rowNumber) {
		CellStyle customStyle = workbook.createCellStyle();
		customStyle.setWrapText(true);
		customStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(rowNumber);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			customStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			customStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
			customStyle.setBorderBottom(CellStyle.BORDER_THIN);
			customStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.index);
			customStyle.setBorderLeft(CellStyle.BORDER_THIN);
			customStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.index);
			customStyle.setBorderRight(CellStyle.BORDER_THIN);
			customStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.index);
			customStyle.setBorderTop(CellStyle.BORDER_THIN);
			customStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.index);
			row.getCell(i).setCellStyle(customStyle);
			sheet.autoSizeColumn((short) i);
		}
	}

	public void setCustomRowStyleForHeader(int sheetNumber, int rowNumber, short foregroundColor, float height) {
		CellStyle customStyle = workbook.createCellStyle();
		customStyle.setWrapText(true);
		Font font = workbook.createFont();
		customStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		// sheet.setDefaultColumnWidth(60);
		Row row = sheet.getRow(rowNumber);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontName("Calibri");
		customStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		customStyle.setFillForegroundColor(foregroundColor);
		customStyle.setBorderBottom(CellStyle.BORDER_THIN);
		customStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.index);
		customStyle.setBorderLeft(CellStyle.BORDER_THIN);
		customStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.index);
		customStyle.setBorderRight(CellStyle.BORDER_THIN);
		customStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.index);
		customStyle.setBorderTop(CellStyle.BORDER_THIN);
		customStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.index);
		customStyle.setFont(font);

		for (int i = 0; i < row.getLastCellNum(); i++) {
			row.getCell(i).setCellStyle(customStyle);
			sheet.autoSizeColumn((short) i);
		}
		row.setHeightInPoints(height);

	}

	public void customizeRowStyle(int sheetNumber, int rowNumber, int colNumber, short foregroundColor) {
		CellStyle customStyle = workbook.createCellStyle();
		// customStyle.setWrapText(true);
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		customStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		customStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		customStyle.setFillForegroundColor(foregroundColor);
		customStyle.setFont(font);
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(rowNumber);
		for (int i = colNumber; i < row.getLastCellNum(); i++) {
			row.getCell(i).setCellStyle(customStyle);
			sheet.autoSizeColumn((short) i);
		}
	}

	public void customizeRowStyle(int sheetNumber, int rowNumber) {
		CellStyle customStyle = workbook.createCellStyle();
		// customStyle.setWrapText(true);
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		// customStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(rowNumber);
		/*
		 * customStyle.setBorderBottom(CellStyle.BORDER_THIN);
		 * customStyle.setBorderTop(CellStyle.BORDER_THIN);
		 * customStyle.setBorderLeft(CellStyle.BORDER_THIN);
		 * customStyle.setBorderRight(CellStyle.BORDER_THIN);
		 */
		customStyle.setFont(font);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			row.getCell(i).setCellStyle(customStyle);
			sheet.autoSizeColumn((short) i);
		}
	}

	public void customizeBorder(int sheetNumber, int rowNumber) {
		CellStyle customStyle = workbook.createCellStyle();
		customStyle.setWrapText(true);
		customStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(rowNumber);
		for (int i = 1; i < row.getLastCellNum(); i++) {
			customStyle.setBorderBottom(CellStyle.BORDER_THIN);
			customStyle.setBorderTop(CellStyle.BORDER_THIN);
			customStyle.setBorderLeft(CellStyle.BORDER_THIN);
			customStyle.setBorderRight(CellStyle.BORDER_THIN);
			row.getCell(i).setCellStyle(customStyle);
			sheet.autoSizeColumn((short) i);
		}
	}

	public void setCustomNewRowStyle(int sheetNumber, int rowNumber) {
		CellStyle customStyle = workbook.createCellStyle();
		customStyle.setWrapText(true);
		customStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		customStyle.setFont(font);
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(rowNumber);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			customStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			customStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
			customStyle.setBorderBottom(CellStyle.BORDER_THIN);
			customStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.index);
			customStyle.setBorderLeft(CellStyle.BORDER_THIN);
			customStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.index);
			customStyle.setBorderRight(CellStyle.BORDER_THIN);
			customStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.index);
			customStyle.setBorderTop(CellStyle.BORDER_THIN);
			customStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.index);
			row.getCell(i).setCellStyle(customStyle);
			sheet.autoSizeColumn((short) i);
		}
	}

	public void setCustomStyle(int sheetNumber, int rowNumber) {
		CellStyle customStyle = workbook.createCellStyle();
		customStyle.setWrapText(true);
		customStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(rowNumber);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			customStyle.setBorderBottom(CellStyle.BORDER_THIN);
			customStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.index);
			customStyle.setBorderLeft(CellStyle.BORDER_THIN);
			customStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.index);
			customStyle.setBorderRight(CellStyle.BORDER_THIN);
			customStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.index);
			customStyle.setBorderTop(CellStyle.BORDER_THIN);
			customStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.index);
			row.getCell(i).setCellStyle(customStyle);
			sheet.autoSizeColumn((short) i);
		}
	}

	public void createTitle(int sheetNumber) {
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontName("Calibri");
		CellStyle style = workbook.createCellStyle();
		style.setFont(font);
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(0);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			row.getCell(i).setCellStyle(style);
			sheet.autoSizeColumn((short) i);
		}
	}

	/**
	 * Creates a bold title for the given cell of a row
	 */
	public void createTitleForCell(int sheetNumber, int rowNum, int colNum) {
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		CellStyle style = workbook.createCellStyle();
		style.setFont(font);

		Sheet sheet = workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(rowNum);
		if (row != null)
			row.getCell(colNum, Row.CREATE_NULL_AS_BLANK).setCellStyle(style);
	}

	/**
	 * Hide a column in the spreadsheet
	 */
	public void hideColumn(int sheetNumber, int colNumber) {
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		sheet.setColumnHidden(colNumber, true);
	}

	/**
	 * Hide entire Row in the spreadsheet
	 * 
	 * @param sheetNumber
	 * @param rowNumber
	 */
	public void hideRow(int sheetNumber, int rowNumber) {
		if (workbook == null) {
			workbook = readWb();
		}
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(rowNumber);
		row.setZeroHeight(true);
	}

	/**
	 * Create a drop-down list for a column using data from another sheet.
	 * 
	 * @param sheetNumber    The sheet number to be read.
	 * @param colNumber      The number of the column (starting with 0) to contain
	 *                       the dropdown list
	 * @param dropDownValues A string array of the drop down list's selection
	 *                       values.
	 */
	public void createLargeDropDownList(int sheetNumber, int colNumber, int dropColumn, String[] dropDownValues) {
		if (dropDownValues != null && dropDownValues.length > 0) {
			Sheet sheet = workbook.getSheetAt(sheetNumber);
			// Insert a sheet that will hold the data JUST for the
			// lists in the data validation(s). Add a named area
			// and indicate the cells it refers to.
			Sheet dataSheet = workbook.getSheet("Data Sheet");
			if (dataSheet == null) {
				dataSheet = workbook.createSheet("Data Sheet");
				workbook.setSheetHidden(1, true);
			}
			for (int i = 0; i < dropDownValues.length; i++) {
				Row row = dataSheet.getRow(i);
				if (row == null) {
					row = dataSheet.createRow(i);
				}
				Cell cell = row.getCell(colNumber);
				if (cell == null) {
					cell = row.createCell(colNumber);
				}
				cell.setCellValue(dropDownValues[i]);
			}
			// The CellRangeAddressList indicates the cell that will
			// contain the data validation.
			String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
					"R", "S", "T", "U", "V", "X", "Y", "Z" };
			CellRangeAddressList addressList = new CellRangeAddressList(1, ROWS_FOR_PULLDOWN_MENUS, dropColumn,
					dropColumn);
			int last = dropDownValues.length;

			DataValidation dataValidation = null;
			if (this.filename.contains(BULK_UPLOAD_FILE_EXTENSION_XLSX)) {
				XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
				XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
						.createFormulaListConstraint(
								"'Data Sheet'!$" + letters[colNumber] + "$1:$" + letters[colNumber] + "$" + last);
				dataValidation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
				dataValidation.setSuppressDropDownArrow(true);
				dataValidation.setShowErrorBox(true);

			} else {
				DVConstraint dvConstraint = DVConstraint.createFormulaListConstraint(
						"'Data Sheet'!$" + letters[colNumber] + "$1:$" + letters[colNumber] + "$" + last);
				dataValidation = new HSSFDataValidation(addressList, dvConstraint);
				dataValidation.setSuppressDropDownArrow(false);
			}
			sheet.addValidationData(dataValidation);
		}
	}

	/**
	 * Create a drop-down list for a column using data from another sheet having
	 * multiple sheets in a workbook.
	 * 
	 * @param sheetNumber    The sheet number to be read.
	 * @param colNumber      The number of the column (starting with 0) to contain
	 *                       the dropdown list
	 * @param dropDownValues A string array of the drop down list's selection
	 *                       values.
	 */
	public void createLargeDropDownListtoWorkBook(int sheetNumber, int rowNumber, int colNumber, int dropColumn,
			String[] dropDownValues, int dataSheetNum) {
		if (dropDownValues != null && dropDownValues.length > 0) {
			Sheet sheet = this.workbook.getSheetAt(sheetNumber);
			// Insert a sheet that will hold the data JUST for the
			// lists in the data validation(s). Add a named area
			// and indicate the cells it refers to.
			Sheet dataSheet = this.workbook.getSheet("Data Sheet");
			if (dataSheet == null) {
				dataSheet = this.workbook.createSheet("Data Sheet");
				this.workbook.setSheetHidden(dataSheetNum, true);
			}
			for (int i = 0; i < dropDownValues.length; i++) {
				Row row = dataSheet.getRow(i);
				if (row == null) {
					row = dataSheet.createRow(i);
				}
				Cell cell = row.getCell(colNumber);
				if (cell == null) {
					cell = row.createCell(colNumber);
				}
				cell.setCellValue(dropDownValues[i]);
			}
			// The CellRangeAddressList indicates the cell that will
			// contain the data validation.
			String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
					"R", "S", "T", "U", "V", "X", "Y", "Z" };
			CellRangeAddressList addressList = new CellRangeAddressList(rowNumber, ROWS_FOR_PULLDOWN_MENUS, dropColumn,
					dropColumn);
			int last = dropDownValues.length;
			DataValidation dataValidation = null;
			if (this.filename.contains(BULK_UPLOAD_FILE_EXTENSION_XLSX)) {
				XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
				XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
						.createFormulaListConstraint(
								"'Data Sheet'!$" + letters[colNumber] + "$1:$" + letters[colNumber] + "$" + last);
				dataValidation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
				dataValidation.setSuppressDropDownArrow(true);
				dataValidation.setShowErrorBox(true);
			} else {
				DVConstraint dvConstraint = DVConstraint.createFormulaListConstraint(
						"'Data Sheet'!$" + letters[colNumber] + "$1:$" + letters[colNumber] + "$" + last);
				dataValidation = new HSSFDataValidation(addressList, dvConstraint);
				dataValidation.setSuppressDropDownArrow(false);
			}
			sheet.addValidationData(dataValidation);
		}
	}

	public void createLargeDropDownListtoWorkBook(int sheetNumber, int colNumber, int dropColumn,
			String[] dropDownValues, int dataSheetNum) {
		createLargeDropDownListtoWorkBook(sheetNumber, 1, colNumber, dropColumn, dropDownValues, dataSheetNum);
	}

	/**
	 * Create a drop-down list at given Row and Column using data from another
	 * sheet.
	 * 
	 * @param sheetNumber    The sheet number to be read.
	 * @param rowNumber      The number of the the Row
	 * @param colNumber      The number of the column (starting with 0) to contain
	 *                       the dropdown list in the Data Sheet
	 * @param dropColumn     Actual number of the column
	 * @param dropDownValues A string array of the drop down list's selection
	 *                       values.
	 */
	public void createLargeDropDownList(int sheetNumber, int rowNumber, int colNumber, int dropColumn,
			String[] dropDownValues) {
		if (dropDownValues != null && dropDownValues.length > 0) {
			Sheet sheet = workbook.getSheetAt(sheetNumber);
			// Insert a sheet that will hold the data JUST for the
			// lists in the data validation(s). Add a named area
			// and indicate the cells it refers to.
			Sheet dataSheet = workbook.getSheet("Data Sheet");
			if (dataSheet == null) {
				dataSheet = workbook.createSheet("Data Sheet");
				workbook.setSheetHidden(1, true);
			}
			for (int i = 0; i < dropDownValues.length; i++) {
				Row row = dataSheet.getRow(i);
				if (row == null) {
					row = dataSheet.createRow(i);
				}
				Cell cell = row.getCell(colNumber);
				if (cell == null) {
					cell = row.createCell(colNumber);
				}
				cell.setCellValue(dropDownValues[i]);
			}
			// The CellRangeAddressList indicates the cell that will
			// contain the data validation.
			String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
					"R", "S", "T", "U", "V", "X", "Y", "Z" };
			CellRangeAddressList addressList = new CellRangeAddressList(rowNumber, rowNumber, dropColumn, dropColumn);
			int last = dropDownValues.length;
			DataValidation dataValidation = null;
			if (this.filename.contains(BULK_UPLOAD_FILE_EXTENSION_XLSX)) {
				XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
				XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
						.createFormulaListConstraint(
								"'Data Sheet'!$" + letters[colNumber] + "$1:$" + letters[colNumber] + "$" + last);
				dataValidation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
				dataValidation.setSuppressDropDownArrow(true);
				dataValidation.setShowErrorBox(true);

			} else {

				DVConstraint dvConstraint = DVConstraint.createFormulaListConstraint(
						"'Data Sheet'!$" + letters[colNumber] + "$1:$" + letters[colNumber] + "$" + last);
				dataValidation = new HSSFDataValidation(addressList, dvConstraint);
				dataValidation.setSuppressDropDownArrow(false);
			}
			sheet.addValidationData(dataValidation);
		}
	}

	/**
	 * Create a drop-down list for a column in the Excel spreadsheet.
	 * 
	 * @param sheetNumber    The sheet number to be read.
	 * @param colNumber      The number of the column (starting with 0) to contain
	 *                       the dropdown list
	 * @param dropDownValues A string array of the drop down list's selection
	 *                       values.
	 */
	public void createDropDownList(int sheetNumber, int colNumber, String[] dropDownValues) {
		if (dropDownValues != null && dropDownValues.length > 0) {
			Sheet sheet = workbook.getSheetAt(sheetNumber);
			CellRangeAddressList addressList = new CellRangeAddressList(1, ROWS_FOR_PULLDOWN_MENUS, colNumber,
					colNumber);
			DataValidation dataValidation = null;
			if (this.filename.contains(BULK_UPLOAD_FILE_EXTENSION_XLSX)) {
				XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
				XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
						.createExplicitListConstraint(dropDownValues);
				dataValidation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
				dataValidation.setSuppressDropDownArrow(true);
				dataValidation.setShowErrorBox(true);
			} else {

				DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(dropDownValues);
				dataValidation = new HSSFDataValidation(addressList, dvConstraint);
				dataValidation.setSuppressDropDownArrow(false);
			}
			sheet.addValidationData(dataValidation);
		}
	}

	/**
	 * Create a drop-down list for a column in the Excel spreadsheet.
	 * 
	 * @param sheetNumber    The sheet number to be read.
	 * @param rowNumber      The row at which the dropdown to be created
	 * @param colNumber      The number of the column (starting with 0) to contain
	 *                       the dropdown list
	 * @param dropDownValues A string array of the drop down list's selection
	 *                       values.
	 */
	public void createDropDownList(int sheetNumber, int rowNumber, int colNumber, String[] dropDownValues) {
		if (dropDownValues != null && dropDownValues.length > 0) {
			Sheet sheet = workbook.getSheetAt(sheetNumber);
			CellRangeAddressList addressList = new CellRangeAddressList(rowNumber, rowNumber, colNumber, colNumber);
			DataValidation dataValidation = null;
			if (this.filename.contains(BULK_UPLOAD_FILE_EXTENSION_XLSX)) {
				XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
				XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
						.createExplicitListConstraint(dropDownValues);
				dataValidation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
				dataValidation.setSuppressDropDownArrow(true);
				dataValidation.setShowErrorBox(true);
			} else {

				DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(dropDownValues);
				dataValidation = new HSSFDataValidation(addressList, dvConstraint);
				dataValidation.setSuppressDropDownArrow(false);
			}
			sheet.addValidationData(dataValidation);
		}
	}

	/**
	 * Create a two-dimensional string array in an Excel spreadsheet. This does not
	 * write the spreadsheet.
	 * 
	 * @param filename  Name of the Excel file.
	 * @param sheetname Name of the sheet in the workbook.
	 * @param records   Two dimensional String array.
	 */
	public void createSheet(String sheetName, String[][] records) {
		try {
			if (sheetName == null || records == null)
				return;
			if (sheetName.trim().length() > 31) {
				sheetName = sheetName.substring(0, 29) + "..";
			}
			if (this.filename.contains(BULK_UPLOAD_FILE_EXTENSION_XLSX))
				workbook = new XSSFWorkbook();
			else
				workbook = new HSSFWorkbook();
			Sheet sheet = workbook.createSheet(sheetName);
			RichTextString richTextString = null;
			for (int i = 0; i < records.length; i++) {
				Row row = sheet.createRow(i);
				for (int j = 0; j < records[i].length; j++) {
					if (this.filename.contains(BULK_UPLOAD_FILE_EXTENSION_XLSX))
						richTextString = new XSSFRichTextString(records[i][j]);
					else
						richTextString = new HSSFRichTextString(records[i][j]);
					if (richTextString != null)
						row.createCell(j).setCellValue(richTextString);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write a two-dimensional string array into an Excel spreadsheet file.
	 * 
	 * @param filename  Name of the Excel file.
	 * @param sheetname Name of the sheet in the workbook.
	 * @param records   Two dimensional String array.
	 */
	public boolean writeSheet(String sheetName, String[][] records) {
		if (sheetName == null || records == null)
			return false;
		if (sheetName.trim().length() > 31) {
			sheetName = sheetName.substring(0, 29) + "..";
		}

		if (this.filename.contains(BULK_UPLOAD_FILE_EXTENSION_XLSX))
			workbook = new XSSFWorkbook();
		else
			workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetName);
		CellStyle cs = workbook.createCellStyle();
		DataFormat df = workbook.createDataFormat();
		cs.setDataFormat(df.getFormat("text"));
		// cs.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
		RichTextString richTextString = null;
		for (int i = 0; i < records.length; i++) {
			Row row = sheet.createRow(i);
			for (int j = 0; j < records[i].length; j++) {
				Cell cell = row.createCell(j);
				cell.setCellStyle(cs);
				if (this.filename.contains(BULK_UPLOAD_FILE_EXTENSION_XLSX))
					richTextString = new XSSFRichTextString(records[i][j]);
				else
					richTextString = new HSSFRichTextString(records[i][j]);
				if (richTextString != null)
					cell.setCellValue(richTextString);
			}
		}
		if (this.filename.contains(BULK_UPLOAD_FILE_EXTENSION_XLSX))
			return true;
		else
			return writeWorkbook();
	}

	public boolean writeSheettoWorkBook(String sheetName, String[][] records, Workbook workbook) {
		if (sheetName == null || records == null)
			return false;
		if (sheetName.trim().length() > 31) {
			sheetName = sheetName.substring(0, 29) + "..";
		}
		// workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetName);
		CellStyle cs = workbook.createCellStyle();
		DataFormat df = workbook.createDataFormat();
		cs.setDataFormat(df.getFormat("text"));
		// cs.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
		RichTextString richTextString = null;
		for (int i = 0; i < records.length; i++) {
			Row row = sheet.createRow(i);
			for (int j = 0; j < records[i].length; j++) {
				Cell cell = row.createCell(j);
				cell.setCellStyle(cs);
				if (this.filename.contains(BULK_UPLOAD_FILE_EXTENSION_XLSX))
					richTextString = new XSSFRichTextString(records[i][j]);
				else
					richTextString = new HSSFRichTextString(records[i][j]);
				if (richTextString != null)
					cell.setCellValue(richTextString);
			}
		}
		return writeWorkbook();
	}

	public Workbook createSheettoWorkbook(String sheetName, String[][] records) {
		if (sheetName == null || records == null)
			return null;
		if (sheetName.trim().length() > 31) {
			sheetName = sheetName.substring(0, 29) + "..";
		}
		// workbook = new HSSFWorkbook();
		Sheet sheet = this.workbook.createSheet(sheetName);
		CellStyle style = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();

		style.setDataFormat(format.getFormat("text"));
		RichTextString richTextString = null;
		for (int i = 0; i < records.length; i++) {
			if (records[i] == null) {
				continue;
			}
			Row row = sheet.createRow(i);
			for (int j = 0; j < records[i].length; j++) {
				Cell cell = row.createCell(j);
				cell.setCellStyle(style);
				if (this.filename.contains(BULK_UPLOAD_FILE_EXTENSION_XLSX))
					richTextString = new XSSFRichTextString(records[i][j]);
				else
					richTextString = new HSSFRichTextString(records[i][j]);
				if (richTextString != null)
					cell.setCellValue(richTextString);
			}
		}
		return workbook;
	}

	public void createTitletoWorkBook(int sheetNumber) {
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		CellStyle style = this.workbook.createCellStyle();
		style.setFont(font);

		Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(0);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			if (row.getCell(i) != null)
				row.getCell(i).setCellStyle(style);
		}
	}

	public void createDropDownListtoWorkBook(int sheetNumber, int rowNumber, int colNumber, String[] dropDownValues) {
		if (dropDownValues != null && dropDownValues.length > 0) {
			Sheet sheet = this.workbook.getSheetAt(sheetNumber);
			CellRangeAddressList addressList = new CellRangeAddressList(rowNumber, ROWS_FOR_PULLDOWN_MENUS, colNumber,
					colNumber);
			DataValidation dataValidation = null;
			if (this.filename.contains(BULK_UPLOAD_FILE_EXTENSION_XLSX)) {
				XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
				XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
						.createExplicitListConstraint(dropDownValues);
				dataValidation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
				dataValidation.setSuppressDropDownArrow(true);
				dataValidation.setShowErrorBox(true);
			} else {

				DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(dropDownValues);
				dataValidation = new HSSFDataValidation(addressList, dvConstraint);
				dataValidation.setSuppressDropDownArrow(false);
			}

			sheet.addValidationData(dataValidation);
		}
	}

	public void createDropDownListtoWorkBook(int sheetNumber, int colNumber, String[] dropDownValues) {
		createDropDownListtoWorkBook(sheetNumber, 1, colNumber, dropDownValues);
	}

	public void hideColumnWorkBook(int sheetNumber, int colNumber) {
		Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		sheet.setColumnHidden(colNumber, true);
	}

	public void hideRowWorkBook(int sheetNumber, int rowNumber) {

		Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(rowNumber);
		row.setZeroHeight(true);
	}

	/**
	 * Read a line from a workbook into a List of String.
	 * 
	 * @param filename    The Excel file to be read.
	 * @param sheetNumber The sheet number to be read.
	 * @param lineNumber  The line number to be read.
	 * @return List of Strings.
	 */
	public List<String> readLineToList(int sheetNumber, int lineNumber) {
		workbook = readWb();
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(lineNumber);
		int totalCols = row.getPhysicalNumberOfCells();
		List<String> cells = new ArrayList<String>();
		for (int i = 0; i < totalCols; i++) {
			cells.add(row.getCell(i).toString());
		}
		return cells;
	}

	/**
	 * Read a line from a workbook into an array of String.
	 * 
	 * @param filename    The Excel file to be read.
	 * @param sheetNumber The sheet number to be read.
	 * @param lineNumber  The line number to be read.
	 * @return Array of Strings.
	 */
	public String[] readLineToArray(int sheetNumber, int lineNumber) {
		workbook = readWb();
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(lineNumber);
		int totalCols = row.getPhysicalNumberOfCells();
		String[] cells = new String[totalCols];
		for (int i = 0; i < totalCols; i++) {
			cells[i] = row.getCell(i).toString();
		}
		return cells;
	}

	/**
	 * Update the value of a cell in a workbook.
	 * 
	 * @param workbook     The workbook to be updated.
	 * @param sheetNumber  The sheet number to be updated.
	 * @param rowNumber    The line number to be updated.
	 * @param columnNumber The column number to be updated.
	 * @param newValue     String containing the new value.
	 */
	public boolean updateCell(int sheetNumber, int rowNumber, int columnNumber, String newValue) {
		workbook = readWb();
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(rowNumber);
		Cell cell = row.getCell(columnNumber, Row.CREATE_NULL_AS_BLANK);
		Cell firstCell = row.getCell(0, Row.CREATE_NULL_AS_BLANK);
		CellStyle cellStyle = firstCell.getCellStyle();
		if (cell == null) {
			cell = row.createCell(columnNumber);
		}
		RichTextString richTextString = null;
		if (this.filename.contains(BULK_UPLOAD_FILE_EXTENSION_XLSX))
			richTextString = new XSSFRichTextString(newValue);
		else
			richTextString = new HSSFRichTextString(newValue);
		if (richTextString != null)
			cell.setCellValue(richTextString);
		if (cellStyle != null)
			cell.setCellStyle(cellStyle);
		return writeWorkbook();
	}

	/**
	 * Update the value of a cell in a workbook.
	 * 
	 * @param workbook     The workbook to be updated.
	 * @param sheetNumber  The sheet number to be updated.
	 * @param rowNumber    The line number to be updated.
	 * @param columnNumber The column number to be updated.
	 * @param newValue     Calendar containing the new value.
	 */
	public boolean updateCell(int sheetNumber, int rowNumber, int columnNumber, Calendar newValue) {
		workbook = readWb();
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(rowNumber);
		Cell cell = row.getCell(columnNumber);
		if (cell == null) {
			cell = row.createCell(columnNumber);
		}
		cell.setCellValue(newValue);
		return writeWorkbook();
	}

	/**
	 * Update the value of a cell in a workbook.
	 * 
	 * @param workbook     The workbook to be updated.
	 * @param sheetNumber  The sheet number to be updated.
	 * @param rowNumber    The line number to be updated.
	 * @param columnNumber The column number to be updated.
	 * @param newValue     Number containing the new value.
	 */
	public boolean updateCell(int sheetNumber, int rowNumber, int columnNumber, Number newValue) {
		workbook = readWb();
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(rowNumber);
		Cell cell = row.getCell(columnNumber);
		if (cell == null) {
			cell = row.createCell(columnNumber);
		}
		if (newValue != null) {
			RichTextString richTextString = null;
			if (this.filename.contains(BULK_UPLOAD_FILE_EXTENSION_XLSX))
				richTextString = new XSSFRichTextString(newValue.toString());
			else
				richTextString = new HSSFRichTextString(newValue.toString());
			cell.setCellValue(richTextString);
		}
		return writeWorkbook();
	}

	public void customizeRowStyle(int sheetNumber, int rowNumber, int startCellNum, int endCellNum, String fontName) {
		CellStyle customStyle = workbook.createCellStyle();
		customStyle.setWrapText(true);
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		if (fontName != null && !fontName.equals(""))
			font.setFontName(fontName);
		customStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(rowNumber);
		for (int i = startCellNum; i <= endCellNum; i++) {
			customStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			customStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
			customStyle.setBorderBottom(CellStyle.BORDER_THIN);
			customStyle.setBorderTop(CellStyle.BORDER_THIN);
			customStyle.setBorderLeft(CellStyle.BORDER_THIN);
			customStyle.setBorderRight(CellStyle.BORDER_THIN);
			customStyle.setFont(font);
			row.getCell(i).setCellStyle(customStyle);
			sheet.autoSizeColumn((short) i);
		}
	}

	public void customizeBorder(int sheetNumber, int rowNumber, int startCellNum, int endCellNum, String fontName) {
		CellStyle customStyle = workbook.createCellStyle();
		customStyle.setWrapText(true);
		customStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		Font font = workbook.createFont();
		if (fontName != null && !fontName.equals(""))
			font.setFontName(fontName);
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(rowNumber);
		for (int i = startCellNum; i <= endCellNum; i++) {
			customStyle.setBorderBottom(CellStyle.BORDER_THIN);
			customStyle.setBorderTop(CellStyle.BORDER_THIN);
			customStyle.setBorderLeft(CellStyle.BORDER_THIN);
			customStyle.setBorderRight(CellStyle.BORDER_THIN);
			customStyle.setFont(font);
			row.getCell(i).setCellStyle(customStyle);
			sheet.autoSizeColumn((short) i);
		}
	}

	public void customMergeCells(int sheetNumber, int firstRow, int lastRow, int startCellNum, int endCellNum) {
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, startCellNum, endCellNum));
	}

	public void customHeaderFont(int sheetNumber, int rowNumber, int startCellNum, int endCellNum, int fontSize,
			String fontName, short alignType) {

		CellStyle customStyle = workbook.createCellStyle();
		customStyle.setWrapText(true);
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) fontSize);
		if (fontName != null && !fontName.equals(""))
			font.setFontName(fontName);
		customStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		customStyle.setAlignment(alignType);
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(rowNumber);
		for (int i = startCellNum; i <= endCellNum; i++) {
			customStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			customStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
			customStyle.setBorderBottom(CellStyle.BORDER_THIN);
			customStyle.setBorderTop(CellStyle.BORDER_THIN);
			customStyle.setBorderLeft(CellStyle.BORDER_THIN);
			customStyle.setBorderRight(CellStyle.BORDER_THIN);
			customStyle.setFont(font);
			row.getCell(i).setCellStyle(customStyle);
			sheet.autoSizeColumn((short) i);
		}

	}

	public void setCellFormatNumber(int sheetNumber, int rowNumber, int cellNum) {

		Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(rowNumber);
		if (row.getCell(cellNum) != null) {
			String cellValue = row.getCell(cellNum).getRichStringCellValue().getString();
			if (cellValue != null && !cellValue.isEmpty()) {
				if (cellValue.indexOf(".") != -1) {
					row.getCell(cellNum).setCellValue(Double.parseDouble(cellValue));
				} else {
					row.getCell(cellNum).setCellValue(Long.parseLong(cellValue));
				}
				row.getCell(cellNum).setCellType(Cell.CELL_TYPE_NUMERIC);
			}
		}
	}

	/**
	 * Testing
	 * 
	 */
	public static void main(String[] args) throws Exception {
		// Testing writeSheet method
		GenericExcel excel = new GenericExcel("C:\\workbook.xlsx");
		String[][] records = { { "A1111", "B1", "C1", "D1" }, { "A2", "B2", "C2", "0900" },
				{ "A3ASDFAFADF", "B3SDFSDFSDF", "C3ADFDFADF", "D3ADFADFAD" }, { "A4", "B4", "C4", "D4" } };

		excel.writeSheet("Sheet1", records);

		// Testing readSheet method
		/*
		 * String[][] myrecords = excel.readSheet(0); for (int i = 0; i <
		 * myrecords.length; i++) { for (int j = 0; j < myrecords[i].length; j++) {
		 * System.out.print(myrecords[i][j] + ","); } System.out.println(""); }
		 */

		// excel.customizeRowStyle(0, 1);
		excel.setCustomRowStyleForHeader(0, 2, IndexedColors.WHITE.index, 65);
		excel.customMergeCells(0, 0, 0, 0, 3);
		excel.customizeRowStyle(0, 0, 0, IndexedColors.LIGHT_TURQUOISE.index);
		// excel.createTitle(0);
		String[] languages = { "India", "China", "USA" };
		String[] testsdfsdf = { "345345", "5646", "65456456", "35345" };
		excel.createDropDownListtoWorkBook(0, 2, 1, languages);
		excel.createLargeDropDownListtoWorkBook(0, 2, 3, 3, testsdfsdf, 1);
		// excel.setCellTypeText(0,records);
		excel.hideRow(0, 1);
		excel.writeWorkbook();

		// Testing readLineToArray method
		String[] secondLine = excel.readLineToArray(0, 1);
		System.out.println("");
		for (int i = 0; i < secondLine.length; i++) {
			System.out.print(secondLine[i] + ",");
		}
		System.out.println("");
	}

}
