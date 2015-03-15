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
 * A class for displaying the Project Scene
 * @author crw73
 */
public class ProjectScene
{
    
    public static GridPane getProjectScene()
    {
        
        GridPane projectGrid = new GridPane();
        projectGrid.setAlignment(Pos.CENTER);
        projectGrid.setHgap(10);
        projectGrid.setVgap(10);
        projectGrid.setPadding(new Insets(25,25,25,25));
                
        return projectGrid;
    }
 
}
