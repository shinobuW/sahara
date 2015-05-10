package seng302.group2.scenes.information.Release;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.dialog.CreateReleaseDialog;
import seng302.group2.scenes.listdisplay.ReleaseCategory;
import seng302.group2.scenes.listdisplay.TreeViewItem;

import static seng302.group2.scenes.MainScene.informationPane;

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
    public static ScrollPane getReleaseCategoryScene(ReleaseCategory selectedCategory)
    {
        informationPane = new VBox(10);
        /*informationPane.setAlignment(Pos.TOP_LEFT);
        informationPane.setHgap(10);
        informationPane.setVgap(10);*/
        informationPane.setPadding(new Insets(25,25,25,25));
        Label title = new Label("Releases in " + selectedCategory.getProject().toString());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnView = new Button("View");
        //Button btnDelete = new Button("Delete");
        Button btnCreate = new Button("Create New Release");

        HBox selectionButtons = new HBox();
        selectionButtons.spacingProperty().setValue(10);
        selectionButtons.getChildren().add(btnView);
        selectionButtons.getChildren().add(btnCreate);
        //selectionButtons.getChildren().add(btnDelete);
        selectionButtons.setAlignment(Pos.TOP_LEFT);


        ListView releaseBox = new ListView(selectedCategory.getProject().getReleases());
        releaseBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        releaseBox.setMaxWidth(275);

        informationPane.getChildren().add(title);
        informationPane.getChildren().add(releaseBox);
        informationPane.getChildren().add(selectionButtons);

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

        return new ScrollPane(informationPane);
    }
}
