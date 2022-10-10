package com.knu.column;

import org.junit.Assert;
import org.junit.Test;

public class IntColumnTest {

    private final IntColumn intColumn = new IntColumn("integer");

    @Test
    public void validateInt(){
        Assert.assertTrue(intColumn.validate("12"));
    }

    @Test
    public void validateWrongInt(){
        Assert.assertFalse(intColumn.validate("wrong"));
    }
}
