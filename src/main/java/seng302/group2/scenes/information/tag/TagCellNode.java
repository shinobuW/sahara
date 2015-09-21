package seng302.group2.scenes.information.tag;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.controlsfx.control.spreadsheet.Grid;
import seng302.group2.scenes.control.Tooltip;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.control.search.SearchType;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.workspace.tag.Tag;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static seng302.group2.scenes.dialog.DeleteDialog.showDeleteDialog;

/**
 * The content of a Tag cell as shown on the Tag management pane.
 * Created by drm127 on 14/09/15.
 */
public class TagCellNode extends VBox implements SearchableControl {

    private Tag tag = null;
    private String tagName = "";
    private Color tagColor = Color.ROYALBLUE;
    private boolean removable;

    private Set<SearchableControl> searchControls = new HashSet<>();


    /**
     * Constructor for a tag cell node.
     * @param tag The tag to display.
     */
    public TagCellNode(Tag tag, boolean removable) {
        this.tag = tag;
        this.tagName = tag.getName();
        this.tagColor = tag.getColor();
        this.removable = removable;

        construct();
    }

    /**
     * Constructor for a tag cell node. Adds to the given list of searchable controls
     * @param tag The tag to display.
     */
    public TagCellNode(Tag tag, boolean removable, Collection<SearchableControl> searchableControls) {
        this.tag = tag;
        this.tagName = tag.getName();
        this.tagColor = tag.getColor();
        this.removable = removable;

        searchableControls.add(this);

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
        textContent.setPadding(new Insets(2, 4, 2, 4));
        textContent.setAlignment(Pos.CENTER_LEFT);

        SearchableText titleLabel = new SearchableText(tagName, "-fx-font-weight: bold;", searchControls);
        Text titleLabelShadow = new Text(tagName);  // A transparent text to keep the width of the tag equal to the text
        titleLabelShadow.setFill(Color.TRANSPARENT);
        titleLabelShadow.setStyle("-fx-font-weight: bold;");
        GridPane titleGrid = new GridPane();
        titleGrid.add(titleLabel, 0, 0);
        titleGrid.add(titleLabelShadow, 0, 0);

        if (tagColor.getBrightness() < 0.9) {
            for (Text text : titleLabel.getTexts()) {
                text.setFill(Color.WHITE);
            }
            titleLabel.injectStyle("-fx-text-fill: white;");
        }

        textContent.getChildren().addAll(titleGrid);

        if (removable) {
            Node deletionNode = createDeletionNode(tag);
            content.getChildren().addAll(textContent, deletionNode);
        }
        else {
            content.getChildren().addAll(textContent);
        }

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

    /**
     * Creates a deletion node
     * @param tag The tag to be deleted
     */
    public Node createDeletionNode(Tag tag) {
        ImageView deletionImage = new ImageView("icons/tag_remove.png");
        Tooltip.create("Remove", deletionImage, 50);

        deletionImage.setOnMouseEntered(me -> {
            this.getScene().setCursor(Cursor.HAND); //Change cursor to hand
        });
        deletionImage.setOnMouseExited(me -> {
            this.getScene().setCursor(Cursor.DEFAULT); //Change cursor to hand
        });

        deletionImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            showDeleteDialog(tag);
            event.consume();
        });


        return deletionImage;

    }

    @Override
    public boolean query(String query) {
        boolean found = false;
        for (SearchableControl control : searchControls) {
            found = control.query(query) || found;
        }
        return found;
    }

    @Override
    public int advancedQuery(String query, SearchType searchType) {
        // TODO @Jordane
        return 0;
    }
}
