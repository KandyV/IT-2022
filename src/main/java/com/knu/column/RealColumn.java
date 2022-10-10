package com.knu.column;

import com.knu.Column;
import lombok.Builder;

public class RealColumn extends Column {
    private static String TYPE = "REAL";

    public RealColumn(String name) {
        super(name);
    }

    @Override
    protected String getType() {
        return TYPE;
    }

    @Override
    public boolean validate(String value) {
        try {
           Float.parseFloat(value);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

}


