package seng302.group2.scenes.control.chart;

import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.project.story.tasks.Log;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * A class for displaying a burndown chart.
 * Created by btm38 on 31/07/15.
 */
public class BurndownChart extends LineChart {

    public BurndownChart(Axis axis, Axis axis2) {
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
     * Populates the chart with the information passed through in the current sprint.
     * This function will define the series, css, titles, and any other graph functionality.
     * @param currentSprint the sprint the chart is displaying.
     */
    public void populateGraph(Sprint currentSprint) {
        this.setTitle(currentSprint.getGoal() + " Burndown");
        double effortLeft = 0;
        double effortSpent = 0;
        List<Log> logList;
        logList = currentSprint.getAllLogsWithIntialLogs();

        LocalDate startDate = currentSprint.getStartDate();

        /* Map which stores a (key, value) pair of (date: hours), where hours is the total n
        umber of hours logged on the date.
         */
        Map<LocalDate, Double> dailyEffortSpentMap = new LinkedHashMap<>();

        /* Map which stores a (key, value) pair of (date: hours), where hours is the total effort left
        for that date.
         */
        Map<LocalDate, Double> dailyEffortLeftMap = new LinkedHashMap<>();


        boolean sprintEndReached = false;
        while (!sprintEndReached) {
            dailyEffortSpentMap.put(startDate, 0.0);
            dailyEffortLeftMap.put(startDate, 0.0);
            startDate = startDate.plusDays(1);
            if (startDate.isAfter(currentSprint.getEndDate())) {
                sprintEndReached = true;
            }
        }

        for (Log log : logList) {
            if (dailyEffortSpentMap.containsKey(log.getStartDate().toLocalDate())) {
                Double sum = dailyEffortSpentMap.get(log.getStartDate().toLocalDate());
                dailyEffortSpentMap.put(log.getStartDate().toLocalDate(), sum + log.getDurationInHours());
            }
            if (dailyEffortLeftMap.containsKey(log.getStartDate().toLocalDate())) {
                Double sum = dailyEffortLeftMap.get(log.getStartDate().toLocalDate());
                dailyEffortLeftMap.put(log.getStartDate().toLocalDate(), sum + log.getEffortLeftInHours());
                effortLeft += log.getEffortLeftInHours();
            }
        }

        XYChart.Series effortSpentSeries = new XYChart.Series();
        effortSpentSeries.setName("Effort Spent");

        for (LocalDate d : dailyEffortSpentMap.keySet()) {
            effortSpent += dailyEffortSpentMap.get(d);
            String monthStr = d.getMonth().toString().substring(0, 3);
            effortSpentSeries.getData().add(new XYChart.Data<>(monthStr + " " + d.getDayOfMonth(), effortSpent));
        }

        XYChart.Series effortLeftSeries = new XYChart.Series();
        effortLeftSeries.setName("Effort Left");

        for (LocalDate d : dailyEffortLeftMap.keySet()) {
            effortLeft -= dailyEffortLeftMap.get(d);
            String monthStr = d.getMonth().toString().substring(0, 3);
            effortLeftSeries.getData().add(new XYChart.Data<>(monthStr + " " + d.getDayOfMonth(), effortLeft));


        }


        this.getData().add(effortSpentSeries);
        this.getData().add(effortLeftSeries);
    }

    public XYChart.Series effortLeftSeries(Sprint currentSprint) {
        List<Log> logList = new ArrayList<Log>();
        logList = currentSprint.getAllLogs();
        return new Series<>();

    }

    public XYChart.Series effortSpentSeries(Sprint currentSprint) {
        return null;
    }
}


