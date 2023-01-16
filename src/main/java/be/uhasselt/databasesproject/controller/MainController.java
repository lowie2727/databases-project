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
        switch (string) {
            case "ranking" -> SwitchAnchorPane.goToRankingMenu();
            case "admin" -> SwitchAnchorPane.goToAdmin();
            case "loginAdmin" -> SwitchAnchorPane.goToLoginAdmin();
            case "registerRunner" -> SwitchAnchorPane.goToRegisterRunner();
            case "loginRunner" -> SwitchAnchorPane.goToLogin(true);
            case "registerVolunteer" -> SwitchAnchorPane.goToRegisterVolunteer();
            case "loginVolunteer" -> SwitchAnchorPane.goToLogin(false);
        }
    }
}
