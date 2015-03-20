/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import seng302.group2.App;

/**
 * A class for displaying the Person Scene
 * @author crw73
 */
public class PersonScene
{
    
    
    public static GridPane getPersonScene()
    {
        
        App.informationGrid = new GridPane();
        
        App.informationGrid.setAlignment(Pos.CENTER);
        App.informationGrid.setHgap(10);
        App.informationGrid.setVgap(10);
        App.informationGrid.setPadding(new Insets(25,25,25,25));
        
        return App.informationGrid;
    }
    
}
