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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author Shinobu
 */
public class CustomComboBox extends VBox
{
    boolean required;
    String errorMessage = "";
    Label errorMessageText = new Label();
    ObservableList<String> options = FXCollections.observableArrayList();
    ComboBox comboBox = new ComboBox(options);
    
    /**
     * Creates a required label HBox inside of the VBox containing a Label with an appended red
     * asterisk if the field is required.
     * @param name The node field
     * @param required Whether or not the field is required
     */
    public CustomComboBox(String name, boolean required)
    {
        this.required = required;
        this.errorMessageText.setText(errorMessage);

        HBox labelBox = new HBox();
        labelBox.setPrefWidth(165);
        labelBox.setAlignment(Pos.CENTER_LEFT);
        labelBox.spacingProperty().setValue(0);



        labelBox.getChildren().addAll(new Label(name));

        if (required)
        {
            Label aster = new Label(" * ");
            aster.setTextFill(Color.web("#ff0000"));
            labelBox.getChildren().add(aster);
        }

        HBox entry = new HBox();
        entry.setPrefWidth(175);
        entry.getChildren().addAll(labelBox, this.comboBox);
        entry.setHgrow(labelBox, Priority.ALWAYS);
        
        this.comboBox.setMinWidth(135);
        this.getChildren().add(entry);
    }
    
    /**
     * Displays error Field
     */
    public void showErrorField()
    {
        comboBox.setStyle("-fx-border-color: red;");
        this.getChildren().remove(errorMessageText);    // Ensure that it is not shown already
        this.getChildren().add(errorMessageText);
    }

    /**
     * Shows the error field with the with the given text.
     * @param errorMessage The error message to show
     */
    public void showErrorField(String errorMessage)
    {
        this.errorMessageText.setTextFill(Color.web("FF0000"));
        this.errorMessageText.setText(errorMessage);
        showErrorField();
    }

    /**
     * Add option item to Combo Box
     * @param item String item to add
     */
    public void addToComboBox(String item)
    {
        this.options.add(item); 
    }

    /**
     * Gets the inner combo box
     * @return The combo box
     */
    public ComboBox getComboBox()
    {
        return this.comboBox;
    }

    /** Gets the value of the chosen item in combo box
     * @return The string of the chosen item
     */
    public String getValue()
    {
        if (this.comboBox.getValue() != null)
        {
            return this.comboBox.getValue().toString();
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the value of the selected item of combo box
     * @param value value to set to 
     */
    public void setValue(String value)
    {
        this.comboBox.setValue(value);
    }


    /**
     * Hides the error field.
     */
    public void hideErrorField()
    {
        this.getChildren().remove(errorMessageText);
        this.comboBox.setStyle(null);
    }
        
}
