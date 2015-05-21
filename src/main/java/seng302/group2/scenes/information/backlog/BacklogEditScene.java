package seng302.group2.scenes.information.backlog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.workspace.backlog.Backlog;

import java.util.Collections;

import static seng302.group2.scenes.MainScene.informationPane;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * Created by cvs20 on 19/05/15.
 */
public class BacklogEditScene
{
    /**
     * Gets the Backlog Edit information scene.
     * @param currentBacklog The Backlog to show the information of
     * @return The Backlog Edit information display
     */
    public static ScrollPane getBacklogEditScene(Backlog currentBacklog)
    {
        informationPane = new VBox(10);
        informationPane.setPadding(new Insets(25,25,25,25));

        Button btnCancel = new Button("Cancel");
        Button btnSave = new Button("Done");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.TOP_LEFT);
        buttons.getChildren().addAll(btnSave, btnCancel);

        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        CustomTextArea longNameCustomField = new CustomTextArea("Long Name:");
        CustomComboBox productOwnerComboBox = new CustomComboBox("Product Owner:", true);
        CustomTextArea descriptionTextArea = new CustomTextArea("Backlog Description:", 300);

        shortNameCustomField.setMaxWidth(450);
        longNameCustomField.setMaxWidth(450);
        productOwnerComboBox.setMaxWidth(450);
        descriptionTextArea.setMaxWidth(450);

        shortNameCustomField.setText(currentBacklog.getShortName());
        longNameCustomField.setText(currentBacklog.getLongName());
        descriptionTextArea.setText(currentBacklog.getDescription());
        productOwnerComboBox.addToComboBox(currentBacklog.getProductOwner().toString());


        informationPane.getChildren().add(shortNameCustomField);
        informationPane.getChildren().add(longNameCustomField);
        informationPane.getChildren().add(productOwnerComboBox);
        informationPane.getChildren().add(descriptionTextArea);
        informationPane.getChildren().add(buttons);

        btnSave.setOnAction((event) ->
            {
                boolean shortNameUnchanged = shortNameCustomField.getText().equals(
                        currentBacklog.getShortName());
                boolean longNameUnchanged = longNameCustomField.getText().equals(
                        currentBacklog.getLongName());
                boolean descriptionUnchanged = descriptionTextArea.getText().equals(
                        currentBacklog.getDescription());

                if (shortNameUnchanged && longNameUnchanged && descriptionUnchanged)
                {
                    // No changes
                    SceneSwitcher.changeScene(SceneSwitcher.ContentScene.BACKLOG, currentBacklog);
                    return;
                }

                boolean correctShortName = validateShortName(
                        shortNameCustomField, currentBacklog.getShortName());
                if (correctShortName)
                {
                    currentBacklog.edit(shortNameCustomField.getText(),
                        longNameCustomField.getText(),
                        descriptionTextArea.getText(),
                        null,
                        currentBacklog.getProject()
                    );

                    Collections.sort(currentBacklog.getProject().getBacklogs());
                    SceneSwitcher.changeScene(SceneSwitcher.ContentScene.BACKLOG, currentBacklog);
                    MainScene.treeView.refresh();
                }

                else
                {
                    event.consume();
                }

            });

        btnCancel.setOnAction((event) ->
            {
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.BACKLOG, currentBacklog);
            });


        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;
    }
}
