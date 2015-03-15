/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

/**
 * A class for displaying the Person Scene
 * @author crw73
 */
public class PersonScene
{
    
    
    public static GridPane getPersonScene()
    {
        
        GridPane personGrid = new GridPane();
        personGrid.setAlignment(Pos.CENTER);
        personGrid.setHgap(10);
        personGrid.setVgap(10);
        personGrid.setPadding(new Insets(25,25,25,25));
        
        return personGrid;
    }
    
}
