package seng302.group2.scenes.information.project.backlog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.dialog.CreateBacklogDialog;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.listdisplay.categories.subCategory.project.BacklogCategory;

import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;

/**
 * Created by btm38 on 2/07/15.
 */
public class BacklogCategoryTab extends Tab {
    public BacklogCategoryTab(BacklogCategory selectedCategory) {
        this.setText("Basic Information");
        Pane categoryPane = new VBox(10);
        categoryPane.setBorder(null);
        categoryPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(categoryPane);
        this.setContent(wrapper);

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
        backlogBox.setMaxWidth(450);

        categoryPane.getChildren().add(title);
        categoryPane.getChildren().add(backlogBox);
        categoryPane.getChildren().add(selectionButtons);

        btnView.setOnAction((event) -> {
                if (backlogBox.getSelectionModel().getSelectedItem() != null) {
                    MainScene.treeView.selectItem((TreeViewItem)
                            backlogBox.getSelectionModel().getSelectedItem());
                }
            });


        btnDelete.setOnAction((event) -> {
                if (backlogBox.getSelectionModel().getSelectedItem() != null) {
                    showDeleteDialog((TreeViewItem) backlogBox.getSelectionModel()
                            .getSelectedItem());
                }
            });

        btnCreate.setOnAction((event) -> {
                CreateBacklogDialog.show();
            });
    }
}
