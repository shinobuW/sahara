package seng302.group2.scenes.information.workspace;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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

    List<SearchableControl> searchControls = new ArrayList<>();

    /**
     * Constructor of the Workspace Info Tab.
     * 
     * @param currentWorkspace The currently selected Workspace.
     */
    public WorkspaceInfoTab(Workspace currentWorkspace) {
        // Tab settings
        this.setText("Basic Information");

        Pane basicInfoPane = new VBox(10);
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        // Create Controls
        SearchableText title = new SearchableTitle(currentWorkspace.getLongName());
        Button btnEdit = new Button("Edit");
        SearchableText shortName = new SearchableText("Short Name: " + currentWorkspace.getShortName());
        SearchableText desc = new SearchableText("Workspace Description: " + currentWorkspace.getDescription());

        // Events
        btnEdit.setOnAction((event) -> {
                currentWorkspace.switchToInfoScene(true);
            });

        // Add items to pane & search collection
        basicInfoPane.getChildren().addAll(
                title,
                shortName,
                desc,
                btnEdit
        );

        Collections.addAll(searchControls,
                title,
                shortName,
                desc
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
