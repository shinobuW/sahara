package seng302.group2.scenes.information;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.dialog.CreateSkillDialog;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.skills.Skill;

import static seng302.group2.scenes.MainScene.informationGrid;

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
    public static Pane getSkillCategoryScene(Workspace currentWorkspace)
    {
        informationGrid = new VBox();
        /*informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);*/
        informationGrid.setPadding(new Insets(25,25,25,25));
        Label title = new Label("Skills in " + currentWorkspace.getShortName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnView = new Button("View");
        Button btnDelete = new Button("Delete");
        Button btnCreate = new Button("Create New Skill");

        VBox selectionButtons = new VBox();
        selectionButtons.spacingProperty().setValue(10);
        selectionButtons.getChildren().add(btnView);
        selectionButtons.getChildren().add(btnDelete);
        selectionButtons.setAlignment(Pos.TOP_CENTER);

        HBox createButton = new HBox();
        createButton.getChildren().add(btnCreate);
        createButton.setAlignment(Pos.CENTER_RIGHT);

        ListView skillBox = new ListView(currentWorkspace.getSkills());
        skillBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        informationGrid.getChildren().add(title);
        informationGrid.getChildren().add(skillBox);
        informationGrid.getChildren().add(selectionButtons);
        informationGrid.getChildren().add(createButton);

        btnView.setOnAction((event) ->
            {
                if (skillBox.getSelectionModel().getSelectedItem() != null)
                {
                    MainScene.treeView.selectItem((TreeViewItem)
                            skillBox.getSelectionModel().getSelectedItem());
                }
            });


        btnDelete.setOnAction((event) ->
            {
                if (skillBox.getSelectionModel().getSelectedItem() != null)
                {
                    ((Skill) skillBox.getSelectionModel().getSelectedItem()).deleteSkill();
                }
            });

        btnCreate.setOnAction((event) ->
            {
                CreateSkillDialog.show();
            });

        return MainScene.informationGrid;
    }
}
