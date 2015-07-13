package seng302.group2.scenes.sceneswitch.switchStrategies.category;

import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.information.team.TeamCategoryScene;
import seng302.group2.scenes.sceneswitch.switchStrategies.CategorySwitchStrategy;

/**
 * The switch strategy for the Team category
 * Created by jml168 on 7/06/15.
 */
public class TeamCategoryCategorySwitchStrategy implements CategorySwitchStrategy {
    @Override
    public void switchScene() {
        App.mainPane.setContent(TeamCategoryScene.getTeamCategoryScene(
                Global.currentWorkspace));
    }
}
