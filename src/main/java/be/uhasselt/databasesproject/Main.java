package be.uhasselt.databasesproject;

import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
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
        launch();
    }

    public static Stage getRootStage() {
        return rootStage;
    }
}