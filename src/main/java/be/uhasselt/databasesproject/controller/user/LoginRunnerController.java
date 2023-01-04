package be.uhasselt.databasesproject.controller.user;

import be.uhasselt.databasesproject.Password;
import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RunnerJdbi;
import be.uhasselt.databasesproject.model.Runner;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginRunnerController {

    @FXML
    private Text errorText;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField runnerIdTextField;

    private int id;
    private Stage stage;

    @FXML
    void initialize() {
        runnerIdTextField.requestFocus();
        errorText.setText("");
        loginButton.setOnAction(event -> login());
    }

    private void login() {
        runnerIdTextField.setBorder(Border.EMPTY);
        passwordField.setBorder(Border.EMPTY);

        if (isValidInput()) {
            if (isPasswordCorrect()) {
                SwitchAnchorPane.goToEditRunner(stage, id);
            } else {
                errorText.setText("Incorrect password!");
                passwordField.setBorder(Border.stroke(Paint.valueOf("red")));
            }
        }
    }

    private boolean isValidInput() {
        try {
            id = Integer.parseInt(runnerIdTextField.getText());

            RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);
            Runner runner = runnerJdbi.getById(id);

            if (runner.getPassword() == null) {
                errorText.setText("This runner has no account!");
                runnerIdTextField.setBorder(Border.stroke(Paint.valueOf("red")));
                return false;
            }
            return true;
        } catch (NumberFormatException exception) {
            errorText.setText("Please enter a valid id!");
            runnerIdTextField.setBorder(Border.stroke(Paint.valueOf("red")));
            return false;
        } catch (IllegalStateException e) {
            errorText.setText("A runner with this id does not exist!");
            runnerIdTextField.setBorder(Border.stroke(Paint.valueOf("red")));
            return false;
        }
    }

    private boolean isPasswordCorrect() {
        return Password.isSamePassword(passwordField.getText(), id);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
