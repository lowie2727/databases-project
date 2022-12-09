package be.uhasselt.databasesproject.controller;

import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RunnerJdbi;
import be.uhasselt.databasesproject.model.Runner;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class EditRunnerController {

    @FXML
    private Text idText;

    @FXML
    private TextField ageTextField;

    @FXML
    private TextField boxNumberTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private Button closeButton;

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

    @FXML
    void initialize() {
        closeButton.setOnAction(event -> close());
    }

    private void close() {
        runnerUpdate();
        final RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);
        runnerJdbi.updateRunner(runner);
        closeButton.getScene().getWindow().hide();
    }

    public void inflateUI(final Runner runner) {
        this.runner = runner;
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
}
