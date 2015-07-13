package seng302.group2.scenes.information.project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.dialog.CreateProjectDialog;
import seng302.group2.scenes.dialog.DeleteDialog;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.workspace.Workspace;

/**
 * A class for displaying all projects currently created in a workspace.
 *
 * @author David Moseley
 */
public class ProjectCategoryScene {
    /**
     * Gets the Project Category Scene
     *
     * @param currentWorkspace The workspace currently being used
     * @return The project category info scene
     */
    public static ScrollPane getProjectCategoryScene(Workspace currentWorkspace) {
        Pane informationPane = new VBox(10);

        informationPane.setPadding(new Insets(25, 25, 25, 25));
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

        informationPane.getChildren().add(title);
        informationPane.getChildren().add(projectBox);
        informationPane.getChildren().add(createButton);

        btnView.setOnAction((event) -> {
                if (projectBox.getSelectionModel().getSelectedItem() != null) {
                    App.mainPane.selectItem((SaharaItem)
                            projectBox.getSelectionModel().getSelectedItem());
                }
            });

        btnDelete.setOnAction((event) -> {
                if (projectBox.getSelectionModel().getSelectedItem() != null) {
                    DeleteDialog.showDeleteDialog((SaharaItem)
                            projectBox.getSelectionModel().getSelectedItem());
                }
            });
        btnCreate.setOnAction((event) -> {
                CreateProjectDialog.show();
            });

        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;
    }
}
