package com.safeway.titan.dug.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SkuRepository {

	public Map<String,String> getbarcode(List<String> skus, String storeNumber) throws SQLException;
}
