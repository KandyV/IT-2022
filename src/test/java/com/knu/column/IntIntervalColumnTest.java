package com.knu.column;

import org.junit.Assert;
import org.junit.Test;

public class IntIntervalColumnTest {

    private final IntIntervalColumn intIntervalColumn = new IntIntervalColumn("int interval");

    @Test
    public void validateMoreNumbers(){
        Assert.assertFalse(intIntervalColumn.validate("1,2,3"));
    }
    @Test
    public void validateFirstNumberIsBigger(){
        Assert.assertFalse(intIntervalColumn.validate("3,1"));
    }
    @Test
    public void validateAnotherSymbol(){
        Assert.assertFalse(intIntervalColumn.validate("2?1"));
    }
    @Test
    public void validateAnotherType(){
        Assert.assertFalse(intIntervalColumn.validate("r"));
    }
    @Test
    public void validateIntInterval(){
        Assert.assertTrue(intIntervalColumn.validate("1 ,3"));
    }
}
