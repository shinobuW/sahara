package seng302.group2.scenes.information.skill;

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
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.skills.Skill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A class for displaying a tab used to edit skills.
 * Created by btm38 on 30/07/15.
 */
public class SkillEditTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();

    /**
     * Constructor for the SkillEditTab class. This constructor creates a JavaFX ScrollPane
     * which is populated with relevant controls and then shown.
     *
     * @param currentSkill The skill being edited
     */
    public SkillEditTab(Skill currentSkill) {
        this.setText("Edit Skill");
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
        CustomTextArea descriptionTextArea = new CustomTextArea("Skill Description:", 300);

        shortNameCustomField.setMaxWidth(275);
        descriptionTextArea.setMaxWidth(275);

        shortNameCustomField.setText(currentSkill.getShortName());
        descriptionTextArea.setText(currentSkill.getDescription());

        editPane.getChildren().add(shortNameCustomField);
        editPane.getChildren().add(descriptionTextArea);
        editPane.getChildren().add(buttons);

        btnSave.setOnAction((event) -> {
                boolean shortNameUnchanged = shortNameCustomField.getText().equals(
                        currentSkill.getShortName());
                boolean descriptionUnchanged = descriptionTextArea.getText().equals(
                        currentSkill.getDescription());

                if (shortNameUnchanged && descriptionUnchanged) {
                    // No changes
                    currentSkill.switchToInfoScene();
                    return;
                }

                boolean correctShortName = ShortNameValidator.validateShortName(shortNameCustomField,
                        currentSkill.getShortName());

                if (correctShortName) {
                    // Valid short name, make the edit
                    currentSkill.edit(shortNameCustomField.getText(),
                            descriptionTextArea.getText()
                    );

                    Collections.sort(Global.currentWorkspace.getSkills());
                    currentSkill.switchToInfoScene();
                    App.mainPane.refreshTree();
                }
                else {
                    event.consume();
                }

            });

        btnCancel.setOnAction((event) -> {
                currentSkill.switchToInfoScene();
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
