package com.knu;

import com.knu.column.CharColumn;
import com.knu.column.IntColumn;
import com.knu.column.StringColumn;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class DatabaseManagerTest {

    public static final Row ROW1 = new Row(List.of("abc1a", "1", "a"));
    public static final Row ROW2 = new Row(List.of("abc2b", "2", "b"));
    public static final Row ROW3 = new Row(List.of("abc3c", "3", "c"));
    private static DatabaseManager databaseManager;

    @BeforeClass
    public static void init() {
        databaseManager = DatabaseManager.getInstance();
        Database database = new Database("TestDatabase");

        Table table = Table.builder()
                .name("TestTable")
                .rows(List.of(
                        ROW1,
                        ROW2,
                        ROW3
                ))
                .columns(List.of(
                        new StringColumn("stringColumn"),
                        new IntColumn("intColumn"),
                        new CharColumn("charColumn")
                ))
                .build();

        database.setTables(Collections.singletonList(table));
        databaseManager.setDatabase(database);
    }

    @Test
    public void selectRows() {
        List<Row> actual = databaseManager.selectRows(0, "abc3c");
        Assert.assertEquals(1, actual.size());
        Assert.assertEquals(actual.get(0), ROW3);
    }

    @Test
    public void selectRowsByColumn() {
        List<Row> actual = databaseManager.selectRowsByColumn(0, 0, "abc3c");
        Assert.assertEquals(1, actual.size());
        Assert.assertEquals(actual.get(0), ROW3);
    }


}