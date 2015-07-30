package seng302.group2.scenes.information.skill;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.skills.Skill;

/**
 * A class for displaying the skill scene
 *
 * @author jml168
 */
public class SkillScene extends TrackedTabPane {
    /**
     * Constructor for the SkillScene class. Displays an instance of SkillInfoTab.
     * @param currentSkill the current skill for which information will be displayed
     */
    public SkillScene(Skill currentSkill) {
        super(ContentScene.SKILL, currentSkill);

        // Define and add the tabs
        Tab informationTab = new SkillInfoTab(currentSkill);

        this.getTabs().addAll(informationTab);  // Add the tabs to the pane
    }

    /**
     * Constructor for the SkillScene class. This creates an instance of the SkillEditTab tab and displays it.
     * @param currentSkill the skill which will be edited
     * @param editScene a boolean - if the scene is an edit scene
     */
    public SkillScene(Skill currentSkill, boolean editScene) {
        super(ContentScene.SKILL_EDIT, currentSkill);

        // Define and add the tabs
        Tab editTab = new SkillEditTab(currentSkill);

        this.getTabs().addAll(editTab);  // Add the tabs to the pane
    }

    public Tab getCurrentTab() {
        return this.getSelectionModel().getSelectedItem();
    }
}
