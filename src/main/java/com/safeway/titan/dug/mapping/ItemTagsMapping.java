package com.safeway.titan.dug.mapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safeway.titan.dug.domain.ItemDestMap;
import com.safeway.titan.dug.domain.ItemTags;
import com.safeway.titan.dug.domain.TagItem;
import com.safeway.titan.dug.service.DspLocn;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ItemTagsMapping {

	@Autowired
	private DspLocn dspLocn;

	public List<ItemDestMap> createTagWithMods(List<TagItem>  tags, String storeNumber) {
		log.info("Preparing tags for store : {}" , storeNumber);
		
		List<ItemDestMap> itemsWithRemap = new ArrayList<ItemDestMap>();
		for (TagItem itemTags : tags) {
			if (!dspLocn.getAisleName(itemTags.getAisle()).contains("Invalid")
					&& !dspLocn.getSectionName(itemTags.getSection(),dspLocn.getAisleName(itemTags.getAisle())).contains("Invalid") && itemTags.getBrcd() != null) {
				itemsWithRemap.add(prepareItemDestmap(itemTags,storeNumber));
			} else {
				itemTags.setComments(itemTags.getBrcd() == null ? "Invalid Sku" : "");
				itemTags.setComments(dspLocn.getCommentForInvalidItem( itemTags));
			}
			
		}
		filterDuplicates(itemsWithRemap, tags);
		ItemDestMap.sort(itemsWithRemap);
		setSequence(itemsWithRemap);
		return itemsWithRemap;
	}


	public void filterDuplicates(List<ItemDestMap> itemsWithRemap, List<TagItem> tags) {
		Map<String, List<ItemDestMap>> subLists =  itemsWithRemap.stream().collect(Collectors.groupingBy(ItemDestMap::getSkuBrcd));

		subLists.forEach( (k,v) -> {
			if(v.size() >1) {
				List<TagItem> orginalSublistTags =  tags.stream().filter(tag -> k.equals(tag.getBrcd())).collect(Collectors.toCollection(() -> new ArrayList<TagItem>()));
				orginalSublistTags.sort(Comparator.comparing(TagItem::getAisle).thenComparing(TagItem::getSection));
				//v.removeIf( (ItemDestMap itemDestmap ) -> itemDestmap.getDspLocn().equals(orginalSublistTags.get(0).getTagSubLoc()));
				
				v.forEach(item -> {
					itemsWithRemap.removeIf( (ItemDestMap itemDestmap ) -> itemDestmap.getDspLocn().equals(item.getDspLocn()));
				});
			}
		});
	}
	



	private ItemDestMap prepareItemDestmap(TagItem itemTags, String storeNumber) {
		ItemDestMap destMap = new ItemDestMap();
		destMap.setSkuBrcd(itemTags.getBrcd());
		destMap.setStoreName(storeNumber);
		destMap.setDspLocn(dspLocn.getAisleName(itemTags.getAisle()) + dspLocn.getSectionName(itemTags.getSection(),dspLocn.getAisleName(itemTags.getAisle()))
				+ String.format("%03d", Integer.valueOf(itemTags.getSide())));
		itemTags.setTagSubLoc(destMap.getDspLocn());
		return destMap;
	}

	private void setSequence(List<ItemDestMap> itemsWithRemap) {

		itemsWithRemap.forEach(new Consumer<ItemDestMap>() {

			String previousDispLocn = null;
			String previousLocnPickSeq = "01000";

			@Override
			public void accept(ItemDestMap t) {
				if (t.getDspLocn().equals(previousDispLocn)) {
					t.setLocnPickSeq(previousLocnPickSeq);
				} else {
					previousLocnPickSeq = generateNewPickLocnSeq();
					previousDispLocn = t.getDspLocn();
					t.setLocnPickSeq(previousLocnPickSeq);
				}
			}

			private String generateNewPickLocnSeq() {
				Integer inputAisle = Integer.valueOf(previousLocnPickSeq) + 1;
				return String.format("%05d", inputAisle);
			}
		});
	}

}
