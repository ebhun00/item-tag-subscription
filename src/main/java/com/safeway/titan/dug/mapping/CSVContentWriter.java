package com.safeway.titan.dug.mapping;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.opencsv.CSVWriter;
import com.safeway.titan.dug.domain.ItemDestMap;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CSVContentWriter {

	public void writeToCSV(List<ItemDestMap> itemsWithMapping) throws IOException {

		CSVWriter csvWriter = new CSVWriter(new FileWriter("Tag_sub_1211_ver_1.csv"));
		
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

}
