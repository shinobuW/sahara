/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableText;

import java.util.Collection;

/**
 * @author Shinobu
 */
public class CustomComboBox<T> extends VBox implements SearchableControl {
    private boolean required = false;
    private String errorMessage = "";
    private Label errorMessageText = new Label();
    private ObservableList<T> options = FXCollections.observableArrayList();
    private ComboBox<T> comboBox = new ComboBox<>(options);
    private Label astrLabel = new Label(" * ");
    private SearchableText titleLabel = new SearchableText();


    /**
     * Creates a required label HBox inside of the VBox containing a Label with an appended red
     * asterisk if the field is required.
     *
     * @param name     The node field
     */
    public CustomComboBox(String name) {
        this.errorMessageText.setText(errorMessage);

        HBox labelBox = new HBox();
        labelBox.setPrefWidth(165);
        labelBox.setAlignment(Pos.CENTER_LEFT);
        labelBox.spacingProperty().setValue(0);

        titleLabel.setText(name);
        labelBox.getChildren().addAll(titleLabel);

        if (required) {
            Label aster = astrLabel;
            aster.setTextFill(Color.web("#ff0000"));
            labelBox.getChildren().add(aster);
        }

        HBox entry = new HBox();
        entry.setPrefWidth(275);
        entry.getChildren().addAll(labelBox, this.comboBox);
        HBox.setHgrow(labelBox, Priority.ALWAYS);

        this.comboBox.setMinWidth(135);
        this.getChildren().add(entry);
    }

    /**
     * Creates a required label HBox inside of the VBox containing a Label with an appended red
     * asterisk if the field is required.
     *
     * @param name     The node field
     * @param required Whether or not the field is required
     */
    public CustomComboBox(String name, boolean required) {
        this.required = required;
        this.errorMessageText.setText(errorMessage);

        HBox labelBox = new HBox();
        labelBox.setPrefWidth(165);
        labelBox.setAlignment(Pos.CENTER_LEFT);
        labelBox.spacingProperty().setValue(0);

        titleLabel.setText(name);
        labelBox.getChildren().addAll(titleLabel);

        if (required) {
            Label aster = astrLabel;
            aster.setTextFill(Color.web("#ff0000"));
            labelBox.getChildren().add(aster);
        }

        HBox entry = new HBox();
        entry.setPrefWidth(275);
        entry.getChildren().addAll(labelBox, this.comboBox);
        HBox.setHgrow(labelBox, Priority.ALWAYS);

        this.comboBox.setMinWidth(135);
        this.getChildren().add(entry);
    }

    /**
     * Creates a required label HBox inside of the VBox containing a Label with an appended red
     * asterisk if the field is required.
     *
     * @param name     The node field
     * @param searchableControls The collection of searchable controls to add this to
     */
    public CustomComboBox(String name, Collection<SearchableControl> searchableControls) {
        searchableControls.add(this);
        this.errorMessageText.setText(errorMessage);

        HBox labelBox = new HBox();
        labelBox.setPrefWidth(165);
        labelBox.setAlignment(Pos.CENTER_LEFT);
        labelBox.spacingProperty().setValue(0);

        titleLabel.setText(name);
        labelBox.getChildren().addAll(titleLabel);

        if (required) {
            Label aster = astrLabel;
            aster.setTextFill(Color.web("#ff0000"));
            labelBox.getChildren().add(aster);
        }

        HBox entry = new HBox();
        entry.setPrefWidth(275);
        entry.getChildren().addAll(labelBox, this.comboBox);
        HBox.setHgrow(labelBox, Priority.ALWAYS);

        this.comboBox.setMinWidth(135);
        this.getChildren().add(entry);
    }

    /**
     * Creates a required label HBox inside of the VBox containing a Label with an appended red
     * asterisk if the field is required.
     *
     * @param name     The node field
     * @param required Whether or not the field is required
     * @param searchableControls The collection of searchable controls to add this to
     */
    public CustomComboBox(String name, boolean required, Collection<SearchableControl> searchableControls) {
        searchableControls.add(this);
        this.required = required;
        this.errorMessageText.setText(errorMessage);

        HBox labelBox = new HBox();
        labelBox.setPrefWidth(165);
        labelBox.setAlignment(Pos.CENTER_LEFT);
        labelBox.spacingProperty().setValue(0);

        titleLabel.setText(name);
        labelBox.getChildren().addAll(titleLabel);

        if (required) {
            Label aster = astrLabel;
            aster.setTextFill(Color.web("#ff0000"));
            labelBox.getChildren().add(aster);
        }

        HBox entry = new HBox();
        entry.setPrefWidth(275);
        entry.getChildren().addAll(labelBox, this.comboBox);
        HBox.setHgrow(labelBox, Priority.ALWAYS);

        this.comboBox.setMinWidth(135);
        this.getChildren().add(entry);
    }

    /**
     * Displays error Field
     */
    public void showErrorField() {
        comboBox.setStyle("-fx-border-color: red;");
        this.getChildren().remove(errorMessageText);    // Ensure that it is not shown already
        this.getChildren().add(errorMessageText);
    }

    /**
     * Disables the combobox.
     */
    public void disable() {
        titleLabel.setDisable(true);
        astrLabel.setDisable(true);
        this.comboBox.setDisable(true);
    }

    /**
     * Enables the combobox.
     */
    public void enable() {
        this.comboBox.setDisable(false);
    }

    /**
     * Sets the tooltip of the combobox.
     * @param tool The tooltip
     */
    public void setTooltip(Tooltip tool) {
        this.comboBox.setTooltip(tool);
    }

    /**
     * Removes the tooltip of the combobox.
     */
    public void removeTooltip() {
        this.comboBox.setTooltip(null);
    }

    /**
     * Shows the error field with the with the given text.
     *
     * @param errorMessage The error message to show
     */
    public void showErrorField(String errorMessage) {
        this.errorMessageText.setTextFill(Color.web("FF0000"));
        this.errorMessageText.setText(errorMessage);
        showErrorField();
    }

    /**
     * Add option item to Combo Box
     *
     * @param item String item to add
     */
    public void addToComboBox(T item) {
        this.options.add(item);
    }


    /**
     * Removes all options inside the combo box
     */
    public void clear() {
        options.clear();
    }

    /**
     * Gets the inner combo box
     *
     * @return The combo box
     */
    public ComboBox<T> getComboBox() {
        return this.comboBox;
    }

    /**
     * Gets the value of the chosen item in combo box
     *
     * @return The string of the chosen item
     */
    public T getValue() {
        if (this.comboBox.getValue() != null) {
            return this.comboBox.getValue();
        }
        else {
            return null;
        }
    }

    /**
     * Sets the value of the selected item of combo box
     *
     * @param value value to set to
     */
    public void setValue(T value) {
        this.comboBox.setValue(value);
    }

    /**
     * Hides the error field.
     */
    public void hideErrorField() {
        this.getChildren().remove(errorMessageText);
        this.comboBox.setStyle(null);
    }

    /**
     * Queries the combo box to find any elements containing the given query string. If found inside the combo box, the
     * combo box border will be highlighted, and if found in the label text, the matching text will be highlighted.
     * @param query The query string to search
     * @return Whether any elements inside of the combobox or label were found to contain the query string
     */
    @Override
    public boolean query(String query) {
        if (query.trim().isEmpty()) {
            comboBox.setStyle("-fx-border-color: inherit");
        }
        boolean foundCombo = false;
        for (T item : comboBox.getItems()) {
            if (item.toString().toLowerCase().contains(query.toLowerCase()) && !query.trim().isEmpty()) {
                foundCombo = true;
            }
        }
        if (foundCombo) {
            comboBox.setStyle("-fx-border-color: " + SearchableControl.highlightColour + ";");
        }
        else {
            comboBox.setStyle("-fx-border-color: inherit");
        }

        boolean foundText = titleLabel.query(query);

        return foundCombo || foundText;
    }
}
