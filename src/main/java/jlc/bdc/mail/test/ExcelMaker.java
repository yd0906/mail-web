package jlc.bdc.mail.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jlc.bdc.mail.util.POIExcelDown;

public class ExcelMaker {
	
	public static void main(String[] args) throws IOException {
		String root = "src/main/webapp/res/temp/";
		// Generate Excel
		POIExcelDown pOIExcelDown = new POIExcelDown();
		pOIExcelDown.createWorkBook("测试");
		// Set the title -- the header merge
		List<String> headList = new ArrayList<String>();
		headList.add("测试");
		pOIExcelDown.setHead(headList);
		// Set data
		List<List<String>> data = new ArrayList<List<String>>();
		List<String> row = new ArrayList<String>();
		row.add("1");
		data.add(row);
		pOIExcelDown.exportExcelWithList(1, data);
		
		String fileName = "测试.xlsx";
		System.out.println("Export file name===>"+fileName.toString());
		// Export excel file
		pOIExcelDown.writeToExcelFile(root, fileName.toString());
	}

}
