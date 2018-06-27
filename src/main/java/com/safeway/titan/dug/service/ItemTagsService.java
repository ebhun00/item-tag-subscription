package com.safeway.titan.dug.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.safeway.titan.dug.domain.ItemDestMap;
import com.safeway.titan.dug.domain.TagItem;
import com.safeway.titan.dug.mapping.CSVContentWriter;
import com.safeway.titan.dug.mapping.ItemTagsMapping;
import com.safeway.titan.dug.repository.CategoryRepositoryImpl;
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
	private String categoriesPath;

	@Setter
	private String warningMsg;
	@Setter
	private String sucessfulMsg;
	@Setter
	private String errorMsg;

	@Autowired
	private CategoryRepositoryImpl categoryRepositoryImpl;

	@Autowired
	private EOMItemLocationService eomItemLocationService;

	public String readTags() throws Exception {

		File[] inputFiles = FileReaderUtil.finder(filepath);
		String resultMessage = null;
		List<String> itemNames = null;
		if (inputFiles.length > 0) {
			resultMessage = sucessfulMsg;
			log.info("loading categories");
			itemNames = FileUtils.readLines(new File(categoriesPath + "categoires.txt"));
		} else {
			resultMessage = warningMsg;
		}

		for (File file : inputFiles) {
			String inputFileName = file.getName();
			List<TagItem> itemList = mapCsvToObject(file);

			String storeNumber = inputFileName.substring(inputFileName.length() - 8, inputFileName.length() - 4);
			getBarcodeFromSkus(itemList, storeNumber);
			List<ItemDestMap> itemsWithMapping = itemTagsMapping.createTagWithMods(itemList, storeNumber);
			try {
				// excelContentWriter.genereateValidationCommentsFile(tags);
				contentWriter.writeToCSV(itemsWithMapping, storeNumber);
				eomItemLocationService.prepareAndSendLocationXmls(itemsWithMapping, storeNumber, itemNames);
				// excelContentWriter.genereateValidationCommentsFile(itemList);
			} catch (IOException e) {
				resultMessage = errorMsg;
				log.error("Item tag subscription error for file , {}", inputFileName);
			}
			FileUtils.moveFile(file, FileUtils.getFile(fileArchivePath + inputFileName));
			log.info("Item tag subscription successfully completed for file , {}", inputFileName);
		}
		log.debug(resultMessage);
		return resultMessage;
	}

	private void getBarcodeFromSkus(List<TagItem> tags, String storeNumber) {
		List<String> skus = new ArrayList<String>();
		tags.forEach(tag -> {
			skus.add(tag.getUpc());
		});
		try {
			Map<String, String> barcodesMap = skuRepositoryImpl.getbarcode(skus, storeNumber);
			tags.forEach(tag -> tag.setBrcd(barcodesMap.get(tag.getUpc())));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<TagItem> mapCsvToObject(File file) {
		List<TagItem> itemList = new ArrayList<TagItem>();
		List<String> upcList = new ArrayList<String>();
		try {
			BufferedReader fileReader = null;
			fileReader = new BufferedReader(new FileReader(file.getAbsolutePath()));
			String record = fileReader.readLine();
			while ((record = fileReader.readLine()) != null) {
				String[] fields = record.split(",");
				if (!upcList.contains(fields[4])) {
					TagItem tagItem = new TagItem();
					tagItem.setAisle(fields[0]);
					tagItem.setSection(fields[1]);
					tagItem.setSide(fields[2]);
					tagItem.setSku(fields[3]);
					tagItem.setUpc(fields[4]);
					upcList.add(fields[4]);
					tagItem.setDescription(fields[5]);
					tagItem.setTag_id(fields[6]);
					tagItem.setRetailSection(fields[7]);
					tagItem.setLocation(fields[8]);
					itemList.add(tagItem);
				}
			}
			fileReader.close();
		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		return itemList;
	}

	@Scheduled(fixedDelay = 86400000)
	public void getToken() {
		log.info("pulling the  categories");
		List<String> itemNames = new ArrayList<String>();
		itemNames = categoryRepositoryImpl.getCurrentCategories();
		
		String[] items = new String[itemNames.size()];
		itemNames.toArray(items);
		log.info("all items {}", (Object) items);
		try {
			FileUtils.writeLines(new File(categoriesPath + "categoires.txt"), null, itemNames);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
