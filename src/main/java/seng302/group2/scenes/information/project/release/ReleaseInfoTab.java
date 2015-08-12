package seng302.group2.scenes.information.project.release;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.control.search.SearchableTitle;
import seng302.group2.workspace.project.release.Release;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * The release information tab.
 * Created by jml168 on 11/05/15.
 */
public class ReleaseInfoTab extends SearchableTab {
    List<SearchableControl> searchControls = new ArrayList<>();
    
    /**
     * Constructor for the Release Info Tab
     * 
     * @param currentRelease The currently selected Release
     */
    public ReleaseInfoTab(Release currentRelease) {
        // Tab settings
        this.setText("Basic Information");
        Pane basicInfoPane = new VBox(10);
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        // Create controls
        SearchableText title = new SearchableTitle(currentRelease.getShortName());
        SearchableText description = new SearchableText("Release Description: " + currentRelease.getDescription());
        SearchableText projectLabel = new SearchableText("Project: " + currentRelease.getProject().toString());

        String releaseDateString = "";
        if (currentRelease.getEstimatedDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            releaseDateString = currentRelease.getEstimatedDate().format(formatter);
        }
        SearchableText releaseDate = new SearchableText("Estimated Release Date: " + releaseDateString);

        Button btnEdit = new Button("Edit");

        // Events
        btnEdit.setOnAction((event) -> {
                currentRelease.switchToInfoScene(true);
            });

        // Add items to pane & search collection
        basicInfoPane.getChildren().addAll(
                title,
                description,
                releaseDate,
                projectLabel,
                btnEdit
        );

        Collections.addAll(searchControls,
                title,
                description,
                releaseDate,
                projectLabel
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
