package seng302.group2.scenes.information.project.backlog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.Global;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.story.Story;

import java.util.HashSet;
import java.util.Set;

/**
 * The information tab for a backlog
 * Created by cvs20 on 19/05/15.
 */
public class TestingCellTestTab extends Tab {

    ObservableList<Story> laneOneStories = FXCollections.observableArrayList();
    ObservableList<Story> laneTwoStories = FXCollections.observableArrayList();

    ListView<Story> laneOne = new ListView<>(laneOneStories);
    ListView<Story> laneTwo = new ListView<>(laneTwoStories);

    Set<ListView<Story>> lanes = new HashSet<>();


    /**
     * Constructor for the Backlog Info tab
     *
     * @param currentBacklog The currently selected backlog
     */
    public TestingCellTestTab(Backlog currentBacklog) {
        lanes.add(laneOne);
        lanes.add(laneTwo);
        initializeListeners();
        laneOneStories.addAll(currentBacklog.getStories());

        this.setText("TEST");
        Pane basicInfoPane = new VBox(10);

        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        Label title = new TitleLabel(currentBacklog.getLongName());
        basicInfoPane.getChildren().add(title);

        basicInfoPane.getChildren().add(new Label("Stories: "));

        laneOne.setCellFactory(list -> new TestingCellFactory());
        laneTwo.setCellFactory(List -> new TestingCellFactory());

        HBox lists = new HBox();
        lists.getChildren().addAll(laneOne, laneTwo);

        basicInfoPane.getChildren().addAll(lists);
    }



    public Story getStoryByName(String name) {
        System.out.println("Name: " + name);
        for (SaharaItem item : Global.currentWorkspace.getItemsSet()) {
            if (item instanceof Story && ((Story) item).getShortName().equals(name)) {
                return (Story)item;
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

                Story story = getStoryByName(player);
                if (!laneTwoStories.contains(story)) {
                    laneTwoStories.add(story);
                }
                laneOneStories.remove(story);
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

                Story story = getStoryByName(player);
                if (!laneOneStories.contains(story)) {
                    laneOneStories.add(story);
                }
                laneTwoStories.remove(story);
                dragEvent.setDropCompleted(true);
            });
    }
}
