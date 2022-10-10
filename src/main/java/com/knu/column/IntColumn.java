package com.knu.column;

import com.knu.Column;
import lombok.Builder;
import lombok.Data;

public class IntColumn extends Column {

    private static String TYPE = "INT";

    public IntColumn(String name) {
        super(name);
    }

    @Override
    public boolean validate(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    @Override
    protected String getType() {
        return TYPE;
    }

}
