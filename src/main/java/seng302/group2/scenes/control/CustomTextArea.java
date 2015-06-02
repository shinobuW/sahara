package seng302.group2.scenes.control;

import com.sun.javafx.scene.control.behavior.TextAreaBehavior;
import com.sun.javafx.scene.control.skin.TextAreaSkin;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Creates a custom text area which displays appropriate error messages when required.
 * Created by Codie on 02/04/2015
 */
public class CustomTextArea extends VBox
{
    String errorMessage = "";
    TextArea inputText = new TextArea();
   
    Label errorMessageText = new Label();

    /**
     * Creates a required label HBox inside of the VBox containing a Label with an appended red
     * asterisk.
     * @param name The node field that is required
     */
    public CustomTextArea(String name)
    {
        this.errorMessageText.setText(errorMessage);
        inputText.setWrapText(true);
        inputText.setPrefRowCount(5);
        
         
        HBox labelBox = new HBox();
        labelBox.setPrefWidth(175);
        labelBox.spacingProperty().setValue(0);
        labelBox.setAlignment(Pos.CENTER_LEFT);
                
        labelBox.getChildren().addAll(new Label(name));
        
        Insets insets = new Insets(0, 0, 5, 0);
        labelBox.setPadding(insets);
        
        VBox entry = new VBox();
        entry.setPrefWidth(175);
        entry.getChildren().addAll(labelBox, inputText);

        this.getChildren().add(entry);

        inputText.addEventFilter(KeyEvent.KEY_PRESSED, event ->
            {
                if (event.getCode() == KeyCode.TAB)
                {
                    TextAreaSkin skin = (TextAreaSkin) inputText.getSkin();
                    if (skin.getBehavior() != null)
                    {
                        TextAreaBehavior behavior = skin.getBehavior();
                        if (event.isControlDown())
                        {
                            behavior.callAction("InsertTab");
                        }
                        else
                        {
                            behavior.callAction("TraverseNext");
                        }
                        event.consume();
                    }

                }
            });
    }

        /**
     * Creates a required label HBox inside of the VBox containing a Label with an appended red
     * asterisk.
     * @param name The node field that is required
     * @param width The width of the area
     */
    public CustomTextArea(String name, int width)
    {
        this.errorMessageText.setText(errorMessage);
        inputText.setWrapText(true);
        inputText.setPrefRowCount(5);
        
         
        HBox labelBox = new HBox();
        labelBox.setPrefWidth(175);
        labelBox.spacingProperty().setValue(0);
                
        labelBox.getChildren().addAll(new Label(name));
        
        Insets insets = new Insets(0, 0, 5, 0);
        labelBox.setPadding(insets);
        
        VBox entry = new VBox();
        entry.setPrefWidth(width);
        entry.getChildren().addAll(labelBox, inputText);

        this.getChildren().add(entry);
    }
    
    /**
     * Returns the text inside the text field of the CustomTextArea.
     * @return The text of the text field
     */
    public String getText()
    {
        return this.inputText.getText();
    }

    
    /**
     * Sets the text inside the text field of the CustomTextArea.
     * @param text the text to be inserted into the text field
     */
    public void setText(String text)
    {
        this.inputText.setText(text);
    }

    /**
     * Shows the error field.
     */
    public void showErrorField()
    {
        inputText.setStyle("-fx-border-color: red;");
        this.getChildren().remove(errorMessageText);    // Ensure that it is not shown already
        this.getChildren().add(errorMessageText);
    }


    /**
     * Shows the error field with the with the given text.
     * @param errorMessage The error message to show
     */
    public void showErrorField(String errorMessage)
    {
        this.errorMessageText.setText(errorMessage);
        showErrorField();
    }


    /**
     * Hides the error field.
     */
    public void hideErrorField()
    {
        inputText.setStyle(null);
        this.getChildren().remove(errorMessageText);
    }
}

