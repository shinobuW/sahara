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
import seng302.group2.scenes.control.CustomTextField;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.tasks.Task;
import seng302.group2.workspace.team.Team;

import java.util.Map;

import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * Created by cvs20 on 29/07/15.
 */
public class CreateTaskDialog extends Dialog<Map<String, String>> {
    static Boolean correctShortName = Boolean.FALSE;
    static Boolean correctDescription = Boolean.FALSE;
    static Boolean correctEffortLeft = Boolean.FALSE;
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
        CustomTextField effortLeftField = new CustomTextField("Effort Left:");

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

        CustomComboBox<Person> assigneeComboBox = new CustomComboBox<Person>("Assignee");
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
                createButton.setDisable(!(correctShortName));
            });

        effortLeftField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    Integer parsedInt = Integer.parseInt(newValue);
                    effortLeftField.hideErrorField();
                    correctEffortLeft = true;
                }
                catch (NumberFormatException ex) {
                    correctEffortLeft = false;
                    effortLeftField.showErrorField("* You must enter integer values only");

                }
            });

        projectComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null) {
                    return;
                }

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
                if (newValue.getCurrentTeams().size() == 0 ) {
                    assigneeComboBox.setDisable(true);
                }
                else {
                    assigneeComboBox.setDisable(false);
                    for (Team team : newValue.getCurrentTeams()) {
                        for (Person person : team.getPeople()) {
                            assigneeComboBox.addToComboBox(person);
                        }
                    }
                }
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

                    if (correctShortName && correctEffortLeft) {
                        //get user input
                        String shortName = shortNameCustomField.getText();
                        String description = descriptionTextArea.getText();
                        Float parsedInt = Float.parseFloat(effortLeftField.getText());


                        Task task = new Task(shortName, description,
                                storyComboBox.getSelectionModel().getSelectedItem(), assigneeComboBox.getComboBox()
                                .getSelectionModel().getSelectedItem());
                        task.setEffortLeft(parsedInt);
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
