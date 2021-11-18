/*      */
package connectionTest;

/*      */
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.text.NumberFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import org.apache.poi.hssf.usermodel.DVConstraint;
/*      */ import org.apache.poi.hssf.usermodel.HSSFDataValidation;
/*      */ import org.apache.poi.hssf.usermodel.HSSFRichTextString;
/*      */ import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/*      */ import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
/*      */ import org.apache.poi.poifs.filesystem.POIFSFileSystem;
/*      */ import org.apache.poi.ss.usermodel.Cell;
/*      */ import org.apache.poi.ss.usermodel.CellStyle;
/*      */ import org.apache.poi.ss.usermodel.DataFormat;
/*      */ import org.apache.poi.ss.usermodel.DataValidation;
/*      */ import org.apache.poi.ss.usermodel.DataValidationConstraint;
/*      */ import org.apache.poi.ss.usermodel.DateUtil;
/*      */ import org.apache.poi.ss.usermodel.Font;
/*      */ import org.apache.poi.ss.usermodel.IndexedColors;
/*      */ import org.apache.poi.ss.usermodel.RichTextString;
/*      */ import org.apache.poi.ss.usermodel.Row;
/*      */ import org.apache.poi.ss.usermodel.Sheet;
/*      */ import org.apache.poi.ss.usermodel.Workbook;
/*      */ import org.apache.poi.ss.usermodel.WorkbookFactory;
/*      */ import org.apache.poi.ss.util.CellRangeAddress;
/*      */ import org.apache.poi.ss.util.CellRangeAddressList;
/*      */ import org.apache.poi.xssf.usermodel.XSSFDataValidation;
/*      */ import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
/*      */ import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
/*      */ import org.apache.poi.xssf.usermodel.XSSFRichTextString;
/*      */ import org.apache.poi.xssf.usermodel.XSSFSheet;
/*      */ import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */ public class GenericExcel
/*      */ {
	/* 52 */ public SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mm:ss.SSS");
	/*      */
	/*      */ private String filename;
	/*      */ private String excelFormatType;
	/*      */ private Workbook workbook;
	/*      */ boolean isMultiSheetExcel;
	/* 58 */ public int ROWS_FOR_PULLDOWN_MENUS = 10000;
	/*      */ public static final String BULK_UPLOAD_FILE_EXTENSION = ".xls";
	/*      */ public static final String BULK_UPLOAD_FILE_EXTENSION_XLSX = ".xlsx";
	/* 61 */ public static final String FILE_SEPERATOR = File.separator;
	/*      */
	/*      */ static {
		/* 64 */ System.setProperty("java.awt.headless", "true");
		/*      */ }

	/*      */
	/*      */
	/*      */ public GenericExcel(String filename) {
		/* 69 */ this.filename = filename;
		/* 70 */ if (this.filename != null && this.filename.substring(filename.length() - 5, filename.length())
				.toLowerCase()/* 71 */ .equals(".xlsx")) {
			/* 72 */ this.excelFormatType = ".xlsx";
			/*      */ } else {
			/* 74 */ this.excelFormatType = ".xls";
			/*      */ }
		/*      */ }

	/*      */
	/*      */ public GenericExcel(String filename, boolean isMultiSheetExcel) {
		/* 79 */ this.filename = filename;
		/* 80 */ this.isMultiSheetExcel = isMultiSheetExcel;
		/* 81 */ if (this.filename.contains(".xlsx")) {
			/* 82 */ this.excelFormatType = ".xlsx";
			/* 83 */ this.workbook = (Workbook) new XSSFWorkbook();
			/*      */ } else {
			/* 85 */ this.excelFormatType = ".xls";
			/* 86 */ this.workbook = (Workbook) new HSSFWorkbook();
			/*      */ }
		/*      */ }

	/*      */
	/*      */ public String getFilename() {
		/* 91 */ return this.filename;
		/*      */ }

	/*      */
	/*      */ public void setFilename(String filename) {
		/* 95 */ this.filename = filename;
		/*      */ }

	/*      */
	/*      */ public String getExcelFormatType() {
		/* 99 */ return this.excelFormatType;
		/*      */ }

	/*      */
	/*      */ public void setExcelFormatType(String excelFormatType) {
		/* 103 */ this.excelFormatType = excelFormatType;
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public void readWorkbook() {
		/* 113 */ readWb();
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ private Workbook readWb() {
		/*      */ try {
			/* 124 */ if (this.excelFormatType != null && this.excelFormatType.equals(".xlsx")) {
				/* 125 */ this.workbook = WorkbookFactory.create(new FileInputStream(this.filename));
				/*      */ } else {
				/* 127 */ POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(this.filename));
				/* 128 */ this.workbook = (Workbook) new HSSFWorkbook(fs);
				/*      */ }
			/* 130 */ } catch (IOException ex) {
			/* 131 */ ex.printStackTrace();
			/* 132 */ this.workbook = null;
			/* 133 */ } catch (InvalidFormatException e) {
			/* 134 */ e.printStackTrace();
			/*      */ }
		/* 136 */ return this.workbook;
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public boolean writeWorkbook() {
		/*      */ try {
			/* 147 */ BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(this.filename));
			/* 148 */ this.workbook.write(fileOut);
			/* 149 */ fileOut.flush();
			/* 150 */ fileOut.close();
			/* 151 */ return true;
			/* 152 */ } catch (FileNotFoundException ex) {
			/* 153 */ ex.printStackTrace();
			/* 154 */ } catch (IOException ex) {
			/* 155 */ ex.printStackTrace();
			/*      */ }
		/* 157 */ return false;
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public int getNumberOfSheets() {
		/* 166 */ this.workbook = readWb();
		/* 167 */ return this.workbook.getNumberOfSheets();
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public int getNumberOfRowsInSheet(int sheetNumber) {
		/* 176 */ this.workbook = readWb();
		/* 177 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 178 */ return sheet.getPhysicalNumberOfRows();
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public String[][] readSheet(int sheetNumber) throws Exception {
		/* 189 */ this.workbook = readWb();
		/* 190 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 191 */ return readSheet(sheet);
		/*      */ }

	/*      */
	/*      */ public String[][] readSheet(int sheetNumber, String excelFormatType) throws Exception {
		/* 195 */ if (excelFormatType != null && excelFormatType.length() > 0) {
			/* 196 */ this.excelFormatType = excelFormatType;
			/*      */ }
		/* 198 */ this.workbook = readWb();
		/* 199 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 200 */ return readSheet(sheet);
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public String[][] readSheet(String sheetName) throws Exception {
		/* 211 */ if (sheetName == null)
			/* 212 */ return null;
		/* 213 */ this.workbook = readWb();
		/* 214 */ Sheet sheet = this.workbook.getSheet(sheetName);
		/* 215 */ return readSheet(sheet);
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ private String[][] readSheet(Sheet sheet) throws Exception {
		/*      */ try {
			/* 227 */ NumberFormat nf = NumberFormat.getNumberInstance(Locale.ROOT);
			/* 228 */ nf.setParseIntegerOnly(true);
			/* 229 */ if (sheet.getPhysicalNumberOfRows() <= 0) {
				/* 230 */ throw new Exception("Please select a valid excel");
				/*      */ }
			/*      */
			/* 233 */ int totalRows = sheet.getPhysicalNumberOfRows();
			/* 234 */ Row row = sheet.getRow(sheet.getFirstRowNum());
			/* 235 */ int totalCols = row.getPhysicalNumberOfCells();
			/* 236 */ String[][] records = new String[totalRows][totalCols];
			/* 237 */ Boolean[] emptyRows = new Boolean[totalRows];
			/* 238 */ int emptyRowCount = 0;
			/* 239 */ for (int i = 0; i < totalRows; i++) {
				/* 240 */ row = sheet.getRow(i);
				/* 241 */ emptyRows[i] = Boolean.valueOf(true);
				/* 242 */ for (int k = 0; k < totalCols; k++) {
					/* 243 */ if (row.getCell(k) != null) {
						/* 244 */ if (row.getCell(k).getCellType() == 0 ||
						/* 245 */ row.getCell(k).getCellType() == 2) {
							/* 246 */ if (row.getCell(k).getCellType() == 0 &&
							/* 247 */ DateUtil.isCellDateFormatted(row.getCell(k))) {
								/* 248 */ records[i][k] = row.getCell(k).toString();
								/*      */ } else {
								/* 250 */ String regex = "(?<=[\\d])(,)(?=[\\d])";
								/* 251 */ Pattern p = Pattern.compile(regex);
								/* 252 */ Matcher m = p.matcher(nf.format(row.getCell(k).getNumericCellValue()));
								/* 253 */ records[i][k] = m.replaceAll("");
								/*      */ }
							/*      */ } else {
							/* 256 */ records[i][k] = row.getCell(k).toString();
							/*      */ }
						/* 258 */ if (records[i][k] != null && !records[i][k].equals(""))
							/* 259 */ emptyRows[i] = Boolean.valueOf(false);
						/*      */ } else {
						/* 261 */ records[i][k] = "";
						/*      */ }
					/*      */ }
				/* 264 */ if (emptyRows[i].booleanValue()) {
					/* 265 */ emptyRowCount++;
					/* 266 */ emptyRows[i] = Boolean.valueOf(true);
					/*      */ }
				/*      */ }
			/*      */
			/*      */
			/*      */
			/* 272 */ String[][] nonEmptyRows = new String[totalRows - emptyRowCount][totalCols];
			/* 273 */ int nonEmptyRowCount = 0;
			/* 274 */ for (int j = 0; j < totalRows; j++) {
				/* 275 */ if (!emptyRows[j].booleanValue()) {
					/* 276 */ nonEmptyRows[nonEmptyRowCount] = records[j];
					/* 277 */ nonEmptyRowCount++;
					/*      */ }
				/*      */ }
			/* 280 */ return nonEmptyRows;
			/*      */
			/*      */ }
		/* 283 */ catch (Throwable throwable) {
			/* 284 */ throw new Exception("Please select a valid excel", throwable);
			/*      */ }
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public void setRowFont(int sheetNumber, int rowNumber, int fontStyle) {
		/* 292 */ Font font = this.workbook.createFont();
		/*      */
		/* 294 */ if (fontStyle == 1) {
			/* 295 */ font.setBoldweight((short) 700);
			/*      */ } else {
			/* 297 */ font.setBoldweight((short) 400);
			/* 298 */ }
		font.setFontName("Calibri");
		/* 299 */ CellStyle style = this.workbook.createCellStyle();
		/* 300 */ style.setFont(font);
		/*      */
		/* 302 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 303 */ Row row = sheet.getRow(rowNumber);
		/* 304 */ for (int i = 0; i < row.getLastCellNum(); i++) {
			/* 305 */ row.getCell(i).setCellStyle(style);
			/*      */ }
		/*      */ }

	/*      */
	/*      */ public void setCustomRowStyle(int sheetNumber, int rowNumber) {
		/* 310 */ CellStyle customStyle = this.workbook.createCellStyle();
		/* 311 */ customStyle.setWrapText(true);
		/* 312 */ customStyle.setVerticalAlignment((short) 0);
		/* 313 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 314 */ Row row = sheet.getRow(rowNumber);
		/* 315 */ for (int i = 0; i < row.getLastCellNum(); i++) {
			/* 316 */ customStyle.setFillPattern((short) 1);
			/* 317 */ customStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
			/* 318 */ customStyle.setBorderBottom((short) 1);
			/* 319 */ customStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.index);
			/* 320 */ customStyle.setBorderLeft((short) 1);
			/* 321 */ customStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.index);
			/* 322 */ customStyle.setBorderRight((short) 1);
			/* 323 */ customStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.index);
			/* 324 */ customStyle.setBorderTop((short) 1);
			/* 325 */ customStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.index);
			/* 326 */ row.getCell(i).setCellStyle(customStyle);
			/* 327 */ sheet.autoSizeColumn((short) i);
			/*      */ }
		/*      */ }

	public void setCustomRowStyleForHeader(int sheetNumber, int rowNumber, short foregroundColor, float height) {
		CellStyle customStyle = this.workbook.createCellStyle();
		customStyle.setWrapText(true);
		Font font = this.workbook.createFont();
		customStyle.setVerticalAlignment((short) 0);
		Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		Row row = sheet.getRow(rowNumber);
		font.setBoldweight((short) 700);
		font.setFontName("Calibri");
		customStyle.setFillPattern((short) 1);
		customStyle.setFillForegroundColor(foregroundColor);
		customStyle.setBorderBottom((short) 1);
		customStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.index);
		customStyle.setBorderLeft((short) 1);
		customStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.index);
		customStyle.setBorderRight((short) 1);
		customStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.index);
		customStyle.setBorderTop((short) 1);
		customStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.index);
		customStyle.setFont(font);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			row.getCell(i).setCellStyle(customStyle);
			sheet.autoSizeColumn((short) i);
		}
		row.setHeightInPoints(height);
	}

	/*      */
	/*      */
	/*      */ public void customizeRowStyle(int sheetNumber, int rowNumber, int colNumber, short foregroundColor) {
		/* 362 */ CellStyle customStyle = this.workbook.createCellStyle();
		/*      */
		/* 364 */ Font font = this.workbook.createFont();
		/* 365 */ font.setBoldweight((short) 700);
		/* 366 */ customStyle.setVerticalAlignment((short) 0);
		/* 367 */ customStyle.setFillPattern((short) 1);
		/* 368 */ customStyle.setFillForegroundColor(foregroundColor);
		/* 369 */ customStyle.setFont(font);
		/* 370 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 371 */ Row row = sheet.getRow(rowNumber);
		/* 372 */ for (int i = colNumber; i < row.getLastCellNum(); i++) {
			/* 373 */ row.getCell(i).setCellStyle(customStyle);
			/* 374 */ sheet.autoSizeColumn((short) i);
			/*      */ }
		/*      */ }

	/*      */
	/*      */ public void customizeRowStyle(int sheetNumber, int rowNumber) {
		/* 379 */ CellStyle customStyle = this.workbook.createCellStyle();
		/*      */
		/* 381 */ Font font = this.workbook.createFont();
		/* 382 */ font.setBoldweight((short) 700);
		/*      */
		/* 384 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 385 */ Row row = sheet.getRow(rowNumber);
		/*      */
		/*      */
		/*      */
		/*      */
		/*      */
		/*      */
		/* 392 */ customStyle.setFont(font);
		/* 393 */ for (int i = 0; i < row.getLastCellNum(); i++) {
			/* 394 */ row.getCell(i).setCellStyle(customStyle);
			/* 395 */ sheet.autoSizeColumn((short) i);
			/*      */ }
		/*      */ }

	/*      */
	/*      */ public void customizeBorder(int sheetNumber, int rowNumber) {
		/* 400 */ CellStyle customStyle = this.workbook.createCellStyle();
		/* 401 */ customStyle.setWrapText(true);
		/* 402 */ customStyle.setVerticalAlignment((short) 0);
		/* 403 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 404 */ Row row = sheet.getRow(rowNumber);
		/* 405 */ for (int i = 1; i < row.getLastCellNum(); i++) {
			/* 406 */ customStyle.setBorderBottom((short) 1);
			/* 407 */ customStyle.setBorderTop((short) 1);
			/* 408 */ customStyle.setBorderLeft((short) 1);
			/* 409 */ customStyle.setBorderRight((short) 1);
			/* 410 */ row.getCell(i).setCellStyle(customStyle);
			/* 411 */ sheet.autoSizeColumn((short) i);
			/*      */ }
		/*      */ }

	/*      */
	/*      */ public void setCustomNewRowStyle(int sheetNumber, int rowNumber) {
		/* 416 */ CellStyle customStyle = this.workbook.createCellStyle();
		/* 417 */ customStyle.setWrapText(true);
		/* 418 */ customStyle.setVerticalAlignment((short) 0);
		/* 419 */ Font font = this.workbook.createFont();
		/* 420 */ font.setBoldweight((short) 700);
		/* 421 */ customStyle.setFont(font);
		/* 422 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 423 */ Row row = sheet.getRow(rowNumber);
		/* 424 */ for (int i = 0; i < row.getLastCellNum(); i++) {
			/* 425 */ customStyle.setFillPattern((short) 1);
			/* 426 */ customStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
			/* 427 */ customStyle.setBorderBottom((short) 1);
			/* 428 */ customStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.index);
			/* 429 */ customStyle.setBorderLeft((short) 1);
			/* 430 */ customStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.index);
			/* 431 */ customStyle.setBorderRight((short) 1);
			/* 432 */ customStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.index);
			/* 433 */ customStyle.setBorderTop((short) 1);
			/* 434 */ customStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.index);
			/* 435 */ row.getCell(i).setCellStyle(customStyle);
			/* 436 */ sheet.autoSizeColumn((short) i);
			/*      */ }
		/*      */ }

	/*      */
	/*      */ public void setCustomStyle(int sheetNumber, int rowNumber) {
		/* 441 */ CellStyle customStyle = this.workbook.createCellStyle();
		/* 442 */ customStyle.setWrapText(true);
		/* 443 */ customStyle.setVerticalAlignment((short) 0);
		/* 444 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 445 */ Row row = sheet.getRow(rowNumber);
		/* 446 */ for (int i = 0; i < row.getLastCellNum(); i++) {
			/* 447 */ customStyle.setBorderBottom((short) 1);
			/* 448 */ customStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.index);
			/* 449 */ customStyle.setBorderLeft((short) 1);
			/* 450 */ customStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.index);
			/* 451 */ customStyle.setBorderRight((short) 1);
			/* 452 */ customStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.index);
			/* 453 */ customStyle.setBorderTop((short) 1);
			/* 454 */ customStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.index);
			/* 455 */ row.getCell(i).setCellStyle(customStyle);
			/* 456 */ sheet.autoSizeColumn((short) i);
			/*      */ }
		/*      */ }

	/*      */
	/*      */ public void createTitle(int sheetNumber) {
		/* 461 */ Font font = this.workbook.createFont();
		/* 462 */ font.setBoldweight((short) 700);
		/* 463 */ font.setFontName("Calibri");
		/* 464 */ CellStyle style = this.workbook.createCellStyle();
		/* 465 */ style.setFont(font);
		/* 466 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 467 */ Row row = sheet.getRow(0);
		/* 468 */ for (int i = 0; i < row.getLastCellNum(); i++) {
			/* 469 */ row.getCell(i).setCellStyle(style);
			/* 470 */ sheet.autoSizeColumn((short) i);
			/*      */ }
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public void createTitleForCell(int sheetNumber, int rowNum, int colNum) {
		/* 478 */ Font font = this.workbook.createFont();
		/* 479 */ font.setBoldweight((short) 700);
		/*      */
		/* 481 */ CellStyle style = this.workbook.createCellStyle();
		/* 482 */ style.setFont(font);
		/*      */
		/* 484 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 485 */ Row row = sheet.getRow(rowNum);
		/* 486 */ if (row != null) {
			/* 487 */ row.getCell(colNum, Row.CREATE_NULL_AS_BLANK).setCellStyle(style);
			/*      */ }
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */ public void hideColumn(int sheetNumber, int colNumber) {
		/* 494 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 495 */ sheet.setColumnHidden(colNumber, true);
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public void hideRow(int sheetNumber, int rowNumber) {
		/* 505 */ if (this.workbook == null) {
			/* 506 */ this.workbook = readWb();
			/*      */ }
		/* 508 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 509 */ Row row = sheet.getRow(rowNumber);
		/* 510 */ row.setZeroHeight(true);
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public void createLargeDropDownList(int sheetNumber, int colNumber, int dropColumn,
			String[] dropDownValues) {
		/* 523 */ if (dropDownValues != null && dropDownValues.length > 0) {
			/* 524 */ HSSFDataValidation hSSFDataValidation = null;
			Sheet sheet = this.workbook.getSheetAt(sheetNumber);
			/*      */
			/*      */
			/*      */
			/* 528 */ Sheet dataSheet = this.workbook.getSheet("Data Sheet");
			/* 529 */ if (dataSheet == null) {
				/* 530 */ dataSheet = this.workbook.createSheet("Data Sheet");
				/* 531 */ this.workbook.setSheetHidden(1, true);
				/*      */ }
			/* 533 */ for (int i = 0; i < dropDownValues.length; i++) {
				/* 534 */ Row row = dataSheet.getRow(i);
				/* 535 */ if (row == null) {
					/* 536 */ row = dataSheet.createRow(i);
					/*      */ }
				/* 538 */ Cell cell = row.getCell(colNumber);
				/* 539 */ if (cell == null) {
					/* 540 */ cell = row.createCell(colNumber);
					/*      */ }
				/* 542 */ cell.setCellValue(dropDownValues[i]);
				/*      */ }
			/*      */
			/*      */
			/* 546 */ String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
					"P", "Q", /* 547 */ "R", "S", "T", "U", "V", "X", "Y", "Z" };
			/* 548 */ CellRangeAddressList addressList = new CellRangeAddressList(1, this.ROWS_FOR_PULLDOWN_MENUS,
					dropColumn, /* 549 */ dropColumn);
			/* 550 */ int last = dropDownValues.length;
			/*      */
			/* 552 */ DataValidation dataValidation = null;
			/* 553 */ if (this.filename.contains(".xlsx")) {
				/* 554 */ XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
				/* 555 */ XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
						/* 556 */ .createFormulaListConstraint(/* 557 */ "'Data Sheet'!$" + letters[colNumber] + "$1:$"
								+ letters[colNumber] + "$" + last);
				/* 558 */ XSSFDataValidation xSSFDataValidation = (XSSFDataValidation) dvHelper
						.createValidation((DataValidationConstraint) dvConstraint, addressList);
				/* 559 */ xSSFDataValidation.setSuppressDropDownArrow(true);
				/* 560 */ xSSFDataValidation.setShowErrorBox(true);
				/*      */ } else {
				/*      */
				/* 563 */ DVConstraint dvConstraint = DVConstraint.createFormulaListConstraint(
						/* 564 */ "'Data Sheet'!$" + letters[colNumber] + "$1:$" + letters[colNumber] + "$" + last);
				/* 565 */ hSSFDataValidation = new HSSFDataValidation(addressList,
						(DataValidationConstraint) dvConstraint);
				/* 566 */ hSSFDataValidation.setSuppressDropDownArrow(false);
				/*      */ }
			/* 568 */ sheet.addValidationData((DataValidation) hSSFDataValidation);
			/*      */ }
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public void createLargeDropDownListtoWorkBook(int sheetNumber, int rowNumber, int colNumber,
			int dropColumn, String[] dropDownValues, int dataSheetNum) {
		/* 584 */ if (dropDownValues != null && dropDownValues.length > 0) {
			/* 585 */ HSSFDataValidation hSSFDataValidation = null;
			Sheet sheet = this.workbook.getSheetAt(sheetNumber);
			/*      */
			/*      */
			/*      */
			/* 589 */ Sheet dataSheet = this.workbook.getSheet("Data Sheet");
			/* 590 */ if (dataSheet == null) {
				/* 591 */ dataSheet = this.workbook.createSheet("Data Sheet");
				/* 592 */ this.workbook.setSheetHidden(dataSheetNum, true);
				/*      */ }
			/* 594 */ for (int i = 0; i < dropDownValues.length; i++) {
				/* 595 */ Row row = dataSheet.getRow(i);
				/* 596 */ if (row == null) {
					/* 597 */ row = dataSheet.createRow(i);
					/*      */ }
				/* 599 */ Cell cell = row.getCell(colNumber);
				/* 600 */ if (cell == null) {
					/* 601 */ cell = row.createCell(colNumber);
					/*      */ }
				/* 603 */ cell.setCellValue(dropDownValues[i]);
				/*      */ }
			/*      */
			/*      */
			/* 607 */ String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
					"P", "Q", /* 608 */ "R", "S", "T", "U", "V", "X", "Y", "Z" };
			/* 609 */ CellRangeAddressList addressList = new CellRangeAddressList(rowNumber,
					this.ROWS_FOR_PULLDOWN_MENUS, dropColumn, /* 610 */ dropColumn);
			/* 611 */ int last = dropDownValues.length;
			/* 612 */ DataValidation dataValidation = null;
			/* 613 */ if (this.filename.contains(".xlsx")) {
				/* 614 */ XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
				/* 615 */ XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
						/* 616 */ .createFormulaListConstraint(/* 617 */ "'Data Sheet'!$" + letters[colNumber] + "$1:$"
								+ letters[colNumber] + "$" + last);
				/* 618 */ XSSFDataValidation xSSFDataValidation = (XSSFDataValidation) dvHelper
						.createValidation((DataValidationConstraint) dvConstraint, addressList);
				/* 619 */ xSSFDataValidation.setSuppressDropDownArrow(true);
				/* 620 */ xSSFDataValidation.setShowErrorBox(true);
				/*      */ } else {
				/* 622 */ DVConstraint dvConstraint = DVConstraint.createFormulaListConstraint(
						/* 623 */ "'Data Sheet'!$" + letters[colNumber] + "$1:$" + letters[colNumber] + "$" + last);
				/* 624 */ hSSFDataValidation = new HSSFDataValidation(addressList,
						(DataValidationConstraint) dvConstraint);
				/* 625 */ hSSFDataValidation.setSuppressDropDownArrow(false);
				/*      */ }
			/* 627 */ sheet.addValidationData((DataValidation) hSSFDataValidation);
			/*      */ }
		/*      */ }

	/*      */
	/*      */
	/*      */ public void createLargeDropDownListtoWorkBook(int sheetNumber, int colNumber, int dropColumn,
			String[] dropDownValues, int dataSheetNum) {
		/* 633 */ createLargeDropDownListtoWorkBook(sheetNumber, 1, colNumber, dropColumn, dropDownValues,
				dataSheetNum);
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public void createLargeDropDownList(int sheetNumber, int rowNumber, int colNumber, int dropColumn,
			String[] dropDownValues) {
		/* 650 */ if (dropDownValues != null && dropDownValues.length > 0) {
			/* 651 */ HSSFDataValidation hSSFDataValidation = null;
			Sheet sheet = this.workbook.getSheetAt(sheetNumber);
			/*      */
			/*      */
			/*      */
			/* 655 */ Sheet dataSheet = this.workbook.getSheet("Data Sheet");
			/* 656 */ if (dataSheet == null) {
				/* 657 */ dataSheet = this.workbook.createSheet("Data Sheet");
				/* 658 */ this.workbook.setSheetHidden(1, true);
				/*      */ }
			/* 660 */ for (int i = 0; i < dropDownValues.length; i++) {
				/* 661 */ Row row = dataSheet.getRow(i);
				/* 662 */ if (row == null) {
					/* 663 */ row = dataSheet.createRow(i);
					/*      */ }
				/* 665 */ Cell cell = row.getCell(colNumber);
				/* 666 */ if (cell == null) {
					/* 667 */ cell = row.createCell(colNumber);
					/*      */ }
				/* 669 */ cell.setCellValue(dropDownValues[i]);
				/*      */ }
			/*      */
			/*      */
			/* 673 */ String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
					"P", "Q", /* 674 */ "R", "S", "T", "U", "V", "X", "Y", "Z" };
			/* 675 */ CellRangeAddressList addressList = new CellRangeAddressList(rowNumber, rowNumber, dropColumn,
					dropColumn);
			/* 676 */ int last = dropDownValues.length;
			/* 677 */ DataValidation dataValidation = null;
			/* 678 */ if (this.filename.contains(".xlsx")) {
				/* 679 */ XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
				/* 680 */ XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
						/* 681 */ .createFormulaListConstraint(/* 682 */ "'Data Sheet'!$" + letters[colNumber] + "$1:$"
								+ letters[colNumber] + "$" + last);
				/* 683 */ XSSFDataValidation xSSFDataValidation = (XSSFDataValidation) dvHelper
						.createValidation((DataValidationConstraint) dvConstraint, addressList);
				/* 684 */ xSSFDataValidation.setSuppressDropDownArrow(true);
				/* 685 */ xSSFDataValidation.setShowErrorBox(true);
				/*      */ }
			/*      */ else {
				/*      */
				/* 689 */ DVConstraint dvConstraint = DVConstraint.createFormulaListConstraint(
						/* 690 */ "'Data Sheet'!$" + letters[colNumber] + "$1:$" + letters[colNumber] + "$" + last);
				/* 691 */ hSSFDataValidation = new HSSFDataValidation(addressList,
						(DataValidationConstraint) dvConstraint);
				/* 692 */ hSSFDataValidation.setSuppressDropDownArrow(false);
				/*      */ }
			/* 694 */ sheet.addValidationData((DataValidation) hSSFDataValidation);
			/*      */ }
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public void createDropDownList(int sheetNumber, int colNumber, String[] dropDownValues) {
		/* 708 */ if (dropDownValues != null && dropDownValues.length > 0) {
			/* 709 */ HSSFDataValidation hSSFDataValidation = null;
			Sheet sheet = this.workbook.getSheetAt(sheetNumber);
			/* 710 */ CellRangeAddressList addressList = new CellRangeAddressList(1, this.ROWS_FOR_PULLDOWN_MENUS,
					colNumber, /* 711 */ colNumber);
			/* 712 */ DataValidation dataValidation = null;
			/* 713 */ if (this.filename.contains(".xlsx")) {
				/* 714 */ XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
				/* 715 */ XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
						/* 716 */ .createExplicitListConstraint(dropDownValues);
				/* 717 */ XSSFDataValidation xSSFDataValidation = (XSSFDataValidation) dvHelper
						.createValidation((DataValidationConstraint) dvConstraint, addressList);
				/* 718 */ xSSFDataValidation.setSuppressDropDownArrow(true);
				/* 719 */ xSSFDataValidation.setShowErrorBox(true);
				/*      */ } else {
				/*      */
				/* 722 */ DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(dropDownValues);
				/* 723 */ hSSFDataValidation = new HSSFDataValidation(addressList,
						(DataValidationConstraint) dvConstraint);
				/* 724 */ hSSFDataValidation.setSuppressDropDownArrow(false);
				/*      */ }
			/* 726 */ sheet.addValidationData((DataValidation) hSSFDataValidation);
			/*      */ }
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public void createDropDownList(int sheetNumber, int rowNumber, int colNumber, String[] dropDownValues) {
		/* 741 */ if (dropDownValues != null && dropDownValues.length > 0) {
			/* 742 */ HSSFDataValidation hSSFDataValidation = null;
			Sheet sheet = this.workbook.getSheetAt(sheetNumber);
			/* 743 */ CellRangeAddressList addressList = new CellRangeAddressList(rowNumber, rowNumber, colNumber,
					colNumber);
			/* 744 */ DataValidation dataValidation = null;
			/* 745 */ if (this.filename.contains(".xlsx")) {
				/* 746 */ XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
				/* 747 */ XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
						/* 748 */ .createExplicitListConstraint(dropDownValues);
				/* 749 */ XSSFDataValidation xSSFDataValidation = (XSSFDataValidation) dvHelper
						.createValidation((DataValidationConstraint) dvConstraint, addressList);
				/* 750 */ xSSFDataValidation.setSuppressDropDownArrow(true);
				/* 751 */ xSSFDataValidation.setShowErrorBox(true);
				/*      */ } else {
				/*      */
				/* 754 */ DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(dropDownValues);
				/* 755 */ hSSFDataValidation = new HSSFDataValidation(addressList,
						(DataValidationConstraint) dvConstraint);
				/* 756 */ hSSFDataValidation.setSuppressDropDownArrow(false);
				/*      */ }
			/* 758 */ sheet.addValidationData((DataValidation) hSSFDataValidation);
			/*      */ }
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public void createSheet(String sheetName, String[][] records) {
		/*      */ try {
			/* 772 */ if (sheetName == null || records == null)
				/*      */ return;
			/* 774 */ if (sheetName.trim().length() > 31) {
				/* 775 */ sheetName = String.valueOf(sheetName.substring(0, 29)) + "..";
				/*      */ }
			/* 777 */ if (this.filename.contains(".xlsx")) {
				/* 778 */ this.workbook = (Workbook) new XSSFWorkbook();
				/*      */ } else {
				/* 780 */ this.workbook = (Workbook) new HSSFWorkbook();
				/* 781 */ }
			Sheet sheet = this.workbook.createSheet(sheetName);
			/* 782 */ RichTextString richTextString = null;
			/* 783 */ for (int i = 0; i < records.length; i++) {
				/* 784 */ Row row = sheet.createRow(i);
				/* 785 */ for (int j = 0; j < (records[i]).length; j++) {
					/* 786 */ HSSFRichTextString hSSFRichTextString = null;
					XSSFRichTextString xSSFRichTextString = null;
					if (this.filename.contains(".xlsx")) {
						/* 787 */ xSSFRichTextString = new XSSFRichTextString(records[i][j]);
						/*      */ } else {
						/* 789 */ hSSFRichTextString = new HSSFRichTextString(records[i][j]);
						/* 790 */ }
					if (xSSFRichTextString != null)
						/* 791 */ row.createCell(j).setCellValue((RichTextString) xSSFRichTextString);
					if (hSSFRichTextString != null)
						/* 791 */ row.createCell(j).setCellValue((RichTextString) hSSFRichTextString);
					/*      */ }

				/*      */ }
			/* 794 */ } catch (

		Exception e) {
			/* 795 */ e.printStackTrace();
			/*      */ }
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public boolean writeSheet(String sheetName, String[][] records) {
		/* 807 */ if (sheetName == null || records == null)
			/* 808 */ return false;
		/* 809 */ if (sheetName.trim().length() > 31) {
			/* 810 */ sheetName = String.valueOf(sheetName.substring(0, 29)) + "..";
			/*      */ }
		/*      */
		/* 813 */ if (this.filename.contains(".xlsx")) {
			/* 814 */ this.workbook = (Workbook) new XSSFWorkbook();
			/*      */ } else {
			/* 816 */ this.workbook = (Workbook) new HSSFWorkbook();
			/* 817 */ }
		Sheet sheet = this.workbook.createSheet(sheetName);
		/* 818 */ CellStyle cs = this.workbook.createCellStyle();
		/* 819 */ DataFormat df = this.workbook.createDataFormat();
		/* 820 */ cs.setDataFormat(df.getFormat("text"));
		/*      */
		/* 822 */ RichTextString richTextString = null;
		/* 823 */ for (int i = 0; i < records.length; i++) {
			/* 824 */ Row row = sheet.createRow(i);
			/* 825 */ for (int j = 0; j < (records[i]).length; j++) {
				/* 826 */ HSSFRichTextString hSSFRichTextString = null;
				XSSFRichTextString xSSFRichTextString = null;
				Cell cell = row.createCell(j);
				/* 827 */ cell.setCellStyle(cs);
				/* 828 */ if (this.filename.contains(".xlsx")) {
					/* 829 */ xSSFRichTextString = new XSSFRichTextString(records[i][j]);
					/*      */ } else {
					/* 831 */ hSSFRichTextString = new HSSFRichTextString(records[i][j]);
					/* 832 */ }
				if (xSSFRichTextString != null)
					/* 833 */ cell.setCellValue((RichTextString) xSSFRichTextString);
				if (hSSFRichTextString != null)
					/* 833 */ cell.setCellValue((RichTextString) hSSFRichTextString);
				/*      */ }
			/*      */ }
		/* 836 */ if (this.filename.contains(".xlsx")) {
			/* 837 */ return true;
			/*      */ }
		/* 839 */ return writeWorkbook();
		/*      */ }

	/*      */
	/*      */ public boolean writeSheettoWorkBook(String sheetName, String[][] records, Workbook workbook) {
		/* 843 */ if (sheetName == null || records == null)
			/* 844 */ return false;
		/* 845 */ if (sheetName.trim().length() > 31) {
			/* 846 */ sheetName = String.valueOf(sheetName.substring(0, 29)) + "..";
			/*      */ }
		/*      */
		/* 849 */ Sheet sheet = workbook.createSheet(sheetName);
		/* 850 */ CellStyle cs = workbook.createCellStyle();
		/* 851 */ DataFormat df = workbook.createDataFormat();
		/* 852 */ cs.setDataFormat(df.getFormat("text"));
		/*      */
		/* 854 */ RichTextString richTextString = null;
		/* 855 */ for (int i = 0; i < records.length; i++) {
			/* 856 */ Row row = sheet.createRow(i);
			/* 857 */ for (int j = 0; j < (records[i]).length; j++) {
				/* 858 */ HSSFRichTextString hSSFRichTextString = null;
				XSSFRichTextString xSSFRichTextString = null;
				Cell cell = row.createCell(j);
				/* 859 */ cell.setCellStyle(cs);
				/* 860 */ if (this.filename.contains(".xlsx")) {
					/* 861 */ xSSFRichTextString = new XSSFRichTextString(records[i][j]);
					/*      */ } else {
					/* 863 */ hSSFRichTextString = new HSSFRichTextString(records[i][j]);
					/* 864 */ }
				if (xSSFRichTextString != null)
					/* 865 */ cell.setCellValue((RichTextString) xSSFRichTextString);
				if (hSSFRichTextString != null)
					/* 865 */ cell.setCellValue((RichTextString) hSSFRichTextString);
				/*      */ }
			/*      */ }
		/* 868 */ return writeWorkbook();
		/*      */ }

	/*      */
	/*      */ public Workbook createSheettoWorkbook(String sheetName, String[][] records) {
		/* 872 */ if (sheetName == null || records == null)
			/* 873 */ return null;
		/* 874 */ if (sheetName.trim().length() > 31) {
			/* 875 */ sheetName = String.valueOf(sheetName.substring(0, 29)) + "..";
			/*      */ }
		/*      */
		/* 878 */ Sheet sheet = this.workbook.createSheet(sheetName);
		/* 879 */ CellStyle style = this.workbook.createCellStyle();
		/* 880 */ DataFormat format = this.workbook.createDataFormat();
		/*      */
		/* 882 */ style.setDataFormat(format.getFormat("text"));
		/* 883 */ RichTextString richTextString = null;
		/* 884 */ for (int i = 0; i < records.length; i++) {
			/* 885 */ if (records[i] != null) {
				/*      */
				/*      */
				/* 888 */ Row row = sheet.createRow(i);
				/* 889 */ for (int j = 0; j < (records[i]).length; j++) {
					/* 890 */ HSSFRichTextString hSSFRichTextString = null;
					XSSFRichTextString xSSFRichTextString = null;
					Cell cell = row.createCell(j);
					/* 891 */ cell.setCellStyle(style);
					/* 892 */ if (this.filename.contains(".xlsx")) {
						/* 893 */ xSSFRichTextString = new XSSFRichTextString(records[i][j]);
						/*      */ } else {
						/* 895 */ hSSFRichTextString = new HSSFRichTextString(records[i][j]);
						/* 896 */ }
					if (xSSFRichTextString != null)
						/* 897 */ cell.setCellValue((RichTextString) xSSFRichTextString);
					if (hSSFRichTextString != null)
						/* 897 */ cell.setCellValue((RichTextString) hSSFRichTextString);
					/*      */ }
				/*      */ }
			/* 900 */ }
		return this.workbook;
		/*      */ }

	/*      */
	/*      */ public void createTitletoWorkBook(int sheetNumber) {
		/* 904 */ Font font = this.workbook.createFont();
		/* 905 */ font.setBoldweight((short) 700);
		/*      */
		/* 907 */ CellStyle style = this.workbook.createCellStyle();
		/* 908 */ style.setFont(font);
		/*      */
		/* 910 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 911 */ Row row = sheet.getRow(0);
		/* 912 */ for (int i = 0; i < row.getLastCellNum(); i++) {
			/* 913 */ if (row.getCell(i) != null)
				/* 914 */ row.getCell(i).setCellStyle(style);
			/*      */ }
		/*      */ }

	/*      */
	/*      */ public void createDropDownListtoWorkBook(int sheetNumber, int rowNumber, int colNumber,
			String[] dropDownValues) {
		/* 919 */ if (dropDownValues != null && dropDownValues.length > 0) {
			/* 920 */ HSSFDataValidation hSSFDataValidation = null;
			Sheet sheet = this.workbook.getSheetAt(sheetNumber);
			/* 921 */ CellRangeAddressList addressList = new CellRangeAddressList(rowNumber,
					this.ROWS_FOR_PULLDOWN_MENUS, colNumber, /* 922 */ colNumber);
			/* 923 */ DataValidation dataValidation = null;
			/* 924 */ if (this.filename.contains(".xlsx")) {
				/* 925 */ XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
				/* 926 */ XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
						/* 927 */ .createExplicitListConstraint(dropDownValues);
				/* 928 */ XSSFDataValidation xSSFDataValidation = (XSSFDataValidation) dvHelper
						.createValidation((DataValidationConstraint) dvConstraint, addressList);
				/* 929 */ xSSFDataValidation.setSuppressDropDownArrow(true);
				/* 930 */ xSSFDataValidation.setShowErrorBox(true);
				/*      */ } else {
				/*      */
				/* 933 */ DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(dropDownValues);
				/* 934 */ hSSFDataValidation = new HSSFDataValidation(addressList,
						(DataValidationConstraint) dvConstraint);
				/* 935 */ hSSFDataValidation.setSuppressDropDownArrow(false);
				/*      */ }
			/*      */
			/* 938 */ sheet.addValidationData((DataValidation) hSSFDataValidation);
			/*      */ }
		/*      */ }

	/*      */
	/*      */ public void createDropDownListtoWorkBook(int sheetNumber, int colNumber, String[] dropDownValues) {
		/* 943 */ createDropDownListtoWorkBook(sheetNumber, 1, colNumber, dropDownValues);
		/*      */ }

	/*      */
	/*      */ public void hideColumnWorkBook(int sheetNumber, int colNumber) {
		/* 947 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 948 */ sheet.setColumnHidden(colNumber, true);
		/*      */ }

	/*      */
	/*      */
	/*      */ public void hideRowWorkBook(int sheetNumber, int rowNumber) {
		/* 953 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 954 */ Row row = sheet.getRow(rowNumber);
		/* 955 */ row.setZeroHeight(true);
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public List<String> readLineToList(int sheetNumber, int lineNumber) {
		/* 967 */ this.workbook = readWb();
		/* 968 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 969 */ Row row = sheet.getRow(lineNumber);
		/* 970 */ int totalCols = row.getPhysicalNumberOfCells();
		/* 971 */ List<String> cells = new ArrayList<>();
		/* 972 */ for (int i = 0; i < totalCols; i++) {
			/* 973 */ cells.add(row.getCell(i).toString());
			/*      */ }
		/* 975 */ return cells;
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public String[] readLineToArray(int sheetNumber, int lineNumber) {
		/* 987 */ this.workbook = readWb();
		/* 988 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 989 */ Row row = sheet.getRow(lineNumber);
		/* 990 */ int totalCols = row.getPhysicalNumberOfCells();
		/* 991 */ String[] cells = new String[totalCols];
		/* 992 */ for (int i = 0; i < totalCols; i++) {
			/* 993 */ cells[i] = row.getCell(i).toString();
			/*      */ }
		/* 995 */ return cells;
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public boolean updateCell(int sheetNumber, int rowNumber, int columnNumber, String newValue) {
		/*      */ HSSFRichTextString hSSFRichTextString = null;
		/* 1008 */ this.workbook = readWb();
		/* 1009 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 1010 */ Row row = sheet.getRow(rowNumber);
		/* 1011 */ Cell cell = row.getCell(columnNumber, Row.CREATE_NULL_AS_BLANK);
		/* 1012 */ Cell firstCell = row.getCell(0, Row.CREATE_NULL_AS_BLANK);
		/* 1013 */ CellStyle cellStyle = firstCell.getCellStyle();
		/* 1014 */ if (cell == null) {
			/* 1015 */ cell = row.createCell(columnNumber);
			/*      */ }
		/* 1017 */ RichTextString richTextString = null;
		/* 1018 */ if (this.filename.contains(".xlsx")) {
			/* 1019 */ XSSFRichTextString xSSFRichTextString = new XSSFRichTextString(newValue);
			/*      */ } else {
			/* 1021 */ hSSFRichTextString = new HSSFRichTextString(newValue);
			/* 1022 */ }
		if (hSSFRichTextString != null)
			/* 1023 */ cell.setCellValue((RichTextString) hSSFRichTextString);
		/* 1024 */ if (cellStyle != null)
			/* 1025 */ cell.setCellStyle(cellStyle);
		/* 1026 */ return writeWorkbook();
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public boolean updateCell(int sheetNumber, int rowNumber, int columnNumber, Calendar newValue) {
		/* 1039 */ this.workbook = readWb();
		/* 1040 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 1041 */ Row row = sheet.getRow(rowNumber);
		/* 1042 */ Cell cell = row.getCell(columnNumber);
		/* 1043 */ if (cell == null) {
			/* 1044 */ cell = row.createCell(columnNumber);
			/*      */ }
		/* 1046 */ cell.setCellValue(newValue);
		/* 1047 */ return writeWorkbook();
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public boolean updateCell(int sheetNumber, int rowNumber, int columnNumber, Number newValue) {
		/* 1060 */ this.workbook = readWb();
		/* 1061 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 1062 */ Row row = sheet.getRow(rowNumber);
		/* 1063 */ Cell cell = row.getCell(columnNumber);
		/* 1064 */ if (cell == null) {
			/* 1065 */ cell = row.createCell(columnNumber);
			/*      */ }
		/* 1067 */ if (newValue != null) {
			/* 1068 */ HSSFRichTextString hSSFRichTextString = null;
			RichTextString richTextString = null;
			/* 1069 */ if (this.filename.contains(".xlsx")) {
				/* 1070 */ XSSFRichTextString xSSFRichTextString = new XSSFRichTextString(newValue.toString());
				/*      */ } else {
				/* 1072 */ hSSFRichTextString = new HSSFRichTextString(newValue.toString());
				/* 1073 */ }
			cell.setCellValue((RichTextString) hSSFRichTextString);
			/*      */ }
		/* 1075 */ return writeWorkbook();
		/*      */ }

	/*      */
	/*      */ public void customizeRowStyle(int sheetNumber, int rowNumber, int startCellNum, int endCellNum,
			String fontName) {
		/* 1079 */ CellStyle customStyle = this.workbook.createCellStyle();
		/* 1080 */ customStyle.setWrapText(true);
		/* 1081 */ Font font = this.workbook.createFont();
		/* 1082 */ font.setBoldweight((short) 700);
		/* 1083 */ if (fontName != null && !fontName.equals(""))
			/* 1084 */ font.setFontName(fontName);
		/* 1085 */ customStyle.setVerticalAlignment((short) 0);
		/* 1086 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 1087 */ Row row = sheet.getRow(rowNumber);
		/* 1088 */ for (int i = startCellNum; i <= endCellNum; i++) {
			/* 1089 */ customStyle.setFillPattern((short) 1);
			/* 1090 */ customStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
			/* 1091 */ customStyle.setBorderBottom((short) 1);
			/* 1092 */ customStyle.setBorderTop((short) 1);
			/* 1093 */ customStyle.setBorderLeft((short) 1);
			/* 1094 */ customStyle.setBorderRight((short) 1);
			/* 1095 */ customStyle.setFont(font);
			/* 1096 */ row.getCell(i).setCellStyle(customStyle);
			/* 1097 */ sheet.autoSizeColumn((short) i);
			/*      */ }
		/*      */ }

	/*      */
	/*      */ public void customizeBorder(int sheetNumber, int rowNumber, int startCellNum, int endCellNum,
			String fontName) {
		/* 1102 */ CellStyle customStyle = this.workbook.createCellStyle();
		/* 1103 */ customStyle.setWrapText(true);
		/* 1104 */ customStyle.setVerticalAlignment((short) 0);
		/* 1105 */ Font font = this.workbook.createFont();
		/* 1106 */ if (fontName != null && !fontName.equals(""))
			/* 1107 */ font.setFontName(fontName);
		/* 1108 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 1109 */ Row row = sheet.getRow(rowNumber);
		/* 1110 */ for (int i = startCellNum; i <= endCellNum; i++) {
			/* 1111 */ customStyle.setBorderBottom((short) 1);
			/* 1112 */ customStyle.setBorderTop((short) 1);
			/* 1113 */ customStyle.setBorderLeft((short) 1);
			/* 1114 */ customStyle.setBorderRight((short) 1);
			/* 1115 */ customStyle.setFont(font);
			/* 1116 */ row.getCell(i).setCellStyle(customStyle);
			/* 1117 */ sheet.autoSizeColumn((short) i);
			/*      */ }
		/*      */ }

	/*      */
	/*      */ public void customMergeCells(int sheetNumber, int firstRow, int lastRow, int startCellNum,
			int endCellNum) {
		/* 1122 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 1123 */ sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, startCellNum, endCellNum));
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */ public void customHeaderFont(int sheetNumber, int rowNumber, int startCellNum, int endCellNum,
			int fontSize, String fontName, short alignType) {
		/* 1129 */ CellStyle customStyle = this.workbook.createCellStyle();
		/* 1130 */ customStyle.setWrapText(true);
		/* 1131 */ Font font = this.workbook.createFont();
		/* 1132 */ font.setBoldweight((short) 700);
		/* 1133 */ font.setFontHeightInPoints((short) fontSize);
		/* 1134 */ if (fontName != null && !fontName.equals(""))
			/* 1135 */ font.setFontName(fontName);
		/* 1136 */ customStyle.setVerticalAlignment((short) 0);
		/* 1137 */ customStyle.setAlignment(alignType);
		/* 1138 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 1139 */ Row row = sheet.getRow(rowNumber);
		/* 1140 */ for (int i = startCellNum; i <= endCellNum; i++) {
			/* 1141 */ customStyle.setFillPattern((short) 1);
			/* 1142 */ customStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
			/* 1143 */ customStyle.setBorderBottom((short) 1);
			/* 1144 */ customStyle.setBorderTop((short) 1);
			/* 1145 */ customStyle.setBorderLeft((short) 1);
			/* 1146 */ customStyle.setBorderRight((short) 1);
			/* 1147 */ customStyle.setFont(font);
			/* 1148 */ row.getCell(i).setCellStyle(customStyle);
			/* 1149 */ sheet.autoSizeColumn((short) i);
			/*      */ }
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */ public void setCellFormatNumber(int sheetNumber, int rowNumber, int cellNum) {
		/* 1156 */ Sheet sheet = this.workbook.getSheetAt(sheetNumber);
		/* 1157 */ Row row = sheet.getRow(rowNumber);
		/* 1158 */ if (row.getCell(cellNum) != null) {
			/* 1159 */ String cellValue = row.getCell(cellNum).getRichStringCellValue().getString();
			/* 1160 */ if (cellValue != null && !cellValue.isEmpty()) {
				/* 1161 */ if (cellValue.indexOf(".") != -1) {
					/* 1162 */ row.getCell(cellNum).setCellValue(Double.parseDouble(cellValue));
					/*      */ } else {
					/* 1164 */ row.getCell(cellNum).setCellValue(Long.parseLong(cellValue));
					/*      */ }
				/* 1166 */ row.getCell(cellNum).setCellType(0);
				/*      */ }
			/*      */ }
		/*      */ }

	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */
	/*      */ public static void main(String[] args) throws Exception {
		/* 1177 */ GenericExcel excel = new GenericExcel("C:\\workbook.xlsx");
		/* 1178 */ String[][] records = { { "A1111", "B1", "C1", "D1" }, { "A2", "B2", "C2", "0900"
				/* 1179 */ }, { "A3ASDFAFADF", "B3SDFSDFSDF", "C3ADFDFADF", "D3ADFADFAD" },
				{ "A4", "B4", "C4", "D4" } };
		/*      */
		/* 1181 */ excel.writeSheet("Sheet1", records);
		/*      */
		/*      */
		/*      */
		/*      */
		/*      */
		/*      */
		/*      */
		/*      */
		/*      */
		/* 1191 */ excel.setCustomRowStyleForHeader(0, 2, IndexedColors.WHITE.index, 65.0F);
		/* 1192 */ excel.customMergeCells(0, 0, 0, 0, 3);
		/* 1193 */ excel.customizeRowStyle(0, 0, 0, IndexedColors.LIGHT_TURQUOISE.index);
		/*      */
		/* 1195 */ String[] languages = { "India", "China", "USA" };
		/* 1196 */ String[] testsdfsdf = { "345345", "5646", "65456456", "35345" };
		/* 1197 */ excel.createDropDownListtoWorkBook(0, 2, 1, languages);
		/* 1198 */ excel.createLargeDropDownListtoWorkBook(0, 2, 3, 3, testsdfsdf, 1);
		/*      */
		/* 1200 */ excel.hideRow(0, 1);
		/* 1201 */ excel.writeWorkbook();
		/*      */
		/*      */
		/* 1204 */ String[] secondLine = excel.readLineToArray(0, 1);
		/* 1205 */ System.out.println("");
		/* 1206 */ for (int i = 0; i < secondLine.length; i++) {
			/* 1207 */ System.out.print(String.valueOf(secondLine[i]) + ",");
			/*      */ }
		/* 1209 */ System.out.println("");
		/*      */ }
	/*      */ }

/*
 * Location:
 * C:\SAI\Workspace\rico_pts\rico_pts_current\reportGenerator\Reports.jar!\
 * connectionTest\GenericExcel.class Java compiler version: 8 (52.0) JD-Core
 * Version: 1.1.3
 */