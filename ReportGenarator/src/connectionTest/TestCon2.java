/*    */
package connectionTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestCon2 {
	public static void main(String year,String filename) throws IOException {
		InputStream is = null;
		FileOutputStream fileOut = null;
		Workbook wb = null;
		try {
			wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet("Employee Details");
			int i = 0;
			Row row = sheet.createRow((short) i);
			HashMap<String, String> header = populateUsersColHeadersMap();
			int col = 0;

			Set<Entry<String, String>> mapData = header.entrySet();
			Iterator<Entry<String, String>> s = mapData.iterator();
			while (s.hasNext()) {
				row.createCell(col).setCellValue(s.next().getValue());
				col++;
			}
			 setCustomRowStyleForHeader(0, 0, (short) 55, 30.0F, wb);

			fileOut = new FileOutputStream(filename);
			wb.write(fileOut);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileOut != null)
				fileOut.close();
		}
	}

	/*     */ private static HashMap<String, String> populateUsersColHeadersMap() {
		/* 310 */ LinkedHashMap<String, String> headers = new LinkedHashMap<>();
		/* 311 */ headers.put("TFS_ID", "TFS Epic Id");
		/* 312 */ headers.put("TYPE", "TYPE");
		/* 313 */ headers.put("RELEASE", "RELEASE");
		/* 314 */ headers.put("PROJECT", "PROJECT");
		/* 315 */ headers.put("MANAGER_NAME", "MANAGER NAME");
		/* 316 */ headers.put("STATUS", "STATUS");
		/* 317 */ headers.put("TOTAL_CAPACITY", "TOTAL CAPACITY");
		/* 318 */ headers.put("EFFORT", "EFFORT");
		/* 319 */ headers.put("MONTH", "MONTH");
		/* 320 */ headers.put("YEAR", "YEAR");
		/* 321 */ headers.put("IMPLEMENTATION_DATE", "IMPLEMENTATION DATE");
		/*     */
		/* 323 */ return headers;
		/*     */ }

	public static void setCustomRowStyleForHeader(int sheetNumber, int rowNumber, short foregroundColor, float height,
			Workbook wb) {
		CellStyle customStyle = wb.createCellStyle();
		customStyle.setWrapText(true);
		Font font = wb.createFont();
		customStyle.setVerticalAlignment((short) 0);
		Sheet sheet = wb.getSheetAt(sheetNumber);
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
}
