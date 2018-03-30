package com.safeway.titan.dug.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.safeway.titan.dug.domain.SkuBrcd;

//https://stackoverflow.com/questions/4504592/how-to-use-select-in-clause-in-jdbctemplates
// https://www.logicbig.com/tutorials/spring-framework/spring-data-access-with-jdbc/sql-in-clause.html
@Repository
public class SkuRepositoryImpl implements SkuRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Map<String, String> getbarcode(List<String> skus) throws SQLException {
		Map<String, String> barcodesMap = new HashMap<String, String>();

		String query = "SELECT BRCD.SUPPLIER_ITEM_BARCODE, BRCD.ITEM_BARCODE, IFMW.ITEM_ID FROM"
				+ " ITEM_FACILITY_MAPPING_WMS IFMW INNER JOIN"
				+ "  (SELECT SUPPLIER_ITEM_BARCODE, ITEM_BARCODE, ITEM_ID FROM OSFLCA.ITEM_SUPPLIER_XREF_CBO"
				+ " WHERE SUPPLIER_ITEM_BARCODE IN (:skus) )   BRCD ON BRCD.ITEM_ID = IFMW.ITEM_ID AND "
				+ " FACILITY_ID IN (SELECT FACILITY_ID FROM FACILITY_ALIAS WHERE FACILITY_NAME= :facility_name) ";

		int current = 0;
		int iterInc = 1000;

		while (current < skus.size()) {
			Map<String, List<String>> barcodes = Collections.singletonMap("skus",
					skus.subList(current, (current + iterInc > skus.size()) ? skus.size() : current + iterInc));
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("skus", barcodes.get("skus"));
			parameters.addValue("facility_name", "1211");

			List<SkuBrcd> skuBrcds = (List<SkuBrcd>) namedParameterJdbcTemplate.query(query, parameters,
					new RowMapper<SkuBrcd>() {
						@Override
						public SkuBrcd mapRow(ResultSet resultSet, int i) throws SQLException {
							return mapRows(resultSet);
						}
					});

			skuBrcds.forEach(skuBrcd -> barcodesMap.put(skuBrcd.getSupplierItemBrcd(), skuBrcd.getItemBrcd()));
			current += iterInc;
		}

		return barcodesMap;
	}

	public SkuBrcd mapRows(ResultSet rs) throws SQLException {

		SkuBrcd skuBrcd = new SkuBrcd(rs.getString("SUPPLIER_ITEM_BARCODE"), rs.getString("ITEM_BARCODE"),
				rs.getString("ITEM_ID"));

		return skuBrcd;
	}

}
