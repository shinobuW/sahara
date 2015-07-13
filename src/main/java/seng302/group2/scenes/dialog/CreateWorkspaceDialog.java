package seng302.group2.scenes.dialog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.dialog.Dialog;
import seng302.group2.Global;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.workspace.workspace.Workspace;

import static seng302.group2.util.validation.NameValidator.validateName;
import static seng302.group2.util.validation.ShortNameValidator.validateShortNameNonUnique;

/**
 * Class to create a pop up dialog for creating a workspace.
 *
 * @author David Moseley drm127
 */
@SuppressWarnings("deprecation")
public class CreateWorkspaceDialog {
    /**
     * Displays the Dialog box for creating a workspace.
     */
    public static void show() {
        Dialog dialog = new Dialog(null, "New Workspace");
        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);

        Button btnCreate = new Button("Create");
        Button btnCancel = new Button("Cancel");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnCreate, btnCancel);

        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        RequiredField longNameCustomField = new RequiredField("Long Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Workspace Description:");

        grid.getChildren().add(shortNameCustomField);
        grid.getChildren().add(longNameCustomField);
        grid.getChildren().add(descriptionTextArea);
        grid.getChildren().add(buttons);

        btnCreate.setOnAction((event) -> {
                boolean correctShortName = validateShortNameNonUnique(shortNameCustomField, null);
                boolean correctLongName = validateName(longNameCustomField);

                String shortName = shortNameCustomField.getText();
                String longName = longNameCustomField.getText();
                String description = descriptionTextArea.getText();

                if (correctShortName && correctLongName) {
                    Workspace workspace;
                    workspace = new Workspace(shortName, longName, description);
                    Global.currentWorkspace = workspace;
                    MainScene.treeView.selectItem(Global.currentWorkspace);
                    dialog.hide();
                }
                else {
                    event.consume();
                }
            });

        btnCancel.setOnAction((event) -> {
                dialog.hide();
            });

        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(grid);
        dialog.show();
    }
}
