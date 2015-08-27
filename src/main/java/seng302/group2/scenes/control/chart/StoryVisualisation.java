package seng302.group2.scenes.control.chart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;
import seng302.group2.scenes.control.PopOverTip;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.tasks.Task;

import java.text.DecimalFormat;

/**
 * Created by cvs20 on 27/08/15.
 */
public class StoryVisualisation extends Pane {

    public StoryVisualisation(Story story) {
        final int decimalPoint = 0;
        ObservableList<Task> tasks =  story.getTasks();

        ObservableList<Task> done = FXCollections.observableArrayList();
        ObservableList<Task> inProgress = FXCollections.observableArrayList();
        ObservableList<Task> rest = FXCollections.observableArrayList();

        for (Task task : tasks) {
            if (task.getState() == Task.TASKSTATE.DONE) {
                done.add(task);
            }
            else if (task.getState() == Task.TASKSTATE.IN_PROGRESS) {
                inProgress.add(task);
            }
            else {
                rest.add(task);
            }
        }


        double lengthGreen = getEffortSpent(done);
        double lengthBlue = getEffortSpent(inProgress);
        double lengthRed = getEffortLeft(inProgress) + getEffortLeft(inProgress);

        double overallLength = lengthBlue + lengthGreen + lengthRed;

        double percentageGreen = (lengthGreen / overallLength);
        double percentageBlue = (lengthBlue / overallLength);
        double percentageRed = (lengthRed / overallLength);


        double maxWidth = 340;
        double maxGreen = maxWidth * percentageGreen;
        double maxBlue = (maxWidth * percentageBlue) + maxGreen;

        //Creates visualisation bar
        DecimalFormat df = new DecimalFormat("##");

        GridPane visualGrid = new GridPane();

        Rectangle red = new Rectangle();
        Rectangle green = new Rectangle();
        Rectangle blue = new Rectangle();

        green.setWidth(maxGreen);
        green.setHeight(25);
        green.setArcWidth(15);
        green.setArcHeight(15);
        PopOverTip greenPO = new PopOverTip(green, new Text(" Percentage of Story Done: "
                + (df.format(percentageGreen * 100)) + "% "));
        greenPO.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);

        blue.setWidth(maxBlue);
        blue.setHeight(25);
        blue.setArcWidth(15);
        blue.setArcHeight(15);
        PopOverTip bluePO = new PopOverTip(blue, new Text(" Percentage of Story In Progress: "
                + (df.format(percentageBlue * 100)) + "% "));
        bluePO.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);

        red.setWidth(maxWidth);
        red.setHeight(25);
        red.setArcWidth(15);
        red.setArcHeight(15);
        PopOverTip redPO = new PopOverTip(red, new Text(" Remaining work to do: "
                + (df.format(percentageRed * 100)) + "%" ));
        redPO.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);

        red.setFill(Color.RED);
        green.setFill(Color.GREEN);
        blue.setFill(Color.BLUE);

        visualGrid.add(red, 0, 0, 1, 3);
        visualGrid.add(blue, 0, 0, 1, 2);
        visualGrid.add(green, 0, 0, 1, 1);

        this.getChildren().add(visualGrid);

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
