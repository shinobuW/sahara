package seng302.group2.scenes.information;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.dialog.CreateProjectDialog;
import seng302.group2.scenes.dialog.CreateReleaseDialog;
import seng302.group2.scenes.listdisplay.ReleaseCategory;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.release.Release;

import static seng302.group2.scenes.MainScene.informationGrid;

/**
 * A class for displaying all releases in a project.
 * @author David Moseley
 */
public class ReleaseCategoryScene
{
    /**
     * Gets the Release Category Scene
     * @param selectedCategory The category currently selected
     * @return The release category info scene
     */
    public static GridPane getReleaseCategoryScene(ReleaseCategory selectedCategory)
    {
        informationGrid = new GridPane();
        informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);
        informationGrid.setPadding(new Insets(25,25,25,25));
        Label title = new Label("Releases in " + selectedCategory.getProject().toString());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnView = new Button("View");
        //Button btnDelete = new Button("Delete");
        Button btnCreate = new Button("Create New Release");

        VBox selectionButtons = new VBox();
        selectionButtons.spacingProperty().setValue(10);
        selectionButtons.getChildren().add(btnView);
        //selectionButtons.getChildren().add(btnDelete);
        selectionButtons.setAlignment(Pos.TOP_CENTER);

        HBox createButton = new HBox();
        createButton.getChildren().add(btnCreate);
        createButton.setAlignment(Pos.CENTER_RIGHT);

        ListView releaseBox = new ListView(selectedCategory.getProject().getReleases());
        releaseBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        informationGrid.add(title, 0, 1, 5, 1);
        informationGrid.add(releaseBox, 0, 2);
        informationGrid.add(selectionButtons, 1, 2);
        informationGrid.add(createButton, 0, 3);

        btnView.setOnAction((event) ->
            {
                if (releaseBox.getSelectionModel().getSelectedItem() != null)
                {
                    MainScene.treeView.selectItem((TreeViewItem)
                            releaseBox.getSelectionModel().getSelectedItem());
                }
            });


        /*btnDelete.setOnAction((event) ->
            {
                if (releaseBox.getSelectionModel().getSelectedItem() != null)
                {
                    Release.deleteRelease(
                            (Release) releaseBox.getSelectionModel().getSelectedItem());
                }
            });*/

        btnCreate.setOnAction((event) ->
            {
                CreateReleaseDialog.show(selectedCategory.getProject());
            });

        return MainScene.informationGrid;
    }
}