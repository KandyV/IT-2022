package com.knu.column;

import com.knu.Column;
import lombok.Builder;

public class IntIntervalColumn extends Column {
    private static String TYPE = "INT INTERVAL";

    public IntIntervalColumn(String name) {
        super(name);
    }

    @Override
    public boolean validate(String value){
        String[] strings = value.replaceAll(" ", "").split(",");

        return strings.length == 2 &&
                canParse(strings[0]) &&
                canParse(strings[1]) &&
                Integer.parseInt(strings[0]) < Integer.parseInt(strings[1]);
    }

    @Override
    protected String getType() {
        return TYPE;
    }

    private boolean canParse(String value){
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }
}
