package be.uhasselt.databasesproject;

import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.LoperJdbi;
import be.uhasselt.databasesproject.model.Loper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        ConnectionManager.initTables();

        LoperJdbi loperJdbi = new LoperJdbi(ConnectionManager.ConnectionString);

        // loper tabel ophalen
        List<Loper> lopers = loperJdbi.getLopers();
        System.out.println("-------Lijst1-------");
        for (Loper loper : lopers) {
            System.out.println(loper);
        }

        // loper inserten in loper tabel
        Loper loper = new Loper(6, "Joe", "Biden", 100, 60.0F, 160.0F, "Pennsylvania Avenue NW", "1600", null, "DC 20500", "Washington", "Verenigde Staten");
        loperJdbi.insertLoper(loper);

        lopers = loperJdbi.getLopers();
        System.out.println("\n-------Lijst2-------");
        for (Loper l : lopers) {
            System.out.println(l);
        }

        // loper zoeken op naam
        List<Loper> lopers2 = loperJdbi.getLoperByName("Joe");
        System.out.println("\n-------Lijst3-------");
        for (Loper l : lopers2) {
            System.out.println(l);
        }

        launch();
    }
}