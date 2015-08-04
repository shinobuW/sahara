package seng302.group2.scenes.information.project.story;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.App;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.control.search.SearchableText;
import seng302.group2.scenes.control.search.SearchableTitle;
import seng302.group2.workspace.project.story.Story;
import seng302.group2.workspace.project.story.acceptanceCriteria.AcEnumStringConverter;
import seng302.group2.workspace.project.story.acceptanceCriteria.AcceptanceCriteria;

import java.util.*;


/**
 * A tab to show detail of a story's acceptance criteria, that allows the addition, change, and removal of acceptance
 * criteria from a story
 */
public class StoryAcTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();


    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

    /**
     * Constructor for the Story Acceptance Criteria Tab.
     * 
     * @param story The currently selected Story
     */
    public StoryAcTab(Story story) {
        this.setText("Acceptance Criteria");
        Pane acPane = new VBox(10);  // The pane that holds the basic info
        acPane.setBorder(null);
        acPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(acPane);
        this.setContent(wrapper);

        TableView<AcceptanceCriteria> acTable = new TableView<>();
        acTable.setEditable(true);
        acTable.fixedCellSizeProperty();
        acTable.setPrefWidth(700);
        acTable.setPrefHeight(288);
        SearchableText noAcLabel = new SearchableText("This project has no acceptance criteria");
        acTable.setPlaceholder(noAcLabel);
        acTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList<AcceptanceCriteria> data = story.getAcceptanceCriteria();


        // A row factory snippet to allow item drag and drop re-ordering
        acTable.setRowFactory(tv -> {
                TableRow<AcceptanceCriteria> row = new TableRow<>();

                row.setOnDragDetected(event -> {
                        if (!row.isEmpty()) {
                            Integer index = row.getIndex();
                            Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                            db.setDragView(row.snapshot(null, null));
                            ClipboardContent cc = new ClipboardContent();
                            cc.put(SERIALIZED_MIME_TYPE, index);
                            db.setContent(cc);
                            event.consume();
                        }
                    });

                row.setOnDragOver(event -> {
                        Dragboard db = event.getDragboard();
                        if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                            if (row.getIndex() != ((Integer)db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
                                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                                event.consume();
                            }
                        }
                    });

                row.setOnDragDropped(event -> {
                        Dragboard db = event.getDragboard();
                        if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                            int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                            AcceptanceCriteria draggedAC = acTable.getItems().remove(draggedIndex);

                            int dropIndex ;

                            if (row.isEmpty()) {
                                dropIndex = acTable.getItems().size() ;
                            }
                            else {
                                dropIndex = row.getIndex();
                            }

                            acTable.getItems().add(dropIndex, draggedAC);

                            event.setDropCompleted(true);
                            acTable.getSelectionModel().select(dropIndex);
                            event.consume();
                        }
                    });

                return row ;
            });


        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.BOTTOM_RIGHT);
        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        buttons.getChildren().addAll(addButton, deleteButton);

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


        ObservableList<AcceptanceCriteria.AcState> acStates = FXCollections.observableArrayList();
        for (AcceptanceCriteria.AcState state : AcceptanceCriteria.AcState.values()) {
            acStates.add(state);
        }


        AcEnumStringConverter converter = new AcEnumStringConverter();
        TableColumn stateCol = new TableColumn("State");
        stateCol.setCellValueFactory(new PropertyValueFactory<AcceptanceCriteria, AcceptanceCriteria.AcState>("state"));
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

        acTable.setItems(data);
        TableColumn[] columns = {descriptionCol, stateCol};
        acTable.getColumns().setAll(columns);


        deleteButton.setDisable(true);
        acTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    deleteButton.setDisable(false);
                }
                else {
                    deleteButton.setDisable(true);
                }
            });



        CustomTextArea descriptionTextArea = new CustomTextArea("New Acceptance Criteria:");
        descriptionTextArea.getTextArea().textProperty().addListener((observable, oldValue, newValue) -> {
                if (descriptionTextArea.getText().isEmpty()) {
                    addButton.setDisable(true);
                }
                else {
                    addButton.setDisable(false);
                }
            });


        addButton.setDisable(true);
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
        Collections.addAll(searchControls, title, noAcLabel);
    }

    /**
     * Gets all the searchable controls on this tab.
     * @return a collection of all the searchable controls on this tab.
     */
    @Override
    public Collection<SearchableControl> getSearchableControls() {
        return searchControls;
    }
}
