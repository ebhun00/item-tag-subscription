package com.safeway.titan.dug.domain;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class TagItem {
	
	//AISLE,SIDE,SECTION,SKU,UPC,DESCRIPTION,TAG ID,RETAIL SECTION,LOCATION
	
	private String aisle;
	private String section;
	private String side;
	private String sku;
	private String upc;
	private String brcd;
	private String description;
	private String tag_id;
	private String retailSection;
	private String location;
	private String tagSubLoc;
	private String comments;

}
