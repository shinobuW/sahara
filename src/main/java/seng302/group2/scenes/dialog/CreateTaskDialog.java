package seng302.group2.scenes.dialog;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.tasks.Task;
import seng302.group2.workspace.team.Team;

import java.util.Map;

import static javafx.collections.FXCollections.observableArrayList;
import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * Created by cvs20 on 29/07/15.
 */
public class CreateTaskDialog extends Dialog<Map<String, String>> {
    static Boolean correctShortName = Boolean.FALSE;
    static Boolean correctDescription = Boolean.FALSE;
    // TODO Add effort left when its implemented

    public CreateTaskDialog() {
        correctShortName = false;
        correctDescription = false;

        // Initialise Dialog
        this.setTitle("New Task");
        this.getDialogPane().setStyle(" -fx-max-width:600px; -fx-max-height: 600px; -fx-pref-width: 600px; "
                + "-fx-pref-height: 600px;");
        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);

        ButtonType btnTypeCreate = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(btnTypeCreate, ButtonType.CANCEL);

        // Add elements to grid
        RequiredField shortNameCustomField = new RequiredField("Short Name:");

        //Create Project Combo box
        ComboBox<Project> projectComboBox = new ComboBox<>();
        projectComboBox.setStyle("-fx-pref-width: 175;");
        Label projectComboLabel = new Label("Project:");
        HBox projectComboHBox = new HBox(projectComboLabel);

        Label aster1 = new Label(" * ");
        aster1.setTextFill(Color.web("#ff0000"));
        projectComboHBox.getChildren().add(aster1);

        VBox projectVBox = new VBox();
        HBox projectCombo = new HBox();
        projectCombo.getChildren().addAll(projectComboHBox, projectComboBox);
        HBox.setHgrow(projectComboHBox, Priority.ALWAYS);
        projectVBox.getChildren().add(projectCombo);

        //Create Backlog Combo Box
        ComboBox<Backlog> backlogComboBox = new ComboBox<>();
        backlogComboBox.setStyle("-fx-pref-width: 175;");
        Label backlogComboLabel = new Label("Backlog:");
        HBox backlogComboHBox = new HBox(backlogComboLabel);

        Label aster2 = new Label(" * ");
        aster2.setTextFill(Color.web("#ff0000"));
        backlogComboHBox.getChildren().add(aster2);

        VBox backlogVBox = new VBox();
        HBox backlogCombo = new HBox();
        backlogCombo.getChildren().addAll(backlogComboHBox, backlogComboBox);
        HBox.setHgrow(backlogComboHBox, Priority.ALWAYS);
        backlogVBox.getChildren().add(backlogCombo);

        //Create Story Combo Box
        ComboBox<Story> storyComboBox = new ComboBox<>();
        storyComboBox.setStyle("-fx-pref-width: 175;");
        Label storyComboLabel = new Label("Story:");
        HBox storyComboHBox = new HBox(storyComboLabel);

        Label aster3 = new Label(" * ");
        aster3.setTextFill(Color.web("#ff0000"));
        storyComboHBox.getChildren().add(aster3);

        VBox storyVBox = new VBox();
        HBox storyCombo = new HBox();
        storyCombo.getChildren().addAll(storyComboHBox, storyComboBox);
        HBox.setHgrow(storyComboHBox, Priority.ALWAYS);
        storyVBox.getChildren().add(storyCombo);
        

        backlogComboBox.setDisable(true);
        storyComboBox.setDisable(true);

        SearchableText responsibles = new SearchableText("Responsibles:");
        final ObservableList<Person> availablePeople = FXCollections.observableArrayList();
        final ListView<Person> listView = new ListView<>(availablePeople);

        Callback<Person, ObservableValue<Boolean>> getProperty = new Callback<Person, ObservableValue<Boolean>>() {
            @Override
            public BooleanProperty call(Person layer) {

                return layer.selectedProperty();

            }
        };

        Callback<ListView<Person>, ListCell<Person>> forListView = CheckBoxListCell.forListView(getProperty);
        listView.setCellFactory(forListView);
        CustomTextArea descriptionTextArea = new CustomTextArea("Description:");

        //String firstPItem = Global.currentWorkspace.getProjects().get(0).toString();
        //projectComboBox.setValue(firstPItem);

        for (Project project : Global.currentWorkspace.getProjects()) {
            projectComboBox.getItems().add(project);
        }

        grid.getChildren().addAll(shortNameCustomField, projectVBox,
                backlogVBox, storyVBox, responsibles, listView, descriptionTextArea);

        this.getDialogPane().setContent(grid);
        Platform.runLater(() -> shortNameCustomField.getTextField().requestFocus());
        Node createButton = this.getDialogPane().lookupButton(btnTypeCreate);
        createButton.setDisable(true);

        shortNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctShortName = validateShortName(shortNameCustomField, null);
                createButton.setDisable(!(correctShortName));
            });

        projectComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null) {
                    return;
                }

                backlogComboBox.getItems().clear();
                storyComboBox.getItems().clear();
                for (Backlog backlog : newValue.getBacklogs()) {
                    backlogComboBox.getItems().add(backlog);
                }
                availablePeople.clear();
                for (Team team : newValue.getCurrentTeams()) {
                    for (Person person : team.getPeople()) {
                        availablePeople.add(person);
                    }
                }

                backlogComboBox.setDisable(false);
            });

        backlogComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null) {
                    return;
                }
                storyComboBox.getItems().clear();
                for (Story story : newValue.getStories()) {
                    storyComboBox.getItems().add(story);
                }
                storyComboBox.setDisable(false);
            });

        this.setResultConverter(b -> {
                if (b == btnTypeCreate) {

                    if (correctShortName) {
                        //get user input
                        String shortName = shortNameCustomField.getText();
                        String description = descriptionTextArea.getText();
                        ObservableList<Person> selectedPeople = observableArrayList();
                        for (Person person : availablePeople) {
                            if (person.getSelected() == true) {
                                selectedPeople.add(person);
                                person.setSelected(false);
                            }
                        }

                        Task task = new Task(shortName, description, storyComboBox.
                                getSelectionModel().getSelectedItem(), selectedPeople);
                        storyComboBox.getSelectionModel().getSelectedItem().add(task);
                        App.refreshMainScene();
                        App.mainPane.selectItem(task);
                        this.close();
                    }
                }
                return null;
            });
        this.setResizable(false);
        this.show();
    }
}
