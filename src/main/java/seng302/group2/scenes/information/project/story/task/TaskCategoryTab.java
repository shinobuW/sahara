package seng302.group2.scenes.information.project.story.task;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.dialog.CreateStoryDialog;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.categories.subCategory.project.task.TaskCategory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;

/**
 * Created by cvs20 on 28/07/15.
 */
public class TaskCategoryTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();


    /**
     * Constructor for TaskCategoryTab class
     * @param selectedCategory The current selected category
     */
    public TaskCategoryTab(TaskCategory selectedCategory) {
        this.setText("Basic Information");
        Pane categoryPane = new VBox(10);
        categoryPane.setBorder(null);
        categoryPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(categoryPane);
        this.setContent(wrapper);
        Label title = new TitleLabel("Stories in " + selectedCategory.getSprint().toString());

        Button btnView = new Button("View");
        Button btnDelete = new Button("Delete");
        Button btnCreate = new Button("Create New Story");

        HBox selectionButtons = new HBox();
        selectionButtons.spacingProperty().setValue(10);
        selectionButtons.getChildren().add(btnView);
        selectionButtons.getChildren().add(btnDelete);
        selectionButtons.getChildren().add(btnCreate);
        selectionButtons.setAlignment(Pos.TOP_LEFT);


        ListView taskBox = new ListView<>(selectedCategory.getSprint().getUnallocatedTasks());
        taskBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        taskBox.setMaxWidth(275);

        categoryPane.getChildren().add(title);
        categoryPane.getChildren().add(taskBox);
        categoryPane.getChildren().add(selectionButtons);

        btnView.setOnAction((event) -> {
                if (taskBox.getSelectionModel().getSelectedItem() != null) {
                    App.mainPane.selectItem((SaharaItem)
                            taskBox.getSelectionModel().getSelectedItem());
                }
            });


        btnDelete.setOnAction((event) -> {
                if (taskBox.getSelectionModel().getSelectedItem() != null) {
                    showDeleteDialog((SaharaItem) taskBox
                            .getSelectionModel().getSelectedItem());
                }
            });

        btnCreate.setOnAction((event) -> {
                new CreateStoryDialog();
            });
    }

    /**
     * Gets all the searchable controls on this tab.
     * @return a collection of all the searchable controls on this tab.
     */
    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }
}
