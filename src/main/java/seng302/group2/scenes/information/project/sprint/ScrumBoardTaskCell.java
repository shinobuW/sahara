package seng302.group2.scenes.information.project.sprint;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.controlsfx.control.PopOver;
import seng302.group2.scenes.control.Tooltip;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTableRow;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.information.project.story.task.LoggingEffortPane;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.story.tasks.Task;
import seng302.group2.workspace.team.Team;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A ListCell extension for the neat displaying of tasks on the scrum board view
 * Created by Jordane on 23/07/2015.
 */
public class ScrumBoardTaskCell extends ListCell<Task> implements SearchableControl {

    ListView<Task> parentTable = null;
    Task interactiveTask = null;
    ScrumboardTab tab = null;

    Set<SearchableControl> searchControls = new HashSet<>();

    public ScrumBoardTaskCell(ListView<Task> parentTable, ScrumboardTab tab) {
        this.parentTable = parentTable;
        this.tab = tab;
        tab.searchControls.add(this);
    }

    @Override
    public void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);
        if (task != null) {
            // Setup the cell graphic (HBox)
            HBox content = new HBox();
            //content.setPrefWidth(this.getMaxWidth());
            content.setPrefHeight(48);


            // The cell's coloured rectangle
            Rectangle rect = new Rectangle(5, 48);
            rect.setFill(Color.web(task.getState().getColourString())); //(Color.web(item.getColour()));


            // The text content of the cell
            VBox textContent = new VBox();
            textContent.setPadding(new Insets(2, 2, 2, 6));
            textContent.setAlignment(Pos.CENTER_LEFT);

            SearchableText titleLabel = new SearchableText(task.getShortName(), searchControls);
            titleLabel.injectStyle("-fx-font-weight: bold");

            SearchableText descLabel = new SearchableText("(No Description)", searchControls);
            descLabel.injectStyle("-fx-font-size: 85%");
            if (!task.getDescription().isEmpty()) {
                descLabel.setText(task.getDescription());
            }
            textContent.getChildren().addAll(titleLabel, descLabel);




            // The cell's 'iconic' information
            VBox rightContent = new VBox(1);
            rightContent.setPrefHeight(48);

            SearchableText remainingTime = new SearchableText(task.getEffortLeftString(), searchControls);
            Tooltip.create("Spent Effort: " + task.getEffortSpentString() + "\n"
                    + "Remaining Effort: " + task.getEffortLeftString(), remainingTime, 50);
            remainingTime.injectStyle("-fx-font-size: 85%;");
            remainingTime.setTextAlignment(TextAlignment.RIGHT);


            // Remaining time PopOver
            PopOver loggingEffortPopOver = new PopOver();
            LoggingEffortPane loggingPane = new LoggingEffortPane(task, loggingEffortPopOver);
            loggingEffortPopOver.setContentNode(loggingPane);
            loggingEffortPopOver.setDetachedTitle(task.getShortName());

            remainingTime.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (loggingEffortPopOver.isShowing()) {
                        loggingEffortPopOver.hide();
                    }
                    else {
                        loggingEffortPopOver.show(remainingTime);
                    }
                    event.consume();
                });

            rightContent.setAlignment(Pos.CENTER_RIGHT);
            HBox.setHgrow(rightContent, Priority.ALWAYS);
            rightContent.getChildren().addAll(remainingTime);





            // Assignee icon
            ImageView assigneeImage;
            if (task.getAssignee() != null) {
                assigneeImage = new ImageView("icons/person.png");
                Tooltip.create(task.getAssignee().getFullName(), assigneeImage, 50);
            }
            else {
                assigneeImage = new ImageView("icons/person_empty.png");
                Tooltip.create("This task is unassigned", assigneeImage, 50);
            }
            rightContent.getChildren().addAll(assigneeImage);

            PopOver assignPopOver = new PopOver();
            assignPopOver.setDetachedTitle(task.getShortName() + " Assignment");
            SortedSet<Person> availableAssignees = new TreeSet<>();
            try {
                for (Team team : task.getStory().getProject().getCurrentTeams()) {
                    availableAssignees.addAll(team.getPeople());
                }
            }
            catch (NullPointerException ex) {
            }
            Person nonePerson = new Person();
            nonePerson.setShortName("(none)");
            availableAssignees.add(nonePerson);

            ComboBox<Person> assigneeCombo = new ComboBox<>();
            assigneeCombo.getItems().addAll(availableAssignees);
            assigneeCombo.getSelectionModel().select(task.getAssignee());
            assigneeImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (assignPopOver.isShowing()) {
                        assignPopOver.hide();
                    }
                    else {
                        assignPopOver.show(assigneeImage);
                    }
                    event.consume();
                });

            Button assigneeSaveButton = new Button("Save Assignee");
            assigneeSaveButton.setAlignment(Pos.CENTER_RIGHT);
            assigneeSaveButton.setOnAction(event -> {
                    Person selectedPerson = assigneeCombo.getSelectionModel().getSelectedItem();
                    if (selectedPerson.equals(nonePerson)) {
                        selectedPerson = null;
                    }
                    task.editAssignee(selectedPerson);
                    assignPopOver.hide();
                    this.updateItem(this.getItem(), this.isEmpty());
                });

            SearchableText assigneeLabel = new SearchableText("Assignee: ");
            assigneeLabel.setTextAlignment(TextAlignment.LEFT);
            HBox assigneeComboLabel = new HBox(8);
            assigneeComboLabel.setAlignment(Pos.CENTER);
            assigneeComboLabel.getChildren().addAll(
                    assigneeLabel,
                    assigneeCombo
            );
            VBox assigneeChangeNode = new VBox(8);
            assigneeChangeNode.setPadding(new Insets(8,8,8,8));
            assigneeChangeNode.getChildren().addAll(
                    assigneeComboLabel,
                    assigneeSaveButton
            );
            assignPopOver.setContentNode(assigneeChangeNode);






            // Impediments icon
            ImageView warningImage;
            if (task.getState() == Task.TASKSTATE.BLOCKED || task.getState() == Task.TASKSTATE.DEFERRED) {
                warningImage = new ImageView("icons/dialog-cancel.png");
                if (task.getState() == Task.TASKSTATE.BLOCKED) {
                    if (!task.getImpediments().equals("")) {
                        Tooltip.create("This task is currently blocked, with the following impediments: \n"
                                + task.getImpediments(), warningImage, 50);
                    }
                    else {
                        Tooltip.create("This task is currently blocked", warningImage, 50);
                    }
                }
                else {
                    if (!task.getImpediments().equals("")) {
                        System.out.println(task.getImpediments());
                        Tooltip.create("This task has been deferred, and has the following impediments: \n"
                                + task.getImpediments(), warningImage, 50);
                    }
                    else {
                        Tooltip.create("This task has been deferred", warningImage, 50);
                    }
                }
            }
            else {
                warningImage = new ImageView("icons/dialog-cancel-empty.png");
                Tooltip.create("This task has no impediments or blockages", warningImage, 50);
            }
            rightContent.getChildren().addAll(warningImage);


            PopOver impedimentPopOver = new PopOver();
            impedimentPopOver.setDetachedTitle(task.getShortName() + " Impediments");
            SortedSet<Task.TASKSTATE> availableStatuses = new TreeSet<>();
            try {
                for (Task.TASKSTATE state : Task.getImpedingStates()) {
                    availableStatuses.add(state);
                }
            }
            catch (NullPointerException ex) {
            }


            ComboBox impedimentCombo = new ComboBox<>();
            impedimentCombo.getItems().add("(none)");
            impedimentCombo.getItems().addAll(availableStatuses);
            // Make default selection
            if (impedimentCombo.getItems().contains(task.getState())) {
                impedimentCombo.getSelectionModel().select(task.getState());
            }
            else {
                impedimentCombo.getSelectionModel().select(null);
            }
            warningImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (impedimentPopOver.isShowing()) {
                    impedimentPopOver.hide();
                }
                else {
                    impedimentPopOver.show(warningImage);
                }
                event.consume();
            });

            Button impedimentSaveButton = new Button("Save Status");
            impedimentSaveButton.setAlignment(Pos.CENTER_RIGHT);
            impedimentSaveButton.setOnAction(event -> {
                Task.TASKSTATE selectedState = null;
                Object selectedObject = impedimentCombo.getSelectionModel().getSelectedItem();
                if (!selectedObject.toString().equals("(none)")) {
                    selectedState = (Task.TASKSTATE) selectedObject;
                }
                task.editImpedimentState(selectedState);
                impedimentPopOver.hide();
                this.updateItem(this.getItem(), this.isEmpty());
            });

            SearchableText statusLabel = new SearchableText("Status: ");
            statusLabel.setTextAlignment(TextAlignment.LEFT);
            HBox impedimentComboLabel = new HBox(8);
            impedimentComboLabel.setAlignment(Pos.CENTER);
            impedimentComboLabel.getChildren().addAll(
                    statusLabel,
                    impedimentCombo
            );
            VBox impedimentChangeNode = new VBox(8);
            impedimentChangeNode.setPadding(new Insets(8,8,8,8));
            impedimentChangeNode.getChildren().addAll(
                    impedimentComboLabel,
                    impedimentSaveButton
            );
            impedimentPopOver.setContentNode(impedimentChangeNode);













            // Bring cell parts together
            content.getChildren().addAll(rect, textContent, rightContent);
            setGraphic(content);
        }
        else {
            //System.out.println("Item is null");
            setGraphic(null);
        }

        this.setOnDragDetected(event -> {
                interactiveTask = this.getItem();
                Dragboard dragBoard = this.startDragAndDrop(TransferMode.MOVE);
                dragBoard.setDragView(this.snapshot(null, null));
                ClipboardContent content = new ClipboardContent();
                content.putString("");
                dragBoard.setContent(content);
            });

        this.setOnDragOver(event -> {
                int dropIndex;

                if (this.isEmpty()) {
                    dropIndex = this.parentTable.getItems().size() - 1;
                }
                else {
                    dropIndex = this.getIndex();
                }

                //parentTable.getItems().add(dropIndex, interactiveTask);
                tab.hoverIndex = dropIndex;
            });

        setTextOverrun(OverrunStyle.CLIP);
    }

    @Override
    public boolean query(String query) {
        boolean result = false;
        for (SearchableControl control : searchControls) {
            if (control.query(query)) {
                result = true;
            }
        }
        return result;
    }
}