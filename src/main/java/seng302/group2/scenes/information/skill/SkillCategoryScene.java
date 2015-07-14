package seng302.group2.scenes.information.skill;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.workspace.Workspace;

/**
 * A class for displaying all skills currently created in a workspace.
 *
 * @author David Moseley, Bronson McNaughton
 */
public class SkillCategoryScene extends TrackedTabPane {
    /**
     * Constructor for the SkillCategoryScene class.
     * @param currentWorkspace the current workspace
     */
    public SkillCategoryScene(Workspace currentWorkspace) {
        super(TrackedTabPane.ContentScene.SKILL_CATEGORY);

        // Define and add the tabs
        Tab categoryTab = new SkillCategoryTab(currentWorkspace);

        this.getTabs().addAll(categoryTab);
    }
}