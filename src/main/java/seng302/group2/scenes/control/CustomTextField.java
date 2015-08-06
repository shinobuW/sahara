package seng302.group2.scenes.control;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableText;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Creates a custom text field which displays appropriate error messages when required.
 * Created by Codie on 02/04/2015
 */
public class CustomTextField extends VBox implements SearchableControl {
    private String errorMessage = "";
    private TextField inputText = new TextField();
    private Label errorMessageText = new Label();
    private Set<SearchableControl> searchControls = new HashSet<>();

    /**
     * Creates a required label HBox inside of the VBox containing a Label with an appended red
     * asterisk.
     *
     * @param name The node field that is required
     */
    public CustomTextField(String name) {
        this.errorMessageText.setText(errorMessage);

        HBox labelBox = new HBox();
        labelBox.setPrefWidth(175);
        labelBox.spacingProperty().setValue(0);
        labelBox.setAlignment(Pos.CENTER_LEFT);

        labelBox.getChildren().addAll(new SearchableText(name, searchControls));

        HBox entry = new HBox();
        entry.setPrefWidth(175);
        entry.getChildren().addAll(labelBox, inputText);
        HBox.setHgrow(labelBox, Priority.ALWAYS);

        this.getChildren().add(entry);
    }

    /**
     * Creates a required label HBox inside of the VBox containing a Label with an appended red
     * asterisk.
     *
     * @param name The node field that is required
     * @param controlCollection The search control collection to add this control to
     */
    public CustomTextField(String name, Collection<SearchableControl> controlCollection) {

        controlCollection.add(this);
        this.errorMessageText.setText(errorMessage);

        HBox labelBox = new HBox();
        labelBox.setPrefWidth(175);
        labelBox.spacingProperty().setValue(0);
        labelBox.setAlignment(Pos.CENTER_LEFT);

        labelBox.getChildren().addAll(new SearchableText(name, searchControls));

        HBox entry = new HBox();
        entry.setPrefWidth(175);
        entry.getChildren().addAll(labelBox, inputText);
        HBox.setHgrow(labelBox, Priority.ALWAYS);

        this.getChildren().add(entry);
    }

    /**
     * Returns the text inside the text field of the CustomTextField.
     *
     * @return The text of the text field
     */
    public String getText() {
        return this.inputText.getText();
    }


    /**
     * Sets the text inside the text field of the CustomTextField.
     *
     * @param text the text to be inserted into the text field
     */
    public void setText(String text) {
        this.inputText.setText(text);
    }


    /**
     * Shows the error field.
     */
    public void showErrorField() {
        inputText.setStyle("-fx-border-color: red;");
        this.getChildren().remove(errorMessageText);    // Ensure that it is not shown already
        this.getChildren().add(errorMessageText);
    }


    /**
     * Shows the error field with the with the given text.
     *
     * @param errorMessage The error message to show
     */
    public void showErrorField(String errorMessage) {
        this.errorMessageText.setText(errorMessage);
        showErrorField();
    }


    /**
     * Hides the error field.
     */
    public void hideErrorField() {
        inputText.setStyle(null);
        this.getChildren().remove(errorMessageText);
    }

    @Override
    public boolean query(String query) {
        query = query.toLowerCase();
        if (query.isEmpty()) {
            inputText.setStyle("-fx-border-color: inherit");
            return false;
        }

        boolean found = false;
        for (SearchableControl control : searchControls) {
            found = found || control.query(query);
        }

        if (inputText.getText().toLowerCase().contains(query)) {
            found = true;
            inputText.setStyle("-fx-border-color: " + SearchableControl.highlightColour);
        }
        else {
            inputText.setStyle("-fx-border-color: inherit");
        }

        return found;
    }
}
