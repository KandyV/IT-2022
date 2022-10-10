package com.knu.column;


import org.junit.Assert;
import org.junit.Test;

public class CharColumnTest {

    private final CharColumn charColumn = new CharColumn("character");

    @Test
    public void validateChar(){
        Assert.assertTrue(charColumn.validate("s"));
    }

    @Test
    public void validateEmptyChar() {
        Assert.assertFalse(charColumn.validate(""));
    }

    @Test
    public void validateCharWithLengthGreaterThan1() {
        Assert.assertFalse(charColumn.validate("ab"));
    }

}