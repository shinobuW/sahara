package seng302.group2.scenes.information.project.story.task;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.control.search.SearchableTitle;
import seng302.group2.workspace.project.story.tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * The task information tab.
 * Created by cvs20 on 28/07/15.
 */
public class TaskInfoTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();
    //TODO Refactor into searchable controls and populate

    /**
     * Constructor for the Task Info Tab
     *
     * @param currentTask The currently selected Task
     */
    public TaskInfoTab(Task currentTask) {

        this.setText("Basic Information");
        Pane basicInfoPane = new VBox(10);

        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        SearchableText title = new SearchableTitle(currentTask.getShortName());

        Button btnEdit = new Button("Edit");

        SearchableText description = new SearchableText("Task Description: " + currentTask.getDescription());
        SearchableText impediments = new SearchableText("Impediments: " + currentTask.getImpediments());
        SearchableText effortLeft = new SearchableText("Effort Left: " + currentTask.getEffortLeftString());
        SearchableText effortSpent = new SearchableText("Effort Spent: " + currentTask.getEffortSpentString());
        SearchableText taskState = new SearchableText("Task State: " + currentTask.getState());
        SearchableText assignedPerson;
        if (currentTask.getAssignee() == null) {
            assignedPerson = new SearchableText("Assigned Person: ");
        }
        else {
            assignedPerson = new SearchableText("Assigned Person: " + currentTask.getAssignee());
        }




        btnEdit.setOnAction((event) -> {
                currentTask.switchToInfoScene(true);
            });

        basicInfoPane.getChildren().addAll(
                title,
                description,
                impediments,
                effortLeft,
                effortSpent,
                taskState,
                assignedPerson,
                btnEdit
        );

        Collections.addAll(searchControls,
                title,
                description,
                impediments,
                effortLeft,
                effortSpent,
                taskState,
                assignedPerson
        );


    }

    /**
     * Gets all the searchable controls on this tab.
     * @return a collection of all the searchable controls on this tab.
     */
    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }
}
