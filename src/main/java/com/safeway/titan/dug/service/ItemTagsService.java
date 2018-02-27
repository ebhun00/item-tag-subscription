package com.safeway.titan.dug.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;
import com.safeway.titan.dug.domain.ItemDestMap;
import com.safeway.titan.dug.domain.ItemTags;
import com.safeway.titan.dug.mapping.CSVContentWriter;
import com.safeway.titan.dug.mapping.ItemTagsMapping;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ItemTagsService {
	
	
	@Autowired
	private ItemTagsMapping itemTagsMapping;
	
	@Autowired
	private CSVContentWriter contentWriter;
	
	public String readTags() {
		PoijiOptions options = PoijiOptionsBuilder.settings(2).build();
		List<ItemTags> tags = Poiji.fromExcel(new File("tag_sub_report_i1211.xlsx"), ItemTags.class, options);
		ItemTags tag = tags.get(1);
		
		List<ItemDestMap> itemsWithMapping = itemTagsMapping.createTagWithMods(tags);
		try {
			contentWriter.writeToCSV(itemsWithMapping);
		} catch (IOException e) {
			log.error("issue while making CSV");
		}
		return tag.toString();
	}

}
