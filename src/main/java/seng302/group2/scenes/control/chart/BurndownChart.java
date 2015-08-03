package seng302.group2.scenes.control.chart;

import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import seng302.group2.workspace.project.sprint.Sprint;


/**
 * A class for displaying a burndown chart.
 * Created by btm38 on 31/07/15.
*/
public class BurndownChart extends LineChart {
    private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();
    private LineChart<Number,Number> burndownChart = new LineChart<>(xAxis,yAxis);

    public BurndownChart(Axis axis, Axis axis2) {
        super(axis, axis2);
        try {
            String css = this.getClass().getResource("/styles/chart.css").toExternalForm();
            this.getStylesheets().add(css);
            //this.getStylesheets().add("/main/java/seng302.group2/util/style/chart.css");
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


        this.setTitle(currentSprint.getGoal() + " burndown");
        //define series
        Series<Number, Number> series1 = new Series();
        series1.setName("Test Series 1");

        Series<Number, Number> series2 = new Series();
        series2.setName("Test Series 2");

        Series<Number, Number> referenceVelocity = new Series();
        referenceVelocity.setName("Reference Velocity");

        //populate series with mock data
        series1.getData().add(new XYChart.Data(0, 0));
        series1.getData().add(new XYChart.Data(1, 10));
        series1.getData().add(new XYChart.Data(2, 11));
        series1.getData().add(new XYChart.Data(3, 13));
        series1.getData().add(new XYChart.Data(4, 13));
        series1.getData().add(new XYChart.Data(5, 13));
        series1.getData().add(new XYChart.Data(6, 15));
        series1.getData().add(new XYChart.Data(7, 19));
        series1.getData().add(new XYChart.Data(8, 22));
        series1.getData().add(new XYChart.Data(9, 25));
        series1.getData().add(new XYChart.Data(10, 28));
        series1.getData().add(new XYChart.Data(11, 35));

        series2.getData().add(new XYChart.Data(0, 35));
        series2.getData().add(new XYChart.Data(1, 25));
        series2.getData().add(new XYChart.Data(2, 24));
        series2.getData().add(new XYChart.Data(3, 22));
        series2.getData().add(new XYChart.Data(4, 22));
        series2.getData().add(new XYChart.Data(5, 22));
        series2.getData().add(new XYChart.Data(6, 20));
        series2.getData().add(new XYChart.Data(7, 16));
        series2.getData().add(new XYChart.Data(8, 13));
        series2.getData().add(new XYChart.Data(9, 10));
        series2.getData().add(new XYChart.Data(10, 7));
        series2.getData().add(new XYChart.Data(11, 0));

        referenceVelocity.getData().add(new XYChart.Data(0, 35));
        referenceVelocity.getData().add(new XYChart.Data(11, 0));




        //referenceVelocity.nodeProperty().get().setStyle("-fx-stroke-width: 1px;");
        this.getData().add(series1);
        this.getData().add(series2);
        this.getData().add(referenceVelocity);

    }
}


