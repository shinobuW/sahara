package seng302.group2.scenes.information.project.sprint;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.categories.subCategory.project.SprintCategory;

/**
 * A class for displaying the sprint category. Contains information
 * about sprints in a project.
 *
 * Created by drm127 on 29/07/15.
 */
public class SprintCategoryScene extends TrackedTabPane {

    public SprintCategoryScene(SprintCategory selectedCategory) {
        super(ContentScene.SPRINT_CATEGORY, selectedCategory.getProject());

        // Define and add the tabs
        Tab informationTab = new SprintCategoryTab(selectedCategory);

        this.getTabs().addAll(informationTab);  // Add the tabs to the pane
    }
}
