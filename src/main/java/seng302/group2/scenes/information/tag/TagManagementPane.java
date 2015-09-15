package seng302.group2.scenes.information.tag;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;
import seng302.group2.Global;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableListView;
import seng302.group2.workspace.tag.Tag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A Pane for the creation and management of Tags.
 * Created by drm127 on 14/09/15.
 */
public class TagManagementPane extends SplitPane {

    Set<SearchableControl> searchControls = new HashSet<>();

    ListView<Tag> tagListView;
    ObservableList<Tag> tagList = Global.currentWorkspace.getAllTags();


    public TagManagementPane() {
        VBox content = new VBox(8);
        content.setPadding(new Insets(8));

    }


    private void construct() {
        this.getChildren().clear();
        this.constructList();
    }



    private void constructList() {
        tagListView = new SearchableListView<>(tagList, searchControls);
        tagListView.selectionModelProperty().addListener(event -> {
                constructDetail();
            });
        // Sort here if needed
        tagListView.getSelectionModel().select(0);
        this.getChildren().add(tagListView);
    }


    private void constructDetail() {

    }
}
