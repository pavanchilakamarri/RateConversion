package com.main.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelCustomUtils {

	public ExcelCustomUtils() {
	}

	public static ArrayList<ArrayList<String>> getExcelRows(String excelPath, Object sheet) {
		ArrayList<ArrayList<String>> rowData = new ArrayList<ArrayList<String>>();

		FileInputStream inputStream = null;
		Workbook workbook = null;
		try {

			inputStream = new FileInputStream(new File(excelPath));
			workbook = new XSSFWorkbook(inputStream);
			Sheet sheetN = null;

			if (sheet instanceof String) {
				sheetN = workbook.getSheet((String) sheet);

			} else if (sheet instanceof Integer) {
				sheetN = workbook.getSheetAt((Integer) sheet);
			}

			Iterator<Row> iterator = sheetN.iterator();
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				ArrayList<String> colData = new ArrayList<String>();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if (cell == null) {
						colData.add("");
					} else if (cell.getCellType() == CellType.BLANK) {
						colData.add("");
					} else if (cell.getCellType() == CellType.STRING) {
						colData.add(cell.toString());
					} else if (cell.getCellType() == CellType.NUMERIC) {
						DataFormatter formatter = new DataFormatter();
						String formattedValue = formatter.formatCellValue(cell);
						colData.add(formattedValue);
					}
				}
				rowData.add(colData);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				workbook.close();
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return rowData;
	}

}
