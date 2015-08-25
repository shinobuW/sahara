package seng302.group2.scenes.information.project.story.task;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
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
import seng302.group2.App;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomDatePicker;
import seng302.group2.scenes.control.CustomTextField;
import seng302.group2.scenes.control.TimeTextField;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.story.tasks.Log;
import seng302.group2.workspace.project.story.tasks.Task;
import seng302.group2.workspace.team.Team;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;

/**
 * Created by jml168 on 25/08/15.
 */
public class LoggingEffortPane extends Pane {

    PopOver popOver = null;

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





        TableView<Log> taskTable = new TableView<>();
        taskTable.setEditable(false);
        taskTable.setPrefWidth(400);
        taskTable.setPrefHeight(200);
        taskTable.setPlaceholder(new SearchableText("There are currently no logs in this task."));
        taskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Log> data = task.getLogs();

        TableColumn loggerCol = new TableColumn("Logger");
        loggerCol.setCellValueFactory(new PropertyValueFactory<Log, Person>("logger"));
        loggerCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));


        TableColumn startTimeCol = new TableColumn("Date");
        startTimeCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Log, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Log,
                            String> log) {
                        SimpleStringProperty property = new SimpleStringProperty();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        property.setValue(log.getValue().getStartDate().format(formatter));
                        return property;
                    }
                });
        startTimeCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));
        startTimeCol.setSortType(TableColumn.SortType.ASCENDING);


        TableColumn descriptionCol = new TableColumn("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Log, String>("description"));
        descriptionCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));


        TableColumn durationCol = new TableColumn("Duration");
        durationCol.setCellValueFactory(new PropertyValueFactory<Log, String>("durationString"));
        durationCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));


        taskTable.setItems(data);
        TableColumn[] columns = {loggerCol, startTimeCol, durationCol, descriptionCol};
        taskTable.getColumns().setAll(columns);
        taskTable.getSortOrder().add(startTimeCol);


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
        final CustomComboBox<Person> personComboBox = new CustomComboBox<>("Logger:");

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

        HBox startTimeHBox = new HBox(10);
        SearchableText startTimeLabel = new SearchableText("Time:");
        startTimeLabel.setPrefWidth(150);
        TimeTextField timeTextField = new TimeTextField();
        HBox.setHgrow(timeTextField, Priority.ALWAYS);
        startTimeHBox.getChildren().addAll(startTimeLabel, timeTextField);

        CustomTextField durationTextField = new CustomTextField("Duration:");
        durationTextField.getTextField().setPromptText("eg. 1h 20min");
        durationTextField.getTextField().setPrefWidth(175);

        personComboBox.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(3).divide(100).multiply(30));
        startDatePicker.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(3).divide(100).multiply(30));
        newLogFieldFirstRow.getChildren().addAll(personComboBox,
                startDatePicker, startTimeHBox, durationTextField);

        startDatePicker.getDatePicker().setMinWidth(175);
        timeTextField.setPrefWidth(175);

        VBox newLogFieldSecondRow = new VBox(10);
        SearchableText descriptionLabel = new SearchableText("Description");
        TextArea descriptionTextArea = new TextArea();
        descriptionTextArea.setPrefSize(300, 100);


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
                startDatePicker.hideErrorField();
                if (personComboBox.getValue() != null && startDatePicker.getValue() != null) {
                    LocalDate startDate = startDatePicker.getValue();
                    Person selectedPerson = personComboBox.getValue();
                    float minutes = timeTextField.getHours() * 60 + timeTextField.getMinutes();

                    Log newLog = new Log(task, descriptionTextArea.getText(),
                            selectedPerson, minutes, startDate);
                    task.add(newLog);
//                    if (popOver != null) {
//                        popOver.hide();
//                    }
                }
                else {
                    if (personComboBox.getValue() == null) {
                        //personComboBox.showErrorField("Please select a team");
                        event.consume();
                    }
                    if (startDatePicker.getValue() == null) {
                        startDatePicker.showErrorField("Please select a date");
                        event.consume();
                    }
                }
            });

        deleteButton.setOnAction((event) -> {
                Log selectedLog = taskTable.getSelectionModel().getSelectedItem();
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
                        //TODO: selectedLog.delete();
                    }
                    else if (result.get() == buttonTypeNo) {
                        event.consume();
                    }
                }
            });

        content.getChildren().add(taskTable);
        content.getChildren().add(newLogFields);
        content.getChildren().add(buttons);






        this.getChildren().add(content);
    }
}
