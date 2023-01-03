package be.uhasselt.databasesproject.controller.user;

import be.uhasselt.databasesproject.Main;
import be.uhasselt.databasesproject.Password;
import be.uhasselt.databasesproject.controller.admin.EditRunnerController;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RunnerJdbi;
import be.uhasselt.databasesproject.model.Runner;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

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
                showPanel();
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

    private Runner getRunnerById(int id) {
        RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);
        return runnerJdbi.getById(id);
    }

    private void showPanel() {
        String resourceName = "/fxml/admin/editRunner.fxml";

        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(resourceName)));
            AnchorPane root = loader.load();

            EditRunnerController editRunnerController = loader.getController();
            editRunnerController.inflateUI(getRunnerById(id));
            editRunnerController.setUserMode();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("edit runner");
            stage.initOwner(Main.getRootStage());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
            this.stage.close();
        } catch (
                Exception e) {
            throw new RuntimeException("Cannot find " + resourceName, e);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
