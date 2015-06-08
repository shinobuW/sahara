package seng302.group2.scenes.sceneswitch.switchStrategies.workspace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.information.skill.SkillEditScene;
import seng302.group2.scenes.information.skill.SkillScene;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.sceneswitch.switchStrategies.InformationSwitchStrategy;
import seng302.group2.workspace.skills.Skill;

/**
 * An switch strategy for people information and edit scenes
 * Created by Jordane on 8/06/2015.
 */
public class SkillInformationSwitchStrategy implements InformationSwitchStrategy {
    Logger logger = LoggerFactory.getLogger(SkillInformationSwitchStrategy.class);

    @Override
    public void switchScene(TreeViewItem item) {
        if (item instanceof Skill) {
            MainScene.contentPane.setContent(new SkillScene((Skill) item));
        }
        else {
            // Bad call
            logger.warn("Tried changing to skill scene with a non-skill instance");
        }
    }

    @Override
    public void switchScene(TreeViewItem item, boolean editScene) {
        if (item instanceof Skill) {
            if (editScene) {
                MainScene.contentPane.setContent(SkillEditScene.getSkillEditScene(
                        (Skill) item));
            }
            else {
                switchScene(item);
            }
        }
        else {
            // Bad call
            logger.warn("Tried changing to skill scene with a non-skill instance");
        }
    }
}
