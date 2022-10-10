package com.knu.column;

import com.knu.Column;
import lombok.Builder;

public class StringColumn extends Column {

    private static String TYPE = "STRING";

    public StringColumn(String name) {
        super(name);
    }

    @Override
    protected String getType() {
        return TYPE;
    }

    @Override
    public boolean validate(String value) {
        return true;
    }
}


