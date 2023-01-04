package be.uhasselt.databasesproject.controller;

import be.uhasselt.databasesproject.Main;
import be.uhasselt.databasesproject.controller.admin.*;
import be.uhasselt.databasesproject.controller.user.LoginRunnerController;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RunnerJdbi;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SwitchAnchorPane {

    public static AnchorPane anchorPane;

    private static void goTo(String resource) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(resource));
            AnchorPane anchorPane = loader.load();
            SwitchAnchorPane.anchorPane.getChildren().setAll(anchorPane);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resource, e);
        }
    }

    public static void goToMainMenu() {
        String resource = "/fxml/main.fxml";
        goTo(resource);
        setStageSize(600, 400);
    }

    public static void goToAdmin() {
        String resource = "/fxml/admin/admin.fxml";
        goTo(resource);
        setStageSize(600, 440);
        Main.getRootStage().setWidth(600);
    }

    public static void goToRunner() {
        String resource = "/fxml/admin/runner.fxml";
        goTo(resource);
        setStageSize(1160, 440);
    }

    public static void goToVolunteer() {
        String resource = "/fxml/admin/volunteer.fxml";
        goTo(resource);
    }

    public static void goToRace() {
        String resource = "/fxml/admin/race.fxml";
        goTo(resource);
        setStageSize(600, 440);
    }

    public static void goToRegisterRunner() {
        String resource = "/fxml/user/registerRunner.fxml";
        goTo(resource);
        setStageSize(600, 440);
    }

    public static void goToLogin() {
        String resource = "/fxml/user/loginRunner.fxml";

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(resource));
            AnchorPane anchorPane = loader.load();
            setLoginRunnerScreen(anchorPane, loader);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resource, e);
        }
    }

    private static void setLoginRunnerScreen(AnchorPane anchorPane, FXMLLoader loader) {
        Stage stage = new Stage();
        Scene scene = new Scene(anchorPane);

        LoginRunnerController loginRunnerController = loader.getController();
        loginRunnerController.setStage(stage);

        stage.setScene(scene);
        stage.setTitle("login");
        stage.initOwner(Main.getRootStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }


    public static void goToEditRunner(Stage stage, int id) {
        String resourceName = "/fxml/admin/editRunner.fxml";

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(resourceName));
            AnchorPane anchorPane = loader.load();
            setEditRunnerScreen(anchorPane, loader, stage, id);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resourceName, e);
        }
    }

    private static void setEditRunnerScreen(AnchorPane anchorPane, FXMLLoader loader, Stage stage, int id) {
        EditRunnerController editRunnerController = loader.getController();
        SwitchAnchorPane.anchorPane.getChildren().setAll(anchorPane);

        RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);
        editRunnerController.inflateUI(runnerJdbi.getById(id));
        editRunnerController.setUserMode();

        setStageSize(750, 340);
        stage.close();
    }

    private static void setStageSize(int width, int height) {
        Main.getRootStage().setWidth(width);
        Main.getRootStage().setHeight(height);
    }

    public static void setAnchorPane(AnchorPane anchorPane) {
        SwitchAnchorPane.anchorPane = anchorPane;
    }
}
