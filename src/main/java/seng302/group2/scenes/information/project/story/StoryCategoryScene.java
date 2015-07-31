package seng302.group2.scenes.information.project.story;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.categories.subCategory.project.StoryCategory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * A class for displaying the story category. Contains information
 * about all the stories in the project.
 *
 * Created by btm38 on 14/07/15.
 */
public class StoryCategoryScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new HashSet<>();

    /**
     * Constructor for the StoryCategoryScene class. Creates a tab
     * of StoryCategoryTab and displays it.
     * @param selectedCategory the current workspace
     */
    public StoryCategoryScene(StoryCategory selectedCategory) {
        super(ContentScene.RELEASE_CATEGORY, selectedCategory.getProject());

        // Define and add the tabs
        SearchableTab informationTab = new StoryCategoryTab(selectedCategory);
        Collections.addAll(searchableTabs, informationTab);

        this.getTabs().addAll(searchableTabs);  // Add the tabs to the pane
    }

    @Override
    public Collection<SearchableTab> getSearchableTabs() {
        return searchableTabs;
    }
}