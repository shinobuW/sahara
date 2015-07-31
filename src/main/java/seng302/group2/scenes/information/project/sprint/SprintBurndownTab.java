package seng302.group2.scenes.information.project.sprint;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.util.chart.BurndownChart;
import seng302.group2.workspace.project.sprint.Sprint;


/**
 * A class for displaying a tab showing a sprints burndown graph.
 * Created by btm38 on 31/07/15.
 */
public class SprintBurndownTab extends Tab {
    private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();
    /**
     * Constructor for SprintBurndownTab.
     *
     * @param currentSprint the corresponding sprint
     */
    public SprintBurndownTab(Sprint currentSprint) {
        this.setText("Burndown Chart");
        Pane burndownPane = new VBox(10);
        burndownPane.setBorder(null);
        burndownPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(burndownPane);
        this.setContent(wrapper);

        //defining the axes
        //configureAxis();
        BurndownChart burndown = new BurndownChart(xAxis, yAxis);
        burndown.populateGraph(currentSprint);
        burndownPane.getChildren().add(burndown);
    }

    private void configureAxis() {
        xAxis.setLabel("Sprint Duration (Days)");
        yAxis.setLabel("Hours worked by JD");
    }
}
