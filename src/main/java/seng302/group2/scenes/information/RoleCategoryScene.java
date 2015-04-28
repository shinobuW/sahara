package seng302.group2.scenes.information;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.Workspace;

import static seng302.group2.scenes.MainScene.informationGrid;

/**
 * A class for displaying all roles currently created in a workspace.
 * @author David Moseley
 */
public class RoleCategoryScene
{
    /**
     * Gets the Role Category Scene
     * @param currentWorkspace The workspace currently being used
     * @return The role category info scene
     */
    public static GridPane getRoleCategoryScene(Workspace currentWorkspace)
    {
        informationGrid = new GridPane();
        informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);
        informationGrid.setPadding(new Insets(25,25,25,25));
        Label title = new Label("Roles in " + currentWorkspace.getShortName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnView = new Button("View");
        //Button btnDelete = new Button("Delete");
        //Button btnCreate = new Button("Create New Role");

        VBox selectionButtons = new VBox();
        selectionButtons.spacingProperty().setValue(10);
        selectionButtons.getChildren().add(btnView);
        //selectionButtons.getChildren().add(btnDelete);
        selectionButtons.setAlignment(Pos.TOP_CENTER);

        HBox createButton = new HBox();
        //createButton.getChildren().add(btnCreate);
        createButton.setAlignment(Pos.CENTER_RIGHT);

        ListView roleBox = new ListView(currentWorkspace.getRoles());
        roleBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        informationGrid.add(title, 0, 1, 5, 1);
        informationGrid.add(roleBox, 0, 2);
        informationGrid.add(selectionButtons, 1, 2);
        informationGrid.add(createButton, 0, 3);

        btnView.setOnAction((event) ->
            {
                if (roleBox.getSelectionModel().getSelectedItem() != null)
                {
                    MainScene.treeView.selectItem((TreeViewItem)
                            roleBox.getSelectionModel().getSelectedItem());
                }
            });


        /*btnDelete.setOnAction((event) ->
            {
                if (roleBox.getSelectionModel().getSelectedItem() != null)
                {
                    Role.deleteRole((Role) roleBox.getSelectionModel().getSelectedItem());
                }
            });*/

        /*btnCreate.setOnAction((event) ->
            {
                CreateRoleDialog.show();
            });*/

        return MainScene.informationGrid;
    }
}