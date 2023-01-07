package be.uhasselt.databasesproject.controller.admin.edit;

import be.uhasselt.databasesproject.controller.SwitchAnchorPane;
import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.VolunteerJdbi;
import be.uhasselt.databasesproject.model.Volunteer;
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

public class EditVolunteerController {

    @FXML
    private TextField jobTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private Text errorMessageText;

    @FXML
    private TextField familyNameTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private Text idText;

    @FXML
    private Button saveButton;

    private Volunteer volunteer;
    private Volunteer originalVolunteer;
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
            SwitchAnchorPane.goToMainMenu();
        }
    }

    public void inflateUI(Volunteer volunteer) {
        this.volunteer = volunteer;
        originalVolunteer = SerializationUtils.clone(volunteer);

        if (volunteer.getId() == -1) {
            idText.setText("tbd");
        } else {
            idText.setText(Integer.toString(volunteer.getId()));
        }

        firstNameTextField.setText(volunteer.getFirstName());
        familyNameTextField.setText(volunteer.getFamilyName());
        jobTextField.setText(volunteer.getJob());
    }

    private void volunteerUpdate() {
        volunteer.setFirstName(firstNameTextField.getText());
        volunteer.setFamilyName(familyNameTextField.getText());
        volunteer.setJob(jobTextField.getText());
    }

    private boolean isNotChanged() {
        volunteerUpdate();
        return originalVolunteer.equals(volunteer);
    }

    private void databaseUpdate(ActionEvent event) {
        if (areMandatoryFieldsFilledIn()) {
            if (isNotChanged()) {
                closeOnNoChanges(event);
            } else {
                VolunteerJdbi volunteerJdbi = new VolunteerJdbi(ConnectionManager.CONNECTION_STRING);
                if (volunteer.getId() == -1) {
                    volunteerJdbi.insert(volunteer);
                } else {
                    volunteerJdbi.update(volunteer);
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
        if (jobTextField.getText().isBlank()) {
            jobTextField.setBorder(Border.stroke(Paint.valueOf(color)));
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
        jobTextField.setBorder(Border.EMPTY);
    }

    private void closeOnNoChanges(ActionEvent event) {
        showAlertWithConfirmation("Waring", "No changes were made!");
        if (confirmation) {
            if (isAdmin) {
                close(event);
            } else {
                SwitchAnchorPane.goToMainMenu();
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
