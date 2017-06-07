package jlc.bdc.mail.util;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class POIExcelDown {

	private IExcelDown excelDown;
	private XSSFWorkbook workbook;

	public POIExcelDown() {
		excelDown = (IExcelDown) new ExcelDown();
	}
	
	// 创建表格
	public Workbook createWorkBook(String sheetName) {
		workbook = excelDown.createWorkbook();
		excelDown.createSheet(workbook, sheetName);
		return workbook;
	}

	//创建表头
	public Workbook setHead(List<String> head) {
		XSSFSheet sheet = workbook.getSheetAt(0);
		XSSFRow row = sheet.createRow(0);
		for (int i = 0; i < head.size(); i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(head.get(i));
		}
		return workbook;
	}

	/**
	 * 获取表对象
	 * @return
	 */
	public Workbook getWorkbook(){
		return workbook;
	}
	
	// 填充数据
	public Workbook exportExcelWithList(int startRow, List<List<String>> list){
		XSSFSheet sheet = workbook.getSheetAt(0);
		//填充行
		for(int rowNum=0; rowNum<list.size(); rowNum++){
			List<String> col = list.get(rowNum);
			XSSFRow row = sheet.createRow(rowNum + startRow);
			for(int colNum=0; colNum<col.size(); colNum++){
				XSSFCell cell = row.createCell(colNum);
				//判断数字,如果是数字强转成double类型放入excel,这样导出的数字不会为文本 2016/1/8
				try{
					cell.setCellValue(Double.parseDouble(col.get(colNum)));
				}catch(Exception e){
					cell.setCellValue(col.get(colNum));
				}
			}
		}
		return workbook;
	}
	
	// 生成文件
	public void writeToExcelFile(String dir, String filename) throws IOException {
		excelDown.writeToExcelFile(getWorkbook(), dir, filename);
	}
}
