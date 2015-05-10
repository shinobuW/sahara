package seng302.group2.scenes.information.project;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.workspace.project.Project;

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
        informationPane.getChildren().add(tabPane);  // Add to the info pane
        ((VBox) informationPane).setVgrow(tabPane, Priority.ALWAYS);

        // Define and add the tabs
        Tab informationTab = new ProjectInfoTab(currentProject);
        Tab allocation = new ProjectHistoryTab(currentProject);

        tabPane.getTabs().addAll(informationTab, allocation);  // Add the tabs to the pane

        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;
    }


    /**
     * Reloads the scene with the given project
     * @param project The project to reload
     */
    public static void refreshProjectScene(Project project)
    {
        SceneSwitcher.changeScene(SceneSwitcher.ContentScene.PROJECT, project);
    }

}
