package com.thoughtworks;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {

    public static List<String> getColumnNames(ResultSet rs) throws SQLException {
        List<String> columnNames = new ArrayList<>();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            String columnName = metaData.getColumnName(columnIndex);
            columnNames.add(columnName);
        }

        return columnNames;
    }
}
