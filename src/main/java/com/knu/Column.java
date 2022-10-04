package com.knu;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
abstract public class Column {
    public String name;

    @Getter(value = AccessLevel.PRIVATE)
    public String type = "";

    public Column(String name) {
        this.name = name;
    }

    public abstract boolean Validate(String value);
}
