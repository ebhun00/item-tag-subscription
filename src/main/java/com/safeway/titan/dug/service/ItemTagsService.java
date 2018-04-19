package com.safeway.titan.dug.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;
import com.safeway.titan.dug.domain.ItemDestMap;
import com.safeway.titan.dug.domain.ItemTags;
import com.safeway.titan.dug.mapping.CSVContentWriter;
import com.safeway.titan.dug.mapping.ExcelContentWriter;
import com.safeway.titan.dug.mapping.ItemTagsMapping;
import com.safeway.titan.dug.repository.SkuRepositoryImpl;
import com.safeway.titan.dug.util.FileReaderUtil;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;

@Service
@Slf4j
@ConfigurationProperties("tag_sub")
public class ItemTagsService {

	@Autowired
	private ItemTagsMapping itemTagsMapping;

	@Autowired
	private CSVContentWriter contentWriter;

	@Autowired
	private SkuRepositoryImpl skuRepositoryImpl;

	@Setter
	private String filepath;
	
	@Setter
	private String fileArchivePath;
	
	@Setter
	private String warningMsg;
	@Setter
	private String sucessfulMsg;
	@Setter
	private String errorMsg;

	@Autowired
	private ExcelContentWriter excelContentWriter;

	public String readTags() throws Exception {

		File[] inputFiles = FileReaderUtil.finder(filepath);
		String resultMessage = inputFiles.length > 0 ? sucessfulMsg : warningMsg;
		for (File file : inputFiles) {
			String inputFileName = file.getName();
			String storeNumber = inputFileName.substring(0, 4);
			PoijiOptions options = PoijiOptionsBuilder.settings(1).build();
			List<ItemTags> tags = Poiji.fromExcel(file, ItemTags.class, options);
			getBarcodeFromSkus(tags);
			List<ItemDestMap> itemsWithMapping = itemTagsMapping.createTagWithMods(tags,storeNumber);
			try {
				contentWriter.writeToCSV(itemsWithMapping, inputFileName);
				// excelContentWriter.genereateValidationCommentsFile(tags);
			} catch (IOException e) {
				resultMessage = errorMsg;
				log.error("Item tag subscription error for file , {}", inputFileName);
			}
			FileUtils.moveFile(file, FileUtils.getFile(fileArchivePath+inputFileName));
			log.error("Item tag subscription successfully completed for file , {}", inputFileName);
		}
		return resultMessage;
	}

	private void getBarcodeFromSkus(List<ItemTags> tags) {
		List<String> skus = new ArrayList<String>();
		tags.forEach(tag -> skus.add(String.valueOf(tag.getSku())));
		try {
			Map<String, String> barcodesMap = skuRepositoryImpl.getbarcode(skus);
			tags.forEach(tag -> tag.setBrcd(barcodesMap.get(tag.getSku())));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
