package be.uhasselt.databasesproject.controller.admin.edit;

import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RunnerRaceJdbi;
import be.uhasselt.databasesproject.model.RunnerRace;
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

public class EditRunnerRaceController {

    @FXML
    private Text runnerIdText;

    @FXML
    private Button cancelButton;

    @FXML
    private Text errorMessageText;

    @FXML
    private Text raceIdText;

    @FXML
    private TextField shirtNumberTextField;

    @FXML
    private TextField timeTextField;

    @FXML
    private Button saveButton;

    private RunnerRace runnerRace;
    private RunnerRace originalRunnerRace;
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

    public void inflateUI(RunnerRace runnerRace) {
        this.runnerRace = runnerRace;
        originalRunnerRace = SerializationUtils.clone(runnerRace);

        if (runnerRace.getRunnerId() == -1) {
            runnerIdText.setText("tbd");
        } else {
            runnerIdText.setText(Integer.toString(runnerRace.getRunnerId()));
        }

        if (runnerRace.getRaceId() == -1) {
            raceIdText.setText("tbd");
        } else {
            raceIdText.setText(Integer.toString(runnerRace.getRaceId()));
        }

        if (runnerRace.getShirtNumber() == -1) {
            shirtNumberTextField.setText("");
        } else {
            shirtNumberTextField.setText(Integer.toString(runnerRace.getShirtNumber()));
        }

        if (runnerRace.getTime() == -1) {
            timeTextField.setText("");
        } else {
            timeTextField.setText(Integer.toString(runnerRace.getTime()));
        }
    }

    private void runnerRaceUpdate() {
        runnerRace.setShirtNumber(Integer.parseInt(shirtNumberTextField.getText()));
        runnerRace.setTime(Integer.parseInt(timeTextField.getText()));
    }

    private boolean isNotChanged() {
        runnerRaceUpdate();
        return originalRunnerRace.equals(runnerRace);
    }

    private void databaseUpdate(ActionEvent event) {
        if (areMandatoryFieldsFilledIn()) {
            if (isNotChanged()) {
                closeOnNoChanges(event);
            } else {
                RunnerRaceJdbi runnerRaceJdbi = new RunnerRaceJdbi(ConnectionManager.CONNECTION_STRING);
                if (runnerRace.getRunnerId() == -1) {
                    runnerRaceJdbi.insert(runnerRace);
                } else {
                    runnerRaceJdbi.update(runnerRace);
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
            Integer.parseInt(shirtNumberTextField.getText());
        } catch (NumberFormatException exception) {
            shirtNumberTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }

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
        shirtNumberTextField.setBorder(Border.EMPTY);
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
