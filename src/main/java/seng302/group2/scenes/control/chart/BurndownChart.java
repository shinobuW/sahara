package seng302.group2.scenes.control.chart;

import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import seng302.group2.workspace.project.sprint.Sprint;


/**
* Created by btm38 on 31/07/15.
*/
public class BurndownChart extends LineChart {
    private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();
    private LineChart<Number,Number> burndownChart = new LineChart<>(xAxis,yAxis);

    public BurndownChart(Axis axis, Axis axis2) {
        super(axis, axis2);
    }

    public void populateGraph(Sprint currentSprint) {
        //define series
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Test Series 1");

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Test Series 2");

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


        series1.nodeProperty().setValue("-fx-stroke-width: 100px;");
        this.getData().add(series1);
        this.getData().add(series2);


    }
}


