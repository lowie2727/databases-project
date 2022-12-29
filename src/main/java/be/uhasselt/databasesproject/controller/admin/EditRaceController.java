package be.uhasselt.databasesproject.controller.admin;

import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RaceJdbi;
import be.uhasselt.databasesproject.model.Race;
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

import java.util.Objects;

public class EditRaceController {

    @FXML
    private TextField distanceTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private Text errorMessageText;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField dateTextField;

    @FXML
    private Text idText;

    @FXML
    private Button saveButton;

    @FXML
    private TextField priceTextField;

    private Race race;
    private Race originalRace;
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

    public void inflateUI(Race race) {
        this.race = race;
        originalRace = SerializationUtils.clone(race);
        if (race.getId() == -1) {
            idText.setText("tbd");
        } else {
            idText.setText(Integer.toString(race.getId()));
        }

        dateTextField.setText(race.getDate());
        nameTextField.setText(race.getName());

        if (race.getDistance() == -1) {
            distanceTextField.setText("");
        } else {
            distanceTextField.setText(Integer.toString(race.getDistance()));
        }

        if (race.getPrice() == -1.0) {
            priceTextField.setText("");
        } else {
            priceTextField.setText(Double.toString(race.getPrice()));
        }
    }

    private void raceUpdate() {
        race.setDate(dateTextField.getText());
        race.setName(nameTextField.getText());

        if (Objects.equals(idText.getText(), "tbd")) {
            try {
                race.setDistance(Integer.parseInt(distanceTextField.getText()));
            } catch (NumberFormatException exception) {
                race.setDistance(-1);
            }
            try {
                race.setPrice(Double.parseDouble(priceTextField.getText()));
            } catch (NumberFormatException exception) {
                race.setPrice(-1.0);
            }
        } else {
            race.setDistance(Integer.parseInt(distanceTextField.getText()));
            race.setPrice(Double.parseDouble(priceTextField.getText()));
        }
    }

    private boolean isNotChanged() {
        raceUpdate();
        return originalRace.equals(race);
    }

    private void databaseUpdate(ActionEvent event) {
        if (areMandatoryFieldsFilledIn()) {
            if (isNotChanged()) {
                closeOnNoChanges(event);
            } else {
                RaceJdbi raceJdbi = new RaceJdbi(ConnectionManager.CONNECTION_STRING);
                if (race.getId() == -1) {
                    raceJdbi.insert(race);
                } else {
                    raceJdbi.update(race);
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
        if (dateTextField.getText().isBlank() || nameTextField.getText().isBlank() || distanceTextField.getText().isBlank() || priceTextField.getText().isBlank()) {
            errorMessageText.setText("Please fill in all mandatory fields.");
            showMandatoryFields();
            status = false;
        }

        showMandatoryFields();
        try {
            Integer.parseInt(distanceTextField.getText());
        } catch (NumberFormatException exception) {
            distanceTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }

        try {
            Double.parseDouble(priceTextField.getText());
        } catch (NumberFormatException exception) {
            priceTextField.setBorder(Border.stroke(Paint.valueOf(color)));
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

    private void showMandatoryFields() {
        String color = "red";
        resetTextFieldBorder();
        if (dateTextField.getText().isBlank()) {
            dateTextField.setBorder(Border.stroke(Paint.valueOf(color)));
        }
        if (nameTextField.getText().isBlank()) {
            nameTextField.setBorder(Border.stroke(Paint.valueOf(color)));
        }
        if (distanceTextField.getText().isBlank()) {
            distanceTextField.setBorder(Border.stroke(Paint.valueOf(color)));
        }
        if (priceTextField.getText().isBlank()) {
            priceTextField.setBorder(Border.stroke(Paint.valueOf(color)));
        }
    }

    private void resetTextFieldBorder() {
        dateTextField.setBorder(Border.EMPTY);
        nameTextField.setBorder(Border.EMPTY);
        distanceTextField.setBorder(Border.EMPTY);
        priceTextField.setBorder(Border.EMPTY);
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
