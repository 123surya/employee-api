package com.thoughtworks;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;

public class ResultSetUtils {

    public static String toCSVString(ResultSet rs, List<String> columnNames) throws SQLException {
        StringJoiner csvJoiner = new StringJoiner(",");

        for (String columnName : columnNames) {
            String value = rs.getString(columnName);
            csvJoiner.add(formatCSVField(value));
        }

        return csvJoiner.toString();
    }

    private static String formatCSVField(String value) {
        return "\"" + (value != null ? value.replace("\"", "\"\"") : "") + "\"";
    }
}
