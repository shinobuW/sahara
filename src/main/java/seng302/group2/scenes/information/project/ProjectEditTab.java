package seng302.group2.scenes.information.project;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.util.validation.NameValidator;
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.project.Project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A class for displaying a tab showing data on all the projects in the workspace.
 * Created by btm38 on 30/07/15.
 */
public class ProjectEditTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();

    /**
     * Gets the workspace edit information scene.
     *
     * @param currentProject The project to display the editable information of
     */
    public ProjectEditTab(Project currentProject) {
        // Tab settings
        this.setText("Edit Project");
        Pane editPane = new VBox(10);
        editPane.setBorder(null);
        editPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(editPane);
        this.setContent(wrapper);

        // Create controls
        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        RequiredField longNameCustomField = new RequiredField("Long Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Project Description:", 300);

        shortNameCustomField.setMaxWidth(275);
        longNameCustomField.setMaxWidth(275);
        descriptionTextArea.setMaxWidth(275);

        Button btnCancel = new Button("Cancel");
        Button btnDone = new Button("Done");
        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.TOP_LEFT);
        buttons.getChildren().addAll(btnDone, btnCancel);

        // Set values
        shortNameCustomField.setText(currentProject.getShortName());
        longNameCustomField.setText(currentProject.getLongName());
        descriptionTextArea.setText(currentProject.getDescription());

        // Events
        btnDone.setOnAction((event) -> {
                boolean shortNameUnchanged = shortNameCustomField.getText().equals(
                        currentProject.getShortName());
                boolean longNameUnchanged = longNameCustomField.getText().equals(
                        currentProject.getLongName());
                boolean descriptionUnchanged = descriptionTextArea.getText().equals(
                        currentProject.getDescription());

                // If no fields have been changed
                if (shortNameUnchanged && longNameUnchanged && descriptionUnchanged) {
                    currentProject.switchToInfoScene();
                    return;
                }

                boolean correctShortName = ShortNameValidator.validateShortName(shortNameCustomField,
                        currentProject.getShortName());
                boolean correctLongName = NameValidator.validateName(longNameCustomField);

                if (correctShortName && correctLongName) {
                    currentProject.edit(shortNameCustomField.getText(),
                            longNameCustomField.getText(), descriptionTextArea.getText(),
                            FXCollections.observableArrayList()
                    );

                    Collections.sort(Global.currentWorkspace.getProjects());
                    currentProject.switchToInfoScene();
                    App.mainPane.refreshTree();
                }
                else {
                    event.consume();
                }
            });

        btnCancel.setOnAction((event) -> {
                currentProject.switchToInfoScene();
            });

        // Add items to pane & search collection
        editPane.getChildren().addAll(
                shortNameCustomField,
                longNameCustomField,
                descriptionTextArea,
                buttons
        );

        Collections.addAll(searchControls,
                shortNameCustomField,
                longNameCustomField,
                descriptionTextArea
        );

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

