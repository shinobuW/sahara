package seng302.group2.scenes.information.skill;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.workspace.Workspace;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * A class for displaying the skill category. Contains information
 * about all the skills in the workspace.
 *
 * Created by btm38 on 14/07/15.
 */
public class SkillCategoryScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new HashSet<>();

    /**
     * Constructor for the SkillCategoryScene class. Creates a tab
     * of SkillCategoryTab and displays it.
     * @param currentWorkspace the current workspace
     */
    public SkillCategoryScene(Workspace currentWorkspace) {
        super(TrackedTabPane.ContentScene.SKILL_CATEGORY, currentWorkspace);

        // Define and add the tabs
        SearchableTab categoryTab = new SkillCategoryTab(currentWorkspace);
        Collections.addAll(searchableTabs, categoryTab);

        this.getTabs().addAll(searchableTabs);
    }

    /**
     * Gets all the SearchableTabs on this scene
     * @return collection of SearchableTabs
     */
    @Override
    public Collection<SearchableTab> getSearchableTabs() {
        return searchableTabs;
    }
}