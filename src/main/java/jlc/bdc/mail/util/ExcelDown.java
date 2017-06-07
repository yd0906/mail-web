package jlc.bdc.mail.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDown implements IExcelDown{
	
	@Override
	public XSSFSheet createSheet(XSSFWorkbook workbook, String sheetName) {
		XSSFSheet sheet = null;
		String replace = sheetName.replace("/", "");
		sheet = workbook.createSheet(replace);
		return sheet;
	}
	
	@Override
	public XSSFWorkbook createWorkbook() {
		return new XSSFWorkbook();
	}
	
	@Override
	public void setExcelHead(XSSFSheet sheet, String[] sheetHead) {
		XSSFRow row = sheet.createRow(0);
		for (int i = 0; i < sheetHead.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(sheetHead[i]);
		}
	}
	
	// 文件名中有空格的时候,
	public static String encodingFileName(String fileName) throws UnsupportedEncodingException {
		fileName = new String(fileName.getBytes("GB2312"),"ISO8859-1");
		fileName = StringUtils.replace(fileName, " ", "");
		return fileName;
	}
	
	@Override
	public void writeToExcelFile(Workbook workbook, String dir, String filename) throws IOException {
		System.out.println("========把数据导出到Excel文件==开始=====");
		// String dir+filename
		FileOutputStream fileOut = new FileOutputStream(String.format("%s%s", dir, filename));  
		workbook.write(fileOut);  
		fileOut.close();
		System.out.println("========Excel文件导出完成=====结束=========");
	}
}
