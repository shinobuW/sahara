package seng302.group2.scenes.information.project.release;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.project.release.Release;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * The workspace information tab
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
        this.setText("Basic Information");

        Pane basicInfoPane = new VBox(10);  // The pane that holds the basic info
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);


        Label title = new TitleLabel(currentRelease.getShortName());

        Button btnEdit = new Button("Edit");

        String releaseDateString = "";

        if (currentRelease.getEstimatedDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            releaseDateString = currentRelease.getEstimatedDate().format(formatter);
        }

        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new Label("Release Description: "
                + currentRelease.getDescription()));
        basicInfoPane.getChildren().add(new Label("Estimated Release Date: "
                + releaseDateString));
        basicInfoPane.getChildren().add(new Label("Project: "
                + currentRelease.getProject().toString()));
        basicInfoPane.getChildren().add(btnEdit);

        btnEdit.setOnAction((event) -> {
                currentRelease.switchToInfoScene(true);
            });

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
