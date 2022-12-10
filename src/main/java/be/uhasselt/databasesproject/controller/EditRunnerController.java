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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.lang3.SerializationUtils;

public class EditRunnerController {

    @FXML
    private Text idText;

    @FXML
    private TextField ageTextField;

    @FXML
    private TextField boxNumberTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField cityTextField;

    @FXML
    private Button saveButton;

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
    private TextField postalCodeTextField;

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

        idText.setText(Integer.toString(runner.getId()));
        firstNameTextField.setText(runner.getFirstName());
        familyNameTextField.setText(runner.getFamilyName());
        ageTextField.setText(Integer.toString(runner.getAge()));
        weightTextField.setText(Double.toString(runner.getWeight()));
        lengthTextField.setText(Double.toString(runner.getLength()));
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
        runner.setAge(Integer.parseInt(ageTextField.getText()));
        runner.setWeight(Double.parseDouble(weightTextField.getText()));
        runner.setLength(Double.parseDouble(lengthTextField.getText()));
        runner.setStreetName(streetNameTextField.getText());
        runner.setHouseNumber(houseNumberTextField.getText());
        runner.setBoxNumber(boxNumberTextField.getText());
        runner.setPostalCode(postalCodeTextField.getText());
        runner.setCity(cityTextField.getText());
        runner.setCountry(countryTextField.getText());
    }

    private boolean isNotChanged() {
        runnerUpdate();
        return originalRunner.equals(runner);
    }

    private void databaseUpdate(ActionEvent event) {
        if (isNotChanged()) {
            closeOnNoChanges(event);
        } else {
            showAlert("Warning", "Are you sure you want to save your changes?");
            if (confirmation) {
                RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);
                runnerJdbi.updateRunner(runner);
                close(event);
            }
        }
    }

    private void cancel(ActionEvent event) {
        if (isNotChanged()) {
            closeOnNoChanges(event);
        } else {
            showAlert("Warning", "Are you sure you want to delete your changes?");
            if (confirmation) {
                close(event);
            }
        }
    }

    private void closeOnNoChanges(ActionEvent event) {
        showAlert("Waring", "No changes were made!");
        if (confirmation) {
            close(event);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING, content, ButtonType.CANCEL, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.showAndWait().filter(ButtonType.OK::equals).ifPresent(buttonType -> confirmation = true);
    }
}
