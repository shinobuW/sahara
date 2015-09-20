package seng302.group2.scenes.information.tag;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import seng302.group2.scenes.control.search.SearchType;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.workspace.tag.Tag;

/**
 * The content of a Tag cell as shown on the Tag management pane.
 * Created by drm127 on 14/09/15.
 */
public class TagCellNode extends VBox implements SearchableControl {

    private Tag tag = null;
    private String tagName = "";
    private Color tagColor = Color.ROYALBLUE;

    /**
     * Constructor for a tag cell node.
     * @param tag The tag to display.
     */
    public TagCellNode(Tag tag) {
        this.tag = tag;
        this.tagName = tag.getName();
        this.tagColor = tag.getColor();

        construct();
    }

    /**
     * Creates the cell node, including text, size and background colour.
     */
    void construct() {
        this.getChildren().clear();

        HBox content = new HBox();
        Insets insetsContent = new Insets(2, 2, 2, 2);
        content.setPadding(insetsContent);
        content.setBackground(new Background(new BackgroundFill(tagColor, new CornerRadii(4), Insets.EMPTY)));

        setPrefWidth(20);
        setMaxWidth(100);

        VBox textContent = new VBox();
        textContent.setPadding(new Insets(2, 2, 2, 6));
        textContent.setAlignment(Pos.CENTER_LEFT);

        Text titleLabel = new Text(tagName);
        titleLabel.setStyle("-fx-font-weight: bold;");

        if (tagColor.getBrightness() < 0.9) {
            titleLabel.setFill(Color.WHITE);
        }

        textContent.getChildren().addAll(titleLabel);

        content.getChildren().addAll(textContent);

        this.getChildren().add(content);
    }


    /**
     * Sets the name of the cell.
     * @param newName The new name to display
     */
    public void setName(String newName) {
        this.tagName = newName;
        construct();
    }

    /**
     * Sets the color of the cell.
     * @param newColor The new color to show
     */
    public void setColor(Color newColor) {
        this.tagColor = newColor;
        construct();
    }

    /**
     * Gets the tag that the cell node is based on
     * @return The cell node's associated tag
     */
    public Tag getTag() {
        return this.tag;
    }

    @Override
    public boolean query(String query) {
        // TODO @Jordane
        return false;
    }

    @Override
    public int advancedQuery(String query, SearchType searchType) {
        // TODO @Jordane
        return 0;
    }
}
