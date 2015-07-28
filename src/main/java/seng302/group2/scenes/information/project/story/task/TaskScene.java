package seng302.group2.scenes.information.project.story.task;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.workspace.project.story.tasks.Task;

/**
 * Created by cvs20 on 28/07/15.
 */
public class TaskScene extends TrackedTabPane {
    /**
     * Constructor for the Task Scene. Creates instances of the TaskInfoTab
     * and displays it.
     *
     * @param currentTask The currently selected task.
     */
    public TaskScene(Task currentTask) {
        super(TrackedTabPane.ContentScene.TASK, currentTask);

        // Define and add the tabs
        Tab informationTab = new TaskInfoTab(currentTask);

        this.getTabs().addAll(informationTab);  // Add the tabs to the pane
    }
}
