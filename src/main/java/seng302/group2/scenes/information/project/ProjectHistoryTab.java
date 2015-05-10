package seng302.group2.scenes.information.project;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


/**
 * Created by swi67 on 10/05/15.
 */
public class ProjectHistoryTab
{
    public static Tab getHistoryTab()
    {
        Tab historyTab = new Tab("Allocation History");
        Pane historyPane = new VBox(10);  // The pane that holds the basic info
        historyPane.setBorder(null);
        historyPane.setPadding(new Insets(25, 25, 25, 25));
        historyTab.setContent(historyPane);

        TableView historyTable = new TableView();
        historyTable.setEditable(false);
        final Label title = new Label("Allocation History");

        TableColumn teamCol = new TableColumn("Team");

        TableColumn startDateCol = new TableColumn("Start Date");
        TableColumn endDateCol = new TableColumn("End Date");

        historyTable.getColumns().addAll(teamCol, startDateCol, endDateCol);
        historyPane.getChildren().addAll(title, historyTable);



        return historyTab;
    }
}
