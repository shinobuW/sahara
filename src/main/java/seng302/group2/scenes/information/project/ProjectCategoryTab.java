package seng302.group2.scenes.information.project;

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
import seng302.group2.scenes.dialog.CreateProjectDialog;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.workspace.Workspace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;

/**
 * A class for displaying a tab used to edit projects.
 * Created by btm38 on 13/07/15.
 */
public class ProjectCategoryTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();

    /**
     * Constructor for ProjectCategoryTab class.
     * @param currentWorkspace The current workspace
     */
    public ProjectCategoryTab(Workspace currentWorkspace) {
        // Tab settings
        this.setText("Basic Information");
        Pane categoryPane = new VBox(10);
        categoryPane.setBorder(null);
        categoryPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(categoryPane);
        this.setContent(wrapper);

        // Create controls
        SearchableText title = new SearchableTitle("Projects in " + currentWorkspace.getShortName(), searchControls);
        SearchableListView projectBox = new SearchableListView<>(currentWorkspace.getProjects());
        projectBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        projectBox.setMaxWidth(275);

        Button btnView = new Button("View");
        Button btnDelete = new Button("Delete");
        Button btnCreate = new Button("Create New Project");

        HBox createButton = new HBox();
        createButton.spacingProperty().setValue(10);
        createButton.getChildren().addAll(btnView, btnDelete, btnCreate);
        createButton.setAlignment(Pos.TOP_LEFT);

        // Events
        btnView.setOnAction((event) -> {
                if (projectBox.getSelectionModel().getSelectedItem() != null) {
                    App.mainPane.selectItem((SaharaItem)
                            projectBox.getSelectionModel().getSelectedItem());
                }
            });

        btnDelete.setOnAction((event) -> {
                if (projectBox.getSelectionModel().getSelectedItem() != null) {
                    showDeleteDialog((SaharaItem)
                            projectBox.getSelectionModel().getSelectedItem());
                }
            });

        btnCreate.setOnAction((event) -> {
                javafx.scene.control.Dialog creationDialog = new CreateProjectDialog();
                creationDialog.show();
            });

        // Add items to pane & search collection
        categoryPane.getChildren().addAll(
                title,
                projectBox,
                createButton
        );

        Collections.addAll(searchControls,
                title,
                projectBox
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
