/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.sceneswitch.switchStrategies.workspace.project.story;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seng302.group2.App;
import seng302.group2.scenes.information.project.story.StoryScene;
import seng302.group2.scenes.information.project.story.task.TaskScene;
import seng302.group2.scenes.sceneswitch.switchStrategies.InformationSwitchStrategy;
import seng302.group2.scenes.sceneswitch.switchStrategies.workspace.project.StoryInformationSwitchStrategy;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.tasks.Task;

/**
 *
 * @author Darzolak
 */
public class TaskInformationSwitchStrategy implements InformationSwitchStrategy {
    transient Logger logger = LoggerFactory.getLogger(TaskInformationSwitchStrategy.class);

    /**
     * Sets the main pane to be an instance of the Task Scene. 
     * @param item The SaharaItem for the scene to be constructed with. 
     */
    @Override
    public void switchScene(SaharaItem item) {
        if (item instanceof Task) {
            App.mainPane.setContent(new TaskScene((Task) item));
        }
        else {
            // Bad call
            logger.warn("Tried changing to story scene with a non-story instance");
        }
    }

    /**
     * Sets the main pane to be an instance of the Task Edit Scene.
     * @param item The SaharaItem for the scene to be constructed with.
     * @param editScene Whether the edit scene is to be shown.
     */
    @Override
    public void switchScene(SaharaItem item, boolean editScene) {
        if (item instanceof Task) {
            if (editScene) {
                App.mainPane.setContent(new TaskScene((Task) item, true));
            }
            else {
                switchScene(item);
            }
        }
        else {
            // Bad call
            logger.warn("Tried changing to story scene with a non-story instance");
        }
    }
}
