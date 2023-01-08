package be.uhasselt.databasesproject.controller.admin.edit;

import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.SegmentJdbi;
import be.uhasselt.databasesproject.model.Segment;
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

public class EditSegmentController {

    @FXML
    private TextField distanceTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private Text errorMessageText;

    @FXML
    private TextField locationTextField;

    @FXML
    private TextField raceIdTextField;

    @FXML
    private Text raceIdText;

    @FXML
    private Text idText;

    @FXML
    private Button saveButton;

    private Segment segment;
    private Segment originalSegment;
    private Boolean confirmation = false;
    private Boolean isFromRace;

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

    public void inflateUI(Segment segment) {
        this.segment = segment;
        originalSegment = SerializationUtils.clone(segment);

        if (segment.getId() == -1) {
            idText.setText("tbd");
        } else {
            idText.setText(Integer.toString(segment.getId()));
        }

        if (isFromRace) {
            raceIdTextField.setVisible(false);
            raceIdText.setText("tbd");
        } else {
            if (segment.getRaceId() == -1) {
                raceIdTextField.setText("");
            } else {
                raceIdTextField.setText(Integer.toString(segment.getRaceId()));
            }
        }

        locationTextField.setText(segment.getLocation());

        if (segment.getDistance() == -1) {
            distanceTextField.setText("");
        } else {
            distanceTextField.setText(Integer.toString(segment.getDistance()));
        }
    }

    private void segmentUpdate() {
        if (!isFromRace) {
            segment.setRaceId(Integer.parseInt(raceIdTextField.getText()));
        }

        segment.setLocation(locationTextField.getText());

        try {
            segment.setDistance(Integer.parseInt(distanceTextField.getText()));
        } catch (NumberFormatException e) {
            segment.setDistance(-1);
        }

    }

    private boolean isNotChanged() {
        segmentUpdate();
        return originalSegment.equals(segment);
    }

    private void databaseUpdate(ActionEvent event) {
        if (areMandatoryFieldsFilledIn()) {
            if (isNotChanged()) {
                closeOnNoChanges(event);
            } else {
                SegmentJdbi segmentJdbi = new SegmentJdbi(ConnectionManager.CONNECTION_STRING);
                if (segment.getId() == -1) {
                    segmentJdbi.insert(segment);
                } else {
                    segmentJdbi.update(segment);
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

        if (!isFromRace) {
            if (raceIdTextField.getText().isBlank()) {
                raceIdTextField.setBorder(Border.stroke(Paint.valueOf(color)));
                status = false;
            }
        }

        if (locationTextField.getText().isBlank()) {
            locationTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }
        if (distanceTextField.getText().isBlank()) {
            distanceTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }

        try {
            Integer.parseInt(distanceTextField.getText());
        } catch (NumberFormatException exception) {
            distanceTextField.setBorder(Border.stroke(Paint.valueOf(color)));
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

    public void setIsFromRace() {
        isFromRace = true;
    }

    public void setIsFromSegment() {
        isFromRace = false;
    }

    private void resetTextFieldBorder() {
        raceIdTextField.setBorder(Border.EMPTY);
        locationTextField.setBorder(Border.EMPTY);
        distanceTextField.setBorder(Border.EMPTY);
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
