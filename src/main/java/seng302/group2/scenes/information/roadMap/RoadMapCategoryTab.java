package seng302.group2.scenes.information.roadMap;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
        SearchableText title = new SearchableTitle("RoadMaps in " + currentWorkspace.getShortName());

        HBox roadMaps = new HBox();
        for (RoadMap roadMap : Global.currentWorkspace.getRoadMaps()) {
            RoadMapNode roadMapNode = new RoadMapNode(roadMap);
            roadMaps.getChildren().add(roadMapNode);
            searchControls.addAll(roadMapNode.getSearchableControls());
            
        } 
        
        // Add items to pane & search collection
        categoryPane.getChildren().addAll(
                title,
                roadMaps
        );

        Collections.addAll(
                searchControls,
                title
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

