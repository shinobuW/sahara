package seng302.group2.scenes.information;

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
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;
import seng302.group2.workspace.skills.Skill;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.Global.selectedTreeItem;
import static seng302.group2.scenes.MainScene.informationGrid;

/**
 * A class for displaying the skill scene
 * @author drm127
 */
@SuppressWarnings("deprecation")
public class SkillScene
{
    /**
     * Gets the Skill information scene
     * @param currentSkill the skill to show the information of
     * @return The Skill information scene
     */
    public static GridPane getSkillScene(Skill currentSkill)
    {
        informationGrid = new GridPane();

        informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);
        informationGrid.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentSkill.getShortName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnEdit = new Button("Edit");

        informationGrid.add(title, 0, 0, 3, 1);
        informationGrid.add(new Label("Description: "), 0, 2);
        
        informationGrid.add(new Label(currentSkill.getDescription()), 1, 2);
        informationGrid.add(btnEdit, 1, 3);

        btnEdit.setOnAction((event) ->
            {
                App.content.getChildren().remove(informationGrid);
                SkillEditScene.getSkillEditScene(currentSkill);
                App.content.getChildren().add(informationGrid);
            });

        return informationGrid;
    }
 
    public static void refreshSkillScene(Skill skill)
    {
	App.content.getChildren().remove(MainScene.informationGrid);
	App.content.getChildren().remove(MainScene.treeView);
	SkillScene.getSkillScene(skill);
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
    
