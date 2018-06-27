package com.safeway.titan.dug.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safeway.titan.dug.domain.ItemDestMap;
import com.safeway.titan.dug.repository.CategoryRepositoryImpl;

@Service
public class EOMItemLocationService {

	@Autowired
	private StoreLayoutService storeLayoutService;

	@Autowired
	private CategoryXmlPreparator categoryXmlPreparator;

	@Autowired
	private StoreLayoutXmlPreparator storeLayoutXmlPreparator;

	@Autowired
	private CategoryDataService categoryDataService;
	
	@Autowired
	private CategoryRepositoryImpl categoryRepositoryImpl;

	public void prepareAndSendLocationXmls(List<ItemDestMap> itemsWithMapping, String storeNumber,
			List<String> itemNames) {
		List<String> csvItems = new ArrayList<String>();
		String storeLayoutXml = storeLayoutXmlPreparator.generateStoreLayout(itemsWithMapping, storeNumber, csvItems);
		Set<String> newCategoryItems = new HashSet<String>(csvItems);
		newCategoryItems.removeAll(itemNames);
//		newCategoryItems.add("960115754");
		if (newCategoryItems.size() > 0) {
			String categoryXml = categoryXmlPreparator.generateCategoryXml(newCategoryItems, storeNumber);
			categoryDataService.sendXmlToEOM(categoryXml);
		}
		storeLayoutService.sendXmlToEOM(storeLayoutXml, storeNumber);
		
		categoryRepositoryImpl.saveTempStoreCategories(storeNumber);
	}

}
