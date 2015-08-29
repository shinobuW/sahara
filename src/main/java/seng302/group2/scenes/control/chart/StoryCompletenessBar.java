package seng302.group2.scenes.control.chart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;
import seng302.group2.scenes.control.PopOverTip;
import seng302.group2.util.conversion.ColorUtils;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.tasks.Task;

import java.text.DecimalFormat;

/**
 * Created by cvs20 on 27/08/15.
 */
public class StoryCompletenessBar extends Pane {

    public StoryCompletenessBar(Story story) {
        ObservableList<Task> tasks =  story.getTasks();

        ObservableList<Task> done = FXCollections.observableArrayList();
        ObservableList<Task> inProgress = FXCollections.observableArrayList();
        ObservableList<Task> rest = FXCollections.observableArrayList();

        for (Task task : tasks) {
            if (task.getLane() == Task.TASKSTATE.DONE || task.getState() == Task.TASKSTATE.DEFERRED) {
                done.add(task);
            }
            else if (task.getLane() == Task.TASKSTATE.NOT_STARTED) {
                rest.add(task);
            }
            else {
                inProgress.add(task);
            }
        }


        double lengthGreen = getEffortSpent(done);
        double lengthBlue = getEffortSpent(inProgress);
        double lengthRed = getEffortLeft(inProgress) + getEffortLeft(rest);

        double overallLength = lengthBlue + lengthGreen + lengthRed;

        double percentageGreen = 0;
        double percentageBlue = 0;
        double percentageRed = 1;

        if (overallLength != 0.0) {
            percentageGreen = (lengthGreen / overallLength);
            percentageBlue = (lengthBlue / overallLength);
            percentageRed = (lengthRed / overallLength);
        }

        double maxWidth = 340;
        double maxGreen = maxWidth * percentageGreen;
        double maxBlue = (maxWidth * percentageBlue) + maxGreen;

        //Creates visualisation bar
        DecimalFormat df = new DecimalFormat("##");

        GridPane visualGrid = new GridPane();


        Rectangle green = new Rectangle(maxGreen, 25);
        Rectangle greenOff = new Rectangle(15, 25);
        greenOff.setFill(Color.TRANSPARENT);
        Rectangle greenSquare = new Rectangle(maxGreen - 15, 25);
        HBox greenBg = new HBox();
        greenBg.getChildren().addAll(greenOff, greenSquare);


        green.setArcWidth(15);
        green.setArcHeight(15);
        PopOverTip greenPO = new PopOverTip(green, new Text(" Percentage of Story Done: "
                + (df.format(percentageGreen * 100)) + "% "));
        greenPO.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);

        Rectangle blue = new Rectangle(maxBlue - maxGreen, 25);
        PopOverTip bluePO = new PopOverTip(blue, new Text(" Percentage of Story In Progress: "
                + (df.format(percentageBlue * 100)) + "% "));
        bluePO.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);

        Rectangle red = new Rectangle(maxWidth - maxBlue, 25);
        Rectangle redSquare = new Rectangle(maxWidth - maxBlue - 15, 25);
        red.setArcWidth(15);
        red.setArcHeight(15);
        PopOverTip redPO = new PopOverTip(red, new Text(" Remaining work to do: "
                + (df.format(percentageRed * 100)) + "%" ));
        redPO.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);

        red.setFill(ColorUtils.toColor(Task.TASKSTATE.BLOCKED.getColourString()));  // Red
        redSquare.setFill(red.getFill());
        green.setFill(ColorUtils.toColor(Task.TASKSTATE.DONE.getColourString()));  // Green
        greenSquare.setFill(green.getFill());
        blue.setFill(ColorUtils.toColor(Task.TASKSTATE.VERIFY.getColourString()));  // Blue


        if (maxBlue - maxGreen > 0.01) {
            if (maxGreen > 15 && maxGreen < maxWidth - 15) {
                visualGrid.add(greenBg, 0, 0);
            }
            if (maxGreen < 0.1) {
                if (maxGreen + maxBlue > maxWidth - 0.01) {
                    // round both
                    blue.setArcHeight(15);
                    blue.setArcWidth(15);
                    visualGrid.add(blue, 1, 0);
                }
                else {
                    // round left
                    HBox blueBox = new HBox();
                    Rectangle blueSquare = new Rectangle(maxBlue - maxGreen, blue.getHeight());
                    blueSquare.setFill(blue.getFill());
                    blue.setArcWidth(15);
                    blue.setArcHeight(15);
                    Rectangle transRec = new Rectangle(blue.getWidth() / 2.0, blue.getHeight());
                    transRec.setFill(Color.TRANSPARENT);
                    blueBox.getChildren().add(transRec);
                    blueBox.getChildren().add(blueSquare);
                    visualGrid.add(blueBox, 1, 0);
                    visualGrid.add(redSquare, 2, 0);
                    visualGrid.add(blue, 1, 0);
                }
            }
            else if (maxGreen + maxBlue > maxWidth - 0.01) {
                // round right
                Rectangle blueSquare = new Rectangle(maxBlue - 7.5, blue.getHeight());
                blueSquare.setFill(blue.getFill());
                blue.setArcWidth(15);
                blue.setArcHeight(15);
                visualGrid.add(blueSquare, 1, 0);
                visualGrid.add(blue, 1, 0);
            }
            else {
                // normal
                visualGrid.add(blue, 1, 0);
            }
        }

        if (maxGreen + maxBlue > 7 && !visualGrid.getChildren().contains(redSquare)) {
            visualGrid.add(redSquare, 2, 0);
        }
        visualGrid.add(green, 0, 0);

        visualGrid.add(red, 2, 0);

        this.getChildren().add(visualGrid);

        System.out.println(visualGrid.getChildren());

    }

    public double getEffortSpent(ObservableList<Task> taskList) {
        double totalEffortSpent = 0;
        for (Task task : taskList) {
            totalEffortSpent += task.getEffortSpent();
        }
        return totalEffortSpent;
    }

    public double getEffortLeft(ObservableList<Task> taskList) {
        double totalEffortLeft = 0;
        for (Task task : taskList) {
            totalEffortLeft += task.getEffortLeft();
        }
        return totalEffortLeft;
    }

}
