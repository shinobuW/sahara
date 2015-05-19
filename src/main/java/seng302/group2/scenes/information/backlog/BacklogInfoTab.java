package seng302.group2.scenes.information.backlog;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.workspace.backlog.Backlog;

/**
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

        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new Label("Backlog Description: "
                + currentBacklog.getDescription()));
        basicInfoPane.getChildren().add(new Label("Project: "
                + currentBacklog.getProject().toString()));
        basicInfoPane.getChildren().add(new Label("Backlog Product Owner: "
                + currentBacklog.getProductOwner()));
        basicInfoPane.getChildren().add(btnEdit);

        btnEdit.setOnAction((event) ->
            {
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.BACKLOG_EDIT, currentBacklog);
            });
    }
}
