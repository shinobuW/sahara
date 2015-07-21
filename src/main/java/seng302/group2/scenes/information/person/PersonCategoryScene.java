package seng302.group2.scenes.information.person;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.workspace.Workspace;

/**
 * A class for displaying the person category. Contains information
 * about all the persons in the workspace.
 *
 * Created by btm38 on 14/07/15.
 */
public class PersonCategoryScene extends TrackedTabPane {
    /**
     * Constructor for the PersonCategoryScene class. Creates a tab
     * of PersonCategoryTab and displays it.
     * @param currentWorkspace the current workspace
     */
    public PersonCategoryScene(Workspace currentWorkspace) {
        super(ContentScene.PERSON_CATEGORY, currentWorkspace);

        // Define and add the tabs
        Tab categoryTab = new PersonCategoryTab(currentWorkspace);

        this.getTabs().addAll(categoryTab);  // Add the tabs to the pane
    }
}
