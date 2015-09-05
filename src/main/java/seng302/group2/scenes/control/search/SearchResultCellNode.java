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
    private String searchedString = "";
    private int noOfResults = 0;
    private String matchingString = "";
    private SearchableTab searchResult = null;
    private TrackedTabPane searchableScene = null;

    public SearchResultCellNode(SaharaItem item, String searchString, SearchableTab searchResult,
                                int noOfResults, TrackedTabPane scene) {
        this.item = item;
        this.searchedString = searchString;
        this.noOfResults = noOfResults;
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

        SearchableText titleLabel = new SearchableText(item.toString());
        titleLabel.injectStyle("-fx-font-weight: bold");

        SearchableText matchLabel;
        if (noOfResults > 1) {
            matchLabel = new SearchableText("Found \"" + noOfResults + "\" instances of " + searchedString
                    + " in the " + matchingString);
        }
        else {
            matchLabel = new SearchableText("Found \"" + noOfResults + "\" instance of " + searchedString
                    + " in the " + matchingString);
        }

        matchLabel.injectStyle("-fx-font-size: 85%");

        content.getChildren().addAll(titleLabel, matchLabel);

        HBox.setHgrow(content, Priority.ALWAYS);

        return content;
    }
}
