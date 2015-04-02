package seng302.group2.scenes.dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * Created by Codie on 02/04/2015
 */
public class CustomBrithDate extends VBox
{
    String errorMessage = "";
    TextField inputText = new TextField();
    Label errorMessageText = new Label();

    /**
     * Creates a required label HBox inside of the VBox containing a Label with an appended red
     * asterisk.
     * @param node The node field that is required
     */
    public CustomBrithDate(String name)
    {
        this.errorMessageText.setText(errorMessage);
        inputText.setPromptText("dd/mm/yyyy");
        HBox labelBox = new HBox();
        labelBox.setPrefWidth(175);
        labelBox.spacingProperty().setValue(0);
                        
        labelBox.getChildren().addAll(new Label(name));
        
        HBox entry = new HBox();
        entry.setPrefWidth(175);
        entry.getChildren().addAll(labelBox, inputText);

        this.getChildren().add(entry);
    }
}
