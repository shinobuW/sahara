package seng302.group2.scenes.information;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.App;
import seng302.group2.workspace.Workspace;

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
     * @return The Workspace information scene
     */
    public static GridPane getWorkspaceScene(Workspace currentWorkspace)
    {
        informationGrid = new GridPane();

        informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);
        informationGrid.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentWorkspace.getLongName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnEdit = new Button("Edit");

        informationGrid.add(title, 0, 0, 3, 1);
        informationGrid.add(new Label("Short Name: "), 0, 2);
        informationGrid.add(new Label("Description: "), 0, 3);

        informationGrid.add(new Label(currentWorkspace.getShortName()), 1, 2);
        informationGrid.add(new Label(currentWorkspace.getDescription()), 1, 3);
        informationGrid.add(btnEdit, 1, 4);

        btnEdit.setOnAction((event) ->
            {
                App.content.getChildren().remove(informationGrid);
                WorkspaceEditScene.getWorkspaceEditScene(currentWorkspace);
                App.content.getChildren().add(informationGrid);
            });

        return informationGrid;
    }
 
}