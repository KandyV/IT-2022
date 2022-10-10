package com.knu.column;

import com.knu.Column;
import lombok.Builder;

import java.nio.file.Files;
import java.nio.file.Path;

public class TextFileColumn extends Column {
    private static String TYPE = "TEXT FILE";

    public TextFileColumn(String name) {
        super(name);
    }

    @Override
    protected String getType() {
        return TYPE;
    }

    @Override
    public boolean validate(String value) {
        return value.endsWith(".txt") && Files.exists(Path.of(value));
    }
}
