package com.knu;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Table {
    public String  Name;
    public List<Row> Rows = new ArrayList<>();
    public List<Column> Columns  = new ArrayList<>();

    public Table(String name) {
        Name = name;
    }
}
