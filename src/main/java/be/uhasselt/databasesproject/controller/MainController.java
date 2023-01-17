package be.uhasselt.databasesproject.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {

    @FXML
    private Button adminButton;

    @FXML
    private Button adminLoginButton;

    @FXML
    private Button rankingButton;

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
        rankingButton.setOnAction(event -> showPanel("ranking"));
        runnerRegistrationButton.setOnAction(event -> showPanel("registerRunner"));
        runnerLoginButton.setOnAction(event -> showPanel("loginRunner"));
        volunteerRegistrationButton.setOnAction(event -> showPanel("registerVolunteer"));
        volunteerLoginButton.setOnAction(event -> showPanel("loginVolunteer"));
        adminButton.setOnAction(event -> showPanel("admin"));
        adminLoginButton.setOnAction(event -> showPanel("loginAdmin"));
    }

    private void showPanel(String string) {
        if (string.equals("ranking")) {
            SwitchAnchorPane.goToRankingMenu();
        } else if (string.equals("admin")) {
            SwitchAnchorPane.goToAdmin();
        } else if (string.equals("loginAdmin")) {
            SwitchAnchorPane.goToLoginAdmin();
        } else if (string.equals("registerRunner")) {
            SwitchAnchorPane.goToRegisterRunner();
        } else if (string.equals("loginRunner")) {
            SwitchAnchorPane.goToLogin(true);
        } else if (string.equals("registerVolunteer")) {
            SwitchAnchorPane.goToRegisterVolunteer();
        } else if (string.equals("loginVolunteer")) {
            SwitchAnchorPane.goToLogin(false);
        }
    }
}
