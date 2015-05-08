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
    public static Pane getSkillScene(Skill currentSkill)
    {
        informationGrid = new VBox();

        /*informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);*/
        informationGrid.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentSkill.getShortName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnEdit = new Button("Edit");

        informationGrid.getChildren().add(title);
        informationGrid.getChildren().add(new Label("Skill Description: "));
        
        informationGrid.getChildren().add(new Label(currentSkill.getDescription()));
        informationGrid.getChildren().add(btnEdit);

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
    
