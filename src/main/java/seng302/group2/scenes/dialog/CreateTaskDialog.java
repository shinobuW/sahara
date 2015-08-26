package seng302.group2.scenes.dialog;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.util.conversion.DurationConverter;
import seng302.group2.util.validation.DateValidator;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.tasks.Log;
import seng302.group2.workspace.project.story.tasks.Task;
import seng302.group2.workspace.team.Team;

import java.util.Map;

import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * Created by cvs20 on 29/07/15.
 */
public class CreateTaskDialog extends Dialog<Map<String, String>> {
    static Boolean correctShortName = Boolean.FALSE;
    static Boolean correctEffortLeft = Boolean.FALSE;
    static Boolean projectSelected = Boolean.FALSE;
    static Boolean backlogSelected = Boolean.FALSE;
    static Boolean storySelected = Boolean.FALSE;

    public CreateTaskDialog() {
        correctShortName = false;
        correctEffortLeft = false;
        projectSelected = false;
        backlogSelected = false;
        storySelected = false;

        // Initialise Dialog
        this.setTitle("New Task");
        this.getDialogPane().setStyle(" -fx-max-width:600px; -fx-max-height: 600px; -fx-pref-width: 600px; "
                + "-fx-pref-height: 420px;");
        VBox grid = new VBox();
        grid.spacingProperty().setValue(10);
        Insets insets = new Insets(20, 20, 20, 20);
        grid.setPadding(insets);

        ButtonType btnTypeCreate = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(btnTypeCreate, ButtonType.CANCEL);

        // Add elements to grid
        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        RequiredField effortLeftField = new RequiredField("Effort Left:");

        //Create Project Combo box
        ComboBox<Project> projectComboBox = new ComboBox<>();
        projectComboBox.setStyle("-fx-pref-width: 175;");
        Label projectComboLabel = new Label("Project:");
        HBox projectComboHBox = new HBox(projectComboLabel);

        Label aster1 = new Label(" * ");
        aster1.setTextFill(Color.web("#ff0000"));
        projectComboHBox.getChildren().add(aster1);

        VBox projectVBox = new VBox();
        HBox projectComboHbox = new HBox();
        projectComboHbox.getChildren().addAll(projectComboHBox, projectComboBox);
        HBox.setHgrow(projectComboHBox, Priority.ALWAYS);
        projectVBox.getChildren().add(projectComboHbox);

        projectComboBox.setCellFactory(
                new Callback<ListView<Project>, ListCell<Project>>() {
                    @Override
                    public ListCell<Project> call(ListView<Project> param) {
                        final ListCell<Project> cell = new ListCell<Project>() {
                            {
                                super.setPrefWidth(100);
                            }
                            @Override
                            public void updateItem(Project item,
                                                   boolean empty) {
                                super.updateItem(item, empty);
                                if (item != null) {
                                    setText(item.toString());
                                    if (item.getBacklogs().size() == 0) {
                                        setStyle("-fx-background-color: red;");
                                        setDisable(true);
                                    }
                                }
                                else {
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                });

        //Create Backlog Combo Box
        ComboBox<Backlog> backlogComboBox = new ComboBox<>();
        backlogComboBox.setStyle("-fx-pref-width: 175;");
        Label backlogComboLabel = new Label("Backlog:");
        HBox backlogComboHBox = new HBox(backlogComboLabel);

        Label aster2 = new Label(" * ");
        aster2.setTextFill(Color.web("#ff0000"));
        backlogComboHBox.getChildren().add(aster2);

        VBox backlogVBox = new VBox();
        HBox backlogComboHbox = new HBox();
        backlogComboHbox.getChildren().addAll(backlogComboHBox, backlogComboBox);
        HBox.setHgrow(backlogComboHBox, Priority.ALWAYS);
        backlogVBox.getChildren().add(backlogComboHbox);

        backlogComboBox.setCellFactory(
                new Callback<ListView<Backlog>, ListCell<Backlog>>() {
                    @Override
                    public ListCell<Backlog> call(ListView<Backlog> param) {
                        final ListCell<Backlog> cell = new ListCell<Backlog>() {
                            {
                                super.setPrefWidth(100);
                            }

                            @Override
                            public void updateItem(Backlog item,
                                                   boolean empty) {
                                super.updateItem(item, empty);
                                if (item != null) {
                                    setText(item.toString());
                                    if (item.getStories().size() == 0) {
                                        setStyle("-fx-background-color: red;");
                                        setDisable(true);
                                    }
                                }
                                else {
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                });

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

        CustomComboBox<Person> assigneeComboBox = new CustomComboBox<Person>("Assignee");
        assigneeComboBox.getComboBox().setPrefWidth(175);
        assigneeComboBox.setDisable(true);

        CustomTextArea descriptionTextArea = new CustomTextArea("Description:");


        for (Project project : Global.currentWorkspace.getProjects()) {
            projectComboBox.getItems().add(project);
        }

        grid.getChildren().addAll(shortNameCustomField, projectVBox,
                backlogVBox, storyVBox, assigneeComboBox, effortLeftField, descriptionTextArea);

        this.getDialogPane().setContent(grid);
        Platform.runLater(() -> shortNameCustomField.getTextField().requestFocus());
        Node createButton = this.getDialogPane().lookupButton(btnTypeCreate);
        createButton.setDisable(true);

        shortNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctShortName = validateShortName(shortNameCustomField, null);
                createButton.setDisable(!(correctShortName && correctEffortLeft && projectSelected && backlogSelected
                        && storySelected));
            });

        effortLeftField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctEffortLeft = DateValidator.validDuration(newValue) && !newValue.isEmpty();
                if (correctEffortLeft) {
                    effortLeftField.hideErrorField();
                }
                else {
                    if (newValue.isEmpty()) {
                        effortLeftField.showErrorField("* This field must be filled");
                    }
                    else {
                        effortLeftField.showErrorField("* Please input in valid format");
                    }
                }
                createButton.setDisable(!(correctShortName && correctEffortLeft && projectSelected && backlogSelected
                        && storySelected));
            });

        projectComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null) {
                    projectSelected = false;
                    return;
                }
                projectSelected = true;
                backlogComboBox.getItems().clear();
                storyComboBox.getItems().clear();
                if (newValue.getBacklogs().size() == 0) {
                    backlogComboBox.setDisable(true);
                }
                else {
                    backlogComboBox.setDisable(false);
                    for (Backlog backlog : newValue.getBacklogs()) {
                        backlogComboBox.getItems().add(backlog);
                    }
                }

                assigneeComboBox.clear();
                if (newValue.getCurrentTeams().size() == 0) {
                    assigneeComboBox.setDisable(true);
                }
                else {
                    assigneeComboBox.setDisable(false);
                    Person blankPerson = new Person("", "", "", null, null, null);
                    assigneeComboBox.addToComboBox(blankPerson);
                    for (Team team : newValue.getCurrentTeams()) {
                        for (Person person : team.getPeople()) {
                            assigneeComboBox.addToComboBox(person);
                        }
                    }
                }
                createButton.setDisable(!(correctShortName && correctEffortLeft && projectSelected && backlogSelected
                        && storySelected));
            });

        backlogComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null) {
                    backlogSelected = false;
                    return;
                }
                storyComboBox.getItems().clear();
                for (Story story : newValue.getStories()) {
                    storyComboBox.getItems().add(story);
                }
                storyComboBox.setDisable(false);
                backlogSelected = true;
                createButton.setDisable(!(correctShortName && correctEffortLeft && projectSelected && backlogSelected
                        && storySelected));
            });

        storyComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                storySelected = storyComboBox.getSelectionModel().getSelectedItem() == null ? false : true;
                createButton.setDisable(!(correctShortName && correctEffortLeft && projectSelected
                        && backlogSelected && storySelected));
            }
        );

        this.setResultConverter(b -> {
                if (b == btnTypeCreate) {
                    //get user input
                    String shortName = shortNameCustomField.getText();
                    String description = descriptionTextArea.getText();
                    Double effortSpent = DurationConverter.readDurationToMinutes(effortLeftField.getText());
                    Story story =  storyComboBox.getSelectionModel().getSelectedItem();
                    Person assignee = null;
                    if (assigneeComboBox.getValue() != null && !assigneeComboBox.getValue().toString().isEmpty()) {
                        assignee = assigneeComboBox.getValue();
                    }

                    Task task = new Task(shortName, description, story, assignee);
                    task.setEffortLeft(effortSpent);
                    storyComboBox.getSelectionModel().getSelectedItem().add(task);
                    App.refreshMainScene();
                    App.mainPane.selectItem(task.getStory());
                    this.close();
                }
                return null;
            });
        this.setResizable(false);
        this.show();
    }
}
