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
import seng302.group2.util.validation.NameValidator;
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.project.Project;

/**
 * Class to create a pop up dialog for creating a workspace.
 *
 * @author Jordane Lew jml168
 */
@SuppressWarnings("deprecation")
public class CreateProjectDialog {
    /**
     * Displays the Dialog box for creating a workspace.
     */
    public static void show() {
        Dialog dialog = new Dialog(null, "New Project");
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
        CustomTextArea descriptionTextArea = new CustomTextArea("Project Description:");

        grid.getChildren().add(shortNameCustomField);
        grid.getChildren().add(longNameCustomField);
        grid.getChildren().add(descriptionTextArea);
        grid.getChildren().add(buttons);

        btnCreate.setOnAction((event) -> {
                boolean correctShortName = ShortNameValidator.validateShortName(
                        shortNameCustomField, null);
                boolean correctLongName = NameValidator.validateName(longNameCustomField);

                String shortName = shortNameCustomField.getText();
                String longName = longNameCustomField.getText();
                String description = descriptionTextArea.getText();

                if (correctShortName && correctLongName) {
                    Project project = new Project(shortName, longName, description);
                    Global.currentWorkspace.add(project);
                    MainScene.treeView.selectItem(project);
                    dialog.hide();
                }
                else {
                    event.consume();
                }
            });

        btnCancel.setOnAction((event) ->
                dialog.hide());

        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(grid);
        dialog.show();
    }
}
