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
 * A scene to show information about the stories in Sahara
 * Created by drm127 on 17/05/15.
 */
public class StoryCategoryScene {
    /**
     * Gets the Story Category Scene
     *
     * @param selectedCategory The category currently selected
     * @return The story category info scene
     */
    public static ScrollPane getStoryCategoryScene(StoryCategory selectedCategory) {
        Pane informationPane = new VBox(10);

        informationPane.setPadding(new Insets(25, 25, 25, 25));
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

        informationPane.getChildren().add(title);
        informationPane.getChildren().add(storyBox);
        informationPane.getChildren().add(selectionButtons);

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

        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;
    }
}
