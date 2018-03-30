package com.safeway.titan.dug.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvalidItemDestMap extends ItemDestMap {
	
	private String comments;
	
	private String supplierItemBarcode;

	public String[] toStringArray() {
		return new String[] { storeName, supplierItemBarcode, skuBrcd, dspLocn, locnPickSeq ,comments};
	}
}
