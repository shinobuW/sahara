/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information.Release;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.workspace.release.Release;

import static seng302.group2.scenes.MainScene.informationPane;

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
    public static ScrollPane getReleaseScene(Release currentRelease)
    {
        informationPane = new VBox(10);

        /*informationPane.setAlignment(Pos.TOP_LEFT);
        informationPane.setHgap(10);
        informationPane.setVgap(10);*/
        informationPane.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentRelease.getShortName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnEdit = new Button("Edit");

        informationPane.getChildren().add(title);
        informationPane.getChildren().add(new Label("Release Description: "
                + currentRelease.getDescription()));
        informationPane.getChildren().add(new Label("Estimated Release Date: "
                + currentRelease.getDateString()));
        informationPane.getChildren().add(new Label("Project: "
                + currentRelease.getProject().toString()));
        informationPane.getChildren().add(btnEdit);

        btnEdit.setOnAction((event) ->
            {
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.RELEASE_EDIT, currentRelease);
            });

        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;
    }
 
    /**
    * Refreshes the scene and treeview to show update data
    * @param release The release to show the information of
    */
    public static void refreshReleaseScene(Release release)
    {
        SceneSwitcher.changeScene(SceneSwitcher.ContentScene.RELEASE, release);
    }
}
