package com.knu;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Row {
    private List<String> values;

    public Row() {
        this.values = new ArrayList<>();
    }

    public Row(List<String> values) {
        this.values = values;
    }
}