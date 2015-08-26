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
        List<Log> logList;
        logList = currentSprint.getAllLogs();

        double maxEffortLeft = 0;
        double effortSpent = 0;

        /* Map which stores a (key, value) pair of (date: hours), where hours is the total n
        umber of hours logged on the date.
         */
        Map<LocalDate, Double> dailyEffortMap = new LinkedHashMap<>();

        for (Log log : logList) {
            if  (dailyEffortMap.containsKey(log.getStartDate().toLocalDate())) {
                Double sum = dailyEffortMap.get(log.getStartDate().toLocalDate());
                dailyEffortMap.put(log.getStartDate().toLocalDate(), sum + log.getDurationInHours());
            }
            else {
                dailyEffortMap.put(log.getStartDate().toLocalDate(), log.getDurationInHours());
            }

            maxEffortLeft += log.getDurationInHours();
        }

        for (LocalDate d : dailyEffortMap.keySet()) {
            System.out.println(d + ": " + dailyEffortMap.get(d));
        }
        System.out.println(maxEffortLeft);


        this.setTitle(currentSprint.getGoal() + " Burndown");

//        //define series
//        Series<LocalDate, Number> effortLeftSeries = new Series();
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");

        series.getData().add(new XYChart.Data("Jan", 23));
        series.getData().add(new XYChart.Data("Feb", 14));
        series.getData().add(new XYChart.Data("Mar", 15));
        series.getData().add(new XYChart.Data("Apr", 24));
        series.getData().add(new XYChart.Data("May", 34));
        series.getData().add(new XYChart.Data("Jun", 36));
        series.getData().add(new XYChart.Data("Jul", 22));
        series.getData().add(new XYChart.Data("Aug", 45));
        series.getData().add(new XYChart.Data("Sep", 43));
        series.getData().add(new XYChart.Data("Oct", 17));
        series.getData().add(new XYChart.Data("Nov", 29));
        series.getData().add(new XYChart.Data("Dec", 25));


        this.getData().add(series);

//
//        effortLeftSeries.setName("Effort Left");
//
//        Series<LocalDate, Number> effortSpentSeries = new Series();
//        effortSpentSeries.setName("Test Series 2");
//
//        Series<Number, Number> referenceVelocity = new Series();
//        referenceVelocity.setName("Reference Velocity");
//
//
//        referenceVelocity.getData().add(new XYChart.Data(0, 35));
//        referenceVelocity.getData().add(new XYChart.Data(11, 0));
//
//
//
//
//        this.getData().add(effortLeftSeries);
//        this.getData().add(effortSpentSeries);
//        this.getData().add(referenceVelocity);

    }

    public Series<Number, Number> effortLeftSeries(Sprint currentSprint) {
        List<Log> logList = new ArrayList<Log>();
        logList = currentSprint.getAllLogs();
        return new Series<>();

    }

    public Series<Number, Number> effortSpentSeries(Sprint currentSprint) {
        return null;
    }
}


