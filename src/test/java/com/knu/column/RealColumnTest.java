package com.knu.column;

import org.junit.Assert;
import org.junit.Test;

public class RealColumnTest {

    private final RealColumn realColumn = new RealColumn("float");

    @Test
    public void validateReal(){
        Assert.assertTrue(realColumn.validate("3.2"));
    }

    @Test
    public void validateWrongReal(){
        Assert.assertFalse(realColumn.validate("wrong"));
    }
}
