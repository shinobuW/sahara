package seng302.group2.scenes.dialog;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.workspace.project.Project;

import java.util.Map;

import static seng302.group2.util.validation.NameValidator.validateName;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

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
    static Boolean correctShortName = Boolean.FALSE;
    static Boolean correctLongName = Boolean.FALSE;

    public static void show() {
        correctShortName = false;
        correctLongName = false;
        javafx.scene.control.Dialog<Map<String, String>> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("New Project");
        dialog.getDialogPane().setStyle(" -fx-max-width:600px; -fx-max-height: 500px; -fx-pref-width: 600px; "
                + "-fx-pref-height: 500px;");

        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);

        ButtonType btnCreate = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnCreate, ButtonType.CANCEL);

        //Add grid of controls to dialog
        dialog.getDialogPane().setContent(grid);

        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        RequiredField longNameCustomField = new RequiredField("Long Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Project Description:");

        grid.getChildren().add(shortNameCustomField);
        grid.getChildren().add(longNameCustomField);
        grid.getChildren().add(descriptionTextArea);

        // Request focus on the username field by default.
        Platform.runLater(() -> shortNameCustomField.getTextField().requestFocus());

        Node createButton = dialog.getDialogPane().lookupButton(btnCreate);
        createButton.setDisable(true);

        shortNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctShortName = validateShortName(shortNameCustomField, null);
                createButton.setDisable(!(correctShortName && correctLongName));
            });

        longNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctLongName = validateName(longNameCustomField);
                createButton.setDisable(!(correctShortName && correctLongName));
            });

        dialog.setResultConverter(b -> {
                if (b == btnCreate) {
                    String shortName = shortNameCustomField.getText();
                    String longName = longNameCustomField.getText();
                    String description = descriptionTextArea.getText();

                    if (correctShortName && correctLongName) {
                        Project project = new Project(shortName, longName, description);
                        Global.currentWorkspace.add(project);
                        App.mainPane.selectItem(project);
                        dialog.close();
                    }
                }
                return null;
            });

        //Validation


//        btnCreate.setOnAction((event) -> {
//                String shortName = shortNameCustomField.getText();
//                String longName = longNameCustomField.getText();
//                String description = descriptionTextArea.getText();
//
//                if (correctShortName && correctLongName) {
//                    Project project = new Project(shortName, longName, description);
//                    Global.currentWorkspace.add(project);
//                    App.mainPane.selectItem(project);
//                    dialog.hide();
//                }
//                else {
//                    event.consume();
//                }
//            });
//

        dialog.setResizable(false);
        dialog.show();
    }
}
