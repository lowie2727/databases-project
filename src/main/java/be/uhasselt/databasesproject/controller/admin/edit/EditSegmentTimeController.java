package be.uhasselt.databasesproject.controller.admin.edit;

import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.SegmentTimesJdbi;
import be.uhasselt.databasesproject.model.SegmentTimes;
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

public class EditSegmentTimeController {

    @FXML
    private Text runnerIdText;

    @FXML
    private Button cancelButton;

    @FXML
    private Text errorMessageText;

    @FXML
    private Text segmentIdText;

    @FXML
    private TextField timeTextField;

    @FXML
    private Button saveButton;

    private SegmentTimes segmentTime;
    private SegmentTimes originalSegmentTime;
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

    public void inflateUI(SegmentTimes segmentTime) {
        this.segmentTime = segmentTime;
        originalSegmentTime = SerializationUtils.clone(segmentTime);

        if (segmentTime.getRunnerId() == -1) {
            runnerIdText.setText("tbd");
        } else {
            runnerIdText.setText(Integer.toString(segmentTime.getRunnerId()));
        }

        if (segmentTime.getSegmentId() == -1) {
            segmentIdText.setText("tbd");
        } else {
            segmentIdText.setText(Integer.toString(segmentTime.getSegmentId()));
        }

        if (segmentTime.getTime() == -1) {
            timeTextField.setText("");
        } else {
            timeTextField.setText(Integer.toString(segmentTime.getTime()));
        }
    }

    private void segmentTimeUpdate() {
        segmentTime.setTime(Integer.parseInt(timeTextField.getText()));
    }

    private boolean isNotChanged() {
        segmentTimeUpdate();
        return originalSegmentTime.equals(segmentTime);
    }

    private void databaseUpdate(ActionEvent event) {
        if (areMandatoryFieldsFilledIn()) {
            if (isNotChanged()) {
                closeOnNoChanges(event);
            } else {
                SegmentTimesJdbi segmentTimesJdbi = new SegmentTimesJdbi(ConnectionManager.CONNECTION_STRING);
                if (segmentTime.getSegmentId() == -1) {
                    segmentTimesJdbi.insert(segmentTime);
                } else {
                    segmentTimesJdbi.update(segmentTime);
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

        try {
            Integer.parseInt(timeTextField.getText());
        } catch (NumberFormatException exception) {
            timeTextField.setBorder(Border.stroke(Paint.valueOf(color)));
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
        timeTextField.setBorder(Border.EMPTY);
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
