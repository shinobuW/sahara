package seng302.group2.scenes.control;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 *
 * Created by Codie on 02/04/2015
 */
public class CustomTextField extends VBox
{
    String errorMessage = "";
    TextField inputText = new TextField();
    Label errorMessageText = new Label();

    /**
     * Creates a required label HBox inside of the VBox containing a Label with an appended red
     * asterisk.
     * @param node The node field that is required
     */
    public CustomTextField(String name)
    {
        this.errorMessageText.setText(errorMessage);

        HBox labelBox = new HBox();
        labelBox.setPrefWidth(175);
        labelBox.spacingProperty().setValue(0);
                
        labelBox.getChildren().addAll(new Label(name));
        
        HBox entry = new HBox();
        entry.setPrefWidth(175);
        entry.getChildren().addAll(labelBox, inputText);

        this.getChildren().add(entry);
    }
    
    
    public String getText()
    {
        return this.inputText.getText();
    }
    
    public void setText(String text)
    {
        this.inputText.setText(text);
    }
    

    /**
     * Shows the error field
     */
    public void showErrorField()
    {
        inputText.setStyle("-fx-border-color: red;");
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
        inputText.setStyle(null);
        this.getChildren().remove(errorMessageText);
    }
}
