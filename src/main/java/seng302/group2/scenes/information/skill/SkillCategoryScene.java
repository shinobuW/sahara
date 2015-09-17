package seng302.group2.scenes.information.skill;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.information.project.ProjectCategoryTab;
import seng302.group2.workspace.workspace.Workspace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A class for displaying the skill category. Contains information
 * about all the skills in the workspace.
 *
 * Created by btm38 on 14/07/15.
 */
public class SkillCategoryScene extends TrackedTabPane {

    Workspace currentWorkspace;
    SearchableTab categoryTab;

    /**
     * Constructor for the SkillCategoryScene class. Creates a tab
     * of SkillCategoryTab and displays it.
     * @param currentWorkspace the current workspace
     */
    public SkillCategoryScene(Workspace currentWorkspace) {
        super(TrackedTabPane.ContentScene.SKILL_CATEGORY, currentWorkspace);

        this.currentWorkspace = currentWorkspace;

        // Define and add the tabs
        updateAllTabs();
        Collections.addAll(getSearchableTabs(), categoryTab);

        this.getTabs().addAll(getSearchableTabs());
    }

    @Override
    public void updateTabs() {
        Tab selectedTab = this.getSelectionModel().getSelectedItem();
        if (categoryTab != selectedTab) {
            categoryTab = new ProjectCategoryTab(currentWorkspace);
        }
    }

    @Override
    public void updateAllTabs() {
        categoryTab = new ProjectCategoryTab(currentWorkspace);
    }
}