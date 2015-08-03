package seng302.group2.scenes.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.workspace.SaharaItem;
import seng302.group2.workspace.skills.Skill;

import javafx.scene.control.ListView;
import java.util.ArrayList;

/**
 * Created by btm38 on 3/08/15.
 */
public class SearchableListView<T> extends ListView<T> implements SearchableControl {
    private ListView<T> listView = new ListView<>();
    //private ListView listView = new ListView();

    public SearchableListView(ObservableList<Skill> listItems) {
        super();
        for (Skill i : listItems) {
            listView.getItems().add((T) i);
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
            System.out.println("IT FUKN WORKS KINDA");
            String css = this.getClass().getResource("/styles/chart.css").toExternalForm();
            listView.getStylesheets().add(css);

        }

        return foundList;

    }
}
