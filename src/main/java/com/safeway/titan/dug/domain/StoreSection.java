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
public class StoreSection {

	private String sectionName1;
	private String sectionSequence;
	
	private List<Category> categories;
}
