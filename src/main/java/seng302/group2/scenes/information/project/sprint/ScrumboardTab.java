package seng302.group2.scenes.information.project.sprint;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.Global;
import seng302.group2.scenes.control.search.*;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.tasks.Task;

import java.util.*;

/**
 * The information tab for a backlog
 * Created by cvs20 on 19/05/15.
 */
public class ScrumboardTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();
    Map<SearchableListView<Task>, Task.TASKSTATE> laneStateDict = new HashMap<>();

    //ObservableList<Task> laneOneStories = FXCollections.observableArrayList();
    //ObservableList<Task> laneTwoStories = FXCollections.observableArrayList();

    //ListView<Task> laneOne = new ListView<>(laneOneStories);
    //ListView<Task> laneTwo = new ListView<>(laneTwoStories);

    //List<ListView<Task>> lanes = new ArrayList<>();


    Task interactiveTask = null;


    /**
     * Constructor for the Backlog Info tab
     *
     * @param currentSprint The currently selected backlog
     */
    public ScrumboardTab(Sprint currentSprint) {
        //Collections.addAll(lanes, laneOne,laneTwo);

        //initializeListeners();
        //laneOneStories.addAll(currentSprint.getAllTasks());

        this.setText("Scrumboard");
        Pane basicInfoPane = new VBox(10);

        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        SearchableText title = new SearchableTitle(currentSprint.getLongName());
        basicInfoPane.getChildren().add(title);

        /*laneOne.setCellFactory(list -> new ScrumBoardTaskCell());
        laneTwo.setCellFactory(List -> new ScrumBoardTaskCell());

        HBox lists = new HBox();
        lists.getChildren().addAll(laneOne, laneTwo);

        basicInfoPane.getChildren().addAll(lists);*/



        // Create story title pane regions
        for (Story story : currentSprint.getStories()) {
            basicInfoPane.getChildren().add(getCollapsableStoryPane(story));
        }
        basicInfoPane.getChildren().add(getCollapsableStoryPane(currentSprint.getUnallocatedTasksStory()));



        // Add the searchable controls to the collection for searching
        Collections.addAll(searchControls, title);
    }


    /**
     * Creates a collapsable title pane to display the scrumboard information of stories
     * @param story The story to generate the pane for
     * @return A pane showing the information of tasks in the given story
     */
    private Node getCollapsableStoryPane(Story story) {
        GridPane taskLanesGrid = new GridPane();
        taskLanesGrid.setPadding(new Insets(8));

        SearchableListView<Task> todoLane = new SearchableListView<>(story.todoTasks);
        SearchableListView<Task> inProgressLane = new SearchableListView<>(story.inProgTasks);
        SearchableListView<Task> verifyLane = new SearchableListView<>(story.verifyTasks);
        SearchableListView<Task> completedLane = new SearchableListView<>(story.completedTasks);

        laneStateDict.put(todoLane, Task.TASKSTATE.NOT_STARTED);
        laneStateDict.put(inProgressLane, Task.TASKSTATE.IN_PROGRESS);
        laneStateDict.put(verifyLane, Task.TASKSTATE.VERIFY);
        laneStateDict.put(completedLane, Task.TASKSTATE.DONE);

        Set<ListView<Task>> lanes = new HashSet<>();
        Collections.addAll(lanes, todoLane, inProgressLane, verifyLane, completedLane);
        initLaneListeners(lanes, story);

        // Set cell factory
        todoLane.setCellFactory(list -> new ScrumBoardTaskCell());
        inProgressLane.setCellFactory(list -> new ScrumBoardTaskCell());
        verifyLane.setCellFactory(list -> new ScrumBoardTaskCell());
        completedLane.setCellFactory(list -> new ScrumBoardTaskCell());

        // Add labels to grid
        taskLanesGrid.add(new SearchableText("Todo", searchControls), 0, 0);
        taskLanesGrid.add(new SearchableText("In Progress", searchControls), 1, 0);
        taskLanesGrid.add(new SearchableText("Verify", searchControls), 2, 0);
        taskLanesGrid.add(new SearchableText("Done", searchControls), 3, 0);

        // Add task list views
        taskLanesGrid.add(todoLane, 0, 1);
        taskLanesGrid.add(inProgressLane, 1, 1);
        taskLanesGrid.add(verifyLane, 2, 1);
        taskLanesGrid.add(completedLane, 3, 1);


        return new TitledPane("[" + story.getEstimate() + "] "
                + story.getShortName(), taskLanesGrid);
    }


    public Task getTaskByName(String name) {
        System.out.println("Name: " + name);
        for (SaharaItem item : Global.currentWorkspace.getItemsSet()) {
            if (item instanceof Task && ((Task) item).getShortName().equals(name)) {
                return (Task)item;
            }
        }
        return null;
    }



    private void initLaneListeners(Collection<ListView<Task>> lanes, Story story) {
        for (ListView<Task> lane : lanes) {
            lane.setOnDragDetected(event -> {
                interactiveTask = lane.getSelectionModel().getSelectedItem();

                Dragboard dragBoard = lane.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(lane.getSelectionModel().getSelectedItem()
                        .getShortName());
                dragBoard.setContent(content);
            });

            lane.setOnDragOver(dragEvent -> {
                dragEvent.acceptTransferModes(TransferMode.MOVE);
            });

            lane.setOnDragDropped(dragEvent -> {
                if (interactiveTask == null) {
                    return;
                }
                if (interactiveTask.getStory() != story) {
                    Alert differentStoryAlert = new Alert(Alert.AlertType.WARNING);
                    differentStoryAlert.setTitle("Unable to Move Task");
                    differentStoryAlert.setHeaderText(null);
                    differentStoryAlert.setContentText("Can't move a task between different stories");
                    differentStoryAlert.showAndWait();
                    return;
                }

                interactiveTask.editState(laneStateDict.get(lane));
            });
        }
    }


    @Deprecated
    private void initializeListeners() {
        // FROM: http://www.java2s.com/Tutorials/Java/JavaFX/0640__JavaFX_ListView.htm

        // drag from left to right
        /*laneOne.setOnDragDetected(event -> {
                if (laneOne.getSelectionModel().getSelectedItem() == null) {
                    return;
                }

                /*Dragboard dragBoard = laneOne.startDragAndDrop(TransferMode.MOVE);

                ClipboardContent content = new ClipboardContent();
                content.putString(laneOne.getSelectionModel().getSelectedItem()
                        .getShortName());
                dragBoard.setContent(content);*/

                /*interactiveTask = laneOne.getSelectionModel().getSelectedItem();
            });

        laneTwo.setOnDragOver(dragEvent -> dragEvent.acceptTransferModes(TransferMode.MOVE));

        laneTwo.setOnDragDropped(dragEvent -> {
                /*String player = dragEvent.getDragboard().getString();

                Task task = getTaskByName(player);
                if (!laneTwoStories.contains(task)) {
                    laneTwoStories.add(task);
                }
                laneOneStories.remove(task);
                dragEvent.setDropCompleted(true);*/
                /*if (interactiveTask == null) {
                    return;
                }
                for (ListView<Task> lane : lanes) {
                    lane.getItems().remove(interactiveTask);
                }
            });


        // drag from right to left
        laneTwo.setOnDragDetected(event -> {
                Dragboard dragBoard = laneTwo.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(laneTwo.getSelectionModel().getSelectedItem()
                        .getShortName());
                dragBoard.setContent(content);
            });

        laneOne.setOnDragOver(dragEvent -> dragEvent.acceptTransferModes(TransferMode.MOVE));

        laneOne.setOnDragDropped(dragEvent -> {
                String player = dragEvent.getDragboard().getString();

                Task task = getTaskByName(player);
                if (!laneOneStories.contains(task)) {
                    laneOneStories.add(task);
                }
                laneTwoStories.remove(task);
                dragEvent.setDropCompleted(true);
            });*/
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
