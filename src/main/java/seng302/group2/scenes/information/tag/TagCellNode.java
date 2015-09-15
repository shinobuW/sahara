package seng302.group2.scenes.information.tag;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.workspace.tag.Tag;

/**
 * The content of a Tag cell as shown on the Tag management pane.
 * Created by drm127 on 14/09/15.
 */
public class TagCellNode extends VBox {

    private Tag tag = null;
    private String tagName;
    private Color tagColor;

    public TagCellNode(Tag tag) {
        this.tag = tag;
        this.tagName = tag.getName();
        this.tagColor = tag.getColor();

        VBox content = new VBox();
        content.setPrefHeight(35);
        content.setBackground(new Background(new BackgroundFill(tagColor, CornerRadii.EMPTY, Insets.EMPTY)));

        VBox textContent = new VBox();
        textContent.setPadding(new Insets(2, 2, 2, 6));
        textContent.setAlignment(Pos.CENTER_LEFT);

        SearchableText titleLabel = new SearchableText(tagName);
        titleLabel.injectStyle("-fx-font-weight: bold");

        textContent.getChildren().addAll(titleLabel);

        VBox rightContent = new VBox(1);
        rightContent.setPrefHeight(35);

        rightContent.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(rightContent, Priority.ALWAYS);

        content.getChildren().addAll(textContent, rightContent);

        HBox.setHgrow(content, Priority.ALWAYS);
    }

    /**
     * Sets the name of the cell.
     * @param newName The new name to display
     */
    public void setName(String newName) {
        this.tagName = newName;
    }

    /**
     * Sets the color of the cell.
     * @param newColor The new color to show
     */
    public void setColor(Color newColor) {
        this.tagColor = newColor;
    }

}
