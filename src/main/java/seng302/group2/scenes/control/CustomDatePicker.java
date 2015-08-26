package seng302.group2.scenes.control;

import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.validation.ValidationStyle;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Creates a custom DatePicker which displays appropriate error messages when required.
 * Created by swi67 on 17/05/15.
 */
public class CustomDatePicker extends VBox implements SearchableControl {
    private boolean required = false;
    private String errorMessage = "";
    private Label errorMessageText = new Label();
    private DatePicker datePicker = new DatePicker();
    private Set<SearchableControl> searchControls = new HashSet<>();

    /**
     * Creates a required label HBox inside of the VBox containing a Label with an appended red
     * asterisk if the field is required.
     *
     * @param name     The node field
     * @param required Whether or not the field is required
     */
    public CustomDatePicker(String name, boolean required) {
        this.required = required;
        this.errorMessageText.setText(errorMessage);
        datePicker.setPrefWidth(175);


        HBox labelBox = new HBox();
        labelBox.setPrefWidth(175);

        errorMessageText.setTextFill(Color.web("#ff0000"));

        SearchableText label = new SearchableText(name.trim(), searchControls);
        label.setStyle("-fx-font-weight: bold");
        labelBox.getChildren().addAll(label);

        if (required) {
            Label aster = new Label(" * ");
            aster.setTextFill(Color.web("#ff0000"));
            labelBox.getChildren().add(aster);
        }

        HBox entry = new HBox();
        entry.setPrefWidth(175);
        entry.getChildren().addAll(labelBox, datePicker);
        HBox.setHgrow(labelBox, Priority.ALWAYS);


        this.getChildren().add(entry);
    }


    /**
     * Creates a required label HBox inside of the VBox containing a Label with an appended red
     * asterisk if the field is required.
     *
     * @param name     The node field
     * @param required Whether or not the field is required
     */
    public CustomDatePicker(String name, boolean required, Collection<SearchableControl> searchableControls) {
        searchableControls.add(this);
        this.required = required;
        this.errorMessageText.setText(errorMessage);

        HBox labelBox = new HBox();
        labelBox.setPrefWidth(165);
        labelBox.spacingProperty().setValue(0);
        labelBox.setAlignment(Pos.CENTER_LEFT);

        SearchableText label = new SearchableText(name.trim(), searchControls);
        label.setStyle("-fx-font-weight: bold");
        labelBox.getChildren().addAll(label);

        if (required) {
            Label aster = new Label(" * ");
            aster.setTextFill(Color.web("#ff0000"));
            labelBox.getChildren().add(aster);
        }


        HBox entry = new HBox();

        entry.setPrefWidth(175);
        entry.getChildren().addAll(labelBox, this.datePicker);
        HBox.setHgrow(labelBox, Priority.ALWAYS);

        this.datePicker.setStyle("-fx-pref-width: 135;");
        this.getChildren().add(entry);
    }

    /**
     * Sets the style of the date picker component to the default.
     */
    public void setDefaultStyle() {
        datePicker.setStyle("-fx-pref-width: 135;");
    }

    /**
     * Sets the tooltip of the date picker.
     * @param tool The tooltip
     */
    public void setTooltip(javafx.scene.control.Tooltip tool) {
        this.datePicker.setTooltip(tool);
    }

    /**
     * Removes the tooltip of the datepicker.
     */
    public void removeTooltip() {
        this.datePicker.setTooltip(null);
    }

    /**
     * Displays error Field
     */
    public void showErrorField() {
        ValidationStyle.borderGlowRed(datePicker);
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
        this.errorMessageText.setTextFill(Color.web("FF0000"));
        showErrorField();
    }

    /**
     * Hides the error field.
     */
    public void hideErrorField() {
        this.getChildren().remove(errorMessageText);
        ValidationStyle.borderGlowNone(datePicker);
    }

    /**
     * Gets the inner date picker
     *
     * @return The date picker
     */
    public DatePicker getDatePicker() {
        return this.datePicker;
    }

    /**
     * Gets the value of the selected date
     *
     * @return The value in LocalDate format
     */
    public LocalDate getValue() {
        if (this.datePicker.getValue() != null) {
            return this.datePicker.getValue();
        }
        else {
            return null;
        }
    }

    public void setDateRestrictions() {

    }

    /**
     * Sets the value of the date picker
     *
     * @param value value to set to
     */
    public void setValue(LocalDate value) {
        this.datePicker.setValue(value);
    }


    /**
     * Queries the date picker to find any elements containing the given query string. If found inside the date picker,
     * the picker border will be highlighted, and if found in the label text, the matching text will be highlighted.
     * @param query The query string to search
     * @return Whether any elements inside of the date picker or label were found to contain the query string
     */
    @Override
    public boolean query(String query) {
        boolean found = false;
        for (SearchableControl control : searchControls) {
            if (control.query(query)) {
                found = true;
            }
        }
        return found;
    }
}
