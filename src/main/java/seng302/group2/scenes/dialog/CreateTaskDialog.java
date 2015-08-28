package seng302.group2.scenes.dialog;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import seng302.group2.App;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.validation.ValidationStyle;
import seng302.group2.util.conversion.DurationConverter;
import seng302.group2.util.validation.DateValidator;
import seng302.group2.workspace.person.Person;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.tasks.Task;
import seng302.group2.workspace.team.Team;

import java.util.Map;

import static seng302.group2.util.validation.ShortNameValidator.validateShortName;

/**
 * A class used for task creation.
 * Created by cvs20 on 29/07/15.
 */
@Deprecated
public class CreateTaskDialog extends Dialog<Map<String, String>> {
    static Boolean correctShortName = Boolean.FALSE;
    static Boolean correctEffortLeft = Boolean.FALSE;
    static Boolean projectSelected = Boolean.FALSE;
    static Boolean backlogSelected = Boolean.FALSE;
    static Boolean storySelected = Boolean.FALSE;
    Node createButton;


    /**
     * Used to create a task. This dialog is only accessible from the File -> New menu.
     */
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
        CustomComboBox<Project> projectComboBox = new CustomComboBox<>("Project:", true);
        projectComboBox.getComboBox().setItems(Global.currentWorkspace.getProjects());

        projectComboBox.getComboBox().setCellFactory(
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
        CustomComboBox<Backlog> backlogComboBox = new CustomComboBox<>("Backlog:", true);

        backlogComboBox.getComboBox().setCellFactory(
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
        CustomComboBox<Story> storyComboBox = new CustomComboBox<>("Story:", true);


        backlogComboBox.disable(true);
        storyComboBox.disable(true);

        CustomComboBox<Person> assigneeComboBox = new CustomComboBox<Person>("Assignee");
        assigneeComboBox.getComboBox().setPrefWidth(175);
        assigneeComboBox.disable(true);

        CustomTextArea descriptionTextArea = new CustomTextArea("Description:");

        grid.getChildren().addAll(shortNameCustomField, projectComboBox,
                backlogComboBox, storyComboBox, assigneeComboBox, effortLeftField, descriptionTextArea);

        this.getDialogPane().setContent(grid);
        Platform.runLater(() -> shortNameCustomField.getTextField().requestFocus());
        createButton = this.getDialogPane().lookupButton(btnTypeCreate);
        createButton.setDisable(true);

        shortNameCustomField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctShortName = validateShortName(shortNameCustomField, null);
                toggleCreateBtn();
            });

        effortLeftField.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
                correctEffortLeft = DateValidator.validDuration(newValue) && !newValue.isEmpty();
                if (correctEffortLeft) {
                    ValidationStyle.borderGlowNone(effortLeftField.getTextField());
                }
                else {
                    if (newValue.isEmpty()) {
                        ValidationStyle.borderGlowRed(effortLeftField.getTextField());
                        ValidationStyle.showMessage("This field must be filled", effortLeftField.getTextField());
                    }
                    else {
                        ValidationStyle.borderGlowRed(effortLeftField.getTextField());
                        ValidationStyle.showMessage("Please input in valid format", effortLeftField.getTextField());
                    }
                }
                toggleCreateBtn();
            });

        projectComboBox.getComboBox().valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null) {
                    projectSelected = false;
                    return;
                }
                projectSelected = true;
                backlogComboBox.getComboBox().getItems().clear();
                storyComboBox.getComboBox().getItems().clear();
                if (newValue.getBacklogs().size() == 0) {
                    backlogComboBox.disable(true);
                }
                else {
                    backlogComboBox.disable(false);
                    for (Backlog backlog : newValue.getBacklogs()) {
                        backlogComboBox.getComboBox().getItems().add(backlog);
                    }
                }

                assigneeComboBox.clear();
                if (newValue.getCurrentTeams().size() == 0) {
                    assigneeComboBox.disable(true);
                }
                else {
                    assigneeComboBox.disable(false);
                    Person blankPerson = new Person("", "", "", null, null, null);
                    assigneeComboBox.addToComboBox(blankPerson);
                    for (Team team : newValue.getCurrentTeams()) {
                        for (Person person : team.getPeople()) {
                            assigneeComboBox.addToComboBox(person);
                        }
                    }
                }
                toggleCreateBtn();
            });

        backlogComboBox.getComboBox().valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null) {
                    backlogSelected = false;
                    return;
                }
                storyComboBox.getComboBox().getItems().clear();
                for (Story story : newValue.getStories()) {
                    storyComboBox.getComboBox().getItems().add(story);
                }
                storyComboBox.disable(false);
                backlogSelected = true;
                toggleCreateBtn();
            });

        storyComboBox.getComboBox().valueProperty().addListener((observable, oldValue, newValue) -> {
                storySelected = storyComboBox.getValue() == null ? false : true;
                toggleCreateBtn();
            }
        );

        this.setResultConverter(b -> {
                if (b == btnTypeCreate) {
                    //get user input
                    String shortName = shortNameCustomField.getText();
                    String description = descriptionTextArea.getText();
                    Double effortLeft = DurationConverter.readDurationToMinutes(effortLeftField.getText());
                    Story story =  storyComboBox.getValue();
                    Person assignee = null;
                    if (assigneeComboBox.getValue() != null && !assigneeComboBox.getValue().toString().isEmpty()) {
                        assignee = assigneeComboBox.getValue();
                    }

                    Task task = new Task(shortName, description, story, assignee, effortLeft);
                    story.add(task);
                    App.refreshMainScene();
                    App.mainPane.selectItem(task.getStory());
                    this.close();
                }
                return null;
            });
        this.setResizable(false);
        this.show();
    }

    private void toggleCreateBtn() {
        createButton.setDisable(!(correctShortName && correctEffortLeft && projectSelected
                && backlogSelected && storySelected));
    }
}
