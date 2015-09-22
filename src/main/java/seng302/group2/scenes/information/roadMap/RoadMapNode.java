/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.scenes.information.roadMap;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.*;
import seng302.group2.scenes.control.Tooltip;
import seng302.group2.scenes.control.search.SearchType;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableListView;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.dialog.CreateStoryDialog;
import seng302.group2.scenes.dialog.CustomDialog;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.release.Release;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.roadMap.RoadMap;

import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;
import static seng302.group2.util.validation.NameValidator.validateName;
import static seng302.group2.util.validation.PriorityFieldValidator.validatePriorityField;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 *
 * @author crw73
 */
public class RoadMapNode extends VBox implements SearchableControl {
    
    List<SearchableControl> searchControls = new ArrayList<>();
    RoadMap roadMap;
    Release selectedRelease;
    Release interactiveRelease;
    Sprint selectedSprint;
    Sprint interactiveSprint;
    Story selectedStory;
    Story interactiveStory;

    private static Boolean correctShortName = Boolean.FALSE;
    private static Boolean correctCreator = Boolean.FALSE;
    private static Boolean correctLongName = Boolean.FALSE;
    private static Boolean correctPriority = Boolean.FALSE;


    public RoadMapNode(RoadMap currentRoadMap) {
        roadMap = currentRoadMap;
        HBox roadMapContent = new HBox();


        roadMapContent.setStyle("-fx-background-color: rgba(255, 116, 10, 0.62); -fx-border-radius: 5 5 5 5; "
                + "-fx-background-radius: 0 5 5 5");
        HBox roadMapChildren = new HBox();

        SearchableText shortNameField = new SearchableText(currentRoadMap.getShortName());
        roadMapContent.setAlignment(Pos.CENTER);
        roadMapContent.setMinHeight(35);
        Insets insetsContent = new Insets(5, 15, 5, 5);
        shortNameField.setPadding(insetsContent);

        shortNameField.setStyle("-fx-font: 35px Tahoma;");

        HBox.setHgrow(roadMapContent, Priority.ALWAYS);


        VBox roadMapVbox = new VBox();
        Text roadMapPriority = new Text(currentRoadMap.getShortName() + "'s priority is "
                + currentRoadMap.getPriority().toString());
        roadMapVbox.getChildren().addAll(roadMapPriority);
        PopOverTip storyTip = new PopOverTip(roadMapContent, roadMapVbox);
        storyTip.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        
        roadMapContent.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !(((SaharaItem) Global.selectedTreeItem.getValue())
                        .equals(currentRoadMap))) {
                    App.mainPane.selectItem(currentRoadMap);
//                    currentRoadMap.switchToInfoScene();
                }
                event.consume();
            });

        roadMapContent.getChildren().addAll(
                shortNameField
        );

        for (Release release : currentRoadMap.getReleases()) {
            Node releaseNode = createReleaseNode(release, currentRoadMap);
            roadMapChildren.getChildren().add(releaseNode);
            initReleaseListeners(releaseNode, release);
        }

        this.getChildren().addAll(
                roadMapContent,
                roadMapChildren
        );
        // Add items to pane & search collection
        Collections.addAll(searchControls,
                shortNameField  
        );
    }
    

    private VBox createReleaseNode(Release release, RoadMap roadmap) {
        VBox releaseNode = new VBox();
        Insets insetsNode = new Insets(5, 5, 5, 0);
        releaseNode.setPadding(insetsNode);
        HBox releaseContent = new HBox();

        releaseContent.setStyle("-fx-background-color: rgba(11, 0, 255, 0.62); -fx-border-radius: 5 5 5 5; "
                + "-fx-background-radius: 0 5 5 5");

        HBox releaseChildren = new HBox();
        releaseContent.setMinHeight(35);
        SearchableText shortNameField = new SearchableText(release.getShortName());
        SearchableText projectName = new SearchableText("Project: " + release.getProject().toString());
        Insets insetsContent = new Insets(5, 15, 5, 5);

        VBox deletionBox = new VBox();
        ImageView deletionImage = new ImageView("icons/tag_remove.png");
        Tooltip.create("Remove Release from Road Map", deletionImage, 50);

        deletionImage.setOnMouseEntered(me -> {
            this.getScene().setCursor(Cursor.HAND); //Change cursor to hand
        });

        deletionImage.setOnMouseExited(me -> {
            this.getScene().setCursor(Cursor.DEFAULT); //Change cursor to hand
        });

        deletionImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ObservableList<Release> tempReleases = observableArrayList();
            for (Release tempRelease : roadmap.getReleases()) {
                tempReleases.add(tempRelease);
            }
            tempReleases.remove(release);
            roadmap.edit(roadmap.getShortName(), roadmap.getPriority(), tempReleases, roadmap.getTags());
            event.consume();
        });

        deletionBox.getChildren().addAll(deletionImage);

        HBox.setHgrow(releaseContent, Priority.ALWAYS);

        releaseContent.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                App.mainPane.selectItem(release);
                release.switchToInfoScene();
            }
            event.consume();
        });

        VBox releaseVBox = new VBox();
        Text releaseEndText = new Text("End date of " + release.getShortName() + " is " + release.getDateString());
        releaseVBox.getChildren().addAll(releaseEndText);
        PopOverTip releaseTip = new PopOverTip(releaseContent, releaseVBox);
        releaseTip.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);

        for (Project proj : Global.currentWorkspace.getProjects()) {
            for (Sprint sprint : proj.getSprints()) {
                if (sprint.getRelease().equals(release)) {
                    Node sprintNode = createSprintNode(sprint);
                    releaseChildren.getChildren().add(sprintNode);
                    initSprintListeners(sprintNode, sprint);

                }
            }
        }

        releaseContent.setOnDragDetected(event -> {
            selectedRelease = release;
            Dragboard dragBoard = this.startDragAndDrop(TransferMode.MOVE);
            dragBoard.setDragView(this.snapshot(null, null));
            ClipboardContent content = new ClipboardContent();
            content.putString("release");
            dragBoard.setContent(content);
        });


        VBox content = new VBox(1);
        content.getChildren().addAll(shortNameField, projectName);
        content.setPadding(insetsContent);

        HBox deletionHBox = new HBox();
        deletionHBox.getChildren().add(deletionBox);
        deletionHBox.setAlignment(Pos.CENTER_RIGHT);

        HBox nameHBox = new HBox();
        nameHBox.getChildren().add(content);
        nameHBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(nameHBox, Priority.ALWAYS);

        releaseContent.getChildren().addAll(nameHBox, deletionHBox);
        releaseContent.setAlignment(Pos.CENTER);
        releaseNode.getChildren().addAll(
                releaseContent,
                releaseChildren
        );

        // Add items to pane & search collection
        Collections.addAll(searchControls,
                shortNameField
        );
        return releaseNode;
    }

    private Node createSprintNode(Sprint sprint) {
        VBox sprintNode = new VBox();
        sprintNode.setAlignment(Pos.CENTER);
        Insets insets = new Insets(5, 5, 5, 0);

        sprintNode.setPadding(insets);

        HBox sprintContent = new HBox();
        HBox.setHgrow(sprintContent, Priority.ALWAYS);
        sprintContent.setStyle("-fx-background-color: rgba(100, 255, 124, 0.83); -fx-border-radius: 5 5 5 5; "
                + "-fx-background-radius: 0 5 5 5");

        VBox deletionBox = deletionBox(sprint);
        deletionBox.setAlignment(Pos.TOP_RIGHT);

        GridPane sprintChildren = new GridPane();
        int xCounter = 0;
        int yCounter = 0;

        sprintContent.setMinHeight(35);

        SearchableText shortNameField = new SearchableText(sprint.getGoal());
        Insets insetsContent = new Insets(5, 15, 5, 5);
        shortNameField.setPadding(insetsContent);

        VBox sprintVBox = new VBox();
        Text sprintStartText = new Text("Start date of " + sprint.getGoal() + " is " + sprint.getStartDateString());
        Text sprintEndText = new Text("End date of " + sprint.getGoal() + " is " + sprint.getEndDateString());
        sprintVBox.getChildren().addAll(sprintStartText, sprintEndText);
        PopOverTip sprintTip = new PopOverTip(sprintContent, sprintVBox);
        sprintTip.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);


        sprintContent.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                App.mainPane.selectItem(sprint);
                sprint.switchToInfoScene();
            }
            event.consume();
        });

        for (Story story : sprint.getStories()) {
            Node storyNode = createStoryNode(story, sprint);
            sprintChildren.add(storyNode, xCounter, yCounter);
            xCounter++;
            if (xCounter == 3) {
                yCounter++;
                xCounter = 0;
            }

        }

        sprintContent.setOnDragDetected(event -> {
            selectedSprint = sprint;
            Dragboard dragBoard = this.startDragAndDrop(TransferMode.MOVE);
            dragBoard.setDragView(this.snapshot(null, null));
            ClipboardContent content = new ClipboardContent();
            content.putString("sprint");
            dragBoard.setContent(content);
        });

        HBox deletionHBox = new HBox();
        deletionHBox.getChildren().add(deletionBox);
        deletionHBox.setAlignment(Pos.CENTER_RIGHT);

        HBox nameHBox = new HBox();
        nameHBox.getChildren().add(shortNameField);
        nameHBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(nameHBox, Priority.ALWAYS);

        sprintContent.getChildren().addAll(nameHBox, deletionHBox);
        sprintContent.setAlignment(Pos.CENTER);
        sprintNode.getChildren().addAll(
                sprintContent,
                sprintChildren
        );
        // Add items to pane & search collection
        Collections.addAll(searchControls,
                shortNameField
        );

        return sprintNode;
    }

    private Node createStoryNode(Story story, Sprint sprint) {
        VBox storyNode = new VBox();
        storyNode.setAlignment(Pos.CENTER);
        Insets insetsNode = new Insets(5, 5, 5, 0);
        storyNode.setPadding(insetsNode);

        HBox storyContent = new HBox();
        storyContent.setStyle("-fx-background-color: rgba(255, 7, 0, 0.56); -fx-border-radius: 5 5 5 5; "
                + "-fx-background-radius: 0 5 5 5");

        SearchableText shortNameField = new SearchableText(story.getShortName());
        Insets insetsContent = new Insets(5, 15, 5, 5);
        shortNameField.setPadding(insetsContent);
        storyContent.setMinHeight(35);

        VBox deletionBox = new VBox();
        ImageView deletionImage = new ImageView("icons/tag_remove.png");
        Tooltip.create("Remove Story from Sprint", deletionImage, 50);


        deletionImage.setOnMouseEntered(me -> {
            this.getScene().setCursor(Cursor.HAND); //Change cursor to hand
        });

        deletionImage.setOnMouseExited(me -> {
            this.getScene().setCursor(Cursor.DEFAULT); //Change cursor to hand
        });

        deletionImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ObservableList<Story> tempStories = observableArrayList();
            for (Story tempStory : sprint.getStories()) {
                tempStories.add(tempStory);
            }
            tempStories.remove(story);
            sprint.edit(sprint.getGoal(), sprint.getLongName(), sprint.getDescription(), sprint.getStartDate(),
                    sprint.getEndDate(), sprint.getTeam(), sprint.getRelease(), tempStories, sprint.getTags());
            event.consume();
        });

        deletionBox.getChildren().addAll(deletionImage);

        HBox.setHgrow(storyContent, Priority.ALWAYS);
        deletionBox.setAlignment(Pos.TOP_RIGHT);
        VBox storyVBox = new VBox();
        Text storyPoint = new Text(story.getShortName() + "'s estimate is " + story.getEstimate());
        storyVBox.getChildren().addAll(storyPoint);
        PopOverTip storyTip = new PopOverTip(storyNode, storyVBox);
        storyTip.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);

        storyNode.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                App.mainPane.selectItem(story);
                story.switchToInfoScene();
            }
            event.consume();
        });

        storyContent.setOnDragDetected(event -> {
            selectedStory = story;
            Dragboard dragBoard = this.startDragAndDrop(TransferMode.MOVE);
            dragBoard.setDragView(this.snapshot(null, null));
            ClipboardContent content = new ClipboardContent();
            content.putString("story");
            dragBoard.setContent(content);
        });

        HBox deletionHBox = new HBox();
        deletionHBox.getChildren().add(deletionBox);
        deletionHBox.setAlignment(Pos.CENTER_RIGHT);

        HBox nameHBox = new HBox();
        nameHBox.getChildren().add(shortNameField);
        nameHBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(nameHBox, Priority.ALWAYS);

        storyContent.getChildren().addAll(
                nameHBox,
                deletionHBox
        );

        storyNode.getChildren().addAll(
                storyContent
        );

        // Add items to pane & search collection
        Collections.addAll(searchControls,
                shortNameField
        );

        return storyNode;
    }


    private VBox deletionBox(SaharaItem item) {
        HBox iconBox = new HBox();
        VBox deletionBox = new VBox();
        ImageView deletionImage = new ImageView("icons/tag_remove.png");
        Tooltip.create("Delete Sprint", deletionImage, 50);

        ImageView addImage = new ImageView("icons/add.png");
        Tooltip.create("Add Story to Sprint", addImage, 50);

        deletionImage.setOnMouseEntered(me -> {
            this.getScene().setCursor(Cursor.HAND); //Change cursor to hand
        });

        deletionImage.setOnMouseExited(me -> {
            this.getScene().setCursor(Cursor.DEFAULT); //Change cursor to hand
        });

        deletionImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            showDeleteDialog(item);
            event.consume();
            App.mainPane.refreshAll();

        });

        addImage.setOnMouseEntered(me -> {
            this.getScene().setCursor(Cursor.HAND); //Change cursor to hand
        });

        addImage.setOnMouseExited(me -> {
            this.getScene().setCursor(Cursor.DEFAULT); //Change cursor to hand
        });

        addImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            event.consume();

            correctCreator = false;
            correctLongName = false;
            correctShortName = false;
            correctPriority = false;


            PopOver addStoryPopover = new PopOver();
            VBox addContent = new VBox();
            addContent.setPadding(new Insets(8, 8, 8, 8));

            VBox existingStories = new VBox(5);
            existingStories.setPadding(new Insets(8, 8, 8, 8));

            ObservableList<Story> unassignedStories = observableArrayList();
            for (Story story : Global.currentWorkspace.getAllStories()) {
                if (story.getSprint() == null) {
                    unassignedStories.add(story);
                }
            }

            FilteredListView<Story> unassignedStoryBox = new FilteredListView<>(unassignedStories,
                    "Unassigned Stories");
            SearchableListView<Story> unassignedStoryListView = unassignedStoryBox.getListView();
            unassignedStoryListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            unassignedStoryListView.getSelectionModel().select(0);

            Button btnAdd = new Button("Add");
            btnAdd.setOnAction(random -> {
                Collection<Story> selectedStories = new ArrayList<>();
                selectedStories.addAll(unassignedStoryListView.getSelectionModel().getSelectedItems());
                for (Story story : selectedStories) {
                    ((Sprint) item).add(story);
                }
            });

            existingStories.getChildren().addAll(unassignedStoryBox, btnAdd);

            TitledPane collapsableExisting = new TitledPane("Add Unassigned Stories", existingStories);
            collapsableExisting.setExpanded(true);
            collapsableExisting.setAnimated(true);

            VBox newStories = new VBox(5);
            newStories.setPadding(new Insets(8, 8, 8, 8));

            RequiredField shortNameCustomField = new RequiredField("Short Name:");
            RequiredField longNameCustomField = new RequiredField("Long Name:");
            RequiredField creatorCustomField = new RequiredField("Creator:");
            CustomTextArea descriptionTextArea = new CustomTextArea("Description:");
            RequiredField priorityNumberField = new RequiredField("Priority:");
            Button btnAddNew = new Button("Add");
            newStories.getChildren().addAll(shortNameCustomField, longNameCustomField, creatorCustomField,
                    priorityNumberField, descriptionTextArea, btnAddNew);

            //Validation
            shortNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctShortName = validateShortName(shortNameCustomField, null);
                btnAddNew.setDisable(!(correctShortName && correctCreator && correctPriority && correctLongName));
            });

            creatorCustomField.getTextField().textProperty().addListener((observable, oldValue, newvalue) -> {
                correctCreator = validateName(creatorCustomField);
                btnAddNew.setDisable(!(correctShortName && correctCreator && correctPriority && correctLongName));
            });

            longNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctLongName = validateName(longNameCustomField);
                btnAddNew.setDisable(!(correctShortName && correctCreator && correctPriority && correctLongName));
            });

            priorityNumberField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctPriority = validatePriorityField(priorityNumberField, null, null);
                btnAddNew.setDisable(!(correctShortName && correctCreator && correctPriority && correctLongName));
            });


            btnAddNew.setOnAction(random -> {
                if (correctShortName && correctLongName && correctCreator && correctPriority) {

                    String shortName = shortNameCustomField.getText();
                    String longName = longNameCustomField.getText();
                    String creator = creatorCustomField.getText();
                    String description = descriptionTextArea.getText();
                    Integer priority = Integer.parseInt(priorityNumberField.getText());

                    Project project = ((Sprint) item).getProject();
                    Story story = new Story(shortName, longName, description, creator, project,
                            priority);
                    project.add(story, ((Sprint) item));

                    App.mainPane.refreshTree();
                }
            });

            TitledPane collapsableNew = new TitledPane("Add a new Story", newStories);
            collapsableNew.setExpanded(false);
            collapsableNew.setAnimated(true);

            addContent.getChildren().addAll(collapsableExisting, collapsableNew);
            addStoryPopover.setContentNode(addContent);
            addStoryPopover.show(addImage);
        });

            Insets insetsNode = new Insets(0, 5, 5, 0);
        this.setPadding(insetsNode);
            iconBox.getChildren().addAll(addImage, deletionImage);
            deletionBox.getChildren().addAll(iconBox);

            return deletionBox;
    }

    private void initReleaseListeners(Node releaseNode, Release currentRelease) {

        releaseNode.setOnDragDetected(event -> {
            interactiveRelease = currentRelease;
        });

        releaseNode.setOnDragOver(dragEvent -> {
            dragEvent.acceptTransferModes(TransferMode.MOVE);
        });

        releaseNode.setOnDragDropped(dragEvent -> {
            System.out.println("Second");
            if (dragEvent.getDragboard().getString() == "sprint") {
                if (selectedSprint.getProject().equals(currentRelease.getProject())) {
                    System.out.println(currentRelease.getShortName() + " Drag dropped");
                    selectedSprint.edit(selectedSprint.getGoal(), selectedSprint.getLongName(),
                            selectedSprint.getDescription(), selectedSprint.getStartDate(), selectedSprint.getEndDate(),
                            selectedSprint.getTeam(), currentRelease, selectedSprint.getStories(),
                            selectedSprint.getTags());
                }
                else {
                    dragEvent.consume();
                    //TODO Need an extra click for clicking Dialog OK, does the same thing on the scrumboard.
                    CustomDialog.showDialog("Cannot Move Sprint", "Cannot give the chosen Sprint a Release"
                            + " from a different Project.", Alert.AlertType.WARNING);
                }
                App.mainPane.refreshAll();
            }
        });
    }

    private void initSprintListeners(Node sprintNode, Sprint currentSprint) {

        sprintNode.setOnDragDetected(event -> {
            System.out.println(currentSprint);
            selectedSprint = currentSprint;
        });

        sprintNode.setOnDragOver(dragEvent -> {
            dragEvent.acceptTransferModes(TransferMode.MOVE);
        });

        sprintNode.setOnDragDropped(dragEvent -> {
            if (dragEvent.getDragboard().getString() == "story") {
                System.out.println(selectedStory);
                System.out.println(selectedSprint);
                System.out.println(currentSprint);
                if (currentSprint.getProject().equals(selectedStory.getProject())
                        && !currentSprint.equals(selectedSprint) && selectedSprint != null) {
                    System.out.println(currentSprint + " " + selectedSprint);
                    currentSprint.addRemove(currentSprint, selectedSprint, selectedStory);

                    App.mainPane.refreshAll();
                }
                else if (selectedSprint == null) {
                    dragEvent.consume();
                }
                else {
                    dragEvent.consume();
                    //TODO Need an extra click for clicking Dialog OK, does the same thing on the scrumboard.
                    CustomDialog.showDialog("Cannot Move Story", "Cannot move the Story to a Sprint of a "
                            + " different Project.", Alert.AlertType.WARNING);
                }
            }
        });
    }

    public Release getSelectedRelease() {
        return interactiveRelease;
    }

    public void setSelectedRelease(Release release) {
        selectedRelease = release;
    }

    public Sprint getSelectedSprint() {
        return selectedSprint;
    }

    public void setSelectedSprint(Sprint sprint) {
        selectedSprint = sprint;
    }

    public Story getSelectedStory() {
        return selectedStory;
    }

    public void setSelectedStory(Story story) {
        selectedStory = story;
    }

    public RoadMap getRoadmap() {
        return roadMap;
    }


    public List<SearchableControl> getSearchableControls() {
        return searchControls;
    }

    @Override
    public boolean query(String query) {
        boolean found = false;
        for (SearchableControl control : getSearchableControls()) {
            if (control.query(query)) {
                found = true;
            }
        }
        return found;
    }

    @Override
    public int advancedQuery(String query, SearchType searchType) {
        throw new UnsupportedOperationException("Not supported yet.");
    }



}
