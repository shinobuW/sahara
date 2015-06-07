package seng302.group2.scenes.sceneswitch.switchStrategies.category;

import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.information.person.PersonCategoryScene;
import seng302.group2.scenes.information.project.ProjectCategoryScene;
import seng302.group2.scenes.sceneswitch.switchStrategies.SwitchStrategy;

/**
 * The switch strategy for the Project category
 * Created by jml168 on 7/06/15.
 */
public class ProjectCategorySwitchStrategy implements SwitchStrategy
{
    @Override
    public void switchScene()
    {
        MainScene.contentPane.setContent(ProjectCategoryScene.getProjectCategoryScene(
                Global.currentWorkspace));
    }

    @Override
    public void switchScene(boolean switchToEditScene)
    {
        switchScene();  // Categories don't have an edit scene
    }
}
