package seng302.group2.scenes.information.roadMap;

import javafx.scene.control.Button;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.roadMap.RoadMap;
import seng302.group2.workspace.skills.Skill;

import java.util.ArrayList;
import java.util.Collection;
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
