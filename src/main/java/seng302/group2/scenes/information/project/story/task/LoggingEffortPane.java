package seng302.group2.scenes.information.project.story.task;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.controlsfx.control.PopOver;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomDatePicker;
import seng302.group2.scenes.control.CustomTextField;
import seng302.group2.scenes.control.TimeTextField;
import seng302.group2.scenes.control.search.SearchableTable;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.validation.ValidationStyle;
import seng302.group2.util.conversion.DurationConverter;
import seng302.group2.util.validation.DateValidator;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.story.tasks.Log;
import seng302.group2.workspace.project.story.tasks.Task;
import seng302.group2.workspace.team.Team;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;
import seng302.group2.App;

/**
 * Created by jml168 on 25/08/15.
 */
public class LoggingEffortPane extends Pane {

    PopOver popOver = null;
    Boolean correctEffortLeft = Boolean.FALSE;
    Boolean correctDuration = Boolean.FALSE;
    Boolean loggerSelected = Boolean.FALSE;
    //TODO Make this a Searchable Pane


    public LoggingEffortPane(Task task) {
        try {
            String css = this.getClass().getResource("/styles/tableHeader.css").toExternalForm();
            this.getStylesheets().add(css);
        }
        catch (Exception ex) {
            System.err.println("Cannot acquire stylesheet: " + ex.toString());
        }
        construct(task);
    }


    public LoggingEffortPane(Task task, PopOver popOver) {
        this.popOver = popOver;
        try {
            String css = this.getClass().getResource("/styles/tableHeader.css").toExternalForm();
            this.getStylesheets().add(css);
        }
        catch (Exception ex) {
            System.err.println("Cannot acquire stylesheet: " + ex.toString());
        }
        construct(task);
    }


    void construct(Task task) {
        VBox content = new VBox(8);
        content.setPadding(new Insets(8));

        SearchableTable<Log> logTable = new SearchableTable<>();
        logTable.setEditable(false);
        logTable.setPrefWidth(400);
        logTable.setPrefHeight(200);
        logTable.setPlaceholder(new SearchableText("There are currently no logs in this task."));
        logTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Log> data = task.getLogs();

        TableColumn loggerCol = new TableColumn("Logger");
        loggerCol.setCellValueFactory(new PropertyValueFactory<Log, Person>("logger"));
        loggerCol.prefWidthProperty().bind(logTable.widthProperty()
                .subtract(2).divide(100).multiply(60));


        TableColumn startTimeCol = new TableColumn("Date");
        startTimeCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Log, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Log,
                            String> log) {
                        SimpleStringProperty property = new SimpleStringProperty();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                        property.setValue(log.getValue().getStartDate().format(formatter));
                        return property;
                    }
                });
        startTimeCol.prefWidthProperty().bind(logTable.widthProperty()
                .subtract(2).divide(100).multiply(60));
        startTimeCol.setSortType(TableColumn.SortType.ASCENDING);


        TableColumn descriptionCol = new TableColumn("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Log, String>("description"));
        descriptionCol.prefWidthProperty().bind(logTable.widthProperty()
                .subtract(2).divide(100).multiply(60));


        TableColumn durationCol = new TableColumn("Duration");
        durationCol.setCellValueFactory(new PropertyValueFactory<Log, String>("durationString"));
        durationCol.prefWidthProperty().bind(logTable.widthProperty()
                .subtract(2).divide(100).multiply(60));


        logTable.setItems(data);
        TableColumn[] columns = {loggerCol, startTimeCol, durationCol, descriptionCol};
        logTable.getColumns().setAll(columns);
        logTable.getSortOrder().add(startTimeCol);


        // Listener to disable columns being movable
        /*taskTable.getColumns().addListener(new ListChangeListener() {
            public boolean suspended;

            @Override
            public void onChanged(ListChangeListener.Change change) {
                change.next();
                if (change.wasReplaced() && !suspended) {
                    this.suspended = true;
                    taskTable.getColumns().setAll(columns);
                    this.suspended = false;
                }
            }
        });*/



        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.BOTTOM_RIGHT);
        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        buttons.getChildren().addAll(addButton, deleteButton);

        HBox newLogFields = new HBox(10);
        VBox newLogFieldFirstRow = new VBox(10);
        final CustomComboBox<Person> personComboBox = new CustomComboBox<>("Logger:", true);

        addButton.setDisable(true);

        personComboBox.getComboBox().valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null) {
                    loggerSelected = false;
                    ValidationStyle.borderGlowRed(personComboBox.getComboBox());
                    ValidationStyle.showMessage("Logger required", personComboBox.getComboBox());
                }
                else {
                    ValidationStyle.borderGlowNone(personComboBox.getComboBox());
                    loggerSelected = true;
                }
                addButton.setDisable(!(loggerSelected && correctDuration && correctEffortLeft));
            });


        CustomDatePicker startDatePicker = new CustomDatePicker("Date:", true);

        final Callback<DatePicker, DateCell> endDateCellFactory =
            new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item.isBefore(task.getStory().getSprint().getStartDate())) {
                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");
                            }
                            else {
                                long p = ChronoUnit.DAYS.between(LocalDate.now(), item);
                                setTooltip(new Tooltip(
                                                "Sprint duration: " + p + " days.")
                                );
                            }

                        }
                    };
                }
            };
        startDatePicker.getDatePicker().setDayCellFactory(endDateCellFactory);
        startDatePicker.getDatePicker().setValue(LocalDate.now());

        HBox startTimeHBox = new HBox(10);
        SearchableText startTimeLabel = new SearchableText("Time:");
        startTimeLabel.setPrefWidth(150);
        TimeTextField timeTextField = new TimeTextField();
        HBox.setHgrow(timeTextField, Priority.ALWAYS);
        startTimeHBox.getChildren().addAll(startTimeLabel, timeTextField);

        CustomTextField durationTextField = new CustomTextField("Duration:");
        durationTextField.getTextField().setPromptText("eg. 1h 20min");
        durationTextField.getTextField().setPrefWidth(175);
        
        CustomTextField effortLeftField = new CustomTextField("Task Effort Left:");
        effortLeftField.getTextField().setText(task.getEffortLeftString());
        effortLeftField.getTextField().setPrefWidth(175);

        personComboBox.prefWidthProperty().bind(logTable.widthProperty()
                .subtract(3).divide(100).multiply(30));
        startDatePicker.prefWidthProperty().bind(logTable.widthProperty()
                .subtract(3).divide(100).multiply(30));
        newLogFieldFirstRow.getChildren().addAll(personComboBox,
                startDatePicker, startTimeHBox, durationTextField, effortLeftField);

        startDatePicker.getDatePicker().setMinWidth(175);
        timeTextField.setPrefWidth(175);

        VBox newLogFieldSecondRow = new VBox(10);
        SearchableText descriptionLabel = new SearchableText("Description");
        TextArea descriptionTextArea = new TextArea();
        descriptionTextArea.setPrefSize(300, 100);


        
        durationTextField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctDuration = DateValidator.validDuration(newValue) && !newValue.isEmpty();
                correctEffortLeft = DateValidator.validDuration(effortLeftField.getText()) 
                        && !effortLeftField.getText().isEmpty();
                if (correctDuration) {
                    ValidationStyle.borderGlowNone(durationTextField.getTextField());
                    double effortLeft = task.getEffortLeft()
                            - DurationConverter.readDurationToMinutes(durationTextField.getText());
                    String effortLeftString = (int) Math.floor(effortLeft / 60) + "h " 
                            + (int) Math.floor(effortLeft % 60) + "min";
                    if (effortLeft <= 0) {
                        effortLeftString = "0h 00min";
                    }
                    
                    effortLeftField.setText(effortLeftString);
                }
                else {
                    effortLeftField.setText(task.getEffortLeftString());
                    if (newValue.isEmpty()) {
                        ValidationStyle.borderGlowRed(durationTextField.getTextField());
                        ValidationStyle.showMessage("This field must be filled", durationTextField.getTextField());
                    }
                    else {
                        ValidationStyle.borderGlowRed(durationTextField.getTextField());
                        ValidationStyle.showMessage("Please input in valid format", durationTextField.getTextField());
                    }
                }
                addButton.setDisable(!(loggerSelected && correctDuration && correctEffortLeft));
            });
        
        effortLeftField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctEffortLeft = DateValidator.validDuration(newValue) && !newValue.isEmpty();
                if (correctEffortLeft) {
                    ValidationStyle.borderGlowNone(effortLeftField.getTextField());
                }
                else {
                    if (newValue.isEmpty()) {
                        ValidationStyle.borderGlowRed(effortLeftField.getTextField());
                        ValidationStyle.showMessage("This field must be filled", effortLeftField.getTextField());
                    }
                    else {
                        ValidationStyle.borderGlowRed(effortLeftField.getTextField());
                        ValidationStyle.showMessage("Please input in valid format", effortLeftField.getTextField());
                    }
                }
                //addButton.setDisable(!(loggerSelected && correctDuration && correctEffortLeft));
            });


        newLogFieldSecondRow.getChildren().addAll(descriptionLabel, descriptionTextArea);

        newLogFields.getChildren().addAll(newLogFieldFirstRow, newLogFieldSecondRow);

/*        durationTextField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
            if (Log.readDurationToMinutes(newValue) == null) {
                durationTextField.getTextField().setTooltip(new Tooltip("Wrong format"));
            }
            else {
                durationTextField.getTextField().setTooltip(null);
            }
        });*/


        personComboBox.getComboBox().getItems().clear();
        try {
            Set<Team> teams = task.getStory().getBacklog().getProject().getCurrentTeams();
            if (teams.isEmpty()) {
                personComboBox.setDisable(true);
            }
            else {
                personComboBox.setDisable(false);
                for (Team team : teams) {
                    for (Person person : team.getPeople()) {
                        personComboBox.getComboBox().getItems().add(person);
                    }
                }
            }
        }
        catch (NullPointerException ex) {
            // Somewhere getting to teams hit null
            personComboBox.setDisable(true);
        }

        addButton.setOnAction((event) -> {
                ValidationStyle.borderGlowNone(startDatePicker.getDatePicker());
                if (personComboBox.getValue() != null && startDatePicker.getValue() != null) {
                    LocalDate startDate = startDatePicker.getValue();
                    Person selectedPerson = personComboBox.getValue();
                    double duration = DurationConverter.readDurationToMinutes(durationTextField.getText());
                    double effortLeft = 0;
                    if (DurationConverter.readDurationToMinutes(effortLeftField.getText()) != 0) {
                        effortLeft = DurationConverter.readDurationToMinutes(effortLeftField.getText());
                    }

                    LocalDateTime dateTime = startDate.atTime(timeTextField.getHours(), timeTextField.getMinutes());
                    Log newLog = new Log(task, descriptionTextArea.getText(),
                            selectedPerson, duration, dateTime, effortLeft);
                    task.add(newLog, effortLeft);

                    String effortLeftString = "";
                    if (task.getEffortLeft() == 0
                            || task.getEffortLeft()
                            - DurationConverter.readDurationToMinutes(durationTextField.getText()) <= 0) {
                        effortLeftString = "0h 0min";
                    }
                    else {
                        double newEffortLeft = task.getEffortLeft()
                                - DurationConverter.readDurationToMinutes(durationTextField.getText());
                        effortLeftString = (int) Math.floor(newEffortLeft / 60) + "h "
                                + (int) Math.floor(newEffortLeft % 60) + "min";
                    }

                    effortLeftField.getTextField().setText(effortLeftString);
                    //TODO bug with refreshing
                    App.refreshMainScene();


                }
                else {
                    event.consume();
                }
            });

        deleteButton.setOnAction((event) -> {
                Log selectedLog = logTable.getSelectionModel().getSelectedItem();
                if (selectedLog != null) {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete");
                    alert.setHeaderText("Delete Log?");
                    alert.setContentText("Do you really want to delete this log?");

                    ButtonType buttonTypeYes = new ButtonType("Yes");
                    ButtonType buttonTypeNo = new ButtonType("No");

                    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                    Optional<ButtonType> result  = alert.showAndWait();

                    if (result.get() == buttonTypeYes) {
                        logTable.getSelectionModel().getSelectedItem().deleteLog();
                        App.refreshMainScene();
                    }
                    else if (result.get() == buttonTypeNo) {
                        event.consume();
                    }
                }
            });

        content.getChildren().add(logTable);
        content.getChildren().add(newLogFields);
        content.getChildren().add(buttons);






        this.getChildren().add(content);
    }
}
