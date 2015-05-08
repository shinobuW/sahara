/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;
import seng302.group2.workspace.release.Release;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.Global.selectedTreeItem;
import static seng302.group2.scenes.MainScene.informationGrid;

/**
 *
 * @author Shinobu
 */
public class ReleaseScene
{
    /**
     * Gets the information scene for a release
     * @param currentRelease the release to display the information of
     * @return the information scene for a release
     */
    public static Pane getReleaseScene(Release currentRelease)
    {
        informationGrid = new VBox(10);

        /*informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);*/
        informationGrid.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentRelease.getShortName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnEdit = new Button("Edit");

        informationGrid.getChildren().add(title);
        informationGrid.getChildren().add(new Label("Release Description: "
                + currentRelease.getDescription()));
        informationGrid.getChildren().add(new Label("Estimated Release Date: "
                + currentRelease.getDateString()));
        informationGrid.getChildren().add(new Label("Project: "
                + currentRelease.getProject().toString()));
        informationGrid.getChildren().add(btnEdit);

        btnEdit.setOnAction((event) ->
            {
                App.content.getChildren().remove(informationGrid);
                ReleaseEditScene.getReleaseEditScene(currentRelease);
                App.content.getChildren().add(informationGrid);
            });

        return informationGrid;
    }
 
    /**
    * Refreshes the scene and treeview to show update data
    * @param release The release to show the information of
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
