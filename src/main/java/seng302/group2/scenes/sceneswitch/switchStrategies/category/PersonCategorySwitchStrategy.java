package seng302.group2.scenes.sceneswitch.switchStrategies.category;

import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.information.person.PersonCategoryScene;
import seng302.group2.scenes.sceneswitch.switchStrategies.CategorySwitchStrategy;

/**
 * The switch strategy for the Person category
 * Created by jml168 on 7/06/15.
 */
public class PersonCategorySwitchStrategy implements CategorySwitchStrategy {
    @Override
    public void switchScene() {
        MainScene.contentPane.setContent(PersonCategoryScene.getPersonCategoryScene(
                Global.currentWorkspace));
    }
}
