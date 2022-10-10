package com.knu.column;

import com.knu.Column;
import lombok.Builder;

public class CharColumn extends Column {
    private static String TYPE = "CHAR";

    public CharColumn(String name) {
        super(name);
    }

    @Override
    public boolean validate(String value) {
        return !value.isBlank() && value.length() == 1;
    }

    @Override
    protected String getType() {
        return TYPE;
    }
}
