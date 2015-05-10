package seng302.group2.scenes.information.Skill;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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

import static seng302.group2.scenes.MainScene.informationPane;

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
        /*informationPane.setAlignment(Pos.TOP_LEFT);
        informationPane.setHgap(10);
        informationPane.setVgap(10);*/
        informationPane.setPadding(new Insets(25,25,25,25));
        Label title = new Label("Skills in " + currentWorkspace.getShortName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

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

        return new ScrollPane(informationPane);
    }
}
