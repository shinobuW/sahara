package seng302.group2.scenes.information.workspace;

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
import seng302.group2.workspace.workspace.Workspace;

import java.util.ArrayList;
import java.util.Collection;
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
        this.setText("Basic Information");

        Pane basicInfoPane = new VBox(10);  // The pane that holds the basic info
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);


        Label title = new TitleLabel(currentWorkspace.getLongName());

        Button btnEdit = new Button("Edit");

        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new Label("Short Name: "
                + currentWorkspace.getShortName()));
        basicInfoPane.getChildren().add(new Label("Workspace Description: "
                + currentWorkspace.getDescription()));
        basicInfoPane.getChildren().add(btnEdit);

        btnEdit.setOnAction((event) -> {
                currentWorkspace.switchToInfoScene(true);
            });

    }

    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }
}
