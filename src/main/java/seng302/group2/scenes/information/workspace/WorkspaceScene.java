package seng302.group2.scenes.information.workspace;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.workspace.Workspace;

import static seng302.group2.scenes.MainScene.informationPane;

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
    public static ScrollPane getWorkspaceScene(Workspace currentWorkspace)
    {
        informationPane = new VBox(10);
        informationPane.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentWorkspace.getLongName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnEdit = new Button("Edit");

        informationPane.getChildren().add(title);
        informationPane.getChildren().add(new Label("Short Name: "
                + currentWorkspace.getShortName()));
        informationPane.getChildren().add(new Label("Workspace Description: "
                + currentWorkspace.getDescription()));
        informationPane.getChildren().add(btnEdit);

        btnEdit.setOnAction((event) ->
            {
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.WORKSPACE_EDIT,
                        currentWorkspace);
            });

        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;
    }
    
    public static void refreshWorkspaceScene(Workspace workspace)
    {
        SceneSwitcher.changeScene(SceneSwitcher.ContentScene.WORKSPACE, workspace);
    }
 
}