package seng302.group2.scenes.information.project.story.task;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.controlsfx.control.PopOver;
import seng302.group2.Global;
import seng302.group2.scenes.control.*;
import seng302.group2.scenes.control.search.SearchableTable;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.validation.ValidationStyle;
import seng302.group2.util.conversion.DurationConverter;
import seng302.group2.util.validation.DateValidator;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.story.tasks.Log;
import seng302.group2.workspace.project.story.tasks.Task;
import seng302.group2.workspace.tag.Tag;
import seng302.group2.workspace.team.Team;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Pane for logging on a given Task.
 * Created by jml168 on 25/08/15.
 */
public class LoggingEffortPane extends Pane {

    private PopOver popOver = null;
    private Boolean correctEffortLeft = Boolean.FALSE;
    private Boolean correctDuration = Boolean.FALSE;
    private Boolean loggerSelected = Boolean.FALSE;
    private TableView table = null;
    private Person nullPerson = new Person("", "", "", "", "", null);
    private ObservableList<Person> availablePeople = FXCollections.observableArrayList();


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

    public LoggingEffortPane(Task task, PopOver popOver, TableView table) {
        this.popOver = popOver;
        this.table = table;
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
        logTable.setEditable(true);
        logTable.setPrefWidth(400);
        logTable.setPrefHeight(200);
        logTable.setPlaceholder(new SearchableText("There are currently no logs in this task."));
        logTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Log> data = task.getLogsWithoutGhostLogs();

        TableColumn loggerCol = new TableColumn("Logger");
        loggerCol.setCellValueFactory(new PropertyValueFactory<Log, Person>("logger"));
        loggerCol.setEditable(true);
        loggerCol.prefWidthProperty().bind(logTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        ObservableList<Person> availableLoggers = observableArrayList();

        Set<Team> availableTeams = ((task.getStory().getBacklog() == null)
                ? new HashSet<Team>() :
                task.getStory().getBacklog().getProject().getCurrentTeams());
        for (Team team : availableTeams) {
            availablePeople.addAll(team.getPeople());
        }




        loggerCol.setCellFactory(ComboBoxTableCell.forTableColumn(
                availableLoggers
        ));

//        ComboBoxTableCell comboBoxEditCell = new ComboBoxTableCell<>(availableLoggers);
//        comboBoxEditCell.focusedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//                System.out.println("hello");
//            }
//        });
//        loggerCol.setCellFactory(comboBoxEditCell.);

//        System.out.println(loggerCol.getCellFactory());

//        ComboBoxTableCell loggerEditCell = new ComboBoxTableCell(availableLoggers);
//        loggerCol.setCellFactory(loggerEditCell);

        loggerCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Log, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Log,
                            String> log) {
                        SimpleStringProperty property = new SimpleStringProperty();
                        property.setValue(log.getValue().getLogger().toString());
                        return property;
                    }
                });


        loggerCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Log, Person>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Log,
                            Person> event) {
                        Log currentLog = event.getTableView().getItems().get(
                                event.getTablePosition().getRow());
                        ArrayList<Tag> tags = new ArrayList<>();
                        currentLog.edit(event.getNewValue(), currentLog.getPartner(), currentLog.getStartDate(),
                                currentLog.getDurationInMinutes(), currentLog.getDescription(),
                                currentLog.getEffortLeftDifferenceInMinutes(), tags);
                    }
                }
        );

        ObservableList<Person> availablePartners = observableArrayList();

        logTable.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
                Boolean isPartnerList = true;
                updateObservablePeopleList(availablePartners, newSelection.getLogger(), isPartnerList);
                updateObservablePeopleList(availableLoggers, newSelection.getLogger(), !isPartnerList);
            });

        TableColumn partnerCol = new TableColumn("Partner");
        partnerCol.setCellValueFactory(new PropertyValueFactory<Log, Person>("partner"));
        partnerCol.setEditable(true);

        partnerCol.setCellFactory(ComboBoxTableCell.forTableColumn(
                availablePartners
        ));

        partnerCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Log, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Log,
                            String> log) {
                        SimpleStringProperty property = new SimpleStringProperty();
                        if (log.getValue().getPartner() != null) {
                            property.setValue(log.getValue().getPartner().toString());
                        }
                        return property;
                    }
                });


        partnerCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Log, Person>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Log,
                            Person> event) {
                        Log currentLog = event.getTableView().getItems().get(
                                event.getTablePosition().getRow());
                        ArrayList<Tag> tags = new ArrayList<>();
                        currentLog.editPartner(event.getNewValue());
                    }
                }
        );

        partnerCol.prefWidthProperty().bind(logTable.widthProperty()
                .subtract(2).divide(100).multiply(60));


        logTable.setOnMouseClicked(event -> {
            System.out.println("hello");
            updateObservablePeopleList(availableLoggers, logTable.getSelectionModel().getSelectedItem().getPartner(),
                    false);
            updateObservablePeopleList(availablePartners, logTable.getSelectionModel().getSelectedItem().getLogger(),
                    true);

        });

        Callback<TableColumn, TableCell> cellFactory = col -> new DatePickerEditCell();
        Callback<TableColumn, TableCell> startTimeCellFactory = col -> new TimeTextFieldEditCell();

        TableColumn startDateTimeCol = new TableColumn("Start Time");
        TableColumn startDateCol = new TableColumn("Date");
        startDateCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Log, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Log,
                            String> log) {
                        SimpleStringProperty property = new SimpleStringProperty();
                        property.setValue(log.getValue().getStartDate().format(Global.dateFormatter));
                        return property;
                    }
                });

        startDateCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Log, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Log, String> event) {
                        if (!event.getNewValue().isEmpty()) {
                            Log currentLog = event.getTableView().getItems()
                                    .get(event.getTablePosition().getRow());

                            int hour = currentLog.getStartDate().getHour();
                            int min = currentLog.getStartDate().getMinute();

                            LocalDate startDate = LocalDate.parse(event.getNewValue(),
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                            Month month = startDate.getMonth();
                            int day = startDate.getDayOfMonth();
                            int year = startDate.getYear();

                            LocalDateTime newStartDateTime = LocalDateTime.of(year, month, day, hour, min);
                            currentLog.editStartTime(newStartDateTime);
                        }
                    }
                });
        startDateCol.setCellFactory(cellFactory);
        startDateCol.prefWidthProperty().bind(logTable.widthProperty()
                .subtract(2).divide(100).multiply(60));
        startDateCol.setSortType(TableColumn.SortType.ASCENDING);

        TableColumn startTimeCol = new TableColumn("Time");
        startTimeCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Log, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Log,
                            String> log) {
                        SimpleStringProperty property = new SimpleStringProperty();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                        property.setValue(log.getValue().getStartDate().format(formatter));
                        return property;
                    }
                });

        startTimeCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Log, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Log, String> event) {
                        if (!event.getNewValue().isEmpty()) {
                            Log currentLog = event.getTableView().getItems()
                                    .get(event.getTablePosition().getRow());

                            int year = currentLog.getStartDate().getYear();
                            int month = currentLog.getStartDate().getMonthValue();
                            int day = currentLog.getStartDate().getDayOfMonth();
                            int hours = Integer.parseInt(event.getNewValue().substring(0, 2));
                            int minutes = Integer.parseInt(event.getNewValue().substring(3,5));

                            LocalDateTime newDateTime = LocalDateTime.of(year, month, day, hours, minutes);
                            currentLog.editStartTime(newDateTime);
                        }
                    }
                });

        startTimeCol.setCellFactory(startTimeCellFactory);

        startDateTimeCol.getColumns().addAll(startDateCol, startTimeCol);

        TableColumn descriptionCol = new TableColumn("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Log, String>("description"));
        descriptionCol.prefWidthProperty().bind(logTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Log, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Log, String> event) {
                        Log selectedLog = event.getTableView().getItems().get(
                                event.getTablePosition().getRow());
                        if (!event.getNewValue().isEmpty() && event.getNewValue() != null) {
                            ArrayList<Tag> tags = new ArrayList<>();
                        selectedLog.editDescription(event.getNewValue());
                        }
                    }
                }
        );


        TableColumn durationCol = new TableColumn("Duration");
        durationCol.setCellValueFactory(new PropertyValueFactory<Log, String>("durationString"));
        durationCol.setCellFactory(TextFieldTableCell.forTableColumn());
        durationCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Log, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Log, String> event) {
                        Log selectedLog = event.getTableView().getItems().get(
                                event.getTablePosition().getRow());
                        if (!event.getNewValue().isEmpty() && event.getNewValue() != null) {
                            ArrayList<Tag> tags = new ArrayList<>();
                        }
                        if (DateValidator.validDuration(event.getNewValue())
                                && !event.getNewValue().isEmpty()) {
                            double duration = DurationConverter.readDurationToMinutes(event.getNewValue());
                            selectedLog.editDuration(duration);
                            System.out.println(selectedLog.getTask().getEffortSpent());
                            //TODO:Shinobu figure out how to display error
                        }
                        logTable.refresh(logTable, logTable.getItems());
                        if (table != null) {
                            SearchableTable.refresh(table, table.getItems());
                        }
                    }
                }
        );
        durationCol.prefWidthProperty().bind(logTable.widthProperty()
                .subtract(2).divide(100).multiply(60));


        logTable.setItems(data);
        TableColumn[] columns = {loggerCol, partnerCol, startDateTimeCol, durationCol, descriptionCol};
        logTable.getColumns().setAll(columns);
        logTable.getSortOrder().add(startDateCol);


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

        CustomComboBox<Person> partnerComboBox = new CustomComboBox<Person>("Partner");

        addButton.setDisable(true);

        personComboBox.getComboBox().valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null) {
                    loggerSelected = false;
                    ValidationStyle.borderGlowRed(personComboBox.getComboBox());
                    ValidationStyle.showMessage("Logger required", personComboBox.getComboBox());
                }
                else {
                    partnerComboBox.getComboBox().getItems().remove(newValue);
                    if (oldValue != null) {
                        partnerComboBox.getComboBox().getItems().add(oldValue);
                    }
                    ValidationStyle.borderGlowNone(personComboBox.getComboBox());
                    loggerSelected = true;
                }
                if (partnerComboBox.getComboBox().getItems().size() == 0) {
                    partnerComboBox.getComboBox().setDisable(true);
                    ValidationStyle.showMessage("There is only one Person in the Team",
                            partnerComboBox.getComboBox());
                }
                else {
                    partnerComboBox.getComboBox().setDisable(false);
                }
                addButton.setDisable(!(loggerSelected && correctDuration && correctEffortLeft));
            });


        partnerComboBox.getComboBox().valueProperty().addListener((observable, oldValue, newValue) -> {
                addButton.setDisable(!(loggerSelected && correctDuration && correctEffortLeft));
            });

        CustomDatePicker startDatePicker = new CustomDatePicker("Date:", true);

//        final Callback<DatePicker, DateCell> endDateCellFactory =
//            new Callback<DatePicker, DateCell>() {
//                @Override
//                public DateCell call(final DatePicker datePicker) {
//                    return new DateCell() {
//                        @Override
//                        public void updateItem(LocalDate item, boolean empty) {
//                            super.updateItem(item, empty);
//                            if (item.isBefore(task.getStory().getSprint().getStartDate())) {
//                                setDisable(true);
//                                setStyle("-fx-background-color: #ffc0cb;");
//                            }
//                        }
//                    };
//                }
//            };
//        startDatePicker.getDatePicker().setDayCellFactory(endDateCellFactory);
        startDatePicker.getDatePicker().setValue(LocalDate.now());

        HBox startTimeHBox = new HBox(10);
        SearchableText startTimeLabel = new SearchableText("Start Time:");
        startTimeLabel.setStyle("-fx-font-weight: bold");

        startTimeLabel.setPrefWidth(150);
        TimeTextField timeTextField = new TimeTextField();
        HBox.setHgrow(timeTextField, Priority.ALWAYS);
        startTimeHBox.getChildren().addAll(startTimeLabel, timeTextField);

        RequiredField durationTextField = new RequiredField("Duration:");
        durationTextField.getTextField().setPromptText("eg. 1h 20min");
        durationTextField.getTextField().setPrefWidth(175);
        
        CustomTextField effortLeftField = new CustomTextField("Task Effort Left:");
        effortLeftField.getTextField().setText(task.getEffortLeftString());
        effortLeftField.getTextField().setPrefWidth(175);

        personComboBox.prefWidthProperty().bind(logTable.widthProperty()
                .subtract(3).divide(100).multiply(30));
        startDatePicker.prefWidthProperty().bind(logTable.widthProperty()
                .subtract(3).divide(100).multiply(30));
        newLogFieldFirstRow.getChildren().addAll(personComboBox, partnerComboBox,
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
            Set<Team> teams = ((task.getStory().getBacklog() == null)
                    ? task.getStory().getProject().getCurrentTeams() :
                    task.getStory().getBacklog().getProject().getCurrentTeams());
            if (teams.isEmpty()) {
                personComboBox.setDisable(true);
            }
            else {
                boolean teamPopulated = false;
                for (Team team : teams) {
                    teamPopulated = teamPopulated || !team.getPeople().isEmpty();
                }
                if (teamPopulated) {
                    personComboBox.setDisable(false);
                    for (Team team : teams) {
                        personComboBox.getComboBox().getItems().addAll(team.getPeople());
                        partnerComboBox.getComboBox().getItems().addAll(team.getPeople());
                    }
                }
                else {
                    personComboBox.setDisable(true);
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
                    double effortLeftDifference = task.getEffortLeft() - effortLeft;

                    Person partner = partnerComboBox.getComboBox().getSelectionModel().getSelectedItem();
                    Log newLog = new Log(task, descriptionTextArea.getText(),
                            selectedPerson, partner, duration, dateTime, effortLeftDifference);
                    task.getStory().getProject().add(newLog);

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
                    data.removeAll(task.getLogsWithoutGhostLogs());
                    data.addAll(task.getLogsWithoutGhostLogs());
                    SearchableTable.refresh(logTable, logTable.getItems());
                    // Refresh the table if used, so that the logged effort updates after adding
                    if (table != null) {
                        SearchableTable.refresh(table, table.getItems());
                    }

                    event.consume();
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

    /**
     *
     * @param peopleList
     * @param removePerson
     * @param isPartnerList
     */
    private void updateObservablePeopleList(ObservableList<Person> peopleList, Person removePerson,
                                            Boolean isPartnerList) {
        peopleList.clear();
        if (isPartnerList) {
            peopleList.add(nullPerson);
        }
        peopleList.addAll(availablePeople);
        peopleList.remove(removePerson);
    }
}
