package seng302.group2.scenes.information.team;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.dialog.CreateTeamDialog;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.Workspace;

import static seng302.group2.scenes.MainScene.informationPane;
import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;

/**
 * A class for displaying all teams currently created in a workspace.
 * @author David Moseley
 */
public class TeamCategoryScene
{
    /**
     * Gets the Team Category Scene
     * @param currentWorkspace The workspace currently being used
     * @return The team category info scene
     */
    public static ScrollPane getTeamCategoryScene(Workspace currentWorkspace)
    {
        informationPane = new VBox(10);

        informationPane.setPadding(new Insets(25,25,25,25));
        Label title = new TitleLabel("Teams in " + currentWorkspace.getShortName());

        Button btnView = new Button("View");
        Button btnDelete = new Button("Delete");
        Button btnCreate = new Button("Create New Team");

        HBox selectionButtons = new HBox();
        selectionButtons.spacingProperty().setValue(10);
        selectionButtons.getChildren().add(btnView);
        selectionButtons.getChildren().add(btnDelete);
        selectionButtons.getChildren().add(btnCreate);
        selectionButtons.setAlignment(Pos.TOP_LEFT);


        ListView teamBox = new ListView(currentWorkspace.getTeams());
        teamBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        teamBox.setMaxWidth(275);

        informationPane.getChildren().add(title);
        informationPane.getChildren().add(teamBox);
        informationPane.getChildren().add(selectionButtons);


        btnView.setOnAction((event) ->
            {
                if (teamBox.getSelectionModel().getSelectedItem() != null)
                {
                    MainScene.treeView.selectItem((TreeViewItem)
                            teamBox.getSelectionModel().getSelectedItem());
                }
            });

        btnDelete.setOnAction((event) ->
            {
                if (teamBox.getSelectionModel().getSelectedItem() != null)
                {
                    showDeleteDialog((TreeViewItem)
                            teamBox.getSelectionModel().getSelectedItem());
                }
            });

        btnCreate.setOnAction((event) ->
            {
                CreateTeamDialog.show();
            });

        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;
    }
}