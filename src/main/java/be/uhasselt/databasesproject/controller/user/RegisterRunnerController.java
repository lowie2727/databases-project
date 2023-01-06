package be.uhasselt.databasesproject.controller.user;

import be.uhasselt.databasesproject.Password;
import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RaceJdbi;
import be.uhasselt.databasesproject.jdbi.RunnerJdbi;
import be.uhasselt.databasesproject.jdbi.RunnerRaceJdbi;
import be.uhasselt.databasesproject.model.Race;
import be.uhasselt.databasesproject.model.Runner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.util.List;

public class RegisterRunnerController {

    @FXML
    private TextField ageTextField;

    @FXML
    private TextField boxNumberTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField countryTextField;

    @FXML
    private TextField familyNameTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField houseNumberTextField;

    @FXML
    private TextField lengthTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private Text priceText;

    @FXML
    private ChoiceBox<Race> raceChoiceBox;

    @FXML
    private Button registerButton;

    @FXML
    private TextField streetNameTextField;

    @FXML
    private TextField weightTextField;

    private Runner runner;
    private boolean confirmationNoAccount;
    private boolean confirmationDiscardChanges;
    private AnchorPane anchorPane;

    @FXML
    void initialize() {
        priceText.setText("");
        runner = new Runner();

        choiceBoxSetup();

        cancelButton.setOnAction(event -> cancel());
        registerButton.setOnAction(event -> register());
    }

    private void runnerUpdate() {
        runner.setFirstName(firstNameTextField.getText());
        runner.setFamilyName(familyNameTextField.getText());
        runner.setPassword(hashPassword());
        runner.setStreetName(streetNameTextField.getText());
        runner.setHouseNumber(houseNumberTextField.getText());
        runner.setBoxNumber(boxNumberTextField.getText());
        runner.setPostalCode(postalCodeTextField.getText());
        runner.setCity(cityTextField.getText());
        runner.setCountry(countryTextField.getText());

        try {
            runner.setAge(Integer.parseInt(ageTextField.getText()));
        } catch (NumberFormatException exception) {
            runner.setAge(-1);
        }
        try {
            runner.setWeight(Double.parseDouble(weightTextField.getText()));
        } catch (NumberFormatException exception) {
            runner.setWeight(-1.0);
        }
        try {
            runner.setLength(Double.parseDouble(lengthTextField.getText()));
        } catch (NumberFormatException exception) {
            runner.setLength(-1.0);
        }
    }

    private String hashPassword() {
        return Password.hashString(passwordField.getText());
    }

    private void register() {
        if (areMandatoryFieldsFilledIn()) {
            if (isRegisteredForRace()) {
                runnerUpdate();
                checkEmptyPassword();
                if (confirmationNoAccount) {
                    insertIntoDatabase(runner);
                    addRunnerToRace();
                    SwitchAnchorPane.goToMainMenu();
                }
            } else {
                showAlert("Warning", "Please select a race from the dropdown menu.");
            }
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

    private void addRunnerToRace() {
        int raceId = raceChoiceBox.getValue().getId();
        RunnerRaceJdbi runnerRaceJdbi = new RunnerRaceJdbi(ConnectionManager.CONNECTION_STRING);
        runnerRaceJdbi.insert(raceId);
    }

    private void insertIntoDatabase(Runner runner) {
        RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);
        runnerJdbi.insert(runner);
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
        if (ageTextField.getText().isBlank()) {
            ageTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }
        if (weightTextField.getText().isBlank()) {
            weightTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }
        if (lengthTextField.getText().isBlank()) {
            lengthTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }
        if (streetNameTextField.getText().isBlank()) {
            streetNameTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }
        if (houseNumberTextField.getText().isBlank()) {
            houseNumberTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }
        if (postalCodeTextField.getText().isBlank()) {
            postalCodeTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }
        if (cityTextField.getText().isBlank()) {
            cityTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }
        if (countryTextField.getText().isBlank()) {
            countryTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }

        try {
            Integer.parseInt(ageTextField.getText());
        } catch (NumberFormatException exception) {
            ageTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }

        try {
            Double.parseDouble(weightTextField.getText());
        } catch (NumberFormatException exception) {
            weightTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }

        try {
            Double.parseDouble(lengthTextField.getText());
        } catch (NumberFormatException exception) {
            lengthTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }

        return status;
    }

    private void choiceBoxSetup() {
        RaceJdbi raceJdbi = new RaceJdbi(ConnectionManager.CONNECTION_STRING);
        List<Race> races = raceJdbi.getAllUpcoming();
        ObservableList<Race> observableRaces = FXCollections.observableList(races);
        raceChoiceBox.setItems(observableRaces);
        raceChoiceBox.setOnAction(event -> priceText.setText(Double.toString(raceChoiceBox.getValue().getPrice())));
    }

    private void resetTextFieldBorder() {
        firstNameTextField.setBorder(Border.EMPTY);
        familyNameTextField.setBorder(Border.EMPTY);
        ageTextField.setBorder(Border.EMPTY);
        weightTextField.setBorder(Border.EMPTY);
        lengthTextField.setBorder(Border.EMPTY);
        streetNameTextField.setBorder(Border.EMPTY);
        houseNumberTextField.setBorder(Border.EMPTY);
        postalCodeTextField.setBorder(Border.EMPTY);
        cityTextField.setBorder(Border.EMPTY);
        countryTextField.setBorder(Border.EMPTY);
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
        } else if (!ageTextField.getText().isBlank()) {
            status = true;
        } else if (!weightTextField.getText().isBlank()) {
            status = true;
        } else if (!lengthTextField.getText().isBlank()) {
            status = true;
        } else if (!passwordField.getText().isBlank()) {
            status = true;
        } else if (!streetNameTextField.getText().isBlank()) {
            status = true;
        } else if (!houseNumberTextField.getText().isBlank()) {
            status = true;
        } else if (!boxNumberTextField.getText().isBlank()) {
            status = true;
        } else if (!postalCodeTextField.getText().isBlank()) {
            status = true;
        } else if (!cityTextField.getText().isBlank()) {
            status = true;
        } else if (!countryTextField.getText().isBlank()) {
            status = true;
        } else if (raceChoiceBox.getValue() != null) {
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
