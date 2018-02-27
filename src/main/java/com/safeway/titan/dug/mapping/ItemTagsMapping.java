package com.safeway.titan.dug.mapping;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safeway.titan.dug.domain.ItemDestMap;
import com.safeway.titan.dug.domain.ItemTags;
import com.safeway.titan.dug.service.DspLocn;

@Component
public class ItemTagsMapping {

	@Autowired
	private DspLocn dspLocn;
	
	public List<ItemDestMap> createTagWithMods(List<ItemTags> tags) {

		List<ItemDestMap> itemsWithRemap = new ArrayList<ItemDestMap>();
		for (ItemTags itemTags : tags) {
			
			ItemDestMap destMap = new ItemDestMap();
			destMap.setSkuBrcd(itemTags.getSku());
			destMap.setStoreName("1211");
			destMap.setLocnPickSeq("");
			destMap.setDspLocn( dspLocn.getAisleName(itemTags.getAis())  + dspLocn.getSectionName(itemTags.getSec()) + itemTags.getShe());
			itemsWithRemap.add(destMap);
		}
		
		return itemsWithRemap;
	}
	
	

}
