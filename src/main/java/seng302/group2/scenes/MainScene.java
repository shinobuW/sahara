/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import seng302.group2.App;
import seng302.group2.project.team.person.Person;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;
import seng302.group2.scenes.menu.MainMenuBar;


/**
 * A class for holding JavaFX scenes used in the project
 * @author Jordane Lew (jml168)
 */
public class MainScene
{
    public static Scene getMainScene()
    {
        // The root window box
        VBox root = new VBox();
        HBox content = new HBox();
        
        MenuBar menuBar = MainMenuBar.getMainMenuBar();
        root.getChildren().add(new StackPane(menuBar));
        
        
        // <editor-fold defaultstate="collapsed" desc="Information Grid">
        
        // Set up a grid pane for displaying information in the window
        GridPane informationGrid = new GridPane();
        root.getChildren().add(content);
        
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
        Button btn = new Button("Make Test Person");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        informationGrid.add(hbBtn, 1, 4);
        
        //The button event handler
        btn.setOnAction((event) ->
            {
                String birthDatePattern = "dd/MM/yyyy";
                
                try
                {
                    Person newPerson = new Person("shawty", "first", "last",
                            "a@b.com", "desc",
                            new SimpleDateFormat(birthDatePattern).parse("29/05/1985"));
                    App.currentProject.addPerson(newPerson);
                }
                catch (ParseException e)
                {
                }
                
                
                /*Stage stage = (Stage) btn.getScene().getWindow();
                stage.setScene(Scenes.getStudentsTestScene());*/
            });
        
        // </editor-fold>

        // Old: TreeView display = ListDisplay.getProjectTree();  // (Manual)
        // Create the display menu from the project tree
        TreeViewWithItems display = new TreeViewWithItems(new TreeItem());
        ObservableList<TreeViewItem> children = observableArrayList();
        
        /*
        TreeViewData projectTree = new TreeViewData(
                    App.currentProject.getShortName(),
                    App.currentProject,
                    App.currentProject.getClass()
                );
        */
        
        children.add(App.currentProject);
        
        display.setItems(children);
        display.setShowRoot(false);
        
        content.getChildren().add(display);
        content.getChildren().add(informationGrid);

        return new Scene(root);
    }
}
