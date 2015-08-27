package seng302.group2.scenes.control.chart;

import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.project.story.tasks.Log;

import java.time.LocalDate;
import java.util.*;


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
        double  effortLeft = 0;
        List<Log> logList;
        logList = currentSprint.getAllLogs();

        LocalDate startDate = currentSprint.getStartDate();

        /* Map which stores a (key, value) pair of (date: hours), where hours is the total n
        umber of hours logged on the date.
         */
        Map<LocalDate, Double> dailyEffortMap = new LinkedHashMap<>();


        boolean sprintEndReached = false;
        while (sprintEndReached != true) {
            dailyEffortMap.put(startDate, 0.0);
            startDate = startDate.plusDays(1);
            if (startDate.isAfter(currentSprint.getEndDate())) {
                sprintEndReached = true;
            }
        }

        for (Log log : logList) {
            if (dailyEffortMap.containsKey(log.getStartDate().toLocalDate())) {
                Double sum = dailyEffortMap.get(log.getStartDate().toLocalDate());
                dailyEffortMap.put(log.getStartDate().toLocalDate(), sum + log.getDurationInHours());
                effortLeft += log.getDurationInHours();
            }
        }
        final double maxEffortLeft = effortLeft;


        XYChart.Series effortSpentSeries = new XYChart.Series();
        effortSpentSeries.setName("Effort Spent");

        for (LocalDate d : dailyEffortMap.keySet()) {
            effortLeft -= dailyEffortMap.get(d);
            String monthStr = d.getMonth().toString().substring(0, 3);
            effortSpentSeries.getData().add(new XYChart.Data(monthStr + " " + d.getDayOfMonth(), effortLeft));
        }

        this.getData().add(effortSpentSeries);

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


