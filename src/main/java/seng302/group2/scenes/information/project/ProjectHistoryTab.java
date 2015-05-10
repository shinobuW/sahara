package seng302.group2.scenes.information.project;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.team.Allocation;


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
        teamCol.setCellValueFactory(new PropertyValueFactory<Allocation, String>("shortName"));
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

        historyTable.setItems(data);
        historyTable.getColumns().addAll(teamCol, startDateCol, endDateCol);
        historyPane.getChildren().addAll(title, historyTable);
    }
}
