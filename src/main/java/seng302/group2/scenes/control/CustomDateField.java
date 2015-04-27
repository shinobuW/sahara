package seng302.group2.scenes.control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Creates a custom date field which displays appropriate error messages when required.
 * Created by Codie on 02/04/2015
 */
public class CustomDateField extends VBox
{
    String errorMessage = "";
    TextField inputText = new TextField();
    Label errorMessageText = new Label();

    /**
     * Creates a label & and a text field with date layout prompts.
     * @param name The label for the date field
     */
    public CustomDateField(String name)
    {
        this.errorMessageText.setText(errorMessage);
        inputText.setPromptText("dd/mm/yyyy");
        HBox labelBox = new HBox();
        labelBox.setPrefWidth(175);
        labelBox.spacingProperty().setValue(0);
                        
        labelBox.getChildren().addAll(new Label(name));
        errorMessageText.setTextFill(Color.web("#ff0000"));
        HBox entry = new HBox();
        entry.setPrefWidth(175);
        entry.getChildren().addAll(labelBox, inputText);

        this.getChildren().add(entry);
    }

    /**
     * Returns the text inside the text field of the CustomDateField.
     * @return The text of the text field
     */
    public String getText()
    {
        return this.inputText.getText();
    }
    
    
    /**
     * Sets the text inside the text field of the CustomDateField.
     * @param text the text to be inserted into the text field
     */
    public void setText(String text)
    {
        this.inputText.setText(text);
    }


    /**
     * Returns whether or not the field is erroneous by checking it's children for error fields
     * @return
     */
    public boolean isErroneous()
    {
        return this.getChildren().contains(errorMessageText);
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
     * Shows the error field with the with the given text
     * @param errorMessage The error message to show
     */
    public void showErrorField(String errorMessage)
    {
        inputText.setStyle("-fx-border-color: red;");
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
