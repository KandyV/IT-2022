package com.knu;

import lombok.*;

@Data
abstract public class Column {
    protected String name;

    private String type;

    public Column(String name) {
        this.name = name;
    }

    protected abstract boolean validate(String value);
    protected abstract String getType();

}


