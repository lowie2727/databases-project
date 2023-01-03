package be.uhasselt.databasesproject.controller;

import be.uhasselt.databasesproject.Main;
import be.uhasselt.databasesproject.controller.admin.AdminController;
import be.uhasselt.databasesproject.controller.user.LoginRunnerController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class MainController {

    @FXML
    private Button adminButton;

    @FXML
    private Button runnerLoginButton;

    @FXML
    private Button runnerRegistrationButton;

    @FXML
    private Button volunteerRegistrationButton;

    @FXML
    void initialize() {
        runnerRegistrationButton.setOnAction(event -> showPanel("registerRunner", false));
        runnerLoginButton.setOnAction(event -> showPanel("loginRunner", false));
        adminButton.setOnAction(event -> showPanel("admin", true));
    }

    private void showPanel(String string, boolean isAdmin) {
        String resourceName;

        if (isAdmin) {
            resourceName = "/fxml/admin/" + string + ".fxml";
        } else {
            resourceName = "/fxml/user/" + string + ".fxml";
        }

        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(resourceName)));
            AnchorPane root = loader.load();

            if (Objects.equals(string, "admin")) {
                AdminController adminController = loader.getController();
                adminController.setStage(stage);
            } else if (Objects.equals(string, "loginRunner")) {
                LoginRunnerController loginRunnerController = loader.getController();
                loginRunnerController.setStage(stage);
            }

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(string);
            stage.initOwner(Main.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resourceName, e);
        }
    }
}
