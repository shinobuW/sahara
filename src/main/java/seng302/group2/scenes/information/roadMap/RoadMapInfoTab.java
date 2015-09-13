package seng302.group2.scenes.information.roadMap;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.CustomInfoLabel;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.control.search.SearchableTitle;
import seng302.group2.workspace.roadMap.RoadMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * The RoadMap information tab
 * Created by cvs20 on 11/09/15.
 */
public class RoadMapInfoTab extends SearchableTab {
    List<SearchableControl> searchControls = new ArrayList<>();
    Button btnEdit = new Button("Edit");

    /**
     * Constructor for the RoadMapInfoTab class.
     * @param currentRoadMap the current roadmap for which information will be displayed
     */
    public RoadMapInfoTab(RoadMap currentRoadMap) {

        this.setText("Basic Information");
        Pane basicInfoPane = new VBox(10);
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        // Create controls
        SearchableText title = new SearchableTitle(currentRoadMap.getLongName(), searchControls);

        Separator separator = new Separator();
        Button btnEdit = new Button("Edit");


        

        CustomInfoLabel shortNameField = new CustomInfoLabel("Short Name: ", currentRoadMap.getShortName());
        CustomInfoLabel currentTeamsLabel = new CustomInfoLabel("Current Teams:", "");
        CustomInfoLabel releasesLabel = new CustomInfoLabel("Releases:", "");

        Node roadMapNode = new RoadMapNode(currentRoadMap);


        // Events
        btnEdit.setOnAction((event) -> {
                currentRoadMap.switchToInfoScene(true);
            });

        // Add items to pane & search collection
        basicInfoPane.getChildren().addAll(
                title,
                shortNameField,
                roadMapNode,
                separator,
                btnEdit
                
        );

        Collections.addAll(searchControls,
                title,
                shortNameField,
                currentTeamsLabel,
                releasesLabel
        );

    }
    
    /**
     * Gets the node for the Roadmap.
     * 
     * @param currentRoadMap the roadMap to be processed
     * @return An Hbox with all releases, sprints, stories associated with it.
     */
    public HBox roadMapNode(RoadMap currentRoadMap) {
        
        
        
        return null;
    }


    /**
     * Gets all the searchable controls on this tab.
     * @return a collection of all the searchable controls on this tab.
     */
    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }

    /**
     * Gets the string representation of the current Tab
     * @return The String value
     */
    @Override
    public String toString() {
        return "RoadMap Info Tab";
    }

}
