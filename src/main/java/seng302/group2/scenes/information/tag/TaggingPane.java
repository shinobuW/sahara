package seng302.group2.scenes.information.tag;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.search.SearchType;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.TagField;
import seng302.group2.scenes.control.search.TagLabel;
import seng302.group2.workspace.SaharaItem;

import java.util.ArrayList;
import java.util.List;


/**
 * A pane used to display the tabs on items without a tree view.
 * Created by btm38 on 24/09/15.
 */
public class TaggingPane extends Pane implements SearchableControl {
    List<SearchableControl> searchControls = new ArrayList<>();
    VBox content = new VBox(8);
    SaharaItem item = null;


    /**
     * Constructor for tagging pane. Tagging pan eis used to display and edit the tags on any SaharaItem.
     * @param item The Sahara item being edited.
     */
    public TaggingPane(SaharaItem item) {
        this.item = item;
        constructInfo();
        content.setPadding(new Insets(8));
        this.getChildren().add(content);
    }

    private void constructInfo() {
        content.getChildren().clear();
        TagLabel tagLabel = new TagLabel(item.getTags());
        Button btnEdit = new Button("Edit");

        content.getChildren().add(tagLabel);
        content.getChildren().add(btnEdit);

        searchControls.clear();
        searchControls.add(tagLabel);

        btnEdit.setOnAction((event) -> constructEdit());
    }


    private void constructEdit() {
        content.getChildren().clear();

        TagField tagField = new TagField(item.getTags());
        HBox buttons = new HBox(5);

        Button btnCancel = new Button("Cancel");
        Button btnDone = new Button("Done");

        buttons.getChildren().addAll(btnCancel, btnDone);

        content.getChildren().addAll(tagField, buttons);

        btnCancel.setOnAction((event) -> constructInfo());

        btnDone.setOnAction((event) -> {
            item.editTags(tagField.getTags());
            constructInfo();
        });
    }


    @Override
    public boolean query(String query) {
        // TODO #Bronson
        return false;
    }


    @Override
    public int advancedQuery(String query, SearchType searchType) {
        // TODO @Jordane
        return 0;
    }
}
