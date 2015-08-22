package seng302.group2.scenes.information.project.sprint;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import seng302.group2.workspace.project.story.tasks.Task;

/**
 * A ListCell extension for the neat displaying of tasks on the scrum board view
 * Created by Jordane on 23/07/2015.
 */
public class ScrumBoardTaskCell extends ListCell<Task> {
    @Override
    public void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);
        if (task != null) {
            HBox content = new HBox();
            content.setMaxWidth(148);
            content.setMaxHeight(48);


            Rectangle rect = new Rectangle(4, 48);
            rect.setFill(Color.web(task.getState().getColourString())); //(Color.web(item.getColour()));


            VBox info = new VBox();
            info.setPadding(new Insets(2,2,2,6));
            info.setAlignment(Pos.CENTER_LEFT);
            Label titleLabel = new Label(task.getShortName());
            titleLabel.setStyle("-fx-font-weight: bold");

            Label descLabel = new Label("(No Description)");
            descLabel.setStyle("-fx-font-size: 85%");
            if (!task.getDescription().isEmpty()) {
                descLabel.setText(task.getDescription());
            }
            
            info.getChildren().addAll(titleLabel, descLabel);

            
            content.getChildren().addAll(rect, info);
            setGraphic(content);
        }
        else {
            //System.out.println("Item is null");
            setGraphic(null);
        }

        setTextOverrun(OverrunStyle.CLIP);
    }
}