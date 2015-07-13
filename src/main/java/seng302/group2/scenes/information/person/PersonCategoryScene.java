package seng302.group2.scenes.information.person;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.workspace.Workspace;

/**
 * A class for displaying the person category scene.
 *
 * @author David Moseley
 */
public class PersonCategoryScene extends TrackedTabPane {
    /**
     * Constructor for the PersonCategoryScene class.
     * @param currentWorkspace the current workspace
     */
    public PersonCategoryScene(Workspace currentWorkspace) {
        super(ContentScene.PERSON_CATEGORY);

        // Define and add the tabs
        Tab categoryTab = new PersonCategoryTab(currentWorkspace);

        this.getTabs().addAll(categoryTab);  // Add the tabs to the pane
    }
}
