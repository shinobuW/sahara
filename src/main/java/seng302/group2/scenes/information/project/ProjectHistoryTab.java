package seng302.group2.scenes.information.project;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.team.Allocation;
import seng302.group2.workspace.team.Team;

import java.time.LocalDate;

import static seng302.group2.util.validation.DateValidator.validateAllocation;


/**
 * A class for history allocation tab to display a table of the selected project's allocation
 * history
 * Created by swi67 on 10/05/15.
 */
public class ProjectHistoryTab extends Tab
{
    /**
     * Constructor for project allocation tab
     * @param currentProject currently selected project
     */
    public ProjectHistoryTab(Project currentProject)
    {
        this.setText("Allocation History");
        Pane historyPane = new VBox(10);  // The pane that holds the basic info
        historyPane.setBorder(null);
        historyPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(historyPane);
        this.setContent(wrapper);

        TableView<Allocation> historyTable = new TableView();
        ObservableList<Allocation> data = currentProject.getTeamAllocations();
        historyTable.setEditable(false);
        Label title = new TitleLabel("Allocation History");

        TableColumn teamCol = new TableColumn("Team");
        teamCol.setCellValueFactory(new PropertyValueFactory<Allocation, String>("Team"));
        teamCol.setMinWidth(100);
        teamCol.setMaxWidth(150);

        TableColumn startDateCol = new TableColumn("Start Date");
        startDateCol.setCellValueFactory(new PropertyValueFactory<Allocation, String>("startDate"));
        startDateCol.setMinWidth(100);
        startDateCol.setMaxWidth(150);

        TableColumn endDateCol = new TableColumn("End Date");
        endDateCol.setCellValueFactory(new PropertyValueFactory<Allocation, String>("endDate"));
        endDateCol.setMinWidth(100);
        endDateCol.setMaxWidth(150);

        Button addButton = new Button("Add");
        HBox newAllocationFields = new HBox(10);
        CustomComboBox teamComboBox = new CustomComboBox("Team", true);
        Label startDateLabel = new Label("Start Date");
        DatePicker startDatePicker = new DatePicker();
        Label endDateLabel = new Label("End Date");
        DatePicker endDatePicker = new DatePicker();
        newAllocationFields.getChildren().addAll(teamComboBox, startDateLabel,
                startDatePicker, endDateLabel, endDatePicker);

        teamComboBox.getComboBox().setOnMouseClicked(event ->
            {
                teamComboBox.getComboBox().getItems().clear();
                for (Team team : Global.currentWorkspace.getTeams())
                {
                    teamComboBox.getComboBox().getItems().add(team);
                }
                // Remove the unassigned team
                teamComboBox.getComboBox().getItems().remove(Global.getUnassignedTeam());
            });


//

        addButton.setOnAction((event) ->
            {

                if (startDatePicker.getValue() != null)
                {
                    LocalDate endDate = endDatePicker.getValue();
                    LocalDate startDate = startDatePicker.getValue();
                    Team selectedTeam = null;

                    for (Team team : Global.currentWorkspace.getTeams())
                    {
                        if (team.toString().equals(teamComboBox.getValue()))
                        {
                            selectedTeam = team;
                        }
                    }

                    Allocation alloc = new Allocation(currentProject, selectedTeam,
                            startDate, endDate);

                    if (validateAllocation(alloc, currentProject))
                    {
                        currentProject.add(alloc);
                    }
                    else
                    {
                        event.consume();
                    }
                }
                else
                {
                    event.consume();
                }
            });
        historyTable.setItems(data);
        historyTable.getColumns().addAll(teamCol, startDateCol, endDateCol);
        historyPane.getChildren().addAll(title, historyTable, newAllocationFields, addButton);
    }
}
