package seng302.group2.scenes.control.search;

import javafx.scene.paint.Color;
import seng302.group2.workspace.project.story.Story;

/**
 * Created by jml168 on 31/07/15.
 */
public interface SearchableControl {
    public static String highlightColour = Story.toRGBCode(Color.rgb(255, 255, 0, 0.5));

    boolean query(String query);
}
