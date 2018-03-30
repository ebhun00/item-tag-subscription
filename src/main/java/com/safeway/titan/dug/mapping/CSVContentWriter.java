package com.safeway.titan.dug.mapping;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.opencsv.CSVWriter;
import com.safeway.titan.dug.domain.InvalidItemDestMap;
import com.safeway.titan.dug.domain.ItemDestMap;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@ConfigurationProperties("tag_sub")
public class CSVContentWriter {
	
	@Setter
	private String outputFilePath;

	public final static char CR  = (char) 0x0D;
	public final static char LF  = (char) 0x0A;
		
	public void writeToCSV(List<ItemDestMap> itemsWithMapping, String inputFileName) throws IOException {

		CSVWriter csvWriter = new CSVWriter(new FileWriter(outputFilePath + inputFileName + "-output.csv"),',',CSVWriter.NO_QUOTE_CHARACTER,CSVWriter.NO_QUOTE_CHARACTER,System.getProperty("line.separator"));
		log.info("Creating file");
		List<String[]> csvContent = ConvertToCSVSupportFormat(itemsWithMapping);
		String[] header = { "WHSE", "DSP_LOCN", "LOCN_PICK_SEQ", "SKU_BRCD" };
		csvWriter.writeNext(header,false);
		csvWriter.writeAll(csvContent, false);
		csvWriter.close();
	}

	private List<String[]> ConvertToCSVSupportFormat(List<ItemDestMap> itemsWithMapping) {

		List<String[]> csvContent = new ArrayList<String[]>();
		for (ItemDestMap itemDestMap : itemsWithMapping) {
			csvContent.add(itemDestMap.toStringArray());

		}
		return csvContent;
	}
	
	public void writeInvalidItemsToCSV(List<InvalidItemDestMap> itemsWithMapping) throws IOException {

		CSVWriter csvWriter = new CSVWriter(new FileWriter("invalid_Tag_sub_1211_ver_2.csv"));
		log.info("Creating file");
		List<String[]> csvContent = ConvertInvalidItemsToCSVSupportFormat(itemsWithMapping);
		String[] header = { "WHSE", "SUPPLIER_ITEM_BARCODE", "SKU_BRCD", "DSP_LOCN", "LOCN_PICK_SEQ", "Comments" };
		csvWriter.writeNext(header,false);
		csvWriter.writeAll(csvContent, false);
		csvWriter.close();
	}

	private List<String[]> ConvertInvalidItemsToCSVSupportFormat(List<InvalidItemDestMap> itemsWithMapping) {

		List<String[]> csvContent = new ArrayList<String[]>();
		for (ItemDestMap itemDestMap : itemsWithMapping) {
			csvContent.add(itemDestMap.toStringArray());

		}
		return csvContent;
	}

}
