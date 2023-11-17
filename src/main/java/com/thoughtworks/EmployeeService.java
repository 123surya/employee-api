package com.thoughtworks;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	private final JdbcTemplate jdbcTemplate;

	public EmployeeService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void streamAllEmployees(Consumer<String> consumer, String tablename) {
		String sql = "select * from " + tablename; // Replace with your SQL query
		final List<String> columnNames = new ArrayList<>();

		jdbcTemplate.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				if (columnNames.isEmpty()) {
					columnNames.addAll(DatabaseUtils.getColumnNames(rs));
				}
				String csvString = ResultSetUtils.toCSVString(rs, columnNames);
				System.out.println(csvString);
				consumer.accept(csvString);
			}
		});
	}
}
