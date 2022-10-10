package com.knu;

import com.knu.column.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseManager {
    private static String DELIMITER = "$";
    private static String SPACE = "\t";
    private static String SPECIAL_CHARS = "\\/:*?\"<>|";
    private Database database;
    private static DatabaseManager INSTANCE;

    public static DatabaseManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseManager();
        }

        return INSTANCE;
    }

    private DatabaseManager() {
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database value) {
        database = value;
    }

    public boolean CreateDatabase(String name) {
        boolean containsSpecialCharacters = SPECIAL_CHARS.chars()
                .mapToObj(character -> String.valueOf((char) character))
                .anyMatch(name::contains);

        if (containsSpecialCharacters) {
            return false;
        }

        database = new Database(name);
        return true;
    }

    public boolean SaveDatabase(String filePath) {
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(filePath));
            printWriter.print(database.getName());
            printWriter.close();
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }


    public boolean openDatabase(String filePath) {
        try {
            String content = Files.readString(Path.of(filePath));

            String[] lines = content.split("\\" + DELIMITER);

            if (lines.length == 0) {
                return false;
            }

            database = new Database(lines[0]);

            readTablesFromFile(lines);

            return true;
        } catch (IOException exception) {
            return false;
        }
    }

    public boolean deleteDatabase(String filePath) {
        try {
            if (!filePath.isBlank()) {
                Files.delete(Path.of(filePath));
                database = null;
            }
            return true;
        } catch (IOException exception) {
            return false;
        }
    }

    public boolean AddTable(String name) {
        if (getTableNames().contains(name)) {
            return false;
        }

        database.getTables().add(new Table(name));
        return true;
    }

    public Table getTable(int index) {
        return database.getTables().get(index);
    }

    public List<String> getTableNames() {
        return database.getTables().stream()
                .map(Table::getName)
                .collect(Collectors.toList());
    }

    public List<String> getColumnNames(int tableIndex) {
        return database.getTables().get(tableIndex).getColumns().stream()
                .map(Column::getName)
                .collect(Collectors.toList());
    }

    ;

    public void deleteTable(int index) {
        database.getTables().remove(index);
    }

    public boolean addColumn(int tableIndex, Column column) {
        if (getColumnNames(tableIndex).contains(column.getName())) {
            return false;
        }

        Table table = database.getTables().get(tableIndex);

        table.getColumns().add(column);

        for (Row row : table.getRows()) {
            row.getValues().add("");
        }

        return true;
    }

    public void deleteColumn(int tableIndex, int columnIndex) {
        Table table = database.getTables().get(tableIndex);

        table.getColumns().remove(columnIndex);

        for (Row row : table.getRows()) {
            row.getValues().remove(columnIndex);
        }

        if (table.getColumns().size() == 0) {
            table.setRows(new ArrayList<>());
        }
    }

    public boolean AddRow(int tableIndex) {
        if (columnsIsNull(tableIndex)) {
            return false;
        }

        Table table = database.getTables().get(tableIndex);
        return table.getRows().add(new Row());
    }

    public void DeleteRow(int tableIndex, int rowIndex) {
        database.getTables().get(tableIndex).getRows().remove(rowIndex);
    }

    public boolean changeCellValue(String newValue, int tableIndex, int columnIndex, int rowIndex) {
        if (database.getTables().get(tableIndex).getColumns().get(columnIndex).validate(newValue)) {
            database.getTables().get(tableIndex).getRows().get(rowIndex).getValues().set(columnIndex, newValue);
            return true;
        }

        return false;
    }

    private boolean columnsIsNull(int tableIndex) {
        return database == null || database.getTables().size() == 0
                || database.getTables().get(tableIndex).getColumns().size() == 0;
    }

    public List<Row> selectRows(int tableIndex, String searchByWord) {
        return database.getTables().get(tableIndex).getRows().stream()
                .filter(
                        row -> row.getValues().stream()
                                .anyMatch(value -> value.equals(searchByWord))
                )
                .collect(Collectors.toList());
    }

    public List<Row> selectRowsByColumn(int tableIndex, int columnIndex, String searchByWord) {
        return database.getTables().get(tableIndex).getRows().stream()
                .filter(
                        row -> row.getValues().get(columnIndex).equals(searchByWord)
                )
                .collect(Collectors.toList());
    }

    private void readTablesFromFile(String[] parts) {
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].isEmpty()){
                continue;
            }

            parts[i] = parts[i].replaceAll("\r\n", "\r");
            List<String> buf = Arrays.stream(parts[i].split("\r")).collect(Collectors.toList());

            if(buf.size() == 0){
                return;
            }

            buf.remove(0);

            Table table = new Table(buf.get(0));

            if (buf.size() > 2) {
                readColumnsFromFile(buf, table);
                readRowsFromFile(buf, table);
                database.getTables().add(table);
            }
        }
    }

    private void readColumnsFromFile(List<String> buf, Table table) {
        String[] columnNames = buf.get(1).split(SPACE);
        String[] columnTypes = buf.get(2).split(SPACE);

        for (int j = 0; j < columnNames.length - 1; j++) {
           table.getColumns().add(
                   columnFromString(columnNames[j], columnTypes[j])
           );
        }
    }

    private void readRowsFromFile(List<String> buf, Table table) {
        for (int j = 3; j < buf.size(); j++) {
            table.getRows().add(new Row(Arrays.stream(buf.get(j).split(SPACE)).collect(Collectors.toList())));
        }
    }

    public void writeTablesToFile(PrintWriter writer) throws IOException {
        for (Table table : database.getTables()) {
            writer.println(DELIMITER);
            writer.println(table.getName());

            writeColumnsToFile(writer, table);
            writeRowsToFile(writer, table);
        }
    }

    private static void writeColumnsToFile(PrintWriter writer, Table table) {
        for (Column column : table.getColumns()) {
            writer.print(column.getName() + SPACE);
        }

        writer.println();

        for (Column column : table.getColumns()) {
            writer.print(column.getType() + SPACE);
        }

        writer.println();
    }

    //TODO check
    private static void writeRowsToFile(PrintWriter writer, Table table) {
        for (Row row : table.getRows()) {
            for (String value : row.getValues()) {
                writer.write(value + SPACE);
            }
            writer.println();
        }
    }

    public static Column columnFromString(String name, String type) {
        return switch (type) {
            case "INT" -> new IntColumn(name);
            case "REAL" -> new RealColumn(name);
            case "CHAR" -> new CharColumn(name);
            case "STRING" -> new StringColumn(name);
            case "TEXT FILE" -> new TextFileColumn(name);
            case "INT INTERVAL" -> new IntIntervalColumn(name);
            default -> null;
        };
    }


}
