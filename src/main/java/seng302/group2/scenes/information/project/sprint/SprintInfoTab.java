package seng302.group2.scenes.information.project.sprint;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;
import seng302.group2.scenes.control.PopOverTip;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.control.search.SearchableTitle;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.tasks.Task;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * The sprint information tab.
 * Created by drm127 on 29/07/15.
 */
public class SprintInfoTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();

    /**
     * Constructor for the sprint information tab.
     */
    public SprintInfoTab(Sprint currentSprint) {
        this.setText("Basic Information");
        Pane basicInfoPane = new VBox(10);
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        SearchableText title = new SearchableTitle(currentSprint.getLongName());

        Button btnEdit = new Button("Edit");

        /*//SUBJECT TO CHANGE BASED ON FUTURE STORIES
        ObservableList<Story> data = observableArrayList();
        data.addAll(currentSprint.getStories());
        ListView sprintStoryBox = new ListView(data);
        sprintStoryBox.setPrefHeight(192);
        sprintStoryBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);*/

        //Attempt at expandable list, basic edition
        Pane expandableStoryPanes = createStoryTitlePanes(currentSprint);


//        TableView<Story> storyTable = new TableView<>();
//        storyTable.setEditable(true);
//        storyTable.setPrefWidth(500);
//        storyTable.setPrefHeight(200);
//        storyTable.setPlaceholder(new SearchableText("There are currently no stories in this sprint.",
//                searchControls));
//        storyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//
//        ObservableList<Story> rows = observableArrayList();
//        rows.addAll(currentSprint.getStories());
//
//        TableColumn storyCol = new TableColumn("Story");
//        storyCol.setCellValueFactory(new PropertyValueFactory<Story, String>("shortName"));
//        storyCol.prefWidthProperty().bind(storyTable.widthProperty()
//                .subtract(2).divide(100).multiply(80));
//
//        /*TableColumn priorityCol = new TableColumn("Priority");
//        priorityCol.setCellValueFactory(new PropertyValueFactory<Story, Integer>("priority"));
//        priorityCol.prefWidthProperty().bind(storyTable.widthProperty()
//                .subtract(2).divide(100).multiply(20));*/
//
//        TableColumn readyCol = new TableColumn("Status");
//        readyCol.setCellValueFactory(new PropertyValueFactory<Story, String>("readyString"));
//        readyCol.prefWidthProperty().bind(storyTable.widthProperty()
//                .subtract(2).divide(100).multiply(20));
//        storyTable.setItems(rows);
//        storyTable.getColumns().addAll(storyCol, readyCol);


        final Separator separator = new Separator();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new SearchableText("Sprint Name: " + currentSprint.getLongName(),
                searchControls));
        basicInfoPane.getChildren().add(new SearchableText("Sprint Goal: " + currentSprint.getGoal(),
                searchControls));
        String startDateString = currentSprint.getStartDate().format(formatter);
        basicInfoPane.getChildren().add(new SearchableText("Start Date: " + startDateString,
                searchControls));
        String endDateString = currentSprint.getEndDate().format(formatter);
        basicInfoPane.getChildren().add(new SearchableText("End Date: " + endDateString,
                searchControls));
        basicInfoPane.getChildren().add(new SearchableText("Description: " + currentSprint.getDescription(),
                searchControls));

        basicInfoPane.getChildren().add(separator);

        basicInfoPane.getChildren().add(new SearchableText("Team: " + currentSprint.getTeam(), searchControls));
        basicInfoPane.getChildren().add(new SearchableText("Project: " + currentSprint.getProject(), searchControls));
        basicInfoPane.getChildren().add(new SearchableText("Release: " + currentSprint.getRelease(), searchControls));

        basicInfoPane.getChildren().add(new SearchableText("Stories: ", searchControls));
        //basicInfoPane.getChildren().add(storyTable);

        basicInfoPane.getChildren().add(expandableStoryPanes);

        basicInfoPane.getChildren().add(btnEdit);

        btnEdit.setOnAction((event) -> currentSprint.switchToInfoScene(true));



        //Creates visualisation bar
        GridPane visualGrid = new GridPane();

        Rectangle red = new Rectangle();
        Rectangle green = new Rectangle();
        Rectangle blue = new Rectangle();

        green.setWidth(120);
        green.setHeight(25);
        green.setArcWidth(15);
        green.setArcHeight(15);
        PopOverTip greenPO = new PopOverTip(green, new Text("Green"));
        greenPO.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);

        blue.setWidth(170);
        blue.setHeight(25);
        blue.setArcWidth(15);
        blue.setArcHeight(15);
        PopOverTip bluePO = new PopOverTip(blue, new Text("Blue"));
        bluePO.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);

        red.setWidth(230);
        red.setHeight(25);
        red.setArcWidth(15);
        red.setArcHeight(15);
        PopOverTip redPO = new PopOverTip(red, new Text("Red"));
        redPO.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);

        red.setFill(Color.RED);
        green.setFill(Color.GREEN);
        blue.setFill(Color.BLUE);

        visualGrid.add(red, 0, 0, 1, 3);
        visualGrid.add(blue, 0, 0, 1, 2);
        visualGrid.add(green, 0, 0, 1, 1);

        basicInfoPane.getChildren().add(visualGrid);

        Collections.addAll(searchControls, title);
    }

    /**
     * Creates a stacked series of TitledPanes to display each story in the sprint and its tasks
     * @param currentSprint The current sprint
     * @return A VBox containing the stacked TitledPanes
     */
    private VBox createStoryTitlePanes(Sprint currentSprint) {
        final VBox stackedStoryTitlePanes = new VBox();


        if (currentSprint.getStories().size() != 0) {

            List<Story> stories = new ArrayList<>();
            stories.addAll(currentSprint.getStories().sorted(Story.StoryPriorityComparator));
            stories.add(currentSprint.getUnallocatedTasksStory());

            for (Story story : stories) {

                VBox taskBox = new VBox(4);
                if (story.getTasks().size() != 0) {

                    for (Task task : story.getTasks().sorted(Task.TaskNameComparator)) {
                        taskBox.getChildren().add(new ScrumBoardTaskCellNode(task));
                    }
                }
                else {
                    taskBox.getChildren().add(new SearchableText("This story currently has no tasks.", searchControls));
                }
                TitledPane storyPane = new TitledPane("[" + story.getEstimate() + "] "
                        + story.getShortName() + " - " + story.getReadyString(), taskBox);

                storyPane.setPrefHeight(30);
                storyPane.setExpanded(false);
                storyPane.setAnimated(true);
                stackedStoryTitlePanes.getChildren().add(storyPane);
            }
        }
        else {
            stackedStoryTitlePanes.getChildren().add(new SearchableText("This sprint currently has no stories."));
        }
        return stackedStoryTitlePanes;
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
