/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import seng302.group2.workspace.team.Team;

import java.util.Map;

import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * Class to create a pop up dialog for creating a team.
 *
 * @author crw73
 */
@SuppressWarnings("deprecation")
public class CreateTeamDialog {
    /**
     * Displays the Dialog box for creating a Team.
     */
    public static void show() {
        javafx.scene.control.Dialog<Map<String, String>> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("New Team");
        dialog.getDialogPane().setStyle(" -fx-max-width:600px; -fx-max-height: 500px; -fx-pref-width: 600px; "
                + "-fx-pref-height: 500px;");

        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);

        ButtonType btnTypeCreate = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnTypeCreate, ButtonType.CANCEL);

        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Team Description:");

        grid.getChildren().add(shortNameCustomField);
        grid.getChildren().add(descriptionTextArea);

        // Request focus on the username field by default.
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
                    //get user input
                    String shortName = shortNameCustomField.getText();
                    String description = descriptionTextArea.getText();

                    Team team = new Team(shortName, description);
                    Global.currentWorkspace.add(team);
                    App.mainPane.selectItem(team);
                    dialog.close();

                }
                return null;
            });

        dialog.setResizable(false);
        dialog.show();
    }
}
