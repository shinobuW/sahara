package seng302.group2.scenes.information.project.release;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.dialog.CreateReleaseDialog;
import seng302.group2.scenes.dialog.DeleteDialog;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.categories.subCategory.project.ReleaseCategory;

/**
 * A class for displaying all releases in a project.
 *
 * Created by btm38 on 14/07/15.
 */
public class ReleaseCategoryTab extends Tab {
    /**
     * Gets the Release Category Tab
     *
     * @param selectedCategory The category currently selected
     */
    public ReleaseCategoryTab(ReleaseCategory selectedCategory) {
        this.setText("Basic Information");
        Pane categoryPane = new VBox(10);
        categoryPane.setBorder(null);
        categoryPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(categoryPane);
        this.setContent(wrapper);
        Label title = new TitleLabel("Releases in " + selectedCategory.getProject().toString());

        Button btnView = new Button("View");
        Button btnDelete = new Button("Delete");
        Button btnCreate = new Button("Create New Release");

        HBox selectionButtons = new HBox();
        selectionButtons.spacingProperty().setValue(10);
        selectionButtons.getChildren().add(btnView);
        selectionButtons.getChildren().add(btnDelete);
        selectionButtons.getChildren().add(btnCreate);
        selectionButtons.setAlignment(Pos.TOP_LEFT);


        ListView releaseBox = new ListView(selectedCategory.getProject().getReleases());
        releaseBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        releaseBox.setMaxWidth(275);

        categoryPane.getChildren().add(title);
        categoryPane.getChildren().add(releaseBox);
        categoryPane.getChildren().add(selectionButtons);

        btnView.setOnAction((event) -> {
                if (releaseBox.getSelectionModel().getSelectedItem() != null) {
                    App.mainPane.selectItem((SaharaItem)
                            releaseBox.getSelectionModel().getSelectedItem());
                }
            });


        btnDelete.setOnAction((event) -> {
                if (releaseBox.getSelectionModel().getSelectedItem() != null) {
                    DeleteDialog.showDeleteDialog((SaharaItem) releaseBox.getSelectionModel()
                            .getSelectedItem());
                }
            });

        btnCreate.setOnAction((event) -> {
                CreateReleaseDialog.show(selectedCategory.getProject());
            });

    }
}





