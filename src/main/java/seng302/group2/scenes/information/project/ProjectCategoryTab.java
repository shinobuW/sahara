package seng302.group2.scenes.information.project;

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
import seng302.group2.scenes.dialog.CreateProjectDialog;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.workspace.Workspace;

import java.util.Collection;

import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;

/**
 * A class for displaying a tab showing data on all the projects in the workspace.
 * Created by btm38 on 13/07/15.
 */
public class ProjectCategoryTab extends SearchableTab {
    /**
     * Constructor for ProjectCategoryTab class.
     * @param currentWorkspace The current workspace
     */
    public ProjectCategoryTab(Workspace currentWorkspace) {
        this.setText("Basic Information");
        Pane categoryPane = new VBox(10);
        categoryPane.setBorder(null);
        categoryPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(categoryPane);
        this.setContent(wrapper);
        Label title = new TitleLabel("Projects in " + currentWorkspace.getShortName());

        Button btnView = new Button("View");
        Button btnDelete = new Button("Delete");
        Button btnCreate = new Button("Create New Project");

        HBox createButton = new HBox();
        createButton.spacingProperty().setValue(10);

        createButton.getChildren().add(btnView);
        createButton.getChildren().add(btnDelete);
        createButton.getChildren().add(btnCreate);

        createButton.setAlignment(Pos.TOP_LEFT);

        ListView projectBox = new ListView(currentWorkspace.getProjects());
        projectBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        projectBox.setMaxWidth(275);

        categoryPane.getChildren().add(title);
        categoryPane.getChildren().add(projectBox);
        categoryPane.getChildren().add(createButton);

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
    }

    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return null; //TODO
    }
}
