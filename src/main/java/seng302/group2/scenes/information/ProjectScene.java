/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import seng302.group2.App;
import seng302.group2.project.team.person.Person;

/**
 * A class for displaying the Project Scene
 * @author crw73
 */
public class ProjectScene
{
    
    public static GridPane getProjectScene()
    {
        
        // <editor-fold defaultstate="collapsed" desc="Information Grid">
        
        // Set up a grid pane for displaying information in the window
        App.informationGrid = new GridPane();
        
        App.informationGrid.setAlignment(Pos.CENTER);
        App.informationGrid.setHgap(10);
        App.informationGrid.setVgap(10);
        App.informationGrid.setPadding(new Insets(25,25,25,25));

        // Adds a title to the grid
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        App.informationGrid.add(scenetitle, 0, 0, 2, 1);
        // At top-left (0, 0), spanning 2 columns and 1 row

        // A button to switch to the demo scene
        Button btn = new Button("Make Test Person");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        App.informationGrid.add(hbBtn, 1, 4);
        
        //The button event handler
        btn.setOnAction((event) ->
            {
                App.currentProject.addPerson(new Person());
            });
        
        // </editor-fold>
        return App.informationGrid;
    }
 
}
