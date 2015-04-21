/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Shinobu
 */
public class CustomComboBox extends VBox
{
    String errorMessage = "";
    Label errorMessageText = new Label();
    ObservableList<String> options = FXCollections.observableArrayList();
    ComboBox comboBox = new ComboBox(options);
    
    /**
     * Creates a required label HBox inside of the VBox containing a Label with an appended red
     * asterisk.
     * @param name The node field that is required
     */
    public CustomComboBox(String name)
    {
        this.errorMessageText.setText(errorMessage);

        HBox labelBox = new HBox();
        labelBox.setPrefWidth(165);
        labelBox.spacingProperty().setValue(0);
                
        labelBox.getChildren().addAll(new Label(name));
        
        HBox entry = new HBox();
        entry.setPrefWidth(175);
        entry.getChildren().addAll(labelBox, comboBox);
        
        comboBox.setStyle("-fx-pref-width: 135;");

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
     * Add option item to Combo Box
     * @param item String item to add
     */
    public void addToComboBox(String item)
    {
        this.options.add(item); 
    }
}
