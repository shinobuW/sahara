package seng302.group2.scenes.information.role;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.workspace.Workspace;

/**
 * A class for displaying a tab showing data on all the roles in the workspace.
 * Created by btm38 on 13/07/15.
 */
public class RoleCategoryTab extends Tab {
    /**
     * Constructor for RoleCategoryTab class.
     * @param currentWorkspace The current workspace
     */
    public RoleCategoryTab(Workspace currentWorkspace) {
        this.setText("Basic Information");
        Pane categoryPane = new VBox(10);
        categoryPane.setBorder(null);
        categoryPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(categoryPane);
        this.setContent(wrapper);
        Label title = new TitleLabel("Roles in " + currentWorkspace.getShortName());

        Button btnView = new Button("View");
        //Button btnDelete = new Button("Delete");
        //Button btnCreate = new Button("Create New Role");

        HBox selectionButtons = new HBox();
        selectionButtons.spacingProperty().setValue(10);
        selectionButtons.getChildren().add(btnView);
        //selectionButtons.getChildren().add(btnDelete);
        selectionButtons.setAlignment(Pos.TOP_LEFT);

        HBox createButton = new HBox();
        //createButton.getChildren().add(btnCreate);
        createButton.setAlignment(Pos.CENTER_RIGHT);

        ListView roleBox = new ListView<>(currentWorkspace.getRoles());
        roleBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        roleBox.setMaxWidth(450);

        categoryPane.getChildren().add(title);
        categoryPane.getChildren().add(roleBox);
        categoryPane.getChildren().add(selectionButtons);
        categoryPane.getChildren().add(createButton);

        btnView.setOnAction((event) -> {
                if (roleBox.getSelectionModel().getSelectedItem() != null) {
                    App.mainPane.selectItem((SaharaItem)
                            roleBox.getSelectionModel().getSelectedItem());
                }
            });
    }
}
