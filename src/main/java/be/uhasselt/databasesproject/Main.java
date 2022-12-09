package be.uhasselt.databasesproject;

import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RunnerJdbi;
import be.uhasselt.databasesproject.model.Runner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage rootStage;

    public static Stage getRootStage() {
        return rootStage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        rootStage = stage;
        final FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/mainMenu.fxml"));
        final Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(final String[] args) {

        ConnectionManager.initTables();

        final RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);
        final Runner runner = new Runner(6, "Joe", "Biden", 100, 60.0, 1.6, "Pennsylvania Avenue NW", "1600", null, "DC 20500", "Washington", "Verenigde Staten");
        runnerJdbi.insertRunner(runner);

        launch();
    }
}