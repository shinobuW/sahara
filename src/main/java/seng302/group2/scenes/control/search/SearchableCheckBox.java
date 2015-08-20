package seng302.group2.scenes.control.search;

import com.sun.javafx.font.freetype.HBGlyphLayout;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.Collection;

/**
 * A class to display a searchable text box.
 * Created by btm38 on 19/08/15.
 */
public class SearchableCheckBox extends VBox implements SearchableControl {
    private SearchableText cbLabel = new SearchableText();
    private CheckBox cb = new CheckBox();

    /**
     *
     * @param label Late
     */
    public SearchableCheckBox(String label) {
        HBox labelBox = new HBox();
        labelBox.setPrefWidth(165);
        labelBox.setAlignment(Pos.CENTER_LEFT);
        labelBox.spacingProperty().setValue(0);

        cbLabel.setText(label);
        labelBox.getChildren().addAll(cbLabel, cb);

        this.getChildren().add(labelBox);

    }

    /**
     * Constructor for a SearchableCheckBox that takes an initial set of items and
     * @param searchableControls A collection of searchable controls to add this control to
     */
    public SearchableCheckBox(String label, Collection<SearchableControl> searchableControls) {
        HBox labelBox = new HBox();
        labelBox.setPrefWidth(165);
        labelBox.setAlignment(Pos.CENTER_LEFT);
        labelBox.spacingProperty().setValue(0);

        cbLabel.setText(label);
        labelBox.getChildren().addAll(cbLabel, cb);

        this.getChildren().add(labelBox);
        searchableControls.add(this);
    }

    /**
     * Gets the CheckBox out of this class.
     * @return the CheckBox.
     */
    public CheckBox getCheckBox() {
        return this.cb;
    }

    /**
     * Queries the contents of the checkbox label.
     *
     * @param query The search string to query the list contents with
     * @return If a match was found for the query
     */
    @Override
    public boolean query(String query) {
        if (cbLabel.query(query)) {
            return true;
        }
        else {
            return false;

        }
    }

}


