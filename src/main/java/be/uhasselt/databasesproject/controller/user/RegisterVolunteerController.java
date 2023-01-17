package be.uhasselt.databasesproject.controller.user;

import be.uhasselt.databasesproject.Password;
import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import be.uhasselt.databasesproject.jdbi.*;
import be.uhasselt.databasesproject.model.Race;
import be.uhasselt.databasesproject.model.Volunteer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;

import java.util.List;

public class RegisterVolunteerController {

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField familyNameTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private ChoiceBox<Race> raceChoiceBox;

    @FXML
    private Button registerButton;

    private Volunteer volunteer;
    private boolean confirmationNoAccount;
    private boolean confirmationDiscardChanges;

    @FXML
    void initialize() {
        volunteer = new Volunteer();

        choiceBoxSetup();

        cancelButton.setOnAction(event -> cancel());
        registerButton.setOnAction(event -> register());
    }

    private void volunteerUpdate() {
        volunteer.setFirstName(firstNameTextField.getText());
        volunteer.setFamilyName(familyNameTextField.getText());
        volunteer.setUsername(usernameTextField.getText());
        volunteer.setPassword(hashPassword());
    }

    private String hashPassword() {
        String password;
        if (!passwordField.getText().isEmpty()) {
            password = Password.hashString(passwordField.getText());
        } else {
            password = null;
        }
        return password;
    }

    private void register() {
        if (areMandatoryFieldsFilledIn()) {
            if (isRegisteredForRace()) {
                volunteerUpdate();
                checkEmptyPassword();
                if (confirmationNoAccount) {
                    insertIntoDatabase(volunteer);
                    addVolunteerToRace();
                    SwitchAnchorPane.goToMainMenu();
                }
            } else {
                showAlert("Warning", "Please select a race from the dropdown menu.");
            }
        } else if (checkUsernameExists(usernameTextField.getText())) {
            showAlert("Warning", "This username already exists");
        } else {
            showAlert("Warning", "Please fill in the mandatory fields correctly.");
        }
    }

    private void checkEmptyPassword() {
        if (passwordField.getText().isBlank()) {
            showAlertWithConfirmationAccount("Warning", "Are you sure you don't want an account?");
        } else {
            showAlertWithConfirmationAccount("Warning", "Are you sure all your details have been entered correctly?");
        }
    }

    private boolean checkUsernameExists(String username) {
        VolunteerJdbi volunteerJdbi = new VolunteerJdbi(ConnectionManager.CONNECTION_STRING);
        List<String> usernames = volunteerJdbi.getAllUsernames();

        return usernames.contains(username);
    }

    private void addVolunteerToRace() {
        int raceId = raceChoiceBox.getValue().getId();
        VolunteerRaceJdbi volunteerRaceJdbi = new VolunteerRaceJdbi(ConnectionManager.CONNECTION_STRING);
        volunteerRaceJdbi.insert(raceId);
    }


    private void insertIntoDatabase(Volunteer volunteer) {
        VolunteerJdbi volunteerJdbi = new VolunteerJdbi(ConnectionManager.CONNECTION_STRING);
        volunteerJdbi.insert(volunteer);
    }

    private boolean isRegisteredForRace() {
        return raceChoiceBox.getValue() != null;
    }

    private boolean areMandatoryFieldsFilledIn() {
        boolean status = true;
        String color = "red";

        resetTextFieldBorder();

        if (firstNameTextField.getText().isBlank()) {
            firstNameTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }
        if (familyNameTextField.getText().isBlank()) {
            familyNameTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }

        if (usernameTextField.getText().isBlank() && !passwordField.getText().isBlank()) {
            usernameTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }

        if (passwordField.getText().isBlank() && !usernameTextField.getText().isBlank()) {
            passwordField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }

        if (checkUsernameExists(usernameTextField.getText())) {
            usernameTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }

        return status;
    }

    private void choiceBoxSetup() {
        RaceJdbi raceJdbi = new RaceJdbi(ConnectionManager.CONNECTION_STRING);
        List<Race> races = raceJdbi.getAllUpcoming();
        ObservableList<Race> observableRaces = FXCollections.observableList(races);
        raceChoiceBox.setItems(observableRaces);
    }

    private void resetTextFieldBorder() {
        firstNameTextField.setBorder(Border.EMPTY);
        familyNameTextField.setBorder(Border.EMPTY);
        usernameTextField.setBorder(Border.EMPTY);
        passwordField.setBorder(Border.EMPTY);
    }

    private void cancel() {
        if (isSomeThingFilledIn()) {
            showAlertWithConfirmationDiscardChanges("Warning", "Are you sure you want to discard your changes?");
            if (confirmationDiscardChanges) {
                SwitchAnchorPane.goToMainMenu();
            }
        } else {
            SwitchAnchorPane.goToMainMenu();
        }
    }

    private boolean isSomeThingFilledIn() {
        boolean status = false;
        if (!firstNameTextField.getText().isBlank()) {
            status = true;
        } else if (!familyNameTextField.getText().isBlank()) {
            status = true;
        } else if (!usernameTextField.getText().isBlank()) {
            status = true;
        } else if (!passwordField.getText().isBlank()) {
            status = true;
        }
        return status;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showAlertWithConfirmationAccount(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING, content, ButtonType.CANCEL, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.showAndWait().filter(ButtonType.OK::equals).ifPresent(buttonType -> confirmationNoAccount = true);
    }

    private void showAlertWithConfirmationDiscardChanges(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING, content, ButtonType.CANCEL, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.showAndWait().filter(ButtonType.OK::equals).ifPresent(buttonType -> confirmationDiscardChanges = true);
    }
}



