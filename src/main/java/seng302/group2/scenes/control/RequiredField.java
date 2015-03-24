package seng302.group2.scenes.control;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * Created by Jordane on 24/03/2015.
 */
public class RequiredField extends HBox
{
    /**
     * Creates a required label HBox containing a Label with an appended red asterisk
     * @param node The node field that is required
     */
    public RequiredField(Node node)
    {
        Label aster = new Label("*");
        aster.setTextFill(Color.web("#ff0000"));
        this.getChildren().addAll(node, aster);
    }
}
