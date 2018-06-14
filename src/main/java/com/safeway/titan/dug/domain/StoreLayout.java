package com.safeway.titan.dug.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreLayout {
	
	private String businessUnit;
	private String facilityAliasId;
	private String categoryType;
	
	private List<StoreSection> storeSections;

}
