package be.uhasselt.databasesproject.controller.admin.edit;

import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
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
    private Boolean isAdmin;

    @FXML
    void initialize() {
        saveButton.setOnAction(this::databaseUpdate);
        cancelButton.setOnAction(this::cancel);
        errorMessageText.setText("");
    }

    private void close(ActionEvent event) {
        if (isAdmin) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            WindowEvent windowEvent = new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST);
            stage.getOnCloseRequest().handle(windowEvent);

            stage.close();
        } else {
            SwitchAnchorPane.goToRunnerUser(runner.getId());
        }
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
        streetNameTextField.setText(runner.getStreetName());
        houseNumberTextField.setText(runner.getHouseNumber());
        boxNumberTextField.setText(runner.getBoxNumber());
        postalCodeTextField.setText(runner.getPostalCode());
        cityTextField.setText(runner.getCity());
        countryTextField.setText(runner.getCountry());

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

        try {
            runner.setAge(Integer.parseInt(ageTextField.getText()));
        } catch (NumberFormatException e) {
            runner.setAge(-1);
        }
        try {
            runner.setWeight(Double.parseDouble(weightTextField.getText()));
        } catch (NumberFormatException e) {
            runner.setWeight(-1.0);
        }
        try {
            runner.setLength(Double.parseDouble(lengthTextField.getText()));
        } catch (NumberFormatException e) {
            runner.setLength(-1.0);
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
                    runnerJdbi.insertGlobal(runner, -1);
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
            if (Integer.parseInt(ageTextField.getText()) < 12) {
                ageTextField.setBorder(Border.stroke(Paint.valueOf(color)));
                status = false;
            }
        } catch (NumberFormatException exception) {
            ageTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }

        try {
            if (Double.parseDouble(weightTextField.getText()) <= 0) {
                weightTextField.setBorder(Border.stroke(Paint.valueOf(color)));
                status = false;
            }
        } catch (NumberFormatException exception) {
            weightTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }

        try {
            if (Double.parseDouble(lengthTextField.getText()) <= 0) {
                lengthTextField.setBorder(Border.stroke(Paint.valueOf(color)));
                status = false;
            }
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
            if (isAdmin) {
                close(event);
            } else {
                SwitchAnchorPane.goToRunnerUser(runner.getId());
            }
        }
    }

    public void setAdminMode() {
        isAdmin = true;
    }

    public void setUserMode() {
        isAdmin = false;
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
