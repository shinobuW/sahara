package seng302.group2.scenes.information.backlog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.dialog.CreateBacklogDialog;
import seng302.group2.scenes.listdisplay.BacklogCategory;
import seng302.group2.scenes.listdisplay.TreeViewItem;

import static seng302.group2.scenes.MainScene.informationPane;
import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;

/**
 * Created by cvs20 on 19/05/15.
 */
public class BacklogCategoryScene
{
    /**
     * Gets the Backlog Category Scene
     * @param selectedCategory The category currently selected
     * @return The Backlog category info scene
     */
    public static ScrollPane getBacklogCategoryScene(BacklogCategory selectedCategory)
    {
        informationPane = new VBox(10);

        informationPane.setPadding(new Insets(25,25,25,25));
        Label title = new TitleLabel("Backlogs in " + selectedCategory.getProject().toString());

        Button btnView = new Button("View");
        Button btnDelete = new Button("Delete");
        Button btnCreate = new Button("Create New Backlog");

        HBox selectionButtons = new HBox();
        selectionButtons.spacingProperty().setValue(10);
        selectionButtons.getChildren().add(btnView);
        selectionButtons.getChildren().add(btnDelete);
        selectionButtons.getChildren().add(btnCreate);
        selectionButtons.setAlignment(Pos.TOP_LEFT);


        ListView backlogBox = new ListView(selectedCategory.getProject().getBacklogs());
        backlogBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        backlogBox.setMaxWidth(275);

        informationPane.getChildren().add(title);
        informationPane.getChildren().add(backlogBox);
        informationPane.getChildren().add(selectionButtons);

        btnView.setOnAction((event) ->
            {
                if (backlogBox.getSelectionModel().getSelectedItem() != null)
                {
                    MainScene.treeView.selectItem((TreeViewItem)
                            backlogBox.getSelectionModel().getSelectedItem());
                }
            });


        btnDelete.setOnAction((event) ->
            {
                if (backlogBox.getSelectionModel().getSelectedItem() != null)
                {
                    showDeleteDialog((TreeViewItem) backlogBox.getSelectionModel()
                            .getSelectedItem());
                }
            });

        btnCreate.setOnAction((event) ->
            {
                CreateBacklogDialog.show();
            });

        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;
    }
}
