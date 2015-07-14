package seng302.group2.scenes.information.team;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.dialog.CreateTeamDialog;
import seng302.group2.scenes.dialog.DeleteDialog;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.workspace.Workspace;

/**
 * A class for displaying a tab showing data on all the team in the workspace.
 * Created by btm38 on 13/07/15.
 */
public class TeamCategoryTab extends Tab {
    /**
     * Constructor for TeamCategoryTab class.
     * @param currentWorkspace The current workspace
     */
    public TeamCategoryTab(Workspace currentWorkspace) {
        this.setText("Basic Information");
        Pane categoryPane = new VBox(10);
        categoryPane.setBorder(null);
        categoryPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(categoryPane);
        this.setContent(wrapper);
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

        categoryPane.getChildren().add(title);
        categoryPane.getChildren().add(teamBox);
        categoryPane.getChildren().add(selectionButtons);


        btnView.setOnAction((event) -> {
                if (teamBox.getSelectionModel().getSelectedItem() != null) {
                    App.mainPane.selectItem((SaharaItem)
                            teamBox.getSelectionModel().getSelectedItem());
                }
            });

        btnDelete.setOnAction((event) -> {
                if (teamBox.getSelectionModel().getSelectedItem() != null) {
                    DeleteDialog.showDeleteDialog((SaharaItem)
                            teamBox.getSelectionModel().getSelectedItem());
                }
            });

        btnCreate.setOnAction((event) -> {
                CreateTeamDialog.show();
            });

    }
}

