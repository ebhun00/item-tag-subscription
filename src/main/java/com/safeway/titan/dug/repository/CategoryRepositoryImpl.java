package com.safeway.titan.dug.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
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
	public List<String> getCurrentCategories()  {
		log.info("Querying for all categories list");
		String query = "select EXT_CATEGORY_CODE from CATEGORY";

		List<String> categories = jdbcTemplate.query(query, new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
		
		//categories.sort(Comparator.naturalOrder());
		return categories;
	}

}
