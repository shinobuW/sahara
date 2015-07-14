package seng302.group2.scenes.information.project.release;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.categories.subCategory.project.ReleaseCategory;

/**
 * A class for displaying the release category. Contains information
 * about all the releases in the project.
 *
 * Created by btm38 on 14/07/15.
 */
public class ReleaseCategoryScene extends TrackedTabPane {
    /**
     * Constructor for the ReleaseCategoryScene class. Creates a tab
     * of ReleaseCategoryTab and displays it.
     * @param selectedCategory the current workspace
     */
    public ReleaseCategoryScene(ReleaseCategory selectedCategory) {
        super(ContentScene.RELEASE_CATEGORY);

        // Define and add the tabs
        Tab informationTab = new ReleaseCategoryTab(selectedCategory);

        this.getTabs().addAll(informationTab);  // Add the tabs to the pane
    }
}