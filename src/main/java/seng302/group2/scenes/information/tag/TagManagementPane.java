package seng302.group2.scenes.information.tag;

import javafx.geometry.Insets;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;
import seng302.group2.Global;
import seng302.group2.scenes.control.search.SearchableListView;
import seng302.group2.workspace.tag.Tag;

/**
 * A Pane for the creation and management of Tags.
 * Created by drm127 on 14/09/15.
 */
public class TagManagementPane extends Pane {

    PopOver popOver = null;

    public TagManagementPane() {
        VBox content = new VBox(8);
        content.setPadding(new Insets(8));


        //Create tag field when custom control exists
        //TagField tagCreationField = new TagField

        //SearchableListView<TagCell?> tagView = new SearchableListView<>(Global.currentWorkspace.getTags());
    }
}
