package com.safeway.titan.dug.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryRepositoryImpl implements CategoryRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<String> getCurrentCategories() {
		log.info("Querying for all categories list");
		String query = "select EXT_CATEGORY_CODE from CATEGORY";

		List<String> categories = jdbcTemplate.query(query, new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});

		// categories.sort(Comparator.naturalOrder());
		return categories;
	}

	public void saveTempStoreCategories(String storeNum) {
		String childSql = "Insert into C_STORE_SECTION_CATEGORY ( "
				+ "select *  from OSFLCA.STORE_SECTION_CATEGORY where FACILITY_ID in "
				+ "(select  FACILITY_ID from OSFLMDA.FACILITY_ALIAS where FACILITY_NAME in " + "(?)))";

		String parentSql = "Insert into C_STORE_SECTION ( "
				+ "select *  from OSFLCA.STORE_SECTION where FACILITY_ID in "
				+ "(select  FACILITY_ID from OSFLMDA.FACILITY_ALIAS where FACILITY_NAME in  " + "(?)))";

		log.info("inserting data into c-temp child table");
		if (jdbcTemplate.update(childSql, new Object[] { storeNum }) > 0) {
			log.info("inserting data into c-temp child table");
			jdbcTemplate.update(parentSql, new Object[] { storeNum });
		}

	}

}
