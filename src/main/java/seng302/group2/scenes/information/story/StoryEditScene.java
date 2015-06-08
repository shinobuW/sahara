package seng302.group2.scenes.information.story;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.CustomTextField;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.sceneswitch.SceneSwitcher;
import seng302.group2.workspace.story.Story;

import static seng302.group2.scenes.MainScene.informationPane;
import static seng302.group2.util.validation.NumberFieldValidator.validateNumberField;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * A class for displaying the Story edit scene.
 * Created by drm127 on 17/05/15.
 */
public class StoryEditScene {
    /**
     * Gets the Story Edit information scene.
     *
     * @param currentStory The story to show the information of
     * @return The story Edit information display
     */
    public static ScrollPane getStoryEditScene(Story currentStory) {
        informationPane = new VBox(10);
        /*informationPane.setAlignment(Pos.TOP_LEFT);
        informationPane.setHgap(10);
        informationPane.setVgap(10);*/
        informationPane.setPadding(new Insets(25, 25, 25, 25));

        Button btnCancel = new Button("Cancel");
        Button btnSave = new Button("Done");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.TOP_LEFT);
        buttons.getChildren().addAll(btnSave, btnCancel);

        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        CustomTextField longNameTextField = new CustomTextField("Long Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Story Description:", 300);
        RequiredField priorityNumberField = new RequiredField("Story Priority:");

        shortNameCustomField.setMaxWidth(275);
        descriptionTextArea.setMaxWidth(275);

        shortNameCustomField.setText(currentStory.getShortName());
        longNameTextField.setText(currentStory.getLongName());
        descriptionTextArea.setText(currentStory.getDescription());
        priorityNumberField.setText(currentStory.getPriority().toString());

        informationPane.getChildren().add(shortNameCustomField);
        informationPane.getChildren().add(longNameTextField);
        informationPane.getChildren().add(descriptionTextArea);
        informationPane.getChildren().add(priorityNumberField);
        informationPane.getChildren().add(buttons);


        btnCancel.setOnAction((event) -> {
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.STORY, currentStory);
            });

        btnSave.setOnAction((event) -> {
                boolean shortNameUnchanged = shortNameCustomField.getText().equals(
                        currentStory.getShortName());
                boolean longNameUnchanged = longNameTextField.getText().equals(
                        currentStory.getLongName());
                boolean descriptionUnchanged = descriptionTextArea.getText().equals(
                        currentStory.getDescription());
                boolean priorityUnchanged = priorityNumberField.getText().equals(
                        currentStory.getPriority());

                if (shortNameUnchanged && longNameUnchanged && descriptionUnchanged
                        && priorityUnchanged) {
                    // No changes
                    SceneSwitcher.changeScene(SceneSwitcher.ContentScene.STORY, currentStory);
                    return;
                }

                boolean correctShortName = validateShortName(shortNameCustomField,
                        currentStory.getShortName());
                boolean correctPriority = validateNumberField(priorityNumberField);

                if (correctShortName && correctPriority) {
                    // Valid short name, make the edit
                    currentStory.edit(shortNameCustomField.getText(),
                            longNameTextField.getText(),
                            descriptionTextArea.getText(),
                            currentStory.getProject(),
                            Integer.parseInt(priorityNumberField.getText()),
                            currentStory.getBacklog()
                    );

                    SceneSwitcher.changeScene(SceneSwitcher.ContentScene.STORY, currentStory);
                    MainScene.treeView.refresh();
                }
                else {
                    event.consume();
                }

            });

        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;
    }
}
