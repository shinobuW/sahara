package seng302.group2.scenes.information.roadMap;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import seng302.group2.Global;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.control.search.SearchableTitle;
import seng302.group2.workspace.roadMap.RoadMap;
import seng302.group2.workspace.workspace.Workspace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by cvs20 on 11/09/15.
 */
public class RoadMapCategoryTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();
    Workspace currentWorkspace;

    /**
     * Constructor for RoadMapCategoryTab class.
     * @param currentWorkspace The current workspace
     */
    public RoadMapCategoryTab(Workspace currentWorkspace) {
        this.currentWorkspace = currentWorkspace;
        this.construct();

    }

    /**
     * Gets all the searchable controls on this tab.
     * @return a collection of all the searchable controls on this tab.
     */
    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }

    @Override
    public void construct() {
        // Tab Settings
        this.setText("RoadMap");
        Pane categoryPane = new VBox(10);
        categoryPane.setBorder(null);
        categoryPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(categoryPane);
        this.setContent(wrapper);

        // Create Controls
        SearchableText title = new SearchableTitle("RoadMaps in " + currentWorkspace.getShortName());

        HBox roadMaps = new HBox();
        for (RoadMap roadMap : Global.currentWorkspace.getRoadMaps()) {
            RoadMapNode roadMapNode = new RoadMapNode(roadMap);
            roadMaps.getChildren().add(roadMapNode);
            searchControls.addAll(roadMapNode.getSearchableControls());

        }

        HBox roadmapKeyBox = new HBox(8);
        Rectangle yellow = new Rectangle(250,25,20,20);
        yellow.setFill(Color.color(255 / 255, 0.5, 10 / 255, 0.62));
        yellow.setStrokeWidth(3);
        yellow.setArcWidth(10);
        yellow.setArcHeight(10);
        SearchableText yellowKeyLabel = new SearchableText("= Road Map");
        roadmapKeyBox.getChildren().addAll(yellow, yellowKeyLabel);

        HBox releaseKeyBox = new HBox(8);
        Rectangle blue = new Rectangle(250,25,20,20);
        blue.setFill(Color.color(11 / 255, 0, 255 / 255, 0.62));
        blue.setStrokeWidth(3);
        blue.setArcWidth(10);
        blue.setArcHeight(10);
        SearchableText blueKeyLabel = new SearchableText("= Releases");
        releaseKeyBox.getChildren().addAll(blue, blueKeyLabel);

        HBox sprintKeyBox = new HBox(8);
        Rectangle green = new Rectangle(250,25,20,20);
        green.setFill(Color.color(100 / 255, 255 / 255, 124 / 255, 0.83));
        green.setStrokeWidth(3);
        green.setArcWidth(10);
        green.setArcHeight(10);
        SearchableText greenKeyLabel = new SearchableText("= Sprints");
        sprintKeyBox.getChildren().addAll(green, greenKeyLabel);

        HBox storyKeyBox = new HBox(8);
        Rectangle red = new Rectangle(250,25,20,20);
        red.setFill(Color.color(255 / 255, 7 / 255, 0, 0.56));
        red.setStrokeWidth(3);
        red.setArcWidth(10);
        red.setArcHeight(10);
        SearchableText redKeyLabel = new SearchableText("= Stories");
        storyKeyBox.getChildren().addAll(red, redKeyLabel);

        Pane keyBox = new VBox(4);
        keyBox.getChildren().addAll(roadmapKeyBox, releaseKeyBox, sprintKeyBox, storyKeyBox);

        // Add items to pane & search collection
        categoryPane.getChildren().addAll(
                title,
                roadMaps,
                keyBox
        );

        Collections.addAll(
                searchControls,
                title
        );
    }
}

