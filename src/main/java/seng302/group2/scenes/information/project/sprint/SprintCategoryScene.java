package seng302.group2.scenes.information.project.sprint;

import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.categories.subCategory.project.SprintCategory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
        SearchableTab informationTab = new SprintCategoryTab(selectedCategory);
        Collections.addAll(getSearchableTabs(), informationTab);

        this.getTabs().addAll(getSearchableTabs());  // Add the tabs to the pane
    }
}
