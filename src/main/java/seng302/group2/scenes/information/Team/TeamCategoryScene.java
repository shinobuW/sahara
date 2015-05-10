package seng302.group2.scenes.information.Team;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.dialog.CreateTeamDialog;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.Workspace;
import seng302.group2.workspace.team.Team;

import static seng302.group2.scenes.MainScene.informationPane;

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
        /*informationPane.setAlignment(Pos.TOP_LEFT);
        informationPane.setHgap(10);
        informationPane.setVgap(10);*/
        informationPane.setPadding(new Insets(25,25,25,25));
        Label title = new Label("Teams in " + currentWorkspace.getShortName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

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
                    ((Team) teamBox.getSelectionModel().getSelectedItem()).deleteTeamCascading();
                }
            });


        btnCreate.setOnAction((event) ->
            {
                CreateTeamDialog.show();
            });

        return new ScrollPane(informationPane);
    }
}