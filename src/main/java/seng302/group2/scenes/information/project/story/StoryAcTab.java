package seng302.group2.scenes.information.project.story;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import seng302.group2.App;
import seng302.group2.scenes.TagsTableCell;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.search.*;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.acceptanceCriteria.AcEnumStringConverter;
import seng302.group2.workspace.project.story.acceptanceCriteria.AcceptanceCriteria;
import seng302.group2.workspace.tag.Tag;

import java.util.*;

import static javafx.collections.FXCollections.observableArrayList;


/**
 * A tab to show detail of a story's acceptance criteria, that allows the addition, change, and removal of acceptance
 * criteria from a story
 */
public class StoryAcTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();
    Story story;

    ListProperty<Tag> tags = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<Tag>()));

    public ListProperty<Tag> tagsProperty() {
        return tags;
    }

    public ObservableList<Tag> getEmails() {
        return tagsProperty().get();
    }

    public void setTags(ObservableList<Tag> tags) {
        tagsProperty().set(tags);
    }

    /**
     * Constructor for the Story Acceptance Criteria Tab.
     * 
     * @param story The currently selected Story
     */
    public StoryAcTab(Story story) {
        this.story = story;
        construct();
    }

    /**
     * Gets all the searchable controls on this tab.
     * @return a collection of all the searchable controls on this tab.
     */
    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }

    @Override
    public void construct() {
        // Tab settings
        this.setText("Acceptance Criteria");

        for (AcceptanceCriteria ac : story.getAcceptanceCriteria()) {
            System.out.println(ac.getDescription() + " " + ac.getTags());
        }

        Pane acPane = new VBox(10);  // The pane that holds the basic info
        acPane.setBorder(null);
        acPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(acPane);
        this.setContent(wrapper);

        ObservableList<AcceptanceCriteria> data = observableArrayList();
        data = story.getAcceptanceCriteria();

        ReorderableTable<AcceptanceCriteria> acTable = new ReorderableTable<>(story.getAcceptanceCriteria());

        acTable.setEditable(true);
        acTable.fixedCellSizeProperty();
        acTable.setPrefWidth(700);
        acTable.setPrefHeight(288);
        SearchableText noAcLabel = new SearchableText("This project has no acceptance criteria");
        acTable.setPlaceholder(noAcLabel);
        acTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.BOTTOM_RIGHT);
        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        buttons.getChildren().addAll(addButton, deleteButton);
        deleteButton.setDisable(true);
        addButton.setDisable(true);

        SearchableText title = new SearchableTitle("Acceptance Criteria");

        TableColumn descriptionCol = new TableColumn("Description");
        descriptionCol.setEditable(true);
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionCol.setCellValueFactory(new PropertyValueFactory<AcceptanceCriteria, String>("description"));
        descriptionCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<AcceptanceCriteria, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<AcceptanceCriteria, String> event) {
                        (event.getTableView().getItems().get(
                                event.getTablePosition().getRow())
                        ).edit(event.getNewValue());
                    }
                }
        );


        ObservableList<AcceptanceCriteria.AcState> acStates = observableArrayList();
        Collections.addAll(acStates, AcceptanceCriteria.AcState.values());


        AcEnumStringConverter converter = new AcEnumStringConverter();
        TableColumn stateCol = new TableColumn("State");
        stateCol.setCellValueFactory(new PropertyValueFactory<AcceptanceCriteria,
                AcceptanceCriteria.AcState>("state"));
        stateCol.setCellFactory(ComboBoxTableCell.forTableColumn(
                converter,
                acStates
        ));
        stateCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<AcceptanceCriteria, AcceptanceCriteria.AcState>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<AcceptanceCriteria,
                            AcceptanceCriteria.AcState> event) {
                        (event.getTableView().getItems().get(
                                event.getTablePosition().getRow())
                        ).edit(event.getNewValue());
                    }
                }
        );

        TableColumn tagCol = new TableColumn("Tags");

        tagCol.setCellValueFactory(new PropertyValueFactory<AcceptanceCriteria, ObservableList<Tag>>("tags"));

        Callback<TableColumn, TableCell> tagCellFactory = col -> new TagsTableCell(story);
        tagCol.setCellFactory(tagCellFactory);

        tagCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<AcceptanceCriteria, ObservableList<Tag>>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<AcceptanceCriteria, ObservableList<Tag>> event) {
                        if (!event.getNewValue().isEmpty()) {

                            AcceptanceCriteria currentAc = acTable.getSelectionModel().getSelectedItem();

                            ObservableList<Tag> newTags = event.getNewValue();
                            System.out.println("new tags = " + newTags);

//                            currentAc.getTags().add(event.getNewValue().get(0));
//                            currentAc.editAcTags(newTags);
                            System.out.println(currentAc);
                            //Wny the **** does current ac's get tags have the new tags WITHOUT any edit? @JESUS
                            System.out.println("actual tags = " + currentAc.getTags());
                        }
                    }
                }
        );

        wrapper.setOnMouseClicked(event -> {
            System.out.println(story.getAcceptanceCriteria().get(0));
            System.out.println(story.getAcceptanceCriteria().get(0).getTags());
        });

        acTable.setItems(data);
        TableColumn[] columns = {descriptionCol, stateCol, tagCol};
        acTable.getColumns().setAll(columns);

        acTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                deleteButton.setDisable(false);
            }
            else {
                deleteButton.setDisable(true);
            }
        });



        CustomTextArea descriptionTextArea = new CustomTextArea("New Acceptance Criteria:");

        // Events
        descriptionTextArea.getTextArea().textProperty().addListener((observable, oldValue, newValue) -> {
            if (descriptionTextArea.getText().isEmpty()) {
                addButton.setDisable(true);
            }
            else {
                addButton.setDisable(false);
            }
        });


        addButton.setOnAction((event) -> {
            if (!descriptionTextArea.getText().isEmpty()) {
                String description = descriptionTextArea.getText();
                AcceptanceCriteria newAc = new AcceptanceCriteria(description, story);
                story.add(newAc);
                descriptionTextArea.getTextArea().clear();
            }
            else {
                event.consume();
            }
        });

        deleteButton.setOnAction((event) -> {
            AcceptanceCriteria selectedAc = acTable.getSelectionModel().getSelectedItem();
            if (selectedAc != null) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete");
                alert.setHeaderText("Delete Acceptance Criteria?");
                alert.setContentText("Do you really want to delete this Acceptance Criteria?");
                alert.getDialogPane().setStyle(" -fx-max-width:400px; -fx-max-height: 100px; -fx-pref-width: 400px;"
                        + "-fx-pref-height: 100px;");

                ButtonType buttonTypeYes = new ButtonType("Yes");
                ButtonType buttonTypeNo = new ButtonType("No");

                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                Optional<ButtonType> result  = alert.showAndWait();

                if (result.get() == buttonTypeYes) {
                    selectedAc.delete();
                    App.mainPane.refreshContent();
                }
                else if (result.get() == buttonTypeNo) {
                    event.consume();
                }
            }
        });

        acPane.getChildren().addAll(title, acTable, descriptionTextArea, buttons);
        Collections.addAll(searchControls, acTable, title, descriptionTextArea, noAcLabel);
    }

    /**
     * Gets the string representation of the current Tab
     * @return The String value
     */
    @Override
    public String toString() {
        return "Acceptance Criteria Tab";
    }
}
