package seng302.group2.scenes.information;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import seng302.group2.util.conversion.ColorUtils;

/**
 * Created by cvs20 on 19/09/15.
 */
public class StickyBar extends Pane {

    public StickyBar() {
    }

    public StickyBar(STICKYTYPE type) {
        construct(type);
    }

    void construct(STICKYTYPE type) {
        if (type == STICKYTYPE.INFO) {
            this.getChildren().add(new Button("Edit"));
        }
        if (type == STICKYTYPE.EDIT) {
            this.getChildren().add(new Button("Done"));
            this.getChildren().add(new Button("Cancel"));
        }
        if (type == STICKYTYPE.OTHER) {
            this.getChildren().add(new Button("Done"));
        }

    }

    /**
     * An enum for the states of the Task. Also includes a toString method for GUI application of TaskStates
     */
    public enum STICKYTYPE {
        INFO,
        EDIT,
        CATEGORY,
        OTHER
    }
}

