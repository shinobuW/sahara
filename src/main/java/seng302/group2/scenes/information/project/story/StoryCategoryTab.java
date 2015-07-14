package seng302.group2.scenes.information.project.story;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.dialog.CreateStoryDialog;
import seng302.group2.scenes.dialog.DeleteDialog;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.categories.subCategory.project.StoryCategory;

/**
 * A class for displaying all stories in a project.
 *
 * Created by btm38 on 14/07/15.
 */
public class StoryCategoryTab extends Tab {
    /**
     * The Story Category Tab constructor
     *
     * @param selectedCategory The category currently selected
     */
    public StoryCategoryTab(StoryCategory selectedCategory) {
        this.setText("Basic Information");
        Pane categoryPane = new VBox(10);
        categoryPane.setBorder(null);
        categoryPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(categoryPane);
        this.setContent(wrapper);
        Label title = new TitleLabel("Stories in " + selectedCategory.getProject().toString());

        Button btnView = new Button("View");
        Button btnDelete = new Button("Delete");
        Button btnCreate = new Button("Create New Story");

        HBox selectionButtons = new HBox();
        selectionButtons.spacingProperty().setValue(10);
        selectionButtons.getChildren().add(btnView);
        selectionButtons.getChildren().add(btnDelete);
        selectionButtons.getChildren().add(btnCreate);
        selectionButtons.setAlignment(Pos.TOP_LEFT);


        ListView storyBox = new ListView<>(selectedCategory.getProject().getUnallocatedStories());
        storyBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        storyBox.setMaxWidth(275);

        categoryPane.getChildren().add(title);
        categoryPane.getChildren().add(storyBox);
        categoryPane.getChildren().add(selectionButtons);

        btnView.setOnAction((event) -> {
                if (storyBox.getSelectionModel().getSelectedItem() != null) {
                    App.mainPane.selectItem((SaharaItem)
                            storyBox.getSelectionModel().getSelectedItem());
                }
            });


        btnDelete.setOnAction((event) -> {
                if (storyBox.getSelectionModel().getSelectedItem() != null) {
                    DeleteDialog.showDeleteDialog((SaharaItem) storyBox.getSelectionModel()
                            .getSelectedItem());
                }
            });

        btnCreate.setOnAction((event) -> {
                CreateStoryDialog.show();
            });
    }
}
