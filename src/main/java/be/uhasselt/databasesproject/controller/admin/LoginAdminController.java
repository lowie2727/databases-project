package be.uhasselt.databasesproject.controller.admin;

import be.uhasselt.databasesproject.Password;
import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginAdminController {

    @FXML
    private Text errorText;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameTextField;

    private Stage stage;

    @FXML
    void initialize() {
        loginButton.setOnAction(event -> login());
        errorText.setText("");
    }

    private void login() {
        usernameTextField.setBorder(Border.EMPTY);
        passwordField.setBorder(Border.EMPTY);

        if (isValidInput()) {
            if (isPasswordCorrect()) {
                SwitchAnchorPane.goToAdmin();
                stage.close();
            } else {
                errorText.setText("Incorrect password!");
                passwordField.setBorder(Border.stroke(Paint.valueOf("red")));
            }
        }
    }

    private boolean isValidInput() {
        boolean status = true;
        if (usernameTextField.getText().isBlank()) {
            status = false;
            usernameTextField.setBorder(Border.stroke(Paint.valueOf("red")));
        }
        if (passwordField.getText().isBlank()) {
            status = false;
            passwordField.setBorder(Border.stroke(Paint.valueOf("red")));
        }

        if (!status) {
            errorText.setText("Please fill in all fields.");
        }
        return status;
    }

    private boolean isPasswordCorrect() {
        if (Objects.equals(usernameTextField.getText(), "admin")) {
            return Password.isSamePassword(passwordField.getText());
        }
        return false;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
