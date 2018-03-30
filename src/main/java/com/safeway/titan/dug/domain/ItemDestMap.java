package com.safeway.titan.dug.domain;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ItemDestMap /* implements Comparable<ItemDestMap> */ {

	@Getter
	@Setter
	protected String skuBrcd;

	@Getter
	@Setter
	protected String storeName;

	@Getter
	@Setter
	protected String locnPickSeq;

	@Getter
	@Setter
	protected String dspLocn;

	public String[] toStringArray() {
		return new String[] { storeName, dspLocn, locnPickSeq, skuBrcd };
	}


	public static void sort(List<ItemDestMap> list) {
		Collections.sort(list, new Comparator<ItemDestMap>() {
			@Override
			public int compare(final ItemDestMap object1, final ItemDestMap object2) {
				return object1.getDspLocn().compareTo(object2.getDspLocn());
			}
		});
	}

	/*public String toString() {
		return storeName + " " + dspLocn + " " + locnPickSeq + " " + skuBrcd + "\n";
	}*/
}
