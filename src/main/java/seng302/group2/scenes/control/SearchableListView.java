package seng302.group2.scenes.control;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.workspace.skills.Skill;

/**
 * Created by btm38 on 3/08/15.
 */
public class SearchableListView<T> extends ListView<T> implements SearchableControl {
    private ListView<T> listView = new ListView<>();
    //private ListView listView = new ListView();

    public SearchableListView(ObservableList<T> listItems) {
        super();
        for (T i : listItems) {
            listView.getItems().add(i);
        }
    }

    @Override
    public boolean query(String query) {

        if (query.trim().isEmpty()) {

            return false;
        }
        boolean foundList = false;
        System.out.println(listView.getItems());
        for (T item : listView.getItems()) {
            if (item.toString().toLowerCase().contains(query.toLowerCase())) {
                foundList = true;
            }
        }

        if (foundList) {
            this.setStyle("-fx-border-color: " + SearchableControl.highlightColour + ";");

        }

        return foundList;

    }
}
