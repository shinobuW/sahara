package seng302.group2.scenes.control;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;

/**
 * A custom PopOver that acts as an information ToolTip that's useful for editing and displaying information other than
 * plain string content
 * Created by Jordane on 24/08/2015.
 */
public class PopOverTip extends PopOver {

    Node parent = null;

    /**
     * Creates a PopOverTip on the given parent/owner
     * @param parentNode The parent of the PopOverTip
     */
    public PopOverTip(Node parentNode) {
        super();
        init(parentNode);
        setContentNode(new VBox(8));
    }

    /**
     * Creates a PopOverTip on the given parent/owner with the given content as the inner of the PopOverTip
     * @param parentNode The parent of the PopOverTip
     * @param contentNode The content of the PopOverTip
     */
    public PopOverTip(Node parentNode, Node contentNode) {
        super(contentNode);
        init(parentNode);
        setContentNode(contentNode);
    }

    /**
     * Performs basic initialisation of the PopOverTip
     * @param parentNode The parent of the PopOverTip
     */
    private void init(Node parentNode) {
        this.parent = parentNode;
        setDetachable(false);  // We don't want to detach ToolTip/information PopOverTips, use a PopOver instead
        setupHover();
    }

    /**
     * Creates mouse event handlers that trigger the PopOverTip to display and hide itself on a hover over it's parent
     */
    private void setupHover() {
        if (parent != null) {
            parent.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> show(parent));
            parent.addEventHandler(MouseEvent.MOUSE_EXITED, event -> hide());
        }
    }
}
