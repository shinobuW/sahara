package seng302.group2.scenes.information.project.release;

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
import seng302.group2.scenes.dialog.CreateReleaseDialog;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.categories.subCategory.project.ReleaseCategory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;

/**
 * A class for displaying a tab showing data on all the releases in the current project.
 * Created by btm38 on 13/07/15.
 */
public class ReleaseCategoryTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();
    /**
     * Constructor for ReleaseCategoryTab class.
     * @param selectedCategory The current selected category
     */
    public ReleaseCategoryTab(ReleaseCategory selectedCategory) {
        // Tab settings
        this.setText("Basic Information");
        Pane categoryPane = new VBox(10);
        categoryPane.setBorder(null);
        categoryPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(categoryPane);
        this.setContent(wrapper);

        // Create controls
        SearchableText title = new SearchableTitle("Releases in " + selectedCategory.getProject().toString());
        SearchableListView releaseBox = new SearchableListView<>(selectedCategory.getProject().getReleases());
        releaseBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        releaseBox.setMaxWidth(275);

        Button btnView = new Button("View");
        Button btnDelete = new Button("Delete");
        Button btnCreate = new Button("Create New Release");

        HBox selectionButtons = new HBox();
        selectionButtons.spacingProperty().setValue(10);
        selectionButtons.getChildren().addAll(btnView, btnDelete, btnCreate);
        selectionButtons.setAlignment(Pos.TOP_LEFT);

        // Events
        btnView.setOnAction((event) -> {
                if (releaseBox.getSelectionModel().getSelectedItem() != null) {
                    App.mainPane.selectItem((SaharaItem)
                            releaseBox.getSelectionModel().getSelectedItem());
                }
            });


        btnDelete.setOnAction((event) -> {
                if (releaseBox.getSelectionModel().getSelectedItem() != null) {
                    showDeleteDialog((SaharaItem) releaseBox
                            .getSelectionModel().getSelectedItem());
                }
            });

        btnCreate.setOnAction((event) -> {
                javafx.scene.control.Dialog creationDialog = new CreateReleaseDialog(selectedCategory.getProject());
                creationDialog.show();
            });

        // Add items to pane & search collection
        categoryPane.getChildren().addAll(
                title,
                releaseBox,
                selectionButtons
        );

        Collections.addAll(searchControls,
                title,
                releaseBox
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





