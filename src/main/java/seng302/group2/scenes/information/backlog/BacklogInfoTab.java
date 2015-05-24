package seng302.group2.scenes.information.backlog;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.backlog.Backlog;
import seng302.group2.workspace.story.Story;
import seng302.group2.workspace.team.Allocation;

import java.util.Collections;

import static javafx.collections.FXCollections.observableArrayList;

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

        Label title = new TitleLabel(currentBacklog.getLongName());

        Button btnEdit = new Button("Edit");

        Button btnView = new Button("View");

        TableView<Story> storyTable = new TableView();
        storyTable.setEditable(true);
        storyTable.setPrefWidth(500);
        storyTable.setPrefHeight(200);
        storyTable.setPlaceholder(new Label("There are currently no stories in this backlog."));
        storyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Story> data = observableArrayList();
        data.addAll(currentBacklog.getStories());

        TableColumn storyCol = new TableColumn("Story");
        storyCol.setCellValueFactory(new PropertyValueFactory<Story, String>("shortName"));

        storyCol.prefWidthProperty().bind(storyTable.widthProperty()
                .subtract(2).divide(100).multiply(80));

        TableColumn priorityCol = new TableColumn("Priority");
        priorityCol.setCellValueFactory(new PropertyValueFactory<Story, Integer>("priority"));
        priorityCol.prefWidthProperty().bind(storyTable.widthProperty()
                .subtract(2).divide(100).multiply(20));

        storyTable.setItems(data);
        storyTable.getColumns().addAll(priorityCol, storyCol);

        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new Label("Short Name: "
                + currentBacklog.getShortName()));
        basicInfoPane.getChildren().add(new Label("Backlog Description: "
                + currentBacklog.getDescription()));
        basicInfoPane.getChildren().add(new Label("Project: "
                + currentBacklog.getProject().toString()));


        if (currentBacklog.getProductOwner() == null)
        {
            basicInfoPane.getChildren().add(new Label("Backlog Product Owner: "
                    + ""));
        }
        else
        {
            basicInfoPane.getChildren().add(new Label("Backlog Product Owner: "
                    + currentBacklog.getProductOwner()));
        }

        basicInfoPane.getChildren().add(new Separator());
        basicInfoPane.getChildren().add(new Label("Stories: "));
        basicInfoPane.getChildren().add(storyTable);

        basicInfoPane.getChildren().add(btnView);
        basicInfoPane.getChildren().add(btnEdit);

        btnEdit.setOnAction((event) ->
            {
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.BACKLOG_EDIT, currentBacklog);
            });

        btnView.setOnAction((event) ->
            {
                if (storyTable.getSelectionModel().getSelectedItem() != null)
                {
                    MainScene.treeView.selectItem(storyTable.getSelectionModel().getSelectedItem());
                }
            });
    }
}
