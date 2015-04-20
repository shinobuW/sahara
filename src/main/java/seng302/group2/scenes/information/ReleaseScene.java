/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information;

import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.App;
import seng302.group2.Global;
import static seng302.group2.Global.selectedTreeItem;
import seng302.group2.scenes.MainScene;
import static seng302.group2.scenes.MainScene.informationGrid;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;
import seng302.group2.workspace.release.Release;

/**
 *
 * @author Shinobu
 */
public class ReleaseScene
{
    /**
     * Information pane for Release
     * @param currentRelease selected Release
     * @return information pane
     */
     public static GridPane getReleaseScene(Release currentRelease)
    {
        informationGrid = new GridPane();

        informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);
        informationGrid.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentRelease.getShortName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnEdit = new Button("Edit");

        informationGrid.add(title, 0, 0, 3, 1);
        informationGrid.add(new Label("Description: "), 0, 2);
        informationGrid.add(new Label("Estimated Release Date"), 0, 3);
        informationGrid.add(new Label("Project"), 0, 4);
        
        informationGrid.add(new Label(currentRelease.getDescription()), 1, 2);
        informationGrid.add(new Label(currentRelease.getEstimatedReleaseDate().toString()), 1, 3);
        informationGrid.add(new Label(currentRelease.getProject().toString()), 1, 4);
        informationGrid.add(btnEdit, 1, 5);

        btnEdit.setOnAction((event) ->
            {
                App.content.getChildren().remove(informationGrid);
                ReleaseEditScene.getReleaseEditScene(currentRelease);
                App.content.getChildren().add(informationGrid);
            });

        return informationGrid;
    }
 
     /**
      * Refreshes the scene & treeview to show update data
      * @param release 
      */
    public static void refreshReleaseScene(Release release)
    {
	App.content.getChildren().remove(MainScene.informationGrid);
	App.content.getChildren().remove(MainScene.treeView);
	ReleaseScene.getReleaseScene(release);
	MainScene.treeView = new TreeViewWithItems(new TreeItem());
	ObservableList<TreeViewItem> children = observableArrayList();
	children.add(Global.currentWorkspace);

	MainScene.treeView.setItems(children);
	MainScene.treeView.setShowRoot(false);
	App.content.getChildren().add(MainScene.treeView);
	App.content.getChildren().add(MainScene.informationGrid);
	MainScene.treeView.getSelectionModel().select(selectedTreeItem);
    }
}
