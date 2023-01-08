package be.uhasselt.databasesproject.controller.admin.edit;

import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RaceJdbi;
import be.uhasselt.databasesproject.jdbi.RunnerJdbi;
import be.uhasselt.databasesproject.jdbi.RunnerRaceJdbi;
import be.uhasselt.databasesproject.model.Race;
import be.uhasselt.databasesproject.model.Runner;
import be.uhasselt.databasesproject.model.RunnerRace;
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

public class EditRunnerRaceController {

    @FXML
    private Button cancelButton;

    @FXML
    private Text errorMessageText;

    @FXML
    private ChoiceBox<Race> raceChoiceBox;

    @FXML
    private Text raceIdText;

    @FXML
    private ChoiceBox<Runner> runnerChoiceBox;

    @FXML
    private Text runnerIdText;

    @FXML
    private Button saveButton;

    @FXML
    private Text shirtNumberText;

    @FXML
    private TextField timeTextField;

    private RunnerRace runnerRace;
    private RunnerRace originalRunnerRace;
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

    public void inflateUI(RunnerRace runnerRace) {
        if (isEdit) {
            runnerChoiceBox.setVisible(false);
            raceChoiceBox.setVisible(false);
            shirtNumberText.setText(Integer.toString(runnerRace.getShirtNumber()));
        } else {
            shirtNumberText.setText("automagic");
        }

        this.runnerRace = runnerRace;
        originalRunnerRace = SerializationUtils.clone(runnerRace);

        if (runnerRace.getRunnerId() == -1) {
            setRaceChoiceBox();
        } else {
            runnerIdText.setText(Integer.toString(runnerRace.getRunnerId()));
        }

        if (runnerRace.getRaceId() == -1) {
            setRunnerChoiceBox();
        } else {
            raceIdText.setText(Integer.toString(runnerRace.getRaceId()));
        }

        if (runnerRace.getTime() == -1) {
            timeTextField.setText("");
        } else {
            timeTextField.setText(Integer.toString(runnerRace.getTime()));
        }
    }

    private void setRunnerChoiceBox() {
        RunnerJdbi runnerJdbi = new RunnerJdbi(ConnectionManager.CONNECTION_STRING);
        List<Runner> runners = runnerJdbi.getAll();
        ObservableList<Runner> observableRunners = FXCollections.observableList(runners);
        runnerChoiceBox.setItems(observableRunners);
    }

    private void setRaceChoiceBox() {
        RaceJdbi raceJdbi = new RaceJdbi(ConnectionManager.CONNECTION_STRING);
        List<Race> races = raceJdbi.getAll();
        ObservableList<Race> observableRaces = FXCollections.observableList(races);
        raceChoiceBox.setItems(observableRaces);
    }

    private void runnerRaceUpdate() {
        if (!isEdit) {
            if (raceChoiceBox.getValue() == null) {
                runnerRace.setRaceId(-1);
            } else {
                runnerRace.setRaceId(raceChoiceBox.getValue().getId());
            }
            if (runnerChoiceBox.getValue() == null) {
                runnerRace.setRunnerId(-1);
            } else {
                runnerRace.setRunnerId(runnerChoiceBox.getValue().getId());
            }
        }

        try {
            runnerRace.setTime(Integer.parseInt(timeTextField.getText()));
        } catch (NumberFormatException e) {
            runnerRace.setTime(-1);
        }
    }

    private boolean isRaceSelected() {
        return raceChoiceBox.getValue() != null;
    }

    private boolean isRunnerSelected() {
        return runnerChoiceBox.getValue() != null;
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
                if (!isEdit) {
                    int shirtNumber = runnerRaceJdbi.getNextShirtNumber(runnerRace.getRaceId());
                    runnerRace.setShirtNumber(shirtNumber);
                    try {
                        runnerRaceJdbi.insert(runnerRace);
                    } catch (Exception e) {
                        raceChoiceBox.setBorder(Border.stroke(Paint.valueOf("red")));
                        runnerChoiceBox.setBorder(Border.stroke(Paint.valueOf("red")));
                        errorMessageText.setText("runner and race combo exists");
                        return;
                    }
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
            Integer.parseInt(timeTextField.getText());
        } catch (NumberFormatException exception) {
            timeTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }

        if (!isEdit) {
            if (!isRaceSelected()) {
                raceChoiceBox.setBorder(Border.stroke(Paint.valueOf(color)));
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
        raceChoiceBox.setBorder(Border.EMPTY);
        runnerChoiceBox.setBorder(Border.EMPTY);
    }

    private void closeOnNoChanges(ActionEvent event) {
        showAlertWithConfirmation("Waring", "No changes were made!");
        if (confirmation) {
            close(event);
        }
    }

    public void setAdd() {
        isEdit = false;
    }

    public void setEdit() {
        isEdit = true;
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
