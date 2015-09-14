package seng302.group2.scenes.information.project.sprint;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.control.search.SearchableTitle;
import seng302.group2.workspace.project.sprint.Sprint;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A Tab which shows the logs on a task which belongs to the given Sprint.
 * Created by swi67 on 14/09/15.
 */
public class SprintLogTab extends SearchableTab {
    List<SearchableControl> searchControls = new ArrayList<>();

    /**
     * Constructor for the sprint logging tab
     * @param sprint
     */
    public SprintLogTab(Sprint sprint) {
        this.setText("Logging Effort");
        Pane loggingPane = new VBox(10);
        loggingPane.setBorder(null);
        loggingPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(loggingPane);
        this.setContent(wrapper);

        Platform.runLater(() -> {
            SearchableText title = new SearchableTitle(sprint.getLongName() + " Logging Effort");

            final Separator separator = new Separator();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


//            loggingPane.getChildren().addAll(
//                    title,
//                    sprintName,
//                    sprintGoal,
//                    sprintStart,
//                    sprintEnd,
//                    desc,
//                    separator,
//                    team,
//                    project,
//                    release,
//                    stories
//
//            );
//
//            Collections.addAll(searchControls,
//                    title,
//                    sprintName,
//                    sprintGoal,
//                    sprintStart,
//                    sprintEnd,
//                    desc,
//                    team,
//                    project,
//                    release,
//                    stories
//            );

//            loggingPane.getChildren().addAll((currentSprint), btnEdit);
        });

    }

    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return null;
    }
}
