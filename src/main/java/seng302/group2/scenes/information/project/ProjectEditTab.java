package seng302.group2.scenes.information.project;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
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
 * Created by btm38 on 30/07/15.
 */
public class ProjectEditTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();

    /**
     * Gets the workspace edit information scene.
     *
     * @param currentProject The project to display the editable information of
     * @return The Workspace Edit information scene
     */
    public ProjectEditTab(Project currentProject) {
        this.setText("Edit Project");
        Pane editPane = new VBox(10);
        editPane.setBorder(null);
        editPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(editPane);
        this.setContent(wrapper);

        Button btnCancel = new Button("Cancel");
        Button btnSave = new Button("Done");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.TOP_LEFT);
        buttons.getChildren().addAll(btnSave, btnCancel);

        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        RequiredField longNameCustomField = new RequiredField("Long Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Project Description:", 300);

        shortNameCustomField.setMaxWidth(275);
        longNameCustomField.setMaxWidth(275);
        descriptionTextArea.setMaxWidth(275);

        shortNameCustomField.setText(currentProject.getShortName());
        longNameCustomField.setText(currentProject.getLongName());
        descriptionTextArea.setText(currentProject.getDescription());

        Button btnAdd = new Button("<-");
        Button btnDelete = new Button("->");

        VBox teamButtons = new VBox();
        teamButtons.spacingProperty().setValue(10);
        teamButtons.getChildren().add(btnAdd);
        teamButtons.getChildren().add(btnDelete);
        teamButtons.setAlignment(Pos.CENTER);

        editPane.getChildren().add(shortNameCustomField);
        editPane.getChildren().add(longNameCustomField);
        editPane.getChildren().add(descriptionTextArea);

        editPane.getChildren().add(buttons);

        btnSave.setOnAction((event) -> {
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
                    // One or more fields incorrectly validated, stay on the edit scene
                    event.consume();
                }
            });

        btnCancel.setOnAction((event) -> {
                currentProject.switchToInfoScene();
            });

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

