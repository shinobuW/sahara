package seng302.group2.scenes.information.project.backlog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import seng302.group2.workspace.project.story.Story;

/**
 * Created by Jordane on 23/07/2015.
 */
public class TestingCellFactory extends ListCell<Story> {
    @Override
    public void updateItem(Story item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            HBox content = new HBox();
            content.setMaxWidth(148);
            content.setMaxHeight(48);


            Rectangle rect = new Rectangle(4, 48);
            rect.setFill(Color.web(item.getColour()));


            VBox info = new VBox();
            info.setPadding(new Insets(2,2,2,6));
            info.setAlignment(Pos.CENTER_LEFT);
            Label titleLabel = new Label(item.getShortName());
            titleLabel.setStyle("-fx-font-weight: bold");

            Label descLabel = new Label("(No Description)");
            descLabel.setStyle("-fx-font-size: 85%");
            if (!item.getDescription().isEmpty()) {
                descLabel.setText(item.getDescription());
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