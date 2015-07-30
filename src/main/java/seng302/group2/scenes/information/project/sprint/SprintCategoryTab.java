package seng302.group2.scenes.information.project.sprint;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.dialog.CreateSprintDialog;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.categories.subCategory.project.SprintCategory;

/**
 * A class for displaying a tab showing data on all the sprints in the current project.
 * Created by drm127 on 29/07/15.
 */
public class SprintCategoryTab extends Tab {

    /**
     * Constructor for SprintCategoryTab class.
     * @param selectedCategory The current selected category
     */
    public SprintCategoryTab(SprintCategory selectedCategory) {
        this.setText("Basic Information");
        Pane categoryPane = new VBox(10);
        categoryPane.setBorder(null);
        categoryPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(categoryPane);
        this.setContent(wrapper);

        Label title = new TitleLabel("Sprints in " + selectedCategory.getProject().toString());

        Button btnView = new Button("View");
        //Button btnDelete = new Button("Delete");
        Button btnCreate = new Button("Create New Sprint");

        HBox selectionButtons = new HBox();
        selectionButtons.spacingProperty().setValue(10);
        selectionButtons.getChildren().add(btnView);
        //selectionButtons.getChildren().add(btnDelete);
        selectionButtons.getChildren().add(btnCreate);
        selectionButtons.setAlignment(Pos.TOP_LEFT);


        ListView sprintBox = new ListView(selectedCategory.getProject().getSprints());
        sprintBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        sprintBox.setMaxWidth(275);

        categoryPane.getChildren().add(title);
        categoryPane.getChildren().add(sprintBox);
        categoryPane.getChildren().add(selectionButtons);

        btnView.setOnAction((event) -> {
                if (sprintBox.getSelectionModel().getSelectedItem() != null) {
                    App.mainPane.selectItem((SaharaItem)
                            sprintBox.getSelectionModel().getSelectedItem());
                }
            });


        /*btnDelete.setOnAction((event) -> {
                if (sprintBox.getSelectionModel().getSelectedItem() != null) {
                    showDeleteDialog((SaharaItem) sprintBox
                            .getSelectionModel().getSelectedItem());
                }
            });*/

        btnCreate.setOnAction((event) -> {
                new CreateSprintDialog();
            });

    }

}
