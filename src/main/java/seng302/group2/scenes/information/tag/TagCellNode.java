package seng302.group2.scenes.information.tag;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.workspace.tag.Tag;

/**
 * The content of a Tag cell as shown on the Tag management pane.
 * Created by drm127 on 14/09/15.
 */
public class TagCellNode extends VBox {

    private Tag tag = null;

    public TagCellNode(Tag tag) {
        this.tag = tag;

        VBox content = new VBox();
        content.setPrefHeight(35);
        content.setBackground(new Background(new BackgroundFill(tag.getColor(), CornerRadii.EMPTY, Insets.EMPTY)));

        VBox textContent = new VBox();
        textContent.setPadding(new Insets(2, 2, 2, 6));
        textContent.setAlignment(Pos.CENTER_LEFT);

        SearchableText titleLabel = new SearchableText(tag.getName());
        titleLabel.injectStyle("-fx-font-weight: bold");

        textContent.getChildren().addAll(titleLabel);

        VBox rightContent = new VBox(1);
        rightContent.setPrefHeight(35);

        rightContent.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(rightContent, Priority.ALWAYS);

        content.getChildren().addAll(textContent, rightContent);

        HBox.setHgrow(content, Priority.ALWAYS);
    }

}
