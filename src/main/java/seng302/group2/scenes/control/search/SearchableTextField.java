package seng302.group2.scenes.control.search;

import javafx.scene.control.TextField;

/**
 * This class provides functionality to create searchable text fields.
 * Created by jml168 on 2/08/15.
 */
public class SearchableTextField extends TextField implements SearchableControl {

    /**
     * Basic constructor. Empty.
     */
    public SearchableTextField() {
    }

    /**
     * Basic constructor. Sets the text of the text field.
     * @param text the text to be placed inside the text field.
     */
    public SearchableTextField(String text) {
        this.setText(text);
    }

    /**
     * Checks the text field to see if there is a matching string.
     * @param query the string to be queried
     * @return whether or not a matching string was found
     */
    @Override
    public boolean query(String query) {
        if (this.getText().contains(query)) {
            setStyle("-fx-border-color: dodgerblue");
            return true;
        }
        else {
            setStyle(null);
            return false;
        }
    }
}
