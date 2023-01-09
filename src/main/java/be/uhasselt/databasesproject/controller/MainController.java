package be.uhasselt.databasesproject.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.Objects;

public class MainController {

    @FXML
    private Button adminButton;

    @FXML
    private Button adminLoginButton;

    @FXML
    private Button runnerLoginButton;

    @FXML
    private Button runnerRegistrationButton;

    @FXML
    private Button volunteerLoginButton;

    @FXML
    private Button volunteerRegistrationButton;

    @FXML
    void initialize() {
        runnerRegistrationButton.setOnAction(event -> showPanel("registerRunner"));
        runnerLoginButton.setOnAction(event -> showPanel("loginRunner"));
        volunteerRegistrationButton.setOnAction(event -> showPanel("registerVolunteer"));
        volunteerLoginButton.setOnAction(event -> showPanel("loginVolunteer"));
        adminButton.setOnAction(event -> showPanel("admin"));
        adminLoginButton.setOnAction(event -> showPanel("loginAdmin"));
    }

    private void showPanel(String string) {
        if (Objects.equals(string, "admin")) {
            SwitchAnchorPane.goToAdmin();
        } else if (Objects.equals(string, "loginAdmin")) {
            SwitchAnchorPane.goToLoginAdmin();
        } else if (Objects.equals(string, "registerRunner")) {
            SwitchAnchorPane.goToRegisterRunner();
        } else if (Objects.equals(string, "loginRunner")) {
            SwitchAnchorPane.goToLogin(true);
        } else if (Objects.equals(string, "registerVolunteer")) {
            SwitchAnchorPane.goToRegisterVolunteer();
        } else if (Objects.equals(string, "loginVolunteer")) {
            SwitchAnchorPane.goToLogin(false);
        }
    }
}
