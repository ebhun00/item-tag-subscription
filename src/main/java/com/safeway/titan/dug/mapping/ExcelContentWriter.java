package com.safeway.titan.dug.mapping;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.safeway.titan.dug.domain.ItemTags;

@Component
public class ExcelContentWriter {
	
	private static String[] columns =
			
		{"ITEM_ID",	"SKU",	"POS_DESCRIPTION",	"LOCATION",
				"AIS",	"SEC",	"SHE",	/*"FORM_QTY_1",	"OVERRIDE_QTY_1",
				"OVERRIDE_FLAG_1",*/ "BARCODE", "COMMENTS", "DSP_LOCATION" };
	
	public void genereateValidationCommentsFile(List<ItemTags> tags) throws IOException, InvalidFormatException {
		Workbook workbook = new XSSFWorkbook(); 

		Sheet sheet = workbook.createSheet("Item_map_validation");
        
        CreateHeader(workbook, sheet);
        
        AddRowsToSheet(tags, sheet);
        
        FileOutputStream fileOut = new FileOutputStream("test-validation.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }

	private void CreateHeader(Workbook workbook, Sheet sheet) {
		Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        Row headerRow = sheet.createRow(0);
        
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
	}

	private void AddRowsToSheet(List<ItemTags> tags, Sheet sheet) {
		int rowNum = 1;
        for(ItemTags item: tags) {
        	Row row = sheet.createRow(rowNum++);
        	int column = 0;
        	row.createCell(column++).setCellValue(item.getItemId());
        	row.createCell(column++).setCellValue(item.getSku());
        	row.createCell(column++).setCellValue(item.getPos_description());
        	row.createCell(column++).setCellValue(item.getLocation());
        	row.createCell(column++).setCellValue(item.getAisle());
        	row.createCell(column++).setCellValue(item.getSection());
        	row.createCell(column++).setCellValue(item.getShelf());
        	row.createCell(column++).setCellValue(item.getBrcd());
        	row.createCell(column++).setCellValue(item.getComments());
        	row.createCell(column++).setCellValue(item.getTagSubLoc());
        	
        }
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
	}

}
