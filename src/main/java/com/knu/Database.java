package com.knu;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Database {

    public String name;
    public List<Table> tables = new ArrayList<>();

}
