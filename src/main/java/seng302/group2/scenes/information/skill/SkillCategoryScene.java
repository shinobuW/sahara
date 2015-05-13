package seng302.group2.scenes.information.skill;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.dialog.CreateSkillDialog;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.skills.Skill;

import static seng302.group2.scenes.MainScene.informationPane;
import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;

/**
 * A class for displaying all skills currently created in a workspace.
 * @author David Moseley
 */
public class SkillCategoryScene
{
    /**
     * Gets the Skill Category Scene
     * @param currentWorkspace The workspace currently being used
     * @return The skill category info scene
     */
    public static ScrollPane getSkillCategoryScene(Workspace currentWorkspace)
    {
        informationPane = new VBox(10);

        informationPane.setPadding(new Insets(25,25,25,25));
        Label title = new TitleLabel("Skills in " + currentWorkspace.getShortName());

        Button btnView = new Button("View");
        Button btnDelete = new Button("Delete");
        Button btnCreate = new Button("Create New Skill");

        HBox selectionButtons = new HBox();
        selectionButtons.spacingProperty().setValue(10);
        selectionButtons.getChildren().add(btnView);
        selectionButtons.getChildren().add(btnDelete);
        selectionButtons.getChildren().add(btnCreate);
        selectionButtons.setAlignment(Pos.TOP_LEFT);

        ListView skillBox = new ListView(currentWorkspace.getSkills());
        skillBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        skillBox.setMaxWidth(275);

        informationPane.getChildren().add(title);
        informationPane.getChildren().add(skillBox);
        informationPane.getChildren().add(selectionButtons);


        btnView.setOnAction((event) ->
        {
            if (skillBox.getSelectionModel().getSelectedItem() != null) {
                MainScene.treeView.selectItem((TreeViewItem)
                        skillBox.getSelectionModel().getSelectedItem());
            }
        });

        btnDelete.setOnAction((event) ->
        {
            if (skillBox.getSelectionModel().getSelectedItem() != null) {
                showDeleteDialog((TreeViewItem)
                        skillBox.getSelectionModel().getSelectedItem());
            }
        });

        btnCreate.setOnAction((event) ->
            {
                CreateSkillDialog.show();
            });

        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;
    }
}
