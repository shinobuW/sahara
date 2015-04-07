/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.control;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
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
                
        labelBox.getChildren().addAll(new Label(name));
        
        Insets insets = new Insets(0, 0, 5, 0);
        labelBox.setPadding(insets);
        
        VBox entry = new VBox();
        entry.setPrefWidth(175);
        entry.getChildren().addAll(labelBox, inputText);

        this.getChildren().add(entry);
    }
    
    public String getText()
    {
        return this.inputText.getText();
    }
}

