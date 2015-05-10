package seng302.group2.scenes.information.project;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomDateField;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.team.Allocation;
import seng302.group2.workspace.team.Team;

import java.util.Date;

import static seng302.group2.util.validation.DateValidator.isCorrectDateFormat;
import static seng302.group2.util.validation.DateValidator.stringToDate;


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
        this.setContent(historyPane);


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
        endDateCol.setCellValueFactory(new PropertyValueFactory<Allocation, String>("startDate"));
        endDateCol.setMinWidth(100);
        endDateCol.setMaxWidth(150);

        Button addButton = new Button("Add");
        HBox newAllocationFields = new HBox(10);
        CustomComboBox teamComboBox = new CustomComboBox("Team", true);
        CustomDateField startDateField = new CustomDateField("Start Date");
        CustomDateField endDateField = new CustomDateField("End Date");
        newAllocationFields.getChildren().addAll(teamComboBox, startDateField, endDateField);

        teamComboBox.getComboBox().setOnMouseClicked(event ->
            {
            //System.out.println("called" + currentProject.getTeams());
                teamComboBox.getComboBox().getItems().clear();
                for (Team team : currentProject.getTeams())
                {
                    //System.out.println("Team : " + team.toString());
                    teamComboBox.getComboBox().getItems().add(team);
                    //teamComboBox.addToComboBox(team.toString());
                }
            });


        addButton.setOnAction((event) ->
            {

                boolean correctStartDateFormat = isCorrectDateFormat(startDateField);

                if (correctStartDateFormat && startDateField.getText() != "")
                {
                    String endDateFieldText = endDateField.getText();
                    Date endDate = (endDateFieldText != null)
                            ? stringToDate(endDateFieldText) : null;
                    Date startDate = stringToDate(startDateField.getText());
                    Team selectedTeam = new Team();

                    for (Team team : currentProject.getTeams())
                    {
                        if (team.toString().equals(teamComboBox.getValue()))
                        {
                            selectedTeam = (Team)team;
                        }
                    }

                    Allocation alloc = new Allocation(currentProject, selectedTeam,
                            startDate, endDate);
                    currentProject.add(alloc);
                }
            });


        historyTable.setItems(data);
        historyTable.getColumns().addAll(teamCol, startDateCol, endDateCol);
        historyPane.getChildren().addAll(title, historyTable, newAllocationFields, addButton);
    }
}
