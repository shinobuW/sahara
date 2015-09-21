package seng302.group2.scenes.control.search;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import seng302.group2.scenes.control.CustomInfoLabel;
import seng302.group2.scenes.information.tag.TagCellNode;
import seng302.group2.workspace.tag.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A class for displaying the tags on an info scene.
 * Created by Bronson Laptop on 21/09/2015.
 */
public class TagLabel extends HBox implements SearchableControl {
    private Set<SearchableControl> searchControls = new HashSet<>();
    ObservableList<Tag> tags = FXCollections.observableArrayList();



    public TagLabel(ObservableList<Tag> tags) {
        construct(tags);

    }

    public void construct(ObservableList<Tag> newTags) {
        this.tags.clear();
        this.tags = newTags;
        this.getChildren().clear();
        this.setSpacing(5);
        CustomInfoLabel label = new CustomInfoLabel("Tags: ", "", searchControls);
        this.getChildren().add(label);
        for (Tag tag : this.tags) {
            TagCellNode node = new TagCellNode(tag, false, searchControls);
            this.getChildren().add(node);
        }
    }

    @Override
    public boolean query(String query) {
        boolean found = false;
        for (SearchableControl control : searchControls) {
            found = control.query(query) || found;
        }
        return found;
    }

    @Override
    public int advancedQuery(String query, SearchType searchType) {
        // TODO @Bronson
        return 0;
    }


}
