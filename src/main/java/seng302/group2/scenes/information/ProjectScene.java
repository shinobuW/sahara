package seng302.group2.scenes.information;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
     * Gets the project information scene
     * @param currentProject the project to display the information of
     * @return The project information scene
     */
    public static Pane getProjectScene(Project currentProject)
    {
        informationGrid = new VBox();
        Pane infoTabGrid = new VBox();

        //informationGrid.setAlignment(Pos.TOP_LEFT);
        infoTabGrid.setBorder(null);
        //infoTabGrid.setHgap(10);
        //infoTabGrid.setVgap(10);
        infoTabGrid.setPadding(new Insets(25,25,25,25));
        //infoTabGrid.setAlignment(Pos.BASELINE_CENTER);
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getStyleClass().add("floating");

        Tab information = new Tab();
        Tab allocation = new Tab();
        information.setText("Information");
        allocation.setText("Allocation History");
        tabPane.getTabs().addAll(information, allocation);

        tabPane.setMinWidth(informationGrid.getWidth());
        infoTabGrid.setMinWidth(informationGrid.getWidth());
        System.out.println(informationGrid.getWidth());
        ScrollPane s1 = new ScrollPane();
        s1.setContent(tabPane);
        s1.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        informationGrid.getChildren().add(tabPane);

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

        infoTabGrid.getChildren().add(title);
        infoTabGrid.getChildren().add(new Label("Short Name: "));
        infoTabGrid.getChildren().add(new Label("Project Description: "));

        infoTabGrid.getChildren().add(new Label(currentProject.getShortName()));
        infoTabGrid.getChildren().add(new Label(currentProject.getDescription()));

        infoTabGrid.getChildren().add(separator);
        infoTabGrid.getChildren().add(new Label("Teams: "));
        infoTabGrid.getChildren().add(projectTeamsBox);
        infoTabGrid.getChildren().add(new Label("Releases: "));
        infoTabGrid.getChildren().add(projectReleaseBox);

        infoTabGrid.getChildren().add(btnEdit);

        information.setContent(infoTabGrid);

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
