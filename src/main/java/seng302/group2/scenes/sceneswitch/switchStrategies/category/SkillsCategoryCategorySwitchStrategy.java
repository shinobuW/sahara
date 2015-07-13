package seng302.group2.scenes.sceneswitch.switchStrategies.category;

import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.information.skill.SkillCategoryScene;
import seng302.group2.scenes.sceneswitch.switchStrategies.CategorySwitchStrategy;

/**
 * The switch strategy for the Skill category
 * Created by jml168 on 7/06/15.
 */
public class SkillsCategoryCategorySwitchStrategy implements CategorySwitchStrategy {
    @Override
    public void switchScene() {
        App.mainPane.setContent(SkillCategoryScene.getSkillCategoryScene(
                Global.currentWorkspace));
    }
}
