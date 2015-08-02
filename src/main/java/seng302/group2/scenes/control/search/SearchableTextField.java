package seng302.group2.scenes.control.search;

import javafx.scene.control.TextField;

/**
 * Created by jml168 on 2/08/15.
 */
public class SearchableTextField extends TextField implements SearchableControl {

    public SearchableTextField() {
    }

    public SearchableTextField(String text) {
        this.setText(text);
    }

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
