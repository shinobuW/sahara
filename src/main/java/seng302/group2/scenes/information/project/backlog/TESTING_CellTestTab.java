package seng302.group2.scenes.information.project.backlog;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.story.Story;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * The information tab for a backlog
 * Created by cvs20 on 19/05/15.
 */
public class TESTING_CellTestTab extends Tab {

    /**
     * Constructor for the Backlog Info tab
     *
     * @param currentBacklog The currently selected backlog
     */
    public TESTING_CellTestTab(Backlog currentBacklog) {
        final Stage stage;

        this.setText("TEST");
        Pane basicInfoPane = new VBox(10);

        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        Label title = new TitleLabel(currentBacklog.getLongName());
        basicInfoPane.getChildren().add(title);

        basicInfoPane.getChildren().add(new Label("Stories: "));


        /**
         * The magic is here
         */
        ListView<Story> storiesList = new ListView<>();

        storiesList.setItems(currentBacklog.getStories());

        storiesList.setCellFactory(list -> new TESTING_CellFactory());

        basicInfoPane.getChildren().add(storiesList);

    }
}
