package seng302.group2.scenes.dialog;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.controlsfx.dialog.Dialog;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.workspace.backlog.Backlog;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.team.Team;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.util.validation.NameValidator.validateName;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * Class to create a pop up dialog for creating a backlog
 * Created by cvs20 on 19/05/15.
 */
public class CreateBacklogDialog
{
    /**
     * Displays the Dialog box for creating a story.
     */
    public static void show()
    {
        // Initialise Dialog and GridPane
        Dialog dialog = new Dialog(null, "New Backlog");
        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);

        // Initialise Input fields
        Button btnCreate = new Button("Create");
        Button btnCancel = new Button("Cancel");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(btnCreate, btnCancel);

        // Add elements to grid
        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        RequiredField longNameCustomField = new RequiredField("Long Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Description:");
        CustomComboBox projectComboBox = new CustomComboBox("Project:", true);
        //CustomComboBox productOwnerComboBox = new CustomComboBox("Product Owner", false);

        ObservableList<Person> productOwnerOptions = observableArrayList();
        ComboBox<Person> productOwnerComboBox = new ComboBox<>(productOwnerOptions);
        productOwnerComboBox.setStyle("-fx-pref-width: 135;");
        Label poComboLabel = new Label("Product Owner: ");
        HBox poComboHBox = new HBox(poComboLabel);

        HBox poCombo = new HBox();
        poCombo.getChildren().addAll(poComboHBox, productOwnerComboBox);
        poCombo.setHgrow(poComboHBox, Priority.ALWAYS);


        if (Global.currentWorkspace.getProjects().size() > 0)
        {
            String firstItem = Global.currentWorkspace.getProjects().get(0).toString();
            projectComboBox.setValue(firstItem);
        }

        for (TreeViewItem project : Global.currentWorkspace.getProjects())
        {
            projectComboBox.addToComboBox(project.toString());
        }

        for (Team team : Global.currentWorkspace.getTeams())
        {
            if (team.getProductOwner() != null)
            {
                productOwnerOptions.add(team.getProductOwner());
            }
        }

        grid.getChildren().addAll(shortNameCustomField, longNameCustomField,
                projectComboBox, poCombo, descriptionTextArea, buttons);

        // Create button event
        btnCreate.setOnAction((event) ->
            {
                boolean correctShortName = validateShortName(shortNameCustomField, null);
                boolean correctLongName = validateName(longNameCustomField);
                boolean correctProductOwnerCombo = true;
                boolean correctProjectCombo = true;

//                if (productOwnerComboBox.getValue() == null)
//                {
//                    productOwnerComboBox.showErrorField("You must select a product owner.");
//                    correctProductOwnerCombo = false;
//                }

                if (correctShortName && correctLongName && correctProductOwnerCombo)
                {
                    //get user input
                    String shortName = shortNameCustomField.getText();
                    String longName = longNameCustomField.getText();
                    String description = descriptionTextArea.getText();

                    Project project = new Project();
                    for (TreeViewItem item : Global.currentWorkspace.getProjects())
                    {
                        if (item.toString().equals(projectComboBox.getValue()))
                        {
                            project = (Project)item;
                        }
                    }

                    Person productOwner = null;
                    if (productOwnerComboBox.getValue() != null)
                    {
                        productOwner = productOwnerComboBox.getValue();
                    }

                    Backlog backlog = new Backlog(shortName, longName, description, productOwner,
                            project);
                    project.add(backlog);
                    dialog.hide();
                }
                else
                {
                    event.consume();
                }
            });

        // Cancel button event
        btnCancel.setOnAction((event) ->
                dialog.hide());

        dialog.setResizable(false);
        dialog.setIconifiable(false);
        dialog.setContent(grid);
        dialog.show();
    }
}
