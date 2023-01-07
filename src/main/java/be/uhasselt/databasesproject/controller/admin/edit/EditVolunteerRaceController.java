package be.uhasselt.databasesproject.controller.admin.edit;

import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.VolunteerRaceJdbi;
import be.uhasselt.databasesproject.model.VolunteerRace;
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

public class EditVolunteerRaceController {

    @FXML
    private Text raceIdText;

    @FXML
    private Button cancelButton;

    @FXML
    private Text errorMessageText;

    @FXML
    private Text volunteerIdText;

    @FXML
    private Button saveButton;

    private VolunteerRace volunteerRace;
    private VolunteerRace originalVolunteerRace;
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

    public void inflateUI(VolunteerRace volunteerRace) {
        this.volunteerRace = volunteerRace;
        originalVolunteerRace = SerializationUtils.clone(volunteerRace);
        if (volunteerRace.getRaceId() == -1) {
            raceIdText.setText("tbd");
        } else {
            raceIdText.setText(Integer.toString(volunteerRace.getRaceId()));
        }

        if (volunteerRace.getVolunteerId() == -1) {
            volunteerIdText.setText("tbd");
        } else {
            volunteerIdText.setText(Integer.toString(volunteerRace.getVolunteerId()));
        }

    }

    private void VolunteerRaceUpdate() {

    }

    private boolean isNotChanged() {
        VolunteerRaceUpdate();
        return originalVolunteerRace.equals(volunteerRace);
    }

    private void databaseUpdate(ActionEvent event) {
        if (areMandatoryFieldsFilledIn()) {
            if (isNotChanged()) {
                closeOnNoChanges(event);
            } else {
                VolunteerRaceJdbi volunteerRaceJdbi = new VolunteerRaceJdbi(ConnectionManager.CONNECTION_STRING);
                if (volunteerRace.getVolunteerId() == -1) {
                    volunteerRaceJdbi.insert(volunteerRace);
                } else {
                    volunteerRaceJdbi.update(volunteerRace);
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
