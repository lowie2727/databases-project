package be.uhasselt.databasesproject;

import be.uhasselt.databasesproject.jdbi.*;
import be.uhasselt.databasesproject.model.Race;
import be.uhasselt.databasesproject.model.Runner;
import be.uhasselt.databasesproject.model.Segment;
import be.uhasselt.databasesproject.model.Volunteer;
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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/mainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        ConnectionManager.initTables();

        RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);
        RaceJdbi raceJdbi = new RaceJdbi(ConnectionManager.CONNECTION_STRING);
        VolunteerJdbi volunteerJdbi = new VolunteerJdbi(ConnectionManager.CONNECTION_STRING);
        SegmentJdbi segmentJdbi = new SegmentJdbi(ConnectionManager.CONNECTION_STRING);

        Runner runner = new Runner(6, "Joe", "Biden", 100, 60.0, 1.6, "Pennsylvania Avenue NW", "1600", null, "DC 20500", "Washington", "Verenigde Staten");
        Race race = new Race(1, "2022", "dwars door genk", 5000, 50);
        Volunteer volunteer = new Volunteer(1, "Bob", "Dylan", "richting aangever");
        Segment segment = new Segment(1, 1, "door het bos", 2313);

        runnerJdbi.insert(runner);
        raceJdbi.insert(race);
        volunteerJdbi.insert(volunteer);
        segmentJdbi.insert(segment);

        launch();
    }
}