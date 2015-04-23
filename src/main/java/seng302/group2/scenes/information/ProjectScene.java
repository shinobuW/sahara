package seng302.group2.scenes.information;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.TreeViewWithItems;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.team.Team;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.Global.selectedTreeItem;
import static seng302.group2.scenes.MainScene.informationGrid;

/**
 * A class for displaying the project scene
 * @author jml168
 */
@SuppressWarnings("deprecation")
public class ProjectScene
{
    /**
     * Gets the Workspace information scene
     * @return The Workspace information scene
     */
    public static GridPane getProjectScene(Project currentProject)
    {
        informationGrid = new GridPane();

        informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);
        informationGrid.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentProject.getLongName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnEdit = new Button("Edit");

        ListView projectTeamsBox = new ListView(currentProject.getTeams());
        projectTeamsBox.setMaxHeight(150);
        projectTeamsBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ObservableList<Team> dialogTeams = observableArrayList();
        for (TreeViewItem projectTeam : Global.currentWorkspace.getTeams())
        {
            if (!((Team)projectTeam).isUnassignedTeam()
                    && !currentProject.getTeams().contains(projectTeam))
            {
                dialogTeams.add((Team)projectTeam);
            }
        }
        
        ListView projectReleaseBox = new ListView(currentProject.getReleases());
        projectReleaseBox.setMaxHeight(150);
        Separator separator = new Separator();

        informationGrid.add(title, 0, 0, 3, 1);
        informationGrid.add(new Label("Short Name: "), 0, 2);
        informationGrid.add(new Label("Description: "), 0, 3);

        informationGrid.add(new Label(currentProject.getShortName()), 1, 2, 5, 1);
        informationGrid.add(new Label(currentProject.getDescription()), 1, 3, 5, 1);

        informationGrid.add(separator, 0, 4, 4, 1);
        informationGrid.add(new Label("Teams: "), 0, 5);
        informationGrid.add(projectTeamsBox, 0, 6, 2, 1);
        informationGrid.add(new Label("Releases: "), 0, 7);
        informationGrid.add(projectReleaseBox, 0, 8, 2, 1);

        informationGrid.add(btnEdit, 2, 9);

        btnEdit.setOnAction((event) ->
            {
                App.content.getChildren().remove(informationGrid);
                ProjectEditScene.getProjectEditScene(currentProject);
                App.content.getChildren().add(informationGrid);
            });

        return informationGrid;
    }
    
    public static void refreshProjectScene(Project project)
    {
	App.content.getChildren().remove(MainScene.informationGrid);
	App.content.getChildren().remove(MainScene.treeView);
	ProjectScene.getProjectScene(project);
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
