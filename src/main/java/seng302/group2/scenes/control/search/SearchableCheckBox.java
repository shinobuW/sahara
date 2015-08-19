package seng302.group2.scenes.control.search;

import java.awt.*;

/**
 * A class to display a searchable text box.
 * Created by btm38 on 19/08/15.
 */
public class SearchableCheckBox extends Checkbox implements SearchableControl {
    /**
     * Basic constructor for SearchableCheckBox
     */
    public SearchableCheckBox() {
        super();
    }


    /**
     * Queries the contents of the checkbox label.
     *
     * @param query The search string to query the list contents with
     * @return If a match was found for the query
     */
    @Override
    public boolean query(String query) {
        if (this.getLabel().contains(query)) {

            return true;
        }
        else {

            return false;
        }
    }

}


