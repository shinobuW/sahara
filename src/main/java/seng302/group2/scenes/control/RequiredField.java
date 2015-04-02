package seng302.group2.scenes.control;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Created by Jordane on 24/03/2015.
 */
public class RequiredField extends VBox
{
    String errorMessage = "";
    TextField errorMessageText = new TextField();

    /**
     * Creates a required label HBox inside of the VBox containing a Label with an appended red
     * asterisk.
     * @param node The node field that is required
     */
    public RequiredField(Node node)
    {
        this.errorMessageText.setText(errorMessage);

        HBox detail = new HBox();
        Label aster = new Label("*");
        aster.setTextFill(Color.web("#ff0000"));
        detail.getChildren().addAll(node, aster);

        this.getChildren().add(node);
    }


    /**
     * Shows the error field
     */
    public void showErrorField()
    {
        this.getChildren().remove(errorMessageText);    // Ensure that it is not shown already
        this.getChildren().add(errorMessageText);
    }


    /**
     * Shows the error field with the with the given text
     * @param errorMessage The error message to show
     */
    public void showErrorField(String errorMessage)
    {
        this.errorMessageText.setText(errorMessage);
        showErrorField();
    }


    /**
     * Hides the error field
     */
    public void hideErrorField()
    {
        this.getChildren().remove(errorMessageText);
    }
}
