package com.knu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Table {
    private String name;
    private List<Row> rows = new ArrayList<>();
    private List<Column> columns = new ArrayList<>();

    public Table(String name) {
        this.name = name;
    }
}
