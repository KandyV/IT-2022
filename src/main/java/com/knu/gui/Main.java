package com.knu.gui;

import com.knu.Database;
import com.knu.DatabaseManager;
import com.knu.Row;
import com.knu.Table;
import com.knu.column.CharColumn;
import com.knu.column.IntColumn;
import com.knu.column.StringColumn;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;


public class Main extends Application {

    private static String filePath = "C:\\Users\\1\\Desktop\\knu\\4course\\IT\\lab1\\test.txt";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX App");

        MenuBar menuBar = new MenuBar();
        Menu menu1 = new Menu("Файл");
        Menu menu2 = new Menu("Редагувати");
        Menu menu3 = new Menu("Знайти рядок");

        MenuItem menuItem1 = new MenuItem("Додати шлях");


        // create a tile pane
        TilePane r = new TilePane();

        Label l = new Label("no text input");
        // create a text input dialog
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText("Enter the path");

        TextInputDialog td1 = new TextInputDialog();


        Label label = new Label();
        TextInputDialog dialog = new TextInputDialog();

        menuItem1.setOnAction(e -> {
            dialog.setTitle("Set Label Text");
            dialog.showAndWait().ifPresent(string -> {
                label.setText(string);
                System.out.println(dialog.getDefaultValue());
            });
        });


        menu1.getItems().add(menuItem1);

        menuBar.getMenus().add(menu1);
        menuBar.getMenus().add(menu2);
        menuBar.getMenus().add(menu3);
        VBox vBox = new VBox(menuBar);

        System.out.println("output" + dialog.getDefaultValue());
        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();


        System.out.println(dialog.getDefaultValue());
    }

    @SneakyThrows
    private void initialize() {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));

        DatabaseManager instance = DatabaseManager.getInstance();

        Database database = new Database("TestDb");

        Table table = Table.builder()
                .name("TestTable")
                .rows(List.of(
                        new Row(List.of("abc1a", "1", "a")),
                        new Row(List.of("abc2b", "2", "b")),
                        new Row(List.of("abc3c", "3", "c"))
                ))
                .columns(List.of(
                        new StringColumn("stringColumn"),
                        new IntColumn("intColumn"),
                        new CharColumn("charColumn")
                ))
                .build();

        Table table2 = Table.builder()
                .name("TestTable")
                .rows(List.of(
                        new Row(List.of("2abc1a", "21", "2a")),
                        new Row(List.of("2abc2b", "22", "2b")),
                        new Row(List.of("2abc3c", "23", "2c"))
                ))
                .columns(List.of(
                        new StringColumn("stringColumn2"),
                        new IntColumn("intColumn2"),
                        new CharColumn("charColumn2")
                ))
                .build();

        database.setTables(List.of(table, table2));
        database.setTables(List.of(table));

        instance.setDatabase(database);

        instance.writeTablesToFile(out);

        out.close();

        instance.openDatabase(filePath);
        System.out.println(instance.getDatabase().getTables());

    }
}