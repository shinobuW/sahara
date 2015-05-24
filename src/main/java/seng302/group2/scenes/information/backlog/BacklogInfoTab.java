package seng302.group2.scenes.information.backlog;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.workspace.backlog.Backlog;

/**
 * The information tab for a backlog
 * Created by cvs20 on 19/05/15.
 */
public class BacklogInfoTab extends Tab
{
    public BacklogInfoTab(Backlog currentBacklog)
    {
        this.setText("Basic Information");

        Pane basicInfoPane = new VBox(10);

        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        Label title = new TitleLabel(currentBacklog.getShortName());

        Button btnEdit = new Button("Edit");

        ListView backlogStoryBox = new ListView(currentBacklog.getStories());
        backlogStoryBox.setPrefHeight(192);
        backlogStoryBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        final Separator separator = new Separator();

        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new Label("Backlog Description: "
                + currentBacklog.getDescription()));
        basicInfoPane.getChildren().add(new Label("Project: "
                + currentBacklog.getProject().toString()));


        if(currentBacklog.getProductOwner() == null)
        {
            basicInfoPane.getChildren().add(new Label("Backlog Product Owner: "
                    + ""));
        }
        else
        {
            basicInfoPane.getChildren().add(new Label("Backlog Product Owner: "
                    + currentBacklog.getProductOwner()));
        }
        basicInfoPane.getChildren().add(separator);
        basicInfoPane.getChildren().add(new Label("Stories: "));
        basicInfoPane.getChildren().add(backlogStoryBox);

        basicInfoPane.getChildren().add(btnEdit);

        btnEdit.setOnAction((event) ->
            {
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.BACKLOG_EDIT, currentBacklog);
            });
    }
}
