/*package com.safeway.titan.dug.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.safeway.titan.dug.domain.SkuBrcd;

public class SkuBrcdMapper implements RowMapper<SkuBrcd> {

	@Override
	public SkuBrcd mapRow(ResultSet rs, int rowNum) throws SQLException {

		SkuBrcd skuBrcd = new SkuBrcd(rs.getString("SUPPLIER_ITEM_BARCODE"), rs.getString("ITEM_BARCODE"));

		return skuBrcd;
	}

}
*/