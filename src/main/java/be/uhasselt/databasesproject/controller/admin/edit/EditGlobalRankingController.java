package be.uhasselt.databasesproject.controller.admin.edit;

import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.GlobalRankingJdbi;
import be.uhasselt.databasesproject.model.GlobalRanking;
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

public class EditGlobalRankingController {

    @FXML
    private Text runnerIdText;

    @FXML
    private Button cancelButton;

    @FXML
    private Text errorMessageText;

    @FXML
    private TextField prizeMoneyTextField;

    @FXML
    private TextField totalTimeTextField;

    @FXML
    private Button saveButton;

    private GlobalRanking globalRanking;
    private GlobalRanking originalGlobalRanking;
    private Boolean confirmation = false;
    private Boolean isAdmin;

    @FXML
    void initialize() {
        saveButton.setOnAction(this::databaseUpdate);
        cancelButton.setOnAction(this::cancel);
        errorMessageText.setText("");
    }

    private void close(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        if (isAdmin) {
            WindowEvent windowEvent = new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST);
            stage.getOnCloseRequest().handle(windowEvent);
        }

        stage.close();
    }

    public void inflateUI(GlobalRanking globalRanking) {
        this.globalRanking = globalRanking;
        originalGlobalRanking = SerializationUtils.clone(globalRanking);
        if (globalRanking.getRunnerId() == -1) {
            runnerIdText.setText("tbd");
        } else {
            runnerIdText.setText(Integer.toString(globalRanking.getRunnerId()));
        }

        if (globalRanking.getPrizeMoney() == -1) {
            prizeMoneyTextField.setText("");
        } else {
            prizeMoneyTextField.setText(Double.toString(globalRanking.getPrizeMoney()));
        }

        if (globalRanking.getTotalTime() == -1) {
            totalTimeTextField.setText("");
        } else {
            totalTimeTextField.setText(Integer.toString(globalRanking.getTotalTime()));
        }
    }

    private void GlobalRankingUpdate() {
        globalRanking.setPrizeMoney(Double.parseDouble(prizeMoneyTextField.getText()));
        globalRanking.setTotalTime(Integer.parseInt(totalTimeTextField.getText()));
    }

    private boolean isNotChanged() {
        GlobalRankingUpdate();
        return originalGlobalRanking.equals(globalRanking);
    }

    private void databaseUpdate(ActionEvent event) {
        if (areMandatoryFieldsFilledIn()) {
            if (isNotChanged()) {
                closeOnNoChanges(event);
            } else {
                GlobalRankingJdbi globalRankingJdbi = new GlobalRankingJdbi(ConnectionManager.CONNECTION_STRING);
                if (globalRanking.getRunnerId() == -1) {
                    globalRankingJdbi.insert(globalRanking);
                } else {
                    globalRankingJdbi.update(globalRanking);
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
            Double.parseDouble(prizeMoneyTextField.getText());
        } catch (NumberFormatException exception) {
            prizeMoneyTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }

        try {
            Integer.parseInt(totalTimeTextField.getText());
        } catch (NumberFormatException exception) {
            totalTimeTextField.setBorder(Border.stroke(Paint.valueOf(color)));
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
        prizeMoneyTextField.setBorder(Border.EMPTY);
        totalTimeTextField.setBorder(Border.EMPTY);
    }

    private void closeOnNoChanges(ActionEvent event) {
        showAlertWithConfirmation("Waring", "No changes were made!");
        if (confirmation) {
            close(event);
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
