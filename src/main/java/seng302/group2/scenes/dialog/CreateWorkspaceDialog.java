package seng302.group2.scenes.dialog;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.dialog.Dialog;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.workspace.workspace.Workspace;

import java.util.Map;

import static seng302.group2.util.validation.NameValidator.validateName;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;
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
        javafx.scene.control.Dialog<Map<String, String>> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("New Workspace");
        dialog.getDialogPane().setStyle(" -fx-max-width:600px; -fx-max-height: 500px; -fx-pref-width: 600px; "
                + "-fx-pref-height: 500px;");

        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);

        ButtonType btnTypeCreate = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnTypeCreate, ButtonType.CANCEL);

        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        RequiredField longNameCustomField = new RequiredField("Long Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Workspace Description:");

        grid.getChildren().add(shortNameCustomField);
        grid.getChildren().add(longNameCustomField);
        grid.getChildren().add(descriptionTextArea);

        // Request focus on the short name field by default.
        Platform.runLater(() -> shortNameCustomField.getTextField().requestFocus());

        //Add grid of controls to dialog
        dialog.getDialogPane().setContent(grid);

        Node createButton = dialog.getDialogPane().lookupButton(btnTypeCreate);
        createButton.setDisable(true);

        shortNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
            createButton.setDisable(!validateShortName(shortNameCustomField, null));
        });



        dialog.setResultConverter(b -> {
                if (b == btnTypeCreate) {
                    String shortName = shortNameCustomField.getText();
                    String longName = longNameCustomField.getText();
                    String description = descriptionTextArea.getText();

                    Workspace workspace = new Workspace(shortName, longName, description);
                    Global.currentWorkspace = workspace;
                    App.mainPane.selectItem(Global.currentWorkspace);
                    App.mainPane.refreshAll();
                    dialog.close();
                }
                return null;
            });

        dialog.setResizable(false);
        dialog.show();

        }
    }
