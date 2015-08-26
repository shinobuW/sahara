package seng302.group2.scenes.control;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.validation.ValidationStyle;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Creates a custom date field which displays appropriate error messages when required.
 * Created by Codie on 02/04/2015
 */
public class CustomDateField extends VBox implements SearchableControl {
    private String errorMessage = "";
    private TextField inputText = new TextField();
    private Label errorMessageText = new Label();
    Set<SearchableControl> searchControls = new HashSet<>();

    /**
     * Creates a label and a text field with date layout prompts.
     *
     * @param name The label for the date field
     */
    public CustomDateField(String name) {
        this.errorMessageText.setText(errorMessage);
        inputText.setPromptText("dd/mm/yyyy");
        HBox labelBox = new HBox();
        labelBox.setPrefWidth(175);
        labelBox.spacingProperty().setValue(0);
        labelBox.setAlignment(Pos.CENTER_LEFT);

        SearchableText label = new SearchableText(name.trim(), searchControls);
        label.setStyle("-fx-font-weight: bold");
        labelBox.getChildren().addAll(label);

        errorMessageText.setTextFill(Color.web("#ff0000"));
        HBox entry = new HBox();
        entry.setPrefWidth(175);
        entry.getChildren().addAll(labelBox, inputText);
        HBox.setHgrow(labelBox, Priority.ALWAYS);

        this.getChildren().add(entry);
    }

    /**
     * Creates a label and a text field with date layout prompts.
     *
     * @param name The label for the date field
     * @param controlCollection The collection of searchable controls to add this control too
     */
    public CustomDateField(String name, Collection<SearchableControl> controlCollection) {
        controlCollection.add(this);
        this.errorMessageText.setText(errorMessage);
        inputText.setPromptText("dd/mm/yyyy");
        HBox labelBox = new HBox();
        labelBox.setPrefWidth(175);
        labelBox.spacingProperty().setValue(0);
        labelBox.setAlignment(Pos.CENTER_LEFT);

        SearchableText label = new SearchableText(name.trim(), searchControls);
        label.setStyle("-fx-font-weight: bold");
        labelBox.getChildren().addAll(label);

        errorMessageText.setTextFill(Color.web("#ff0000"));
        HBox entry = new HBox();
        entry.setPrefWidth(175);
        entry.getChildren().addAll(labelBox, inputText);
        HBox.setHgrow(labelBox, Priority.ALWAYS);

        this.getChildren().add(entry);
    }

    /**
     * Returns the text inside the text field of the CustomDateField.
     *
     * @return The text of the text field
     */
    public String getText() {
        return this.inputText.getText();
    }


    /**
     * Sets the text inside the text field of the CustomDateField.
     *
     * @param text the text to be inserted into the text field
     */
    public void setText(String text) {
        this.inputText.setText(text);
    }


    /**
     * Returns whether or not the field is erroneous by checking it's children for error fields
     *
     * @return Whether or not the field is erroneous
     */
    public boolean isErroneous() {
        return this.getChildren().contains(errorMessageText);
    }

    /**
     * Shows the error field.
     */
    public void showErrorField() {
        errorMessageText.setStyle("-fx-border-color: red;");
        ValidationStyle.borderGlowRed(inputText);
        this.getChildren().remove(errorMessageText);    // Ensure that it is not shown already
        this.getChildren().add(errorMessageText);
    }


    /**
     * Shows the error field with the with the given text
     *
     * @param errorMessage The error message to show
     */
    public void showErrorField(String errorMessage) {
        this.errorMessageText.setText(errorMessage);
        showErrorField();
    }

    /**
     * Gets the input text field
     */
    public TextField getTextField() {
        return this.inputText;
    }

    /**
     * Hides the error field.
     */
    public void hideErrorField() {
        ValidationStyle.borderGlowRed(inputText);
        this.getChildren().remove(errorMessageText);
    }

    @Override
    public boolean query(String query) {
        boolean found = false;
        for (SearchableControl control : searchControls) {
            found = found || control.query(query);
        }
        return found;
    }
}
