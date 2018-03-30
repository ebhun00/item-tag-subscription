package com.safeway.titan.dug.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class SkuBrcd {

	@Setter
	@Getter
	private String supplierItemBrcd;
	@Setter
	@Getter
	private String itemBrcd;
	
	@Setter
	@Getter
	private String item_id;
}
