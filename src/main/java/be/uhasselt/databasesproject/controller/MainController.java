package be.uhasselt.databasesproject.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
        //volunteerRegistrationButton.setOnAction(event -> showPanel("registerVolunteer", false));
        adminButton.setOnAction(event -> showPanel("admin", true));
    }

    private void showPanel(String string, boolean isAdmin) {
        if (Objects.equals(string, "admin")) {
            SwitchAnchorPane.goToAdmin();
        } else if (Objects.equals(string, "registerRunner")) {
            SwitchAnchorPane.goToRegisterRunner();
        } else if (Objects.equals(string, "registerVolunteer")) {
            //TODO
            //SwitchAnchorPane.goToRegisterVolunteer();
        } else if (Objects.equals(string, "loginRunner")) {
            SwitchAnchorPane.goToLogin();
        }
    }
}
