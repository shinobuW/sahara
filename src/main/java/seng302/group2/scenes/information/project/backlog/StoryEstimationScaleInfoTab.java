package seng302.group2.scenes.information.project.backlog;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import seng302.group2.Global;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * A tab for displaying the values of the various story estimation scales available in the workspace.
 * Created by drm127 on 13/07/15.
 */
public class StoryEstimationScaleInfoTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();

    /**
     * Constructor for the StoryEstimationInfoTab class
     */
    public StoryEstimationScaleInfoTab() {
        this.setText("Available Scales");
        Pane scaleInfoPane = new VBox(10);
        scaleInfoPane.setBorder(null);
        scaleInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(scaleInfoPane);
        this.setContent(wrapper);

        Label title = new TitleLabel("Story Estimation Scales in " + Global.currentWorkspace.getShortName());
        Label instructions = new Label("Please select an estimation scale to view its values.");

        ObservableList<String> scaleOptions = observableArrayList();
        ComboBox<String> scaleComboBox = new ComboBox<>(scaleOptions);
        scaleComboBox.setStyle("-fx-pref-width: 135;");
        Label scaleComboLabel = new Label("Estimation Scales: ");
        HBox scaleComboHBox = new HBox(scaleComboLabel);
        HBox.setHgrow(scaleComboHBox, Priority.ALWAYS);
        scaleComboHBox.setAlignment(Pos.CENTER_LEFT);
        scaleComboHBox.getChildren().add(scaleComboBox);

        for (String scaleName : Global.currentWorkspace.getEstimationScales().getEstimationScaleDict().keySet()) {
            scaleOptions.add(scaleName);
        }

        ObservableList<String> scaleValues = observableArrayList();
        ListView<String> scaleValuesList = new ListView<>(scaleValues);
        scaleValuesList.setPrefHeight(300);

        scaleComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                scaleValues.clear();
                ArrayList<String> valueList = Global.currentWorkspace.getEstimationScales().
                        getEstimationScaleDict().get(newValue);

                for (String value : valueList) {
                    scaleValues.add(value);
                }
            });

        scaleInfoPane.getChildren().add(title);
        scaleInfoPane.getChildren().add(instructions);
        scaleInfoPane.getChildren().add(scaleComboHBox);
        scaleInfoPane.getChildren().add(scaleValuesList);
    }

    /**
     * Gets all the searchable controls on this tab.
     * @return a collection of all the searchable controls on this tab.
     */
    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }
}
