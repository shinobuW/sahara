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
 * A class for displaying all roles currently created in a workspace.
 *
 * Created by btm38 on 14/07/15.
 */
public class RoleCategoryTab extends Tab {
    /**
     * Gets the Release Category Tab.
     *
     * @param currentWorkspace The workspace currently being used
     */
    public RoleCategoryTab(Workspace currentWorkspace) {
        this.setText("Basic Information");
        Pane rolePane = new VBox(10);
        rolePane.setBorder(null);
        rolePane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(rolePane);
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

        rolePane.getChildren().add(title);
        rolePane.getChildren().add(roleBox);
        rolePane.getChildren().add(selectionButtons);
        rolePane.getChildren().add(createButton);

        btnView.setOnAction((event) -> {
                if (roleBox.getSelectionModel().getSelectedItem() != null) {
                    App.mainPane.selectItem((SaharaItem)
                            roleBox.getSelectionModel().getSelectedItem());
                }
            });
    }
}
