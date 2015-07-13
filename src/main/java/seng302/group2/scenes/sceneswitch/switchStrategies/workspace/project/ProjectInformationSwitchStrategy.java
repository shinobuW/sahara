package seng302.group2.scenes.sceneswitch.switchStrategies.workspace.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seng302.group2.App;
import seng302.group2.scenes.information.project.ProjectEditScene;
import seng302.group2.scenes.information.project.ProjectScene;
import seng302.group2.scenes.sceneswitch.switchStrategies.InformationSwitchStrategy;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.project.Project;

/**
 * An switch strategy for people information and edit scenes
 * Created by Jordane on 8/06/2015.
 */
public class ProjectInformationSwitchStrategy implements InformationSwitchStrategy {
    transient Logger logger = LoggerFactory.getLogger(ProjectInformationSwitchStrategy.class);

    @Override
    public void switchScene(SaharaItem item) {
        if (item instanceof Project) {
            App.mainPane.setContent(new ProjectScene((Project)item));
            //MainScene.contentPane.setContent(new ProjectScene((Project) item));
        }
        else {
            // Bad call
            logger.warn("Tried changing to project scene with a non-project instance");
        }
    }

    @Override
    public void switchScene(SaharaItem item, boolean editScene) {
        if (item instanceof Project) {
            if (editScene) {
                App.mainPane.setContent(ProjectEditScene.getProjectEditScene(
                        (Project) item));
            }
            else {
                switchScene(item);
            }
        }
        else {
            // Bad call
            logger.warn("Tried changing to project scene with a non-project instance");
        }
    }
}
