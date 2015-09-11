package seng302.group2.scenes.information.roadMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableListView;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.control.search.SearchableTitle;
import seng302.group2.scenes.dialog.CreateSkillDialog;
import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;
import seng302.group2.workspace.roadMap.RoadMap;
import seng302.group2.workspace.skills.Skill;
import seng302.group2.workspace.workspace.Workspace;

/**
 * Created by cvs20 on 11/09/15.
 */
public class RoadMapCategoryTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();

    /**
     * Constructor for RoadMapCategoryTab class.
     * @param currentWorkspace The current workspace
     */
    public RoadMapCategoryTab(Workspace currentWorkspace) {
        // Tab Settings
        this.setText("Basic Information");
        Pane categoryPane = new VBox(10);
        categoryPane.setBorder(null);
        categoryPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(categoryPane);
        this.setContent(wrapper);

        // Create Controls
        SearchableText title = new SearchableTitle("Skills in " + currentWorkspace.getShortName());
        SearchableListView<RoadMap> roadMapBox = new SearchableListView<>(currentWorkspace.getRoadMaps());
        roadMapBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        roadMapBox.setMaxWidth(275);

        Button btnView = new Button("View");
        Button btnDelete = new Button("Delete");
        Button btnCreate = new Button("Create New Skill");

        HBox selectionButtons = new HBox();
        selectionButtons.spacingProperty().setValue(10);
        selectionButtons.getChildren().addAll(btnView, btnDelete, btnCreate);
        selectionButtons.setAlignment(Pos.TOP_LEFT);

        // Events
        btnView.setOnAction((event) -> {
                if (roadMapBox.getSelectionModel().getSelectedItem() != null) {
                    App.mainPane.selectItem(roadMapBox.getSelectionModel().getSelectedItem());
                }
            });

        btnDelete.setOnAction((event) -> {
                if (roadMapBox.getSelectionModel().getSelectedItem() != null) {
                    showDeleteDialog(roadMapBox.getSelectionModel().getSelectedItem());
                }
            });

        btnCreate.setOnAction((event) -> {
                javafx.scene.control.Dialog creationDialog = new CreateSkillDialog();
                creationDialog.show();
            });

        // Add items to pane & search collection
        categoryPane.getChildren().addAll(
                title,
                roadMapBox,
                selectionButtons
        );

        Collections.addAll(
                searchControls,
                title,
                roadMapBox
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

