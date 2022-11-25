package be.uhasselt.databasesproject;

import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RunnerJdbi;
import be.uhasselt.databasesproject.model.Runner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Main extends Application {

    private static Stage rootStage;

    public static Stage getRootStage(){
        return rootStage;
    }
    @Override
    public void start(Stage stage) throws IOException {
        rootStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/mainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        ConnectionManager.initTables();

        RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.ConnectionString);

        List<Runner> runnersBeforeInsert = runnerJdbi.getRunners();
        System.out.println("initial list");
        for (Runner r : runnersBeforeInsert) {
            System.out.println(r);
        }

        Runner runner = new Runner(6, "Joe", "Biden", 100, 60.0F, 160.0F, "Pennsylvania Avenue NW", "1600", null, "DC 20500", "Washington", "Verenigde Staten");
        runnerJdbi.insertRunner(runner);

        List<Runner> runnersAfterInsert = runnerJdbi.getRunners();
        System.out.println("\nafter insert");
        for (Runner r : runnersAfterInsert) {
            System.out.println(r);
        }

        List<Runner> runnersAfterFilter = runnerJdbi.getRunnerByFirstName("Joe");
        System.out.println("\nfilter Joe");
        for (Runner r : runnersAfterFilter) {
            System.out.println(r);
        }

        launch();
    }
}