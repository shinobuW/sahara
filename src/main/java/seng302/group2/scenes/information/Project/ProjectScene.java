package seng302.group2.scenes.information.Project;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.Global;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.team.Team;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.scenes.MainScene.informationPane;

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
    public static ScrollPane getProjectScene(Project currentProject)
    {
        informationPane = new VBox(10);  // Holds everything

        // The TabPane
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getStyleClass().add("floating");

        // Basic info tab
        Tab informationTab = new Tab("Basic Information");
        Pane basicInfoPane = new VBox(10);  // The pane that holds the basic info
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        informationTab.setContent(basicInfoPane);

        // Alloc history tab
        Tab allocation = new Tab("Allocation History");


        tabPane.getTabs().addAll(informationTab, allocation);  // Add the tabs to the pane










        //tabPane.setMinWidth(informationPane.getWidth());


        //basicInfoPane.setMinWidth(informationPane.getWidth());


        /*ScrollPane s1 = new ScrollPane();
        s1.setContent(tabPane);
        s1.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);*/



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

        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new Label("Short Name: "));
        basicInfoPane.getChildren().add(new Label("Project Description: "));

        basicInfoPane.getChildren().add(new Label(currentProject.getShortName()));
        basicInfoPane.getChildren().add(new Label(currentProject.getDescription()));

        basicInfoPane.getChildren().add(separator);
        basicInfoPane.getChildren().add(new Label("Teams: "));
        basicInfoPane.getChildren().add(projectTeamsBox);
        basicInfoPane.getChildren().add(new Label("Releases: "));
        basicInfoPane.getChildren().add(projectReleaseBox);

        basicInfoPane.getChildren().add(btnEdit);


        btnEdit.setOnAction((event) ->
            {
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.PROJECT_EDIT, currentProject);
            });


        informationPane.getChildren().add(tabPane);

        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;
    }
    
    public static void refreshProjectScene(Project project)
    {
        SceneSwitcher.changeScene(SceneSwitcher.ContentScene.PROJECT, project);
    }

}
