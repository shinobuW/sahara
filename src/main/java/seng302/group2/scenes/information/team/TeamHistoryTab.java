package seng302.group2.scenes.information.team;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomDatePicker;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.control.search.SearchableTitle;
import seng302.group2.util.validation.ValidationStatus;
import seng302.group2.workspace.allocation.Allocation;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.team.Team;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static seng302.group2.util.validation.DateValidator.validateAllocation;

/**
 * A class for history allocation tab to display a table of the selected team's allocation
 * history
 * Created by swi67 on 15/05/15.
 */
public class TeamHistoryTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();

    Boolean isValidEdit;

    /**
     * Constructor for team allocation tab
     *
     * @param currentTeam currently selected team
     */
    public TeamHistoryTab(Team currentTeam) {
        this.setText("Allocation History");
        Pane historyPane = new VBox(10);  // The pane that holds the basic info
        historyPane.setBorder(null);
        historyPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(historyPane);
        this.setContent(wrapper);

        TableView<Allocation> historyTable = new TableView();
        historyTable.setEditable(true);
        historyTable.setPrefWidth(700);
        historyTable.setPrefHeight(400);
        historyTable.setPlaceholder(new SearchableText("This team has no project allocations.", searchControls));
        historyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList<Allocation> data = currentTeam.getProjectAllocations();

        Callback<TableColumn, TableCell> cellFactory = col -> new EditingCell();

        SearchableText title = new SearchableTitle(currentTeam.getShortName() + " Allocation History", searchControls);

        TableColumn teamCol = new TableColumn("Project");
        teamCol.setCellValueFactory(new PropertyValueFactory<Allocation, String>("Project"));
        teamCol.prefWidthProperty().bind(historyTable.widthProperty()
                .subtract(3).divide(100).multiply(40));
        teamCol.setResizable(false);

        TableColumn startDateCol = new TableColumn("Start Date");
        // Sorting Comparator.
        startDateCol.setComparator(new Comparator<String>() {
            @Override
            public int compare(String dateString1, String dateString2) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
                    Date date1 = format.parse(dateString1);
                    Date date2 = format.parse(dateString2);
                    return Long.compare(date1.getTime(), date2.getTime());
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
                return -1;
            }
        });

        startDateCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Allocation, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Allocation,
                            String> alloc) {
                        SimpleStringProperty property = new SimpleStringProperty();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        property.setValue(alloc.getValue().getStartDate().format(formatter));
                        return property;
                    }
                });

        startDateCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Allocation, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Allocation, String> event) {
                        isValidEdit = false;
                        if (!event.getNewValue().isEmpty()) {
                            Allocation currentAlloc = event.getTableView().getItems()
                                    .get(event.getTablePosition().getRow());

                            LocalDate newStartDate = LocalDate.parse(event.getNewValue(),
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            ValidationStatus editValidationStatus = validateAllocation(
                                    currentAlloc.getProject(), currentAlloc.getTeam(),
                                    newStartDate, currentAlloc.getEndDate(),
                                    currentAlloc);

                            if (editValidationStatus == ValidationStatus.VALID) {
                                currentAlloc.editStartDate(newStartDate);
                                isValidEdit = true;
                            }
                            else {
                                showErrorDialog(editValidationStatus);
                                isValidEdit = false;
                            }
                        }
                    }
                });

        startDateCol.setCellFactory(cellFactory);
        startDateCol.prefWidthProperty().bind(historyTable.widthProperty()
                .subtract(3).divide(100).multiply(30));

        TableColumn endDateCol = new TableColumn("End Date");
        // Sorting Comparator.
        endDateCol.setComparator(new Comparator<String>() {
            @Override
            public int compare(String dateString1, String dateString2) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
                    Date date1 = format.parse(dateString1);
                    Date date2 = format.parse(dateString2);
                    return Long.compare(date1.getTime(), date2.getTime());
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
                return -1;
            }
        });

        endDateCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Allocation, String>,
                        ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Allocation,
                            String> alloc) {
                        SimpleStringProperty property = new SimpleStringProperty();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        if (alloc.getValue().getEndDate() != null) {
                            property.setValue(alloc.getValue().getEndDate().format(formatter));
                        }
                        else {
                            property.setValue("");
                        }
                        return property;
                    }
                });

        endDateCol.prefWidthProperty().bind(historyTable.widthProperty()
                .subtract(3).divide(100).multiply(30));
        endDateCol.setCellFactory(cellFactory);
        endDateCol.setEditable(true);
        endDateCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Allocation, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Allocation, String> event) {
                        isValidEdit = false;
                        if (!event.getNewValue().isEmpty()) {
                            Allocation currentAlloc = event.getTableView().
                                    getItems().get(event.getTablePosition().getRow());

                            LocalDate newEndDate = LocalDate.parse(event.getNewValue(),
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                            ValidationStatus editValidationStatus =
                                    validateAllocation(currentAlloc.getProject(),
                                            currentAlloc.getTeam(),
                                            currentAlloc.getStartDate(), newEndDate,
                                            currentAlloc);

                            if (editValidationStatus == ValidationStatus.VALID) {
                                currentAlloc.editEndDate(newEndDate);
                                isValidEdit = true;
                            }
                            else {
                                showErrorDialog(editValidationStatus);
                                isValidEdit = false;
                            }
                        }
                    }
                });

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.BOTTOM_RIGHT);
        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        buttons.getChildren().addAll(addButton, deleteButton);

        HBox newAllocationFields = new HBox(35);
        CustomComboBox<Project> projectComboBox = new CustomComboBox("Project", true);
        CustomDatePicker startDatePicker = new CustomDatePicker("Start Date", true);
        CustomDatePicker endDatePicker = new CustomDatePicker("End Date", false);
        startDatePicker.getDatePicker().setStyle("-fx-pref-width: 200;");
        endDatePicker.getDatePicker().setStyle("-fx-pref-width: 200;");
        projectComboBox.getComboBox().setStyle("-fx-pref-width: 250;");

        projectComboBox.prefWidthProperty().bind(historyTable.widthProperty()
                .subtract(3).divide(100).multiply(30));
        startDatePicker.prefWidthProperty().bind(historyTable.widthProperty()
                .subtract(3).divide(100).multiply(30));
        endDatePicker.prefWidthProperty().bind(historyTable.widthProperty()
                .subtract(3).divide(100).multiply(30));
        newAllocationFields.getChildren().addAll(projectComboBox,
                startDatePicker, endDatePicker);

        projectComboBox.getComboBox().setOnMouseClicked(event -> {
                projectComboBox.getComboBox().getItems().clear();
                for (Project proj : Global.currentWorkspace.getProjects()) {
                    projectComboBox.getComboBox().getItems().add(proj);
                }
            });

        addButton.setOnAction((event) -> {
                projectComboBox.hideErrorField();
                startDatePicker.hideErrorField();
                if (projectComboBox.getValue() != null && startDatePicker.getValue() != null) {
                    LocalDate endDate = endDatePicker.getValue();
                    LocalDate startDate = startDatePicker.getValue();
                    Project selectedProject = null;

                    for (Project proj : Global.currentWorkspace.getProjects()) {
                        if (proj.equals(projectComboBox.getValue())) {
                            selectedProject = proj;
                        }
                    }

                    if (validateAllocation(selectedProject, currentTeam, startDate, endDate)
                            == ValidationStatus.VALID) {
                        Allocation alloc = new Allocation(selectedProject, currentTeam,
                                startDate, endDate);
                        currentTeam.add(alloc);
                    }
                    else {
                        showErrorDialog(validateAllocation(selectedProject,
                                currentTeam, startDate, endDate));
                        event.consume();
                    }
                }
                else {
                    if (projectComboBox.getValue() == null) {
                        projectComboBox.showErrorField("Please select a project");
                        event.consume();
                    }
                    if (startDatePicker.getValue() == null) {
                        startDatePicker.showErrorField("Please select a date");
                        event.consume();
                    }
                }
            });

        deleteButton.setOnAction((event) -> {
                Allocation selectedAlloc = historyTable.getSelectionModel().getSelectedItem();
                if (selectedAlloc != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete");
                    alert.setHeaderText("Delete Allocation?");
                    alert.setContentText("Do you really want to delete this allocation?");
                    alert.getDialogPane().setStyle(" -fx-max-width:450; -fx-max-height: 100px; -fx-pref-width: 450px; "
                            + "-fx-pref-height: 100px;");

                    ButtonType buttonTypeYes = new ButtonType("Yes");
                    ButtonType buttonTypeNo = new ButtonType("No");

                    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                    Optional<ButtonType> result  = alert.showAndWait();

                    if (result.get() == buttonTypeYes) {
                        selectedAlloc.delete();
                    }
                    else if (result.get() == buttonTypeNo) {
                        event.consume();
                    }
                }
            });
        historyTable.setItems(data);
        TableColumn[] columns = {teamCol, startDateCol, endDateCol};
        historyTable.getColumns().setAll(columns);

        // Listener to disable columns being movable
        historyTable.getColumns().addListener(new ListChangeListener() {
            public boolean suspended;

            @Override
            public void onChanged(Change change) {
                change.next();
                if (change.wasReplaced() && !suspended) {
                    this.suspended = true;
                    historyTable.getColumns().setAll(columns);
                    this.suspended = false;
                }
            }
        });
        historyPane.getChildren().addAll(title, historyTable, newAllocationFields, buttons);
    }

    /**
     * Displays the appropriate error dialog according to the validation status
     *
     * @param status the validation status
     */
    private void showErrorDialog(ValidationStatus status) {
        switch (status) {
            case VALID:
                break;
            case ALLOCATION_DATES_WRONG_ORDER:
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().setStyle(" -fx-max-width:450px; -fx-max-height: 70px; -fx-pref-width: 450px; "
                        + "-fx-pref-height: 70px;");
                alert.setTitle("Error");
                alert.setHeaderText("Allocation Date Error");
                alert.setContentText("The end date of your new allocation cannot be before the start"
                        + " date!");
                alert.showAndWait();
                break;
            case ALLOCATION_DATES_EQUAL:
                Alert alertDatesEquals = new Alert(Alert.AlertType.ERROR);
                alertDatesEquals.getDialogPane().setStyle(" -fx-max-width:500px; -fx-max-height: 100px; "
                        + "-fx-pref-width: 500px; -fx-pref-height: 100px;");
                alertDatesEquals.setTitle("Error");
                alertDatesEquals.setHeaderText("Allocation Date Error");
                alertDatesEquals.setContentText("An allocation for that team with those start and end dates"
                        + " already exists!");
                alertDatesEquals.showAndWait();
                break;
            case START_OVERLAP:
                Alert alertOverlap = new Alert(Alert.AlertType.ERROR);
                alertOverlap.getDialogPane().setStyle(" -fx-max-width:550px; -fx-max-height: 100px; "
                        + "-fx-pref-width: 550px; -fx-pref-height: 100px;");
                alertOverlap.setTitle("Error");
                alertOverlap.setHeaderText("Allocation Date Error");
                alertOverlap.setContentText("The start date of your new allocation overlaps with an already"
                        + " existing allocation for that team.");
                alertOverlap.showAndWait();
                break;
            case END_OVERLAP:
                Alert alertEndOverlap = new Alert(Alert.AlertType.ERROR);
                alertEndOverlap.getDialogPane().setStyle(" -fx-max-width:550px; -fx-max-height: 100px; "
                        + "-fx-pref-width: 550px; -fx-pref-height: 100px;");
                alertEndOverlap.setTitle("Error");
                alertEndOverlap.setHeaderText("Allocation Date Error");
                alertEndOverlap.setContentText("The end date of your new allocation overlaps with an already"
                        + " existing allocation for that team.");
                alertEndOverlap.showAndWait();
                break;
            case SUPER_OVERLAP:
                Alert alertSuperOverlap = new Alert(Alert.AlertType.ERROR);
                alertSuperOverlap.getDialogPane().setStyle(" -fx-max-width:550px; -fx-max-height: 100px; "
                        + "-fx-pref-width: 550px; -fx-pref-height: 100px;");
                alertSuperOverlap.setTitle("Error");
                alertSuperOverlap.setHeaderText("Allocation Date Error");
                alertSuperOverlap.setContentText("The start and end dates of your new allocation encompass an"
                        + " existing allocation for that team.");
                alertSuperOverlap.showAndWait();
                break;
            case SUB_OVERLAP:
                Alert alertSubOverlap = new Alert(Alert.AlertType.ERROR);
                alertSubOverlap.getDialogPane().setStyle(" -fx-max-width:550px; -fx-max-height: 100px; "
                        + "-fx-pref-width: 550px; -fx-pref-height: 100px;");
                alertSubOverlap.setTitle("Error");
                alertSubOverlap.setHeaderText("Allocation Date Error");
                alertSubOverlap.setContentText("The start and end dates of your new allocation are encompassed"
                        + " by an existing allocation for that team.");
                alertSubOverlap.showAndWait();
                break;
            default:
                System.out.println("Error: Cannot recognise validation status");
                break;
        }
    }

    /**
     * Gets all the searchable controls on this tab.
     * @return a collection of all the searchable controls on this tab.
     */
    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }


    /**
     * A subclass of TableCell to allow for date pickers to be bound to the cell
     * to allow for start and end date to be edited
     */
    class EditingCell extends TableCell<Allocation, String> {

        public DatePicker datePicker;

        /**
         * Blank constructor for EditingCell
         */
        public EditingCell() {
        }

        /**
         * Start editing the Datepicker cell
         */
        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setGraphic(datePicker);

                if (!getText().isEmpty()) {
                    datePicker.setValue(LocalDate.parse(getText(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
                else {
                    datePicker.setValue(null);
                }
                Platform.runLater(() -> {
                        datePicker.requestFocus();
                    });

            }
        }

        /**
         * Cancel the editing of the cell.
         */
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setGraphic(null);
        }

        /**
         * Updates the item in the cell
         * @param item The item to update
         * @param empty Whether the cell is empty or not
         */
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            }
            else {
                if (isEditing()) {
                    if (!getItem().isEmpty()) {
                        datePicker.setValue(LocalDate.parse(getItem(),
                                DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    }
                    setGraphic(datePicker);
                }
                else {
                    if (datePicker == null) {
                        setText(getString());
                    }
                    else {
                        if (datePicker.getValue() == null) {
                            setText(getString());
                        }
                        else if (isValidEdit) {
                            setText(datePicker.getValue().format(Global.dateFormatter));
                        }
                    }

                    setGraphic(null);
                }
            }
        }

        /**
         * Creates a text field for use in the editing cell
         */
        private void createTextField() {
            datePicker = new DatePicker();
            datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            datePicker.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0,
                                    Boolean arg1, Boolean arg2) {
                    if (!arg2) {
                        if (datePicker.getValue() != null) {
                            commitEdit(datePicker.getValue().toString());
                        }
                        else {
                            commitEdit("");
                        }
                    }
                    else {
                        updateItem(getItem(), false);
                    }
                }
            });
        }

        /**
         * Returns a string in a Date format for use in the cell
         * @return The string in Date format
         */
        private String getString() {
            LocalDate date;

            if (getItem().isEmpty()) {
                return getItem();
            }
            else {
                if (getItem().matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
                    date = LocalDate.parse(getItem(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                }
                else {
                    date = LocalDate.parse(getItem(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
                String dateString = date.format(Global.dateFormatter);
                return getItem() == null ? "" : dateString;
            }
        }
    }
}
