package seng302.group2.scenes.information.project.sprint;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.Global;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.tasks.Task;

import java.util.*;

/**
 * The information tab for a backlog
 * Created by cvs20 on 19/05/15.
 */
public class TestingCellTestTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();

    ObservableList<Task> laneOneStories = FXCollections.observableArrayList();
    ObservableList<Task> laneTwoStories = FXCollections.observableArrayList();

    ListView<Task> laneOne = new ListView<>(laneOneStories);
    ListView<Task> laneTwo = new ListView<>(laneTwoStories);

    Set<ListView<Task>> lanes = new HashSet<>();


    /**
     * Constructor for the Backlog Info tab
     *
     * @param currentSprint The currently selected backlog
     */
    public TestingCellTestTab(Sprint currentSprint) {
        lanes.add(laneOne);
        lanes.add(laneTwo);
        initializeListeners();
        laneOneStories.addAll(currentSprint.getAllTasks());

        this.setText("TEST | Tasks");
        Pane basicInfoPane = new VBox(10);

        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        Label title = new TitleLabel(currentSprint.getLongName());
        basicInfoPane.getChildren().add(title);

        basicInfoPane.getChildren().add(new Label("Tasks: "));

        laneOne.setCellFactory(list -> new TestingCellFactory());
        laneTwo.setCellFactory(List -> new TestingCellFactory());

        HBox lists = new HBox();
        lists.getChildren().addAll(laneOne, laneTwo);

        basicInfoPane.getChildren().addAll(lists);
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


    private void initializeListeners() {
        // FROM: http://www.java2s.com/Tutorials/Java/JavaFX/0640__JavaFX_ListView.htm


        // drag from left to right
        laneOne.setOnDragDetected(event -> {
                if (laneOne.getSelectionModel().getSelectedItem() == null) {
                    return;
                }

                Dragboard dragBoard = laneOne.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(laneOne.getSelectionModel().getSelectedItem()
                        .getShortName());
                dragBoard.setContent(content);
            });

        laneTwo.setOnDragOver(dragEvent -> dragEvent.acceptTransferModes(TransferMode.MOVE));

        laneTwo.setOnDragDropped(dragEvent -> {
                String player = dragEvent.getDragboard().getString();

                Task task = getTaskByName(player);
                if (!laneTwoStories.contains(task)) {
                    laneTwoStories.add(task);
                }
                laneOneStories.remove(task);
                dragEvent.setDropCompleted(true);
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
            });
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
