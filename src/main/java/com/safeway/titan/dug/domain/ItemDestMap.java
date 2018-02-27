package com.safeway.titan.dug.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ItemDestMap {

	@Getter
	@Setter
	private String skuBrcd;

	@Getter
	@Setter
	private String storeName;

	@Getter
	@Setter
	private String locnPickSeq;

	@Getter
	@Setter
	private String dspLocn;

	public String[] toStringArray() {
		return new String[] { storeName, dspLocn, locnPickSeq, skuBrcd };
	}
}
