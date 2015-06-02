package seng302.group2.scenes.information.workspace;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.workspace.Workspace;

import static seng302.group2.scenes.MainScene.informationPane;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * A class for displaying the workspace edit scene.
 * Created by btm38 on 7/04/15.
 */
public class WorkspaceEditScene
{
    /**
     * Gets the workspace edit information scene.
     * @param currentWorkspace The workspace to get the editable information scene for
     * @return The Workspace Edit information scene
     */
    public static ScrollPane getWorkspaceEditScene(Workspace currentWorkspace)
    {
        informationPane = new VBox(10);
        /*informationPane.setAlignment(Pos.TOP_LEFT);
        informationPane.setHgap(10);
        informationPane.setVgap(10);*/
        informationPane.setPadding(new Insets(25,25,25,25));

        Button btnCancel = new Button("Cancel");
        Button btnSave = new Button("Done");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnSave, btnCancel);

        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        RequiredField longNameCustomField = new RequiredField("Long Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Workspace Description:", 300);
        
        shortNameCustomField.setText(currentWorkspace.getShortName());
        longNameCustomField.setText(currentWorkspace.getLongName());
        descriptionTextArea.setText(currentWorkspace.getDescription());

        informationPane.getChildren().add(shortNameCustomField);
        informationPane.getChildren().add(longNameCustomField);
        informationPane.getChildren().add(descriptionTextArea);
        informationPane.getChildren().add(buttons);


        btnCancel.setOnAction((event) ->
            {
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.WORKSPACE, currentWorkspace);
            });

        btnSave.setOnAction((event) ->
            {

                boolean shortNameUnchanged = shortNameCustomField.getText().equals(
                        currentWorkspace.getShortName());
                boolean longNameUnchanged = longNameCustomField.getText().equals(
                        currentWorkspace.getLongName());
                boolean descriptionUnchanged = descriptionTextArea.getText().equals(
                        currentWorkspace.getDescription());

                if (shortNameUnchanged && longNameUnchanged && descriptionUnchanged)
                {
                    // No fields have been changed
                    SceneSwitcher.changeScene(SceneSwitcher.ContentScene.WORKSPACE,
                            currentWorkspace);
                    return;
                }
                    // The short name is the same or valid
                boolean correctShortName = validateShortName(shortNameCustomField,
                        currentWorkspace.getShortName());
                boolean correctLongName = validateShortName(longNameCustomField,
                        currentWorkspace.getLongName());
                if (correctShortName && correctLongName)
                {
                    currentWorkspace.edit(shortNameCustomField.getText(),
                        longNameCustomField.getText(),
                        descriptionTextArea.getText()
                    );

                    SceneSwitcher.changeScene(SceneSwitcher.ContentScene.WORKSPACE,
                            currentWorkspace);
                    MainScene.treeView.refresh();
                }
                else
                {
                    // One or more fields incorrectly validated, stay on the edit scene
                    event.consume();
                }

            });

        ScrollPane wrapper = new ScrollPane(informationPane);
        wrapper.setStyle("-fx-background-color:transparent;");
        return wrapper;
    }
}
