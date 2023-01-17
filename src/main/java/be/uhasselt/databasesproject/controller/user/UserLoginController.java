package be.uhasselt.databasesproject.controller.user;

import be.uhasselt.databasesproject.Password;
import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RunnerJdbi;
import be.uhasselt.databasesproject.jdbi.VolunteerJdbi;
import be.uhasselt.databasesproject.model.Runner;
import be.uhasselt.databasesproject.model.Volunteer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserLoginController {

    @FXML
    private Text errorText;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameTextField;

    private int id;
    private Stage stage;
    private boolean isRunner;

    @FXML
    void initialize() {
        usernameTextField.requestFocus();
        errorText.setText("");
        loginButton.setOnAction(event -> login());
    }

    private void login() {
        usernameTextField.setBorder(Border.EMPTY);
        passwordField.setBorder(Border.EMPTY);

        if (isValidInput()) {
            if (isPasswordCorrect()) {
                if (isRunner) {
                    SwitchAnchorPane.goToRunnerUser(id);
                } else {
                    SwitchAnchorPane.goToVolunteerUser(id);
                }
                stage.close();
            } else {
                errorText.setText("Incorrect password!");
                passwordField.setBorder(Border.stroke(Paint.valueOf("red")));
            }
        }
    }

    private boolean isValidInput() {
        String username = usernameTextField.getText();

        if (isRunner) {
            RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);
            Runner runner;
            try {
                runner = runnerJdbi.getByUsername(username);
                id = runner.getId();
            } catch (IllegalStateException e) {
                errorText.setText("A runner with this id does not exist!");
                usernameTextField.setBorder(Border.stroke(Paint.valueOf("red")));
                return false;
            }

            if (runner.getPassword() == null) {
                errorText.setText("This runner has no account!");
                usernameTextField.setBorder(Border.stroke(Paint.valueOf("red")));
                return false;
            }
        } else {
            VolunteerJdbi volunteerJdbi = new VolunteerJdbi(ConnectionManager.CONNECTION_STRING);
            Volunteer volunteer;
            try {
                volunteer = volunteerJdbi.getByUsername(username);
                id = volunteer.getId();
            } catch (IllegalStateException e) {
                errorText.setText("A volunteer with this id does not exist!");
                usernameTextField.setBorder(Border.stroke(Paint.valueOf("red")));
                return false;
            }

            if (volunteer.getPassword() == null) {
                errorText.setText("This volunteer has no account!");
                usernameTextField.setBorder(Border.stroke(Paint.valueOf("red")));
                return false;
            }
        }
        return true;
    }

    private boolean isPasswordCorrect() {
        if (isRunner) {
            return Password.isSamePasswordRunner(passwordField.getText(), id);
        }
        return Password.isSamePasswordVolunteer(passwordField.getText(), id);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setRunner() {
        isRunner = true;
    }

    public void setVolunteer() {
        isRunner = false;
    }
}
