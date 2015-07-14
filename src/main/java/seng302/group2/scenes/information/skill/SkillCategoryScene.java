package seng302.group2.scenes.information.skill;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.workspace.Workspace;

/**
 * A class for displaying the skill category. Contains information
 * about all the skills in the workspace.
 *
 * Created by btm38 on 14/07/15.
 */
public class SkillCategoryScene extends TrackedTabPane {
    /**
     * Constructor for the SkillCategoryScene class. Creates a tab
     * of SkillCategoryTab and displays it.
     * @param currentWorkspace the current workspace
     */
    public SkillCategoryScene(Workspace currentWorkspace) {
        super(TrackedTabPane.ContentScene.SKILL_CATEGORY);

        // Define and add the tabs
        Tab categoryTab = new SkillCategoryTab(currentWorkspace);

        this.getTabs().addAll(categoryTab);
    }
}