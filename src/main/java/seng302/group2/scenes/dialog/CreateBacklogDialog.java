package seng302.group2.scenes.dialog;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.team.Team;

import java.util.Map;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.util.validation.NameValidator.validateName;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * Class to create a pop up dialog for creating a backlog
 * Created by cvs20 on 19/05/15.
 */
public class CreateBacklogDialog extends Dialog<Map<String, String>> {
    Boolean correctShortName = Boolean.FALSE;
    Boolean correctLongName = Boolean.FALSE;

    ComboBox<Project> projComboBox;
    ComboBox<Person> productOwnerComboBox;
    ComboBox<String> scaleComboBox;



    /**
     * Displays the Dialog box for creating a story.
     */
    public CreateBacklogDialog() {
        correctShortName = Boolean.FALSE;
        correctLongName = Boolean.FALSE;

        this.setTitle("New Backlog");
        this.getDialogPane().setStyle(" -fx-max-width:600px; -fx-max-height: 350px; -fx-pref-width: 600px; "
                + "-fx-pref-height: 350px;");

        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);

        ButtonType btnTypeCreate = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(btnTypeCreate, ButtonType.CANCEL);

        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        RequiredField longNameCustomField = new RequiredField("Long Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Description:");

        // Create project combo box.
        ObservableList<Project> projectOptions = observableArrayList();
        projComboBox = new ComboBox<>(projectOptions);
        projComboBox.setStyle("-fx-pref-width: 135;");
        Label projComboLabel = new Label("Project:");
        HBox projComboHBox = new HBox(projComboLabel);

        Label aster1 = new Label(" * ");
        aster1.setTextFill(Color.web("#ff0000"));
        projComboHBox.getChildren().add(aster1);

        VBox projVBox = new VBox();
        HBox projCombo = new HBox();
        projCombo.getChildren().addAll(projComboHBox, projComboBox);
        HBox.setHgrow(projComboHBox, Priority.ALWAYS);
        projVBox.getChildren().add(projCombo);
        Label noProjSelectedLabel = new Label("* Please select a project");
        noProjSelectedLabel.setTextFill(Color.web("#ff0000"));

        // Create PO combo box
        ObservableList<Person> productOwnerOptions = observableArrayList();
        productOwnerComboBox = new ComboBox<>(productOwnerOptions);
        productOwnerComboBox.setStyle("-fx-pref-width: 135;");
        Label poComboLabel = new Label("Product Owner:");
        HBox poComboHBox = new HBox(poComboLabel);

        Label aster2 = new Label(" * ");
        aster2.setTextFill(Color.web("#ff0000"));
        poComboHBox.getChildren().add(aster2);

        VBox poVBox = new VBox();
        HBox poCombo = new HBox();
        poCombo.getChildren().addAll(poComboHBox, productOwnerComboBox);
        HBox.setHgrow(poComboHBox, Priority.ALWAYS);
        poVBox.getChildren().add(poCombo);
        Label noPoSelectedLabel = new Label("* Please select a product owner");
        noPoSelectedLabel.setTextFill(Color.web("#ff0000"));


        //Create Scales combo box
        ObservableList<String> scaleOptions = observableArrayList();
        scaleComboBox = new ComboBox<>(scaleOptions);
        scaleComboBox.setStyle("-fx-pref-width: 135;");
        Label scaleComboLabel = new Label("Estimation Scale:");
        HBox scaleComboHBox = new HBox(scaleComboLabel);

        Label aster3 = new Label(" * ");
        aster3.setTextFill(Color.web("#ff0000"));
        scaleComboHBox.getChildren().add(aster3);

        VBox scaleVBox = new VBox();
        HBox scaleCombo = new HBox();
        scaleCombo.getChildren().addAll(scaleComboHBox, scaleComboBox);
        HBox.setHgrow(scaleComboHBox, Priority.ALWAYS);
        scaleVBox.getChildren().add(scaleCombo);
        Label noScaleSelectedLabel = new Label("* Please select an estimation scale");
        noScaleSelectedLabel.setTextFill(Color.web("#ff0000"));

        for (Project project : Global.currentWorkspace.getProjects()) {
            projectOptions.add(project);
        }

        for (String scaleName : Global.currentWorkspace.getEstimationScales().getEstimationScaleDict().keySet()) {
            scaleOptions.add(scaleName);
        }

        for (Team team : Global.currentWorkspace.getTeams()) {
            if (team.getProductOwner() != null) {
                productOwnerOptions.add(team.getProductOwner());
            }
        }

        grid.getChildren().addAll(shortNameCustomField, longNameCustomField,
                projVBox, poVBox, scaleVBox, descriptionTextArea);

        // Request focus on the short name field by default.
        Platform.runLater(() -> shortNameCustomField.getTextField().requestFocus());

        //Add grid of controls to dialog
        this.getDialogPane().setContent(grid);

        Node createButton = this.getDialogPane().lookupButton(btnTypeCreate);
        createButton.setDisable(true);

        // Validation.
        shortNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctShortName = validateShortName(shortNameCustomField, null);
                createButton.setDisable(!(correctShortName && correctLongName
                        && projectSelected() & poSelected() & scaleSelected()));
            });

        longNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newvalue) -> {
                correctLongName = validateName(longNameCustomField);
                createButton.setDisable(!(correctShortName && correctLongName
                        && projectSelected() & poSelected() & scaleSelected()));
            });

        projComboBox.valueProperty().addListener(new ChangeListener<Project>() {
            @Override
            public void changed(ObservableValue<? extends Project> observable, Project oldValue, Project newValue) {
                createButton.setDisable(!(correctShortName && correctLongName
                        && projectSelected() && poSelected() && scaleSelected()));
            }
            });

        productOwnerComboBox.valueProperty().addListener(new ChangeListener<Person>() {
            @Override
            public void changed(ObservableValue<? extends Person> observable, Person oldValue, Person newValue) {
                createButton.setDisable(!(correctShortName && correctLongName
                        && projectSelected() && poSelected() && scaleSelected()));
            }
            });

        scaleComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                createButton.setDisable(!(correctShortName && correctLongName
                        && projectSelected() && poSelected() && scaleSelected()));
            }
            });

        // Create button event
        this.setResultConverter(b -> {
                if (b == btnTypeCreate) {
                    String shortName = shortNameCustomField.getText();
                    String longName = longNameCustomField.getText();
                    String description = descriptionTextArea.getText();

                    Project project = projComboBox.getValue();
                    Person productOwner = productOwnerComboBox.getValue();
                    String scale = scaleComboBox.getValue();

                    Backlog backlog = new Backlog(shortName, longName, description, productOwner,
                            project, scale);

                    project.add(backlog);
                    App.mainPane.selectItem(backlog);
                    this.close();

                }
                return null;
            });

        this.setResizable(false);
        this.show();
    }


    /**
     * Shows a backlog creation dialog. A default project is selected in the project combo box.
     * @param defaultProject The project to be used as the default project.
     */
    public CreateBacklogDialog(Project defaultProject) {
        correctShortName = Boolean.FALSE;
        correctLongName = Boolean.FALSE;

        this.setTitle("New Backlog");
        this.getDialogPane().setStyle(" -fx-max-width:600px; -fx-max-height: 350px; -fx-pref-width: 600px; "
                + "-fx-pref-height: 350px;");

        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);

        ButtonType btnTypeCreate = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(btnTypeCreate, ButtonType.CANCEL);

        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        RequiredField longNameCustomField = new RequiredField("Long Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Description:");

        // Create project combo box.
        ObservableList<Project> projectOptions = observableArrayList();
        projComboBox = new ComboBox<>(projectOptions);
        projComboBox.setStyle("-fx-pref-width: 135;");
        Label projComboLabel = new Label("Project:");
        HBox projComboHBox = new HBox(projComboLabel);

        Label aster1 = new Label(" * ");
        aster1.setTextFill(Color.web("#ff0000"));
        projComboHBox.getChildren().add(aster1);

        VBox projVBox = new VBox();
        HBox projCombo = new HBox();
        projCombo.getChildren().addAll(projComboHBox, projComboBox);
        HBox.setHgrow(projComboHBox, Priority.ALWAYS);
        projVBox.getChildren().add(projCombo);
        Label noProjSelectedLabel = new Label("* Please select a project");
        noProjSelectedLabel.setTextFill(Color.web("#ff0000"));

        // Create PO combo box
        ObservableList<Person> productOwnerOptions = observableArrayList();
        productOwnerComboBox = new ComboBox<>(productOwnerOptions);
        productOwnerComboBox.setStyle("-fx-pref-width: 135;");
        Label poComboLabel = new Label("Product Owner:");
        HBox poComboHBox = new HBox(poComboLabel);

        Label aster2 = new Label(" * ");
        aster2.setTextFill(Color.web("#ff0000"));
        poComboHBox.getChildren().add(aster2);

        VBox poVBox = new VBox();
        HBox poCombo = new HBox();
        poCombo.getChildren().addAll(poComboHBox, productOwnerComboBox);
        HBox.setHgrow(poComboHBox, Priority.ALWAYS);
        poVBox.getChildren().add(poCombo);
        Label noPoSelectedLabel = new Label("* Please select a product owner");
        noPoSelectedLabel.setTextFill(Color.web("#ff0000"));


        //Create Scales combo box
        ObservableList<String> scaleOptions = observableArrayList();
        scaleComboBox = new ComboBox<>(scaleOptions);
        scaleComboBox.setStyle("-fx-pref-width: 135;");
        Label scaleComboLabel = new Label("Estimation Scale:");
        HBox scaleComboHBox = new HBox(scaleComboLabel);

        Label aster3 = new Label(" * ");
        aster3.setTextFill(Color.web("#ff0000"));
        scaleComboHBox.getChildren().add(aster3);

        VBox scaleVBox = new VBox();
        HBox scaleCombo = new HBox();
        scaleCombo.getChildren().addAll(scaleComboHBox, scaleComboBox);
        HBox.setHgrow(scaleComboHBox, Priority.ALWAYS);
        scaleVBox.getChildren().add(scaleCombo);
        Label noScaleSelectedLabel = new Label("* Please select an estimation scale");
        noScaleSelectedLabel.setTextFill(Color.web("#ff0000"));

        for (Project project : Global.currentWorkspace.getProjects()) {
            projectOptions.add(project);
        }
        projComboBox.setValue(defaultProject);

        for (String scaleName : Global.currentWorkspace.getEstimationScales().getEstimationScaleDict().keySet()) {
            scaleOptions.add(scaleName);
        }

        for (Team team : Global.currentWorkspace.getTeams()) {
            if (team.getProductOwner() != null) {
                productOwnerOptions.add(team.getProductOwner());
            }
        }

        grid.getChildren().addAll(shortNameCustomField, longNameCustomField,
                projVBox, poVBox, scaleVBox, descriptionTextArea);

        // Request focus on the short name field by default.
        Platform.runLater(() -> shortNameCustomField.getTextField().requestFocus());

        //Add grid of controls to dialog
        this.getDialogPane().setContent(grid);

        Node createButton = this.getDialogPane().lookupButton(btnTypeCreate);
        createButton.setDisable(true);

        // Validation.
        shortNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctShortName = validateShortName(shortNameCustomField, null);
                createButton.setDisable(!(correctShortName && correctLongName
                        && projectSelected() & poSelected() & scaleSelected()));
            });

        longNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newvalue) -> {
                correctLongName = validateName(longNameCustomField);
                createButton.setDisable(!(correctShortName && correctLongName
                        && projectSelected() & poSelected() & scaleSelected()));
            });

        projComboBox.valueProperty().addListener(new ChangeListener<Project>() {
            @Override
            public void changed(ObservableValue<? extends Project> observable, Project oldValue, Project newValue) {
                createButton.setDisable(!(correctShortName && correctLongName
                        && projectSelected() && poSelected() && scaleSelected()));
            }
            });

        productOwnerComboBox.valueProperty().addListener(new ChangeListener<Person>() {
            @Override
            public void changed(ObservableValue<? extends Person> observable, Person oldValue, Person newValue) {
                createButton.setDisable(!(correctShortName && correctLongName
                        && projectSelected() && poSelected() && scaleSelected()));
            }
            });

        scaleComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                createButton.setDisable(!(correctShortName && correctLongName
                        && projectSelected() && poSelected() && scaleSelected()));
            }
            });

        // Create button event
        this.setResultConverter(b -> {
                if (b == btnTypeCreate) {
                    String shortName = shortNameCustomField.getText();
                    String longName = longNameCustomField.getText();
                    String description = descriptionTextArea.getText();

                    Project project = projComboBox.getValue();
                    Person productOwner = productOwnerComboBox.getValue();
                    String scale = scaleComboBox.getValue();

                    Backlog backlog = new Backlog(shortName, longName, description, productOwner,
                            project, scale);

                    project.add(backlog);
                    App.mainPane.selectItem(backlog);
                    this.close();

                }
                return null;
            });

        this.setResizable(false);
        this.show();
    }

    private Boolean projectSelected() {
        return !(projComboBox.getValue() == null);
    }

    private Boolean poSelected() {
        return !(productOwnerComboBox.getValue() == null);
    }

    private Boolean scaleSelected() {
        return !(scaleComboBox.getValue() == null);
    }
}