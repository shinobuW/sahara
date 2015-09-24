package seng302.group2.scenes.information.project.sprint;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.search.*;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.project.story.tasks.Log;
import seng302.group2.workspace.project.story.tasks.Task;
import seng302.group2.workspace.team.Team;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A Tab which shows the logs on a task which belongs to the given Sprint.
 * Created by swi67 on 14/09/15.
 */
public class SprintLogTab extends SearchableTab {
    List<SearchableControl> searchControls = new ArrayList<>();
    ObservableList<Log> data = FXCollections.observableArrayList();
    Person logger = null;
    Person partner = null;
    CustomComboBox<Person> loggerComboBox;
    CustomComboBox<Person> partnerComboBox;
    Person nullPerson = new Person("", "", "", "", "", null);
    Sprint currentSprint;

    /**
     * Constructor for the sprint logging tab
     * @param currentSprint The current sprint
     */
    public SprintLogTab(Sprint currentSprint) {
        this.currentSprint = currentSprint;
        construct();
    }

    /**
     * Updates the data in the log table view according to the filter combo boxes.
     */
    private void updateFilteredLogs() {
        data.clear();
        data.addAll(currentSprint.getAllLogsWithInitialLogs());
        Person selectedLogger = loggerComboBox.getComboBox().getValue();
        Person selectedPartner = partnerComboBox.getComboBox().getValue();
        for (Log log : currentSprint.getAllLogsWithInitialLogs()) {
            if (selectedLogger != null) {
                if (selectedLogger != nullPerson && log.getLogger() != selectedLogger) {
                    data.remove(log);
                }
            }

            if (selectedPartner != null && selectedPartner != nullPerson) {

                if (log.getPartner() != null) {
                    if (selectedPartner == log.getLogger() && log.getPartner() != selectedPartner) {
                        data.remove(log);
                    }
                }
            }
        }
    }

    /**
     * Gets all the searchable controls in the tab
     * @return Collection of Searchable Controls
     */
    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }

    /**
     * constructs the contents of the tab
     */
    @Override
    public void construct() {
        this.setText("Logging Effort");
        Pane loggingPane = new VBox(10);
        loggingPane.setBorder(null);
        loggingPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(loggingPane);
        this.setContent(wrapper);

        loggerComboBox = new CustomComboBox<>("Logger");
        partnerComboBox = new CustomComboBox<>("Partner");

        ObservableList<Team> allocatedTeams = FXCollections.observableArrayList();
        ObservableList<Person> allPeople = FXCollections.observableArrayList();
        allocatedTeams.addAll(currentSprint.getProject().getCurrentTeams());

        for (Team team : allocatedTeams) {
            allPeople.addAll(team.getPeople());
        }

        loggerComboBox.getComboBox().getItems().add(nullPerson);
        loggerComboBox.getComboBox().getItems().addAll(allPeople);

        partnerComboBox.getComboBox().getItems().add(nullPerson);
        partnerComboBox.getComboBox().getItems().addAll(allPeople);

        loggerComboBox.getComboBox().valueProperty().addListener((observable, oldValue, newValue) -> {
            partnerComboBox.clear();
            partnerComboBox.getComboBox().getItems().add(nullPerson);
            partnerComboBox.getComboBox().getItems().addAll(allPeople);
            if (newValue != nullPerson) {
                partnerComboBox.getComboBox().getItems().remove(newValue);
            }
            updateFilteredLogs();
        });

        partnerComboBox.getComboBox().valueProperty().addListener((observable, oldValue, newValue) -> {
            updateFilteredLogs();
        });


        HBox filterHBox = new HBox(10);
        filterHBox.getChildren().addAll(loggerComboBox, partnerComboBox);

        SearchableText title = new SearchableTitle(currentSprint.getLongName() + " Logging Effort");

        SearchableTable<Log> logTable = new SearchableTable<>();
        logTable.setEditable(false);
        logTable.setPrefWidth(700);
        logTable.setPrefHeight(200);
        logTable.setPlaceholder(new SearchableText("There are currently no "
                + "logs between the specified dates.", searchControls));
        logTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        updateFilteredLogs();

        TableColumn loggerCol = new TableColumn("Logger");
        loggerCol.setCellValueFactory(new PropertyValueFactory<Log, Person>("logger"));
        loggerCol.prefWidthProperty().bind(logTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        TableColumn partnerCol = new TableColumn("Partner");
        partnerCol.setCellValueFactory(new PropertyValueFactory<Log, Person>("partner"));
        partnerCol.setEditable(true);
        partnerCol.prefWidthProperty().bind(logTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        TableColumn taskCol = new TableColumn("Task");
        taskCol.setCellValueFactory(new PropertyValueFactory<Log, Task>("task"));
        taskCol.prefWidthProperty().bind(logTable.widthProperty()
                .subtract(2).divide(100).multiply(60));

        TableColumn descriptionCol = new TableColumn("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Log, String>("description"));
        descriptionCol.prefWidthProperty().bind(logTable.widthProperty()
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
        startTimeCol.prefWidthProperty().bind(logTable.widthProperty()
                .subtract(2).divide(100).multiply(60));
        startTimeCol.setSortType(TableColumn.SortType.ASCENDING);

        TableColumn durationCol = new TableColumn("Duration");
        durationCol.setCellValueFactory(new PropertyValueFactory<Log, Long>("durationString"));
        durationCol.prefWidthProperty().bind(logTable.widthProperty()
                .subtract(2).divide(100).multiply(60));


        logTable.setItems(data);
        TableColumn[] columns = {loggerCol, partnerCol, taskCol, startTimeCol, durationCol, descriptionCol};
        logTable.getColumns().setAll(columns);


        loggingPane.getChildren().addAll(
                title,
                filterHBox,
                logTable
        );

        Collections.addAll(searchControls,
                title,
                logTable,
                loggerComboBox,
                partnerComboBox
        );
    }

    /**
     * Gets the string representation of the current Tab
     * @return The String value
     */
    @Override
    public String toString() {
        return "Sprint Logging Tab(.";
    }

}
