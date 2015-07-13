package seng302.group2.scenes.sceneswitch.switchStrategies.workspace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seng302.group2.App;
import seng302.group2.scenes.information.skill.SkillEditScene;
import seng302.group2.scenes.information.skill.SkillScene;
import seng302.group2.scenes.sceneswitch.switchStrategies.InformationSwitchStrategy;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.skills.Skill;

/**
 * An switch strategy for people information and edit scenes
 * Created by Jordane on 8/06/2015.
 */
public class SkillInformationSwitchStrategy implements InformationSwitchStrategy {
    transient Logger logger = LoggerFactory.getLogger(SkillInformationSwitchStrategy.class);

    @Override
    public void switchScene(SaharaItem item) {
        if (item instanceof Skill) {
            App.mainPane.setContent(new SkillScene((Skill) item));
        }
        else {
            // Bad call
            logger.warn("Tried changing to skill scene with a non-skill instance");
        }
    }

    @Override
    public void switchScene(SaharaItem item, boolean editScene) {
        if (item instanceof Skill) {
            if (editScene) {
                App.mainPane.setContent(SkillEditScene.getSkillEditScene(
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
