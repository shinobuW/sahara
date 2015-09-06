package seng302.group2.scenes.information.project.sprint;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.search.*;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.team.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * Task Visualisation Tab
 * Created by cvs20 on 6/09/15.
 */

public class SprintTaskStatusTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();

    /**
     * Constructor for Task Visualisation tab
     *
     *@param currentSprint the currently selected sprint
     */
    public SprintTaskStatusTab(Sprint currentSprint) {

        // Tab settings
        this.setText("Task Visualisation");
        Pane basicInfoPane = new VBox(10);
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        SearchableText title = new SearchableTitle("Task Visualisation", searchControls);
        SearchableText groupBy = new SearchableText("Group By: ", searchControls);
        groupBy.setStyle("-fx-font-weight: bold");

        ToggleGroup group = new ToggleGroup();
        SearchableRadioButton status = new SearchableRadioButton("Status", searchControls);
        SearchableRadioButton story = new SearchableRadioButton("Story", searchControls);
        status.getRadioButton().setToggleGroup(group);
        status.getRadioButton().setSelected(true);
        story.getRadioButton().setToggleGroup(group);

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(groupBy, status, story);

        CustomComboBox filterBox = new CustomComboBox("Filter by: ", searchControls);

        String unassigned = new String("Unassigned");
        String uncompleted = new String("Uncompleted");
        String all = new String("All Tasks");

        filterBox.addToComboBox(unassigned);
        filterBox.addToComboBox(uncompleted);
        filterBox.addToComboBox(all);

        filterBox.setValue(unassigned);



        basicInfoPane.getChildren().addAll(
                title,
                buttonBox,
                filterBox
        );

        Collections.addAll(searchControls,
                title,
                status,
                story,
                filterBox

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
