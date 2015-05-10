package seng302.group2.scenes.information.Role;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.Workspace;

import static seng302.group2.scenes.MainScene.informationPane;

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
    public static ScrollPane getRoleCategoryScene(Workspace currentWorkspace)
    {
        informationPane = new VBox(10);
        /*informationPane.setAlignment(Pos.TOP_LEFT);
        informationPane.setHgap(10);
        informationPane.setVgap(10);*/
        informationPane.setPadding(new Insets(25,25,25,25));
        Label title = new Label("Roles in " + currentWorkspace.getShortName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnView = new Button("View");
        //Button btnDelete = new Button("Delete");
        //Button btnCreate = new Button("Create New Role");

        HBox selectionButtons = new HBox();
        selectionButtons.spacingProperty().setValue(10);
        selectionButtons.getChildren().add(btnView);
        //selectionButtons.getChildren().add(btnDelete);
        selectionButtons.setAlignment(Pos.TOP_LEFT);

        HBox createButton = new HBox();
        //createButton.getChildren().add(btnCreate);
        createButton.setAlignment(Pos.CENTER_RIGHT);

        ListView roleBox = new ListView(currentWorkspace.getRoles());
        roleBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        roleBox.setMaxWidth(275);

        informationPane.getChildren().add(title);
        informationPane.getChildren().add(roleBox);
        informationPane.getChildren().add(selectionButtons);
        informationPane.getChildren().add(createButton);

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

        return new ScrollPane(informationPane);
    }
}