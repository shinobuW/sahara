package seng302.group2.scenes.information.skill;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.sceneswitch.SceneSwitcher;
import seng302.group2.workspace.skills.Skill;

/**
 * A class for displaying the role scene
 *
 * @author jml168
 */
public class SkillScene extends TrackedTabPane {
    public SkillScene(Skill currentSkill) {
        super(ContentScene.SKILL);

        // Define and add the tabs
        Tab informationTab = new SkillInfoTab(currentSkill);

        this.getTabs().addAll(informationTab);  // Add the tabs to the pane
    }
}
