package seng302.group2.scenes.information.project.story.task;

import javafx.scene.control.Tab;
import seng302.group2.scenes.control.TrackedTabPane;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.project.story.tasks.Task;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * A class for displaying the Task Scene
 *
 * Created by cvs20 on 28/07/15.
 */
public class TaskScene extends TrackedTabPane {

    Collection<SearchableTab> searchableTabs = new HashSet<>();

    /**
     * Constructor for the Task Scene. Creates instances of the TaskInfoTab
     * and displays it.
     *
     * @param currentTask The currently selected task.
     */
    public TaskScene(Task currentTask) {
        super(TrackedTabPane.ContentScene.TASK, currentTask);

        // Define and add the tabs
        SearchableTab informationTab = new TaskInfoTab(currentTask);

        Collections.addAll(searchableTabs, informationTab);
        this.getTabs().addAll(informationTab);  // Add the tabs to the pane
    }

    /**
     * Constructor for the TaskScene. This creates an instance of the TaskEditScene tab and displays it.
     * @param currentTask The task to be edited
     * @param editScene a boolean - if the scene is an edit scene
     */
    public TaskScene(Task currentTask, boolean editScene) {
        super(ContentScene.TASK_EDIT, currentTask);

        // Define and add the tabs
        Tab editTab = new TaskEditTab(currentTask);

        this.getTabs().addAll(editTab);  // Add the tabs to the pane
    }

    @Override
    public Collection<SearchableTab> getSearchableTabs() {
        return searchableTabs;
    }
}
