//package seng302.group2.scenes.control.chart;
//
//import javafx.scene.chart.XYChart;
//import javafx.util.converter.LocalDateStringConverter;
//import javafx.util.converter.LocalDateTimeStringConverter;
//import junit.framework.TestCase;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import seng302.group2.workspace.project.sprint.Sprint;
//import seng302.group2.workspace.project.story.tasks.Log;
//import seng302.group2.workspace.project.story.tasks.Task;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
///**
// *
// * Created by Bronson on 6/09/2015.
// */
//public class BurndownChartTest extends TestCase {
//    BurndownChart burndown = new BurndownChart();
//    Sprint newSprint = new Sprint();
//    Task newTask = new Task();
//
//    @BeforeClass
//    public void setUp() {
//        newTask.setEffortLeft(1200);
//        LocalDate newDate = LocalDate.of(2015, 01, 05);
//        LocalDateTime newLocalDate = newDate.atTime(13, 30);
//        double effortLeftDifference = newTask.getEffortLeft() - 10;
//        Log taskLog = new Log(newTask, "test log", null, 60, newLocalDate, effortLeftDifference);
//        Log taskLog2 = new Log(newTask, "test log 2", null, 60, newLocalDate, effortLeftDifference)
//        newTask.add(taskLog, effortLeftDifference);
//
//    }
//
//
//    @Test
//    public void testCreateEffortSpentSeries() {
//        XYChart.Series effortSpentSeries = burndown.createEffortSpentSeries(newSprint);
//
//
//    }
//
//    @Test
//    public void testCreateReferenceVelocity() {
//        XYChart.Series referenceVelocitySeries = burndown.createReferenceVelocity(newSprint);
//
//    }
//
//    @Test
//    public void testCreateLEffortLeftSeries() {
//        XYChart.Series effortLeftSeries = burndown.createLEffortLeftSeries(newSprint);
//
//    }
//}