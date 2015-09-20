package seng302.group2.scenes.information.workspace;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.CustomInfoLabel;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.control.search.SearchableTitle;
import seng302.group2.workspace.workspace.Workspace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * The workspace information tab
 * Created by jml168 on 11/05/15.
 */
public class WorkspaceInfoTab extends SearchableTab {

    private Workspace currentWorkspace;
    List<SearchableControl> searchControls = new ArrayList<>();

    /**
     * Constructor of the Workspace Info Tab.
     * 
     * @param currentWorkspace The currently selected Workspace.
     */
    public WorkspaceInfoTab(Workspace currentWorkspace) {
        this.currentWorkspace = currentWorkspace;
        setContent(new Pane());
        construct();
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
        // Tab settings
        this.setText("Basic Information");

        Pane basicInfoPane = new VBox(10);
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        // Create Controls
        SearchableText title = new SearchableTitle(currentWorkspace.getLongName());
        CustomInfoLabel shortName = new CustomInfoLabel("Short Name: ", currentWorkspace.getShortName());
        CustomInfoLabel desc = new CustomInfoLabel("Workspace Description: ", currentWorkspace.getDescription());
        CustomInfoLabel numOfPeople = new CustomInfoLabel("Number of people in " + currentWorkspace.getShortName()
                + ": ", currentWorkspace.getNumPeople().toString());
        CustomInfoLabel numOfTeams = new CustomInfoLabel("Number of teams in " + currentWorkspace.getShortName()
                + ": ", currentWorkspace.getNumTeams().toString());
        CustomInfoLabel numOfProjects = new CustomInfoLabel("Number of people in " + currentWorkspace.getShortName()
                + ": ", currentWorkspace.getNumProjects().toString());

                // Add items to pane & search collection
        basicInfoPane.getChildren().addAll(
                title,
                shortName,
                numOfProjects,
                numOfTeams,
                numOfPeople,
                desc

        );

        Collections.addAll(searchControls,
                title,
                shortName,
                numOfPeople,
                desc
        );
    }

    /**
     * Switches to the edit scene
     */
    public void edit() {
        currentWorkspace.switchToInfoScene(true);
    }

    /**
     * Gets the string representation of the current Tab
     * @return The String value
     */
    @Override
    public String toString() {
        return "Workspace Info Tab";
    }
}
