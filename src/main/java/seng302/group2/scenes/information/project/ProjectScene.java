package seng302.group2.scenes.information.project;

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
        Tab informationTab = new ProjectInfoTab(currentProject);

        // Alloc history tab
        Tab allocation = ProjectHistoryTab.getHistoryTab();



        tabPane.getTabs().addAll(informationTab, allocation);  // Add the tabs to the pane










        //tabPane.setMinWidth(informationPane.getWidth());


        //basicInfoPane.setMinWidth(informationPane.getWidth());


        /*ScrollPane s1 = new ScrollPane();
        s1.setContent(tabPane);
        s1.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);*/






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
