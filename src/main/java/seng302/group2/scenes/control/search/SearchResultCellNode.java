package seng302.group2.scenes.control.search;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.SaharaItem;

/**
 * The content of a search result cell.
 * Created by drm127 on 4/09/15.
 */
public class SearchResultCellNode extends VBox {

    private SaharaItem item = null;
    private String itemName = "";
    private String relationshipString = "";
    private String matchingString = "";
    private SearchableTab searchResult = null;
    private TrackedTabPane searchableScene = null;

    public SearchResultCellNode(SaharaItem item, String itemName, SearchableTab searchResult,
                                String relationshipString, TrackedTabPane scene) {
        this.item = item;
        this.itemName = itemName;
        this.relationshipString = relationshipString;
        this.matchingString = searchResult.toString();
        this.searchResult = searchResult;
        this.searchableScene = scene;

        VBox content = construct();
        this.getChildren().add(content);
    }

    public SaharaItem getItem() {
        return item;
    }

    public SearchableTab getTab() {
        return searchResult;
    }

    public TrackedTabPane getSearchableScene() {
        return searchableScene;
    }

    private VBox construct() {
        VBox content = new VBox();
        content.setPrefHeight(48);

        content.setPadding(new Insets(2, 2, 2, 6));
        content.setAlignment(Pos.CENTER_LEFT);

        SearchableText titleLabel = new SearchableText(itemName);
        titleLabel.injectStyle("-fx-font-weight: bold");

        SearchableText relationshipLabel = new SearchableText(relationshipString);

        SearchableText matchLabel = new SearchableText(matchingString);
        matchLabel.injectStyle("-fx-font-size: 85%");

        if (!relationshipString.equals("")) {
            content.getChildren().addAll(titleLabel, relationshipLabel, matchLabel);
        }
        else {
            content.getChildren().addAll(titleLabel, matchLabel);
        }
        HBox.setHgrow(content, Priority.ALWAYS);

        return content;
    }
}
