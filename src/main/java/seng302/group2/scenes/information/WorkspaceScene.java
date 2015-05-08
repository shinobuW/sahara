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
import seng302.group2.workspace.Workspace;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.Global.selectedTreeItem;
import static seng302.group2.scenes.MainScene.informationGrid;

/**
 * A class for displaying the Workspace Scene
 * @author crw73
 * @author btm38
 */
@SuppressWarnings("deprecation")
public class WorkspaceScene
{
    /**
     * Gets the Workspace information scene
     * @param currentWorkspace The workspace to show the information of
     * @return The Workspace information scene
     */
    public static Pane getWorkspaceScene(Workspace currentWorkspace)
    {
        informationGrid = new VBox(10);
        informationGrid.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentWorkspace.getLongName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnEdit = new Button("Edit");

        informationGrid.getChildren().add(title);
        informationGrid.getChildren().add(new Label("Short Name: " + currentWorkspace.getShortName()));
        informationGrid.getChildren().add(new Label("Workspace Description: " + currentWorkspace.getDescription()));
        informationGrid.getChildren().add(btnEdit);

        btnEdit.setOnAction((event) ->
            {
                App.content.getChildren().remove(informationGrid);
                WorkspaceEditScene.getWorkspaceEditScene(currentWorkspace);
                App.content.getChildren().add(informationGrid);
            });

        return informationGrid;
    }
    
    public static void refreshWorkspaceScene(Workspace workspace)
    {
	App.content.getChildren().remove(MainScene.informationGrid);
	App.content.getChildren().remove(MainScene.treeView);
	WorkspaceScene.getWorkspaceScene(workspace);
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
