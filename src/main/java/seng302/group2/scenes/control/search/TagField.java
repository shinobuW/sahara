package seng302.group2.scenes.control.search;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import org.controlsfx.control.textfield.CustomTextField;
import seng302.group2.Global;
import seng302.group2.scenes.information.tag.TagCellNode;
import seng302.group2.workspace.tag.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * A control used to identify and add/remove tags to and from models.
 * Created by jml168 on 20/09/15.
 */
public class TagField extends CustomTextField {

    List<Tag> tags = new ArrayList<>();
    public HBox tagStack = new HBox(4);


    /**
     * Creates an empty tag field
     */
    public TagField() {
        addListeners();
        update();
    }


    /**
     * Creates a tag field containing the given tags
     * @param tags The initial list of tags to contain
     */
    public TagField(List<Tag> tags) {
        this.tags.addAll(tags);
        addListeners();
        update();
    }


    /**
     * Adds listeners to the field to add and remove tags on entry as necessary
     */
    void addListeners() {

        /**
         * The text listener for the adding of values
         */
        this.textProperty().addListener((observable, oldValue, newValue) -> {

            // If > 20, copy Dave tag listener

            // Check for a new tag separator (either ',' or ' ', a comma or a space)
            if (!this.getText().isEmpty() && this.getText().length() >= 2
                    && (this.getText().endsWith(",") || this.getText().endsWith(" "))) {

                String tagString = this.getText().substring(0, this.getText().length() - 1);
                Tag selectedTag = null;

                // Find the tag in the global workspace
                for (Tag tag : Global.currentWorkspace.getAllTags()) {
                    if (tagString.equals(tag.getName())) {
                        selectedTag = tag;
                    }
                }

                // Or maybe it's in the list we have already typed out?
                for (Tag tag : tags) {
                    if (tagString.equals(tag.getName())) {
                        Platform.runLater(this::clear);
                        return;
                    }
                }

                // Or create it if not found
                if (selectedTag == null) {
                    selectedTag = new Tag(tagString);
                }

                tags.add(selectedTag);

                Platform.runLater(this::clear);

                update();
            }
        });


        /**
         * The key listener for the removal of values (on backspace)
         */
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent key) {
                if (key.getCode().equals(KeyCode.BACK_SPACE) && getText().isEmpty()
                        && tagStack.getChildren().size() > 0) {

                    key.consume();

                    TagCellNode tagCell = (TagCellNode) tagStack.getChildren().get(tagStack.getChildren().size() - 1);
                    tagStack.getChildren().remove(tagCell);
                    Tag tag = tagCell.getTag();
                    tags.remove(tag);
                    setText(tag.getName());

                    update();
                }
            }
        });
    }


    /**
     * Calls an update on the list of tags so that new tags entered are visible
     */
    void update() {

        tagStack.getChildren().clear();

        for (Tag tag : tags) {
            TagCellNode node = new TagCellNode(tag);
            tagStack.getChildren().add(node);
        }

        this.setLeft(tagStack);

        positionCaret(getLength());

        setPrefWidth(tagStack.getWidth() + tagStack.getChildren().size() * tagStack.getSpacing() + 140);
    }


    /**
     * Returns a list of tags in the tag field that weren't already a tag inside the current workspace
     * @return Tags created that weren't already a tag inside the current workspace
     */
    public List<Tag> getNewlyCreatedTags() {
        List<Tag> newTags = new ArrayList<>();

        for (Tag tag : tags) {
            if (!Global.currentWorkspace.getAllTags().contains(tag)) {
                newTags.add(tag);
            }
        }

        return newTags;
    }

}
