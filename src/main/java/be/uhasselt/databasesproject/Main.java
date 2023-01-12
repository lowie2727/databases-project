package be.uhasselt.databasesproject;

import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import be.uhasselt.databasesproject.jdbi.*;
import be.uhasselt.databasesproject.model.Race;
import be.uhasselt.databasesproject.model.Runner;
import be.uhasselt.databasesproject.model.Segment;
import be.uhasselt.databasesproject.model.Volunteer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage rootStage;

    @Override
    public void start(Stage stage) throws IOException {
        String resourceName = "/fxml/main.fxml";
        rootStage = stage;

        FXMLLoader loader = new FXMLLoader(Main.class.getResource(resourceName));
        AnchorPane anchorPane = loader.load();
        SwitchAnchorPane.setAnchorPane(anchorPane);
        Scene scene = new Scene(anchorPane);

        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.setWidth(585);
        stage.setHeight(435);
        stage.show();
    }

    public static void main(String[] args) {

        ConnectionManager.initTables();

        RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);
        RaceJdbi raceJdbi = new RaceJdbi(ConnectionManager.CONNECTION_STRING);
        VolunteerJdbi volunteerJdbi = new VolunteerJdbi(ConnectionManager.CONNECTION_STRING);
        SegmentJdbi segmentJdbi = new SegmentJdbi(ConnectionManager.CONNECTION_STRING);

        Runner runner = new Runner(-1, "Joe", "Biden", 100, 60.0, 1.6, "252f10c83610ebca1a059c0bae8255eba2f95be4d1d7bcfa89d7248a82d9f111", "Pennsylvania Avenue NW", "1600", null, "DC 20500", "Washington", "Verenigde Staten");
        Race race = new Race(-1, "2022-12-12", "Dwars door Genk", 5000, 50);
        Volunteer volunteer = new Volunteer(-1, "Bob", "Dylan", "252f10c83610ebca1a059c0bae8255eba2f95be4d1d7bcfa89d7248a82d9f111");
        Segment segment = new Segment(1, 1, "door het bos", 2313);

        runnerJdbi.insertGlobal(runner);
        raceJdbi.insert(race);
        volunteerJdbi.insert(volunteer);
        segmentJdbi.insert(segment);

        launch();
    }

    public static Stage getRootStage() {
        return rootStage;
    }
}