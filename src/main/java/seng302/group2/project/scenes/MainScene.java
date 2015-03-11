/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.project.scenes;

import java.io.IOException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import seng302.group2.project.Project;

/**
 * A class for holding JavaFX scenes used in the project
 * @author Jordane Lew (jml168)
 */
public final class MainScene
{
    public static Scene getMainScene()
    {
        // The root window box
        VBox root = new VBox();
        
        
        // <editor-fold defaultstate="collapsed" desc="Menu">
        
        // The menus and menu bar creation
        Menu fileMenu = new Menu("File");
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);
        root.getChildren().add(new StackPane(menuBar));
        
        // Create 'New...' MenuItem
        Menu newProjectBranch = new Menu("New...");

        MenuItem newProjectItem = new MenuItem("Project");
        newProjectItem.setOnAction((event) ->
            {
                // Add project code
            });
        newProjectBranch.getItems().add(newProjectItem);

        // Create 'Quit' MenuItem
        MenuItem quitProgramItem = new MenuItem("Quit");
        quitProgramItem.setOnAction((event) ->
            {
                System.exit(0);
            });
        
        // Add MenuItems to Menu
        fileMenu.getItems().addAll(newProjectBranch, new SeparatorMenuItem(), quitProgramItem);
        
        // </editor-fold>
        
        
        //<editor-fold defaultstate="collapsed" desc="Information Grid">
        
        // Set up a grid pane for displaying information in the window
        GridPane informationGrid = new GridPane();
        root.getChildren().add(informationGrid);
        informationGrid.setAlignment(Pos.CENTER);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);
        informationGrid.setPadding(new Insets(25,25,25,25));
        
        // Adds a title to the grid
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        informationGrid.add(scenetitle, 0, 0, 2, 1);
            // At top-left (0, 0), spanning 2 columns and 1 row

        // A button to switch to the demo scene
        Button btn = new Button("Save Test Project");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        informationGrid.add(hbBtn, 1, 4);
        
        //The button event handler
        btn.setOnAction((event) ->
            {
                Project proj = new Project("shortname", "A full name", "A description");
                Scene thisScene = (Scene) btn.getScene();
                try
                {
                    Project.Save(thisScene, proj);
                }
                catch (IOException e)
                {
                    System.out.println(e.toString());
                }
                /*Stage stage = (Stage) btn.getScene().getWindow();
                stage.setScene(Scenes.getStudentsTestScene());*/
            });
        
        // </editor-fold>

        
        return new Scene(root);
    }
}
