package seng302.group2.scenes.information.project.story;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.scenes.control.search.*;
import seng302.group2.scenes.dialog.CreateStoryDialog;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.categories.subCategory.project.StoryCategory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;

/**
 * A class for displaying a tab showing data on all the stories in the current project.
 * Created by btm38 on 13/07/15.
 */
public class StoryCategoryTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();

    /**
     * Constructor for StoryCategoryTab class.
     * @param selectedCategory The current selected category
     */
    public StoryCategoryTab(StoryCategory selectedCategory) {
        // Tab settings
        this.setText("Basic Information");
        Pane categoryPane = new VBox(10);
        categoryPane.setBorder(null);
        categoryPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(categoryPane);
        this.setContent(wrapper);
        SearchableText title = new SearchableTitle("Stories in " + selectedCategory.getProject().toString());

        Button btnView = new Button("View");
        Button btnDelete = new Button("Delete");
        Button btnCreate = new Button("Create New Story");

        HBox selectionButtons = new HBox();
        selectionButtons.spacingProperty().setValue(10);
        selectionButtons.getChildren().addAll(btnView, btnDelete, btnCreate);
        selectionButtons.setAlignment(Pos.TOP_LEFT);


        SearchableListView storyBox = new SearchableListView<>(selectedCategory.getProject().getUnallocatedStories());
        storyBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        storyBox.setMaxWidth(275);


        // Events
        btnView.setOnAction((event) -> {
                if (storyBox.getSelectionModel().getSelectedItem() != null) {
                    App.mainPane.selectItem((SaharaItem)
                            storyBox.getSelectionModel().getSelectedItem());
                }
            });


        btnDelete.setOnAction((event) -> {
                if (storyBox.getSelectionModel().getSelectedItem() != null) {
                    showDeleteDialog((SaharaItem) storyBox
                            .getSelectionModel().getSelectedItem());
                }
            });

        btnCreate.setOnAction((event) -> {
                new CreateStoryDialog();
            });

        // Add items to pane & search collection
        categoryPane.getChildren().addAll(
                title,
                storyBox,
                selectionButtons
        );

        Collections.addAll(searchControls,
                title,
                storyBox
        );
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
