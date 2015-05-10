package seng302.group2.scenes.information.Project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.dialog.CreateProjectDialog;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.Workspace;

import static seng302.group2.scenes.MainScene.informationPane;

/**
 * A class for displaying all projects currently created in a workspace.
 * @author David Moseley
 */
public class ProjectCategoryScene
{
    /**
     * Gets the Project Category Scene
     * @param currentWorkspace The workspace currently being used
     * @return The project category info scene
     */
    public static ScrollPane getProjectCategoryScene(Workspace currentWorkspace)
    {
        informationPane = new VBox(10);
        /*informationPane.setAlignment(Pos.TOP_LEFT);
        informationPane.setHgap(10);
        informationPane.setVgap(10);*/
        informationPane.setPadding(new Insets(25,25,25,25));
        Label title = new Label("Projects in " + currentWorkspace.getShortName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnView = new Button("View");
        //Button btnDelete = new Button("Delete");
        Button btnCreate = new Button("Create New Project");

        HBox createButton = new HBox();
        createButton.spacingProperty().setValue(10);
        createButton.getChildren().add(btnView);
        createButton.getChildren().add(btnCreate);
        createButton.setAlignment(Pos.TOP_LEFT);

        ListView projectBox = new ListView(currentWorkspace.getProjects());
        projectBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        projectBox.setMaxWidth(275);

        informationPane.getChildren().add(title);
        informationPane.getChildren().add(projectBox);
        informationPane.getChildren().add(createButton);

        btnView.setOnAction((event) ->
            {
                if (projectBox.getSelectionModel().getSelectedItem() != null)
                {
                    MainScene.treeView.selectItem((TreeViewItem)
                            projectBox.getSelectionModel().getSelectedItem());
                }
            });


        /*btnDelete.setOnAction((event) ->
            {
                if (projectBox.getSelectionModel().getSelectedItem() != null)
                {
                    Project.deleteProject(
                            (Project) projectBox.getSelectionModel().getSelectedItem());
                }
            });*/

        btnCreate.setOnAction((event) ->
            {
                CreateProjectDialog.show();
            });

        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;
    }
}
