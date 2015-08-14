package seng302.group2.scenes.information.project;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import seng302.group2.scenes.control.CustomDatePicker;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.story.tasks.Log;
import seng302.group2.workspace.project.story.tasks.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by crw73 on 14/08/15.
 */
public class ProjectLoggingTab extends SearchableTab {
    List<SearchableControl> searchControls = new ArrayList<>();
    LocalDate startDate;
    LocalDate endDate;
    ObservableList<Log> data = observableArrayList();

    /**
     * Constructor for the Task Logging Tab
     *
     * @param currentProject The currently selected Task
     */
    public ProjectLoggingTab(Project currentProject) {

        this.setText("Logging Effort");
        Pane basicInfoPane = new VBox(10);

        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        HBox datePickers = new HBox();

        endDate = LocalDate.now();
        startDate = LocalDate.now().minusYears(1);

        CustomDatePicker startDatePicker = new CustomDatePicker("Start Date", true);
        CustomDatePicker endDatePicker = new CustomDatePicker("End Date", true);


        startDatePicker.setValue(startDate);
        endDatePicker.setValue(endDate);

        datePickers.getChildren().addAll(startDatePicker, endDatePicker);

        TableView<Log> taskTable = new TableView<>();
        taskTable.setEditable(false);
        taskTable.setPrefWidth(700);
        taskTable.setPrefHeight(200);
        taskTable.setPlaceholder(new SearchableText("There are currently no "
                + "logs between the specified dates.", searchControls));
        taskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        startDatePicker.prefWidthProperty().bind(taskTable.widthProperty()
                .divide(2));
        endDatePicker.prefWidthProperty().bind(taskTable.widthProperty()
                .divide(2));

        updateFilteredLogs(currentProject);

        TableColumn loggerCol = new TableColumn("Logger");
        loggerCol.setCellValueFactory(new PropertyValueFactory<Log, Person>("logger"));
        loggerCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        TableColumn taskCol = new TableColumn("Task");
        taskCol.setCellValueFactory(new PropertyValueFactory<Log, Task>("task"));
        taskCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        TableColumn descriptionCol = new TableColumn("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Log, String>("description"));
        descriptionCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        TableColumn startTimeCol = new TableColumn("Start Time");
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

        TableColumn durationCol = new TableColumn("Duration");
        durationCol.setCellValueFactory(new PropertyValueFactory<Log, Long>("durationString"));
        durationCol.prefWidthProperty().bind(taskTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        taskTable.setItems(data);
        TableColumn[] columns = {loggerCol, taskCol, descriptionCol, startTimeCol, durationCol};
        taskTable.getColumns().setAll(columns);

        // Listener to disable columns being movable
        taskTable.getColumns().addListener(new ListChangeListener() {
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
        });

        //TODO restrict date selection so can't pick startdate past end date and visa-versa
        startDatePicker.getDatePicker().valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable,
                                LocalDate oldValue, LocalDate newValue) {
                startDate = newValue;
                updateFilteredLogs(currentProject);
            }
        });

        endDatePicker.getDatePicker().valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable,
                                LocalDate oldValue, LocalDate newValue) {
                endDate = newValue;
                updateFilteredLogs(currentProject);
            }
        });

        basicInfoPane.getChildren().add(datePickers);
        basicInfoPane.getChildren().add(taskTable);


        Collections.addAll(searchControls,
                startDatePicker, endDatePicker);

    }


    private void updateFilteredLogs(Project currentProject) {
        data.clear();
        for (Log log : currentProject.getLogs()) {
            if (log.getStartDate().isAfter(startDate) && log.getStartDate().isBefore(endDate)) {
                data.add(log);
            }
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
}
