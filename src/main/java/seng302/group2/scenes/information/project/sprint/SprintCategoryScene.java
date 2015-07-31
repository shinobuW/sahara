package seng302.group2.scenes.information.project.sprint;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.categories.subCategory.project.SprintCategory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * A class for displaying the sprint category. Contains information
 * about sprints in a project.
 *
 * Created by drm127 on 29/07/15.
 */
public class SprintCategoryScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new HashSet<>();

    public SprintCategoryScene(SprintCategory selectedCategory) {
        super(ContentScene.SPRINT_CATEGORY, selectedCategory.getProject());

        // Define and add the tabs
        SearchableTab informationTab = new SprintCategoryTab(selectedCategory);
        Collections.addAll(searchableTabs, informationTab);

        this.getTabs().addAll(searchableTabs);  // Add the tabs to the pane
    }

    /**
     * Gets all the SearchableTabs on this scene
     * @return collection of tabs
     */
    @Override
    public Collection<SearchableTab> getSearchableTabs() {
        return searchableTabs;
    }
}
