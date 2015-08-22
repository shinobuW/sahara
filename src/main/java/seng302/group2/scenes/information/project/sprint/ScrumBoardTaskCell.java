package seng302.group2.scenes.information.project.sprint;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.OverrunStyle;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import seng302.group2.workspace.project.story.tasks.Task;

import javax.swing.*;

/**
 * A ListCell extension for the neat displaying of tasks on the scrum board view
 * Created by Jordane on 23/07/2015.
 */
public class ScrumBoardTaskCell extends ListCell<Task> {
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
            VBox rightContent = new VBox();
            rightContent.setPrefHeight(48);

            Label remainingTime = new Label(task.getEffortLeftString());
            remainingTime.setStyle("-fx-font-size: 80%");
            remainingTime.setAlignment(Pos.TOP_RIGHT);

            Label assignee = new Label();
            if (task.getAssignee() != null) {
                assignee = new Label(task.getAssignee().getFirstName().substring(0, 1)
                        + task.getAssignee().getLastName().substring(0, 1));
            }
            else {
                assignee = new Label("-");
            }
            assignee.setStyle("-fx-font-size: 80%");
            assignee.setAlignment(Pos.CENTER_RIGHT);


            // TODO Impediments/Warning icon


            rightContent.setAlignment(Pos.CENTER_RIGHT);
            HBox.setHgrow(rightContent, Priority.ALWAYS);
            rightContent.getChildren().addAll(remainingTime, assignee);


            // Bring cell parts together
            content.getChildren().addAll(rect, textContent, rightContent);
            setGraphic(content);
        }
        else {
            //System.out.println("Item is null");
            setGraphic(null);
        }

        this.setOnDragDetected(event -> {
                Dragboard dragBoard = this.startDragAndDrop(TransferMode.MOVE);
                dragBoard.setDragView(this.snapshot(null, null));
                ClipboardContent content = new ClipboardContent();
                content.putString("");
                dragBoard.setContent(content);
            });

        setTextOverrun(OverrunStyle.CLIP);
    }
}