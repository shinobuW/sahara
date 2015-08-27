package seng302.group2.scenes.control.chart;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;
import seng302.group2.scenes.control.PopOverTip;
import seng302.group2.workspace.project.story.Story;

/**
 * Created by cvs20 on 27/08/15.
 */
public class StoryVisualisation extends Pane {

    public StoryVisualisation(Story story) {

        //Creates visualisation bar
        GridPane visualGrid = new GridPane();

        Rectangle red = new Rectangle();
        Rectangle green = new Rectangle();
        Rectangle blue = new Rectangle();

        green.setWidth(120);
        green.setHeight(25);
        green.setArcWidth(15);
        green.setArcHeight(15);
        PopOverTip greenPO = new PopOverTip(green, new Text("Green"));
        greenPO.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);

        blue.setWidth(170);
        blue.setHeight(25);
        blue.setArcWidth(15);
        blue.setArcHeight(15);
        PopOverTip bluePO = new PopOverTip(blue, new Text("Blue"));
        bluePO.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);

        red.setWidth(230);
        red.setHeight(25);
        red.setArcWidth(15);
        red.setArcHeight(15);
        PopOverTip redPO = new PopOverTip(red, new Text("Red"));
        redPO.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);

        red.setFill(Color.RED);
        green.setFill(Color.GREEN);
        blue.setFill(Color.BLUE);

        visualGrid.add(red, 0, 0, 1, 3);
        visualGrid.add(blue, 0, 0, 1, 2);
        visualGrid.add(green, 0, 0, 1, 1);



        this.getChildren().add(visualGrid);
    }
}
