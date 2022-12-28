package be.uhasselt.databasesproject.controller;

import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RunnerJdbi;
import be.uhasselt.databasesproject.model.Runner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.lang3.SerializationUtils;

import java.util.Objects;

public class EditRunnerController {

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
    private Text errorMessageText;

    @FXML
    private TextField familyNameTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField houseNumberTextField;

    @FXML
    private Text idText;

    @FXML
    private TextField lengthTextField;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private Button saveButton;

    @FXML
    private TextField streetNameTextField;

    @FXML
    private TextField weightTextField;

    private Runner runner;
    private Runner originalRunner;
    private Boolean confirmation = false;

    @FXML
    void initialize() {
        saveButton.setOnAction(this::databaseUpdate);
        cancelButton.setOnAction(this::cancel);
        errorMessageText.setText("");
    }

    private void close(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        WindowEvent windowEvent = new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST);

        stage.getOnCloseRequest().handle(windowEvent);
        stage.close();
    }

    public void inflateUI(Runner runner) {
        this.runner = runner;
        originalRunner = SerializationUtils.clone(runner);
        if (runner.getId() == -1) {
            idText.setText("tbd");
        } else {
            idText.setText(Integer.toString(runner.getId()));
        }

        firstNameTextField.setText(runner.getFirstName());
        familyNameTextField.setText(runner.getFamilyName());
        if (runner.getAge() == -1) {
            ageTextField.setText("");
        } else {
            ageTextField.setText(Integer.toString(runner.getAge()));
        }

        if (runner.getWeight() == -1.0) {
            weightTextField.setText("");
        } else {
            weightTextField.setText(Double.toString(runner.getWeight()));
        }

        if (runner.getLength() == -1.0) {
            lengthTextField.setText("");
        } else {
            lengthTextField.setText(Double.toString(runner.getLength()));
        }

        streetNameTextField.setText(runner.getStreetName());
        houseNumberTextField.setText(runner.getHouseNumber());
        boxNumberTextField.setText(runner.getBoxNumber());
        postalCodeTextField.setText(runner.getPostalCode());
        cityTextField.setText(runner.getCity());
        countryTextField.setText(runner.getCountry());
    }

    private void runnerUpdate() {
        runner.setFirstName(firstNameTextField.getText());
        runner.setFamilyName(familyNameTextField.getText());
        runner.setStreetName(streetNameTextField.getText());
        runner.setHouseNumber(houseNumberTextField.getText());
        runner.setBoxNumber(boxNumberTextField.getText());
        runner.setPostalCode(postalCodeTextField.getText());
        runner.setCity(cityTextField.getText());
        runner.setCountry(countryTextField.getText());

        if (Objects.equals(idText.getText(), "tbd")) {
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
        } else {
            runner.setAge(Integer.parseInt(ageTextField.getText()));
            runner.setWeight(Double.parseDouble(weightTextField.getText()));
            runner.setLength(Double.parseDouble(lengthTextField.getText()));
        }
    }

    private boolean isNotChanged() {
        runnerUpdate();
        return originalRunner.equals(runner);
    }

    private void databaseUpdate(ActionEvent event) {
        if (areMandatoryFieldsFilledIn()) {
            if (isNotChanged()) {
                closeOnNoChanges(event);
            } else {
                RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);
                if (runner.getId() == -1) {
                    runnerJdbi.insert(runner);
                } else {
                    runnerJdbi.update(runner);
                }
                close(event);
            }
        } else {
            showAlert("Warning", "Please fill in the mandatory fields.");
        }
    }

    private boolean areMandatoryFieldsFilledIn() {
        boolean status = true;
        String color = "red";
        if (firstNameTextField.getText().isBlank() || familyNameTextField.getText().isBlank() || ageTextField.getText().isBlank() || weightTextField.getText().isBlank() || lengthTextField.getText().isBlank() || streetNameTextField.getText().isBlank() || houseNumberTextField.getText().isBlank() || postalCodeTextField.getText().isBlank() || cityTextField.getText().isBlank() || countryTextField.getText().isBlank()) {
            errorMessageText.setText("Please fill in all mandatory fields.");
            showMandatoryFields();
            status = false;
        }

        showMandatoryFields();
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
        if (status) {
            errorMessageText.setText("");
        }
        return status;
    }

    private void cancel(ActionEvent event) {
        if (isNotChanged()) {
            close(event);
        } else {
            showAlertWithConfirmation("Warning", "Are you sure you want to discard your changes?");
            if (confirmation) {
                close(event);
            }
        }
    }

    private void showMandatoryFields() {
        String color = "red";
        resetTextFieldBorder();
        if (firstNameTextField.getText().isBlank()) {
            firstNameTextField.setBorder(Border.stroke(Paint.valueOf(color)));
        }
        if (familyNameTextField.getText().isBlank()) {
            familyNameTextField.setBorder(Border.stroke(Paint.valueOf(color)));
        }
        if (ageTextField.getText().isBlank()) {
            ageTextField.setBorder(Border.stroke(Paint.valueOf(color)));
        }
        if (weightTextField.getText().isBlank()) {
            weightTextField.setBorder(Border.stroke(Paint.valueOf(color)));
        }
        if (lengthTextField.getText().isBlank()) {
            lengthTextField.setBorder(Border.stroke(Paint.valueOf(color)));
        }
        if (streetNameTextField.getText().isBlank()) {
            streetNameTextField.setBorder(Border.stroke(Paint.valueOf(color)));
        }
        if (houseNumberTextField.getText().isBlank()) {
            houseNumberTextField.setBorder(Border.stroke(Paint.valueOf(color)));
        }
        if (postalCodeTextField.getText().isBlank()) {
            postalCodeTextField.setBorder(Border.stroke(Paint.valueOf(color)));
        }
        if (cityTextField.getText().isBlank()) {
            cityTextField.setBorder(Border.stroke(Paint.valueOf(color)));
        }
        if (countryTextField.getText().isBlank()) {
            countryTextField.setBorder(Border.stroke(Paint.valueOf(color)));
        }
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

    private void closeOnNoChanges(ActionEvent event) {
        showAlertWithConfirmation("Waring", "No changes were made!");
        if (confirmation) {
            close(event);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showAlertWithConfirmation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING, content, ButtonType.CANCEL, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.showAndWait().filter(ButtonType.OK::equals).ifPresent(buttonType -> confirmation = true);
    }
}
