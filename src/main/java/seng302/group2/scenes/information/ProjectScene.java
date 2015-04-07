/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.App;
import static seng302.group2.App.informationGrid;
import static seng302.group2.Global.selectedTreeItem;
import seng302.group2.project.Project;

/**
 * A class for displaying the Project Scene
 * @author crw73
 * @author btm38
 */
@SuppressWarnings("deprecation")
public class ProjectScene
{
    /**
     * Gets the Project information display
     * @return The Project information display
     */
    public static GridPane getProjectScene()
    {
        
        Project currentProject = (Project) selectedTreeItem.getValue();
        informationGrid = new GridPane();

        informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);
        informationGrid.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentProject.getLongName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        informationGrid.add(title, 0, 0, 10, 1);
        informationGrid.add(new Label("Short Name: "), 0, 2);
        informationGrid.add(new Label("Description: "), 0, 3);

        informationGrid.add(new Label(currentProject.getShortName()), 1, 2);
        informationGrid.add(new Label(currentProject.getDescription()), 1, 3);
        return App.informationGrid;
    }
 
}
