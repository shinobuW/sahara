package seng302.group2.scenes.information.project.sprint;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import seng302.group2.scenes.control.Tooltip;
import seng302.group2.workspace.project.story.tasks.Task;

/**
 * A ListCell extension for the neat displaying of tasks on the scrum board view
 * Created by Jordane on 23/07/2015.
 */
public class ScrumBoardTaskCell extends ListCell<Task> {

    ListView<Task> parentTable = null;
    Task interactiveTask = null;
    ScrumboardTab tab = null;

    public ScrumBoardTaskCell(ListView<Task> parentTable, ScrumboardTab tab) {
        this.parentTable = parentTable;
        this.tab = tab;
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

            Label titleLabel = new Label(task.getShortName());
            titleLabel.setStyle("-fx-font-weight: bold");

            Label descLabel = new Label("(No Description)");
            descLabel.setStyle("-fx-font-size: 85%");
            if (!task.getDescription().isEmpty()) {
                descLabel.setText(task.getDescription());
            }
            textContent.getChildren().addAll(titleLabel, descLabel);


            // The cell's 'iconic' information
            VBox rightContent = new VBox(1);
            rightContent.setPrefHeight(48);

            Label remainingTime = new Label(task.getEffortLeftString());
            remainingTime.setStyle("-fx-font-size: 85%");
            remainingTime.setAlignment(Pos.TOP_RIGHT);

            rightContent.setAlignment(Pos.CENTER_RIGHT);
            HBox.setHgrow(rightContent, Priority.ALWAYS);
            rightContent.getChildren().addAll(remainingTime);

            // Assignee icon
            if (task.getAssignee() != null) {
                ImageView assigneeImage = new ImageView("icons/person.png");
                Tooltip.create(task.getAssignee().getShortName(), assigneeImage, 50);
                rightContent.getChildren().addAll(assigneeImage);
            }

            // Impediments icon
            if (task.getState() == Task.TASKSTATE.BLOCKED || task.getState() == Task.TASKSTATE.DEFERRED) {
                ImageView warningImage = new ImageView("icons/dialog-cancel.png");
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
                rightContent.getChildren().addAll(warningImage);
            }


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
}