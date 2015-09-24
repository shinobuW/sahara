package seng302.group2.scenes.control.chart;

import javafx.scene.chart.*;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.team.Team;

import java.time.LocalDate;
import java.util.*;

/**
 * A class for displaying a velocity chart.
 * Created by cvs20 on 24/09/15.
 */
public class VelocityChart extends BarChart {

    public VelocityChart(CategoryAxis axis, NumberAxis axis2) {
        super(axis, axis2);
        try {
            String css = this.getClass().getResource("/styles/chart.css").toExternalForm();
            this.getStylesheets().add(css);
        }
        catch (Exception ex) {
            System.err.println("Cannot acquire stylesheet: " + ex.toString());
        }
    }


    /**
     * Populates the chart with the information passed through in the current team.
     * This function will define the series, css, titles, and any other graph functionality.
     *
     * @param currentTeam the team the chart is displaying.
     */
    public void populateGraph(Team currentTeam) {
        this.setTitle(currentTeam.getShortName() + " Velocity");

        List<Sprint> sprintList = new ArrayList<>();
        for (Sprint sprint : currentTeam.getProject().getSprints()) {
            if (sprint.getTeam() == currentTeam) {
                sprintList.add(sprint);
            }
        }

        Collections.sort(sprintList, new Comparator<Sprint>() {
            @Override
            public int compare(Sprint sprint1, Sprint sprint2) {
                if (sprint1.getStartDate().isBefore(sprint2.getStartDate())) {
                    return 1;
                }
                else if (sprint1.getStartDate() == sprint2.getStartDate()) {
                    return 0;
                }
                else {
                    return -1;
                }
            }
        });

        XYChart.Series series = new XYChart.Series<>();
        this.setLegendVisible(false);
        for (Sprint sprint : sprintList) {
            series.getData().add(new XYChart.Data<>(sprint.getGoal(), sprint.getPointsPerDay() * 7));
        }
        this.getData().add(series);
    }
}


