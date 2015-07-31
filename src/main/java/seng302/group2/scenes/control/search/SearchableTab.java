package seng302.group2.scenes.control.search;

import javafx.scene.control.Tab;

import java.util.Collection;

/**
 * Created by jml168 on 31/07/15.
 */
public abstract class SearchableTab extends Tab {

    public abstract Collection<SearchableControl> getSearchableControls();

    public boolean query(String query) {
        boolean found = false;
        for (SearchableControl control : getSearchableControls()) {
            if (control.query(query)) {
                found = true;
            }
        }
        return found;
    }
}
