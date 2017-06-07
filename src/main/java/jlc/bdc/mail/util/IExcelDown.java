package jlc.bdc.mail.util;

import java.io.IOException;
import java.io.FileNotFoundException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel表格的下载
 * @author TANGCY
 * @date 2017-05-06 ^_^
 */

public interface IExcelDown {
	
	/**
	 * 生成工作对象
	 */
	public XSSFWorkbook createWorkbook();

	/**
	 * 生成指定名称的Sheet 对象
	 * 
	 * @param workbook
	 * @param sheetName
	 * @return
	 */
	public XSSFSheet createSheet(XSSFWorkbook workbook, String sheetName);

	/**
	 * 填充表头
	 * @param sheetHead
	 */
	public void setExcelHead(XSSFSheet sheet, String[] sheetHead);
	
	
	/**
	 * 输出文件
	 * @param workbook
	 * @param dir
	 * @param filename
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void writeToExcelFile(Workbook workbook, String dir, String filename) throws FileNotFoundException, IOException;
	
}
