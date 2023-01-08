package be.uhasselt.databasesproject.controller.admin.edit;

import be.uhasselt.databasesproject.jdbi.ConnectionManager;
import be.uhasselt.databasesproject.jdbi.RaceJdbi;
import be.uhasselt.databasesproject.jdbi.SegmentJdbi;
import be.uhasselt.databasesproject.model.Race;
import be.uhasselt.databasesproject.model.Segment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.lang3.SerializationUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class EditRaceController {


    @FXML
    private Button cancelButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableColumn<Segment, Integer> distanceTableColumn;

    @FXML
    private TextField distanceTextField;

    @FXML
    private Button addSegmentButton;

    @FXML
    private Button editSegmentButton;

    @FXML
    private Text errorMessageText;

    @FXML
    private TableColumn<Segment, Integer> idTableColumn;

    @FXML
    private Text idText;

    @FXML
    private TableColumn<Segment, String> locationTableColumn;

    @FXML
    private TextField nameTextField;

    @FXML
    private Text originalDateDefaultText;

    @FXML
    private Text originalDateText;

    @FXML
    private TextField priceTextField;

    @FXML
    private TableColumn<Segment, Integer> raceIdTableColumn;

    @FXML
    private Button saveButton;

    @FXML
    private TableView<Segment> tableView;

    private Race race;
    private Race originalRace;
    private Boolean confirmation = false;
    private int originalAmountOfSegments;
    private Stage stage;

    @FXML
    void initialize() {
        datePicker.setEditable(false);
        saveButton.setOnAction(this::databaseUpdate);
        cancelButton.setOnAction(this::cancel);
        editSegmentButton.setOnAction(event -> editSegment(true));
        addSegmentButton.setOnAction(event -> editSegment(false));
        errorMessageText.setText("");
    }

    private void close(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        WindowEvent windowEvent = new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST);
        stage.getOnCloseRequest().handle(windowEvent);

        stage.close();
    }

    private void editSegment(boolean isEdit) {
        Segment segment;
        String title;

        if (isEdit) {
            if (!verifyRowSelected()) {
                return;
            }
            segment = getSelectedSegment();
            title = "edit segment";
        } else {
            segment = new Segment(-1, race.getId(), "", -1);
            title = "add segment";
        }

        String resourceName = "/fxml/admin/edit/editSegment.fxml";

        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(resourceName)));
            AnchorPane anchorPane = loader.load();
            setSegmentScreen(anchorPane, loader, segment, title);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find " + resourceName, e);
        }
    }

    private void setSegmentScreen(AnchorPane anchorPane, FXMLLoader loader, Segment segment, String title) {
        EditSegmentController editSegmentController = loader.getController();
        editSegmentController.setIsFromRace();
        editSegmentController.inflateUI(segment);

        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setTitle(title);
        stage.initOwner(this.stage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
        stage.setOnCloseRequest(event -> loadSegments());
    }

    private Segment getSelectedSegment() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    private boolean verifyRowSelected() {
        if (tableView.getSelectionModel().getSelectedCells().size() == 0) {
            showAlert("Warning", "Please select a row");
            return false;
        }
        return true;
    }

    private void setUpTableView() {
        initColumns();
        loadSegments();
    }

    private void initColumns() {
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        raceIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("raceId"));
        locationTableColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        distanceTableColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
    }

    private void loadSegments() {
        SegmentJdbi segmentJdbi = new SegmentJdbi(ConnectionManager.CONNECTION_STRING);
        List<Segment> segments = segmentJdbi.getAllByRaceId(race.getId());
        tableView.getItems().setAll(segments);
    }

    public void inflateUI(Race race) {
        this.race = race;
        originalRace = SerializationUtils.clone(race);

        setUpTableView();
        originalAmountOfSegments = tableView.getItems().size();


        if (race.getId() == -1) {
            idText.setText("tbd");
        } else {
            idText.setText(Integer.toString(race.getId()));
        }

        if (!Objects.equals(race.getDate(), "")) {
            originalDateText.setText(race.getDate());
        } else {
            originalDateDefaultText.setVisible(false);
            originalDateText.setVisible(false);
            originalDateText.setText("");
        }

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
        if (datePicker.getValue() == null) {
            if (originalDateText.getText().isBlank()) {
                race.setDate("");
            } else {
                race.setDate(originalDateText.getText());
            }
        } else {
            race.setDate(datePicker.getValue().toString());
        }

        race.setName(nameTextField.getText());

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
    }

    private boolean isNotChanged() {
        raceUpdate();
        return originalRace.equals(race) && originalAmountOfSegments == tableView.getItems().size();
    }

    private void databaseUpdate(ActionEvent event) {
        if (areMandatoryFieldsFilledIn()) {
            if (isNotChanged()) {
                closeOnNoChanges(event);
            } else {
                RaceJdbi raceJdbi = new RaceJdbi(ConnectionManager.CONNECTION_STRING);
                if (race.getId() == -1) {
                    raceJdbi.insert(race);
                    int raceId = raceJdbi.getIdLatestAddedRunner();
                    setRaceIdSegments(raceId);
                } else {
                    raceJdbi.update(race);
                }
                close(event);
            }
        } else {
            showAlert("Warning", "Please fill in the mandatory fields.");
        }
    }

    private void setRaceIdSegments(int raceId) {
        SegmentJdbi segmentJdbi = new SegmentJdbi(ConnectionManager.CONNECTION_STRING);
        for (Segment s : tableView.getItems()) {
            segmentJdbi.updateRaceId(s, raceId);
        }
    }

    private boolean areMandatoryFieldsFilledIn() {
        boolean status = true;
        String color = "red";

        resetTextFieldBorder();

        if (datePicker.getValue() == null) {
            if (originalDateText.getText().isBlank()) {
                race.setDate("");
            } else {
                race.setDate(originalDateText.getText());
            }
        } else {
            race.setDate(datePicker.getValue().toString());
        }

        if (nameTextField.getText().isBlank()) {
            nameTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }
        if (distanceTextField.getText().isBlank()) {
            distanceTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }
        if (priceTextField.getText().isBlank()) {
            priceTextField.setBorder(Border.stroke(Paint.valueOf(color)));
            status = false;
        }

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
                if (race.getId() == -1) {
                    SegmentJdbi segmentJdbi = new SegmentJdbi(ConnectionManager.CONNECTION_STRING);
                    segmentJdbi.deleteByRaceId(race.getId());
                }
                close(event);
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void resetTextFieldBorder() {
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
