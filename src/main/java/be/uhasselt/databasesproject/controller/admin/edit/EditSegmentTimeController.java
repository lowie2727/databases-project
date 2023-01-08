package be.uhasselt.databasesproject.controller.admin.edit;

import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RunnerJdbi;
import be.uhasselt.databasesproject.jdbi.SegmentJdbi;
import be.uhasselt.databasesproject.jdbi.SegmentTimesJdbi;
import be.uhasselt.databasesproject.model.Runner;
import be.uhasselt.databasesproject.model.Segment;
import be.uhasselt.databasesproject.model.SegmentTimes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.lang3.SerializationUtils;

import java.util.List;

public class EditSegmentTimeController {

    @FXML
    private Text runnerIdText;

    @FXML
    private Button cancelButton;

    @FXML
    private Text errorMessageText;

    @FXML
    private ChoiceBox<Segment> segmentChoiceBox;

    @FXML
    private ChoiceBox<Runner> runnerChoiceBox;

    @FXML
    private Text segmentIdText;

    @FXML
    private TextField timeTextField;

    @FXML
    private Button saveButton;

    private SegmentTimes segmentTime;
    private SegmentTimes originalSegmentTime;
    private Boolean confirmation = false;
    private Boolean isEdit;


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
        if(isEdit){
            runnerChoiceBox.setVisible(false);
            segmentChoiceBox.setVisible(false);
        }

        this.segmentTime = segmentTime;
        originalSegmentTime = SerializationUtils.clone(segmentTime);

        if (segmentTime.getRunnerId() == -1) {
            setRunnerChoiceBox();
        } else {
            runnerIdText.setText(Integer.toString(segmentTime.getRunnerId()));
        }

        if (segmentTime.getSegmentId() == -1) {
            setSegmentChoiceBox();
        } else {
            segmentIdText.setText(Integer.toString(segmentTime.getSegmentId()));
        }

        if (segmentTime.getTime() == -1) {
            timeTextField.setText("");
        } else {
            timeTextField.setText(Integer.toString(segmentTime.getTime()));
        }
    }

    private void setRunnerChoiceBox() {
        RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);
        List<Runner> runners = runnerJdbi.getAll();
        ObservableList<Runner> observableRunners = FXCollections.observableList(runners);
        runnerChoiceBox.setItems(observableRunners);
    }

    private void setSegmentChoiceBox() {
        SegmentJdbi segmentJdbi = new SegmentJdbi(ConnectionManager.CONNECTION_STRING);
        List<Segment> segments = segmentJdbi.getAll();
        ObservableList<Segment> observableSegments = FXCollections.observableList(segments);
        segmentChoiceBox.setItems(observableSegments);
    }
    private void segmentTimeUpdate() {
        if (!isEdit) {
            segmentTime.setSegmentId(segmentChoiceBox.getValue().getId());
            segmentTime.setRunnerId(runnerChoiceBox.getValue().getId());
        }

        try {
            segmentTime.setTime(Integer.parseInt(timeTextField.getText()));
        } catch (NumberFormatException e) {
            segmentTime.setTime(-1);
        }
    }

    private boolean isSegmentSelected() {
        return segmentChoiceBox.getValue() != null;
    }

    private boolean isRunnerSelected() {
        return runnerChoiceBox.getValue() != null;
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
                if (!isEdit) {
                    try{
                        segmentTimesJdbi.insert(segmentTime);
                    } catch (Exception e){
                        runnerChoiceBox.setBorder(Border.stroke(Paint.valueOf("red")));
                        segmentChoiceBox.setBorder(Border.stroke(Paint.valueOf("red")));
                        errorMessageText.setText("runner and segment combo exists");
                        return;
                    }
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

        if (!isEdit) {
            if (!isSegmentSelected()) {
                segmentChoiceBox.setBorder(Border.stroke(Paint.valueOf(color)));
                status = false;
            }

            if (!isRunnerSelected()) {
                runnerChoiceBox.setBorder(Border.stroke(Paint.valueOf(color)));
                status = false;
            }
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
        segmentChoiceBox.setBorder(Border.EMPTY);
        runnerChoiceBox.setBorder(Border.EMPTY);
    }

    private void closeOnNoChanges(ActionEvent event) {
        showAlertWithConfirmation("Waring", "No changes were made!");
        if (confirmation) {
            close(event);
        }
    }

    public void setAdd() { isEdit = false; }

    public void setEdit() { isEdit = true; }

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
