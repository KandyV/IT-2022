package com.knu.column;

import org.junit.Assert;
import org.junit.Test;

public class TextFileColumnTest {

    private final TextFileColumn textFileColumn = new TextFileColumn("text file");

    @Test
    public void validateTextFile(){
        Assert.assertTrue(textFileColumn.validate("C:\\Users\\1\\Desktop\\knu\\4course\\IT\\lab1\\test.txt"));
    }
    @Test
    public void validateWrongTextFile(){
        Assert.assertFalse(textFileColumn.validate("C:\\Users\\1\\Desktop\\knu\\4course\\IT\\lab1\\test.tx"));
    }
}
