package seng302.group2.scenes.information.project.backlog;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import seng302.group2.App;
import seng302.group2.scenes.control.chart.StoryCompletenessBar;
import seng302.group2.scenes.control.search.*;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.story.Story;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * The information tab for a backlog
 * Created by cvs20 on 19/05/15.
 */
public class BacklogInfoTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();
    static Boolean highlightMode = Boolean.FALSE;

    /**
     * Constructor for the Backlog Info tab
     * 
     * @param currentBacklog The currently selected backlog
     */
    public BacklogInfoTab(Backlog currentBacklog) {
        // Tab settings
        this.setText("Basic Information");
        Pane basicInfoPane = new VBox(10);
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);

        // Create Table
        SearchableTable<Story> storyTable = new SearchableTable<>(currentBacklog.getStories(), searchControls);
        SearchableText tablePlaceholder = new SearchableText("There are currently no stories in this backlog.");
        storyTable.setEditable(true);
        storyTable.setPrefWidth(600);
        storyTable.setPrefHeight(200);
        storyTable.setPlaceholder(tablePlaceholder);
        storyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Story, String> storyCol = new TableColumn<>("Story");
        storyCol.setCellValueFactory(new PropertyValueFactory<>("shortName"));
        storyCol.prefWidthProperty().bind(storyTable.widthProperty()
                .subtract(2).divide(100).multiply(33));

        TableColumn<Story, Integer> priorityCol = new TableColumn<>("Priority");
        priorityCol.setCellValueFactory(new PropertyValueFactory<>("priority"));
        priorityCol.prefWidthProperty().bind(storyTable.widthProperty()
                .subtract(2).divide(100).multiply(13));

        TableColumn<Story, String> readyCol = new TableColumn<>("Status");
        readyCol.setCellValueFactory(new PropertyValueFactory<>("readyString"));
        readyCol.prefWidthProperty().bind(storyTable.widthProperty()
                .subtract(2).divide(100).multiply(16));

        TableColumn progressCol = new TableColumn("Progress");
        progressCol.prefWidthProperty().bind(storyTable.widthProperty()
                .subtract(2).divide(100).multiply(38));

        progressCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Story, String>,
                ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Story, String> story) {
                SimpleStringProperty prop = new SimpleStringProperty();
                prop.set(story.getValue().getShortName());
                return prop;
            }
        });

        Callback<TableColumn, TableCell> progressCellFactory = col -> new ProgressCell(currentBacklog);
        progressCol.setCellFactory(progressCellFactory);




        storyTable.getColumns().addAll(priorityCol, storyCol, readyCol, progressCol);

        storyTable.setRowFactory(tr -> new SearchableTableRow<Story>(storyTable) {
                /**
                 * An Overidden updateItem method to control the highlighting of cells in the backlog info tab.
                 *
                 * @param item  The item to be updated
                 * @param empty Whether the cell is empty or not
                 */
                @Override
                protected void updateItem(Story item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null) {
                        return;
                    }
                    item.setHighlightColour();
                    if (parentTable.matchingItems.contains(item)) {
                        setStyle("-fx-background-color: " + SearchableControl.highlightColourString + "; ");
                    }
                    else if (highlightMode && item.getColour() != "transparent") {
                        setStyle("-fx-background-color: " + item.getColour() + ";");
                    }
                    else {
                        setStyle(null);
                    }
                }

            });

        // Create controls
        SearchableText title = new SearchableTitle(currentBacklog.getLongName());
        SearchableText shortName = new SearchableText("Short Name: " + currentBacklog.getShortName());
        SearchableText description = new SearchableText("Backlog Description: " + currentBacklog.getDescription());
        SearchableText project = new SearchableText("Project: " + currentBacklog.getProject());
        SearchableText storiesTableLabel = new SearchableText("Stories: ");
        SearchableText estScale = new SearchableText("Estimation Scale: " + currentBacklog.getScale());
        SearchableText po = new SearchableText("");
        Separator separator = new Separator();

        HBox greenKeyHbox = new HBox(8);
        Rectangle green = new Rectangle(250,25,20,20);
        green.setFill(Story.greenHighlight);
        green.setStrokeWidth(3);
        green.setArcWidth(10);
        green.setArcHeight(10);
        SearchableText greenKeyLabel = new SearchableText("= Ready with no issues");
        greenKeyHbox.getChildren().addAll(green, greenKeyLabel);

        HBox orangeKeyHbox = new HBox(8);
        Rectangle orange = new Rectangle(250,25,20,20);
        orange.setFill(Story.orangeHighlight);
        orange.setStrokeWidth(3);
        orange.setArcWidth(10);
        orange.setArcHeight(10);
        SearchableText orangeKeyLabel = new SearchableText("= Requires estimation");
        orangeKeyHbox.getChildren().addAll(orange, orangeKeyLabel);

        HBox redKeyHbox = new HBox(8);
        Rectangle red = new Rectangle(250,25,20,20);
        red.setFill(Story.redHighlight);
        red.setStrokeWidth(3);
        red.setArcWidth(10);
        red.setArcHeight(10);
        SearchableText redKeyLabel = new SearchableText("= Dependent on a story with a lower priority");
        redKeyHbox.getChildren().addAll(red, redKeyLabel);

        Pane keyBox = new VBox(4);
        keyBox.getChildren().addAll(greenKeyHbox, orangeKeyHbox, redKeyHbox);

        Button btnEdit = new Button("Edit");
        Button btnView = new Button("View");
        Button btnHighlight = new Button("Highlight");

        HBox buttonHBox = new HBox();
        buttonHBox.spacingProperty().setValue(10);
        buttonHBox.alignmentProperty().set(Pos.TOP_LEFT);
        buttonHBox.getChildren().addAll(btnView, btnHighlight);

        if (currentBacklog.getProductOwner() == null) {
            po.setText("Product Owner: (none)");
        }
        else {
            po.setText("Product Owner: " + currentBacklog.getProductOwner());
        }

        if (highlightMode) {
            basicInfoPane.getChildren().add(keyBox);
        }

        // Events
        btnEdit.setOnAction((event) -> {
                currentBacklog.switchToInfoScene(true);
            });

        btnView.setOnAction((event) -> {
                if (storyTable.getSelectionModel().getSelectedItem() != null) {
                    App.mainPane.selectItem(storyTable.getSelectionModel().getSelectedItem());
                }
            });

        btnHighlight.setOnAction((event) ->
            {
                if (highlightMode) {
                    basicInfoPane.getChildren().remove(keyBox);
                }
                else {
                    basicInfoPane.getChildren().add(keyBox);
                }

                highlightMode = !highlightMode;

                for (int i = 0; i < storyTable.getColumns().size(); i++) {
                    ((TableColumn)(storyTable.getColumns().get(i))).setVisible(false);
                    ((TableColumn)(storyTable.getColumns().get(i))).setVisible(true);
                }
                storyCol.prefWidthProperty().bind(storyTable.widthProperty()
                        .subtract(2).divide(100).multiply(60));
                priorityCol.prefWidthProperty().bind(storyTable.widthProperty()
                        .subtract(2).divide(100).multiply(20));
                readyCol.prefWidthProperty().bind(storyTable.widthProperty()
                        .subtract(2).divide(100).multiply(20));
            });

        basicInfoPane.getChildren().addAll(
                title,
                shortName,
                description,
                project,
                po,
                estScale,
                separator,
                storiesTableLabel,
                storyTable,
                buttonHBox,
                btnEdit
        );

        // Add items to pane & search collection
        Collections.addAll(searchControls,
                title,
                shortName,
                description,
                project,
                po,
                estScale,
                storiesTableLabel,
                storyTable,
                orangeKeyLabel,
                greenKeyLabel,
                redKeyLabel,
                tablePlaceholder
        );
    }

    /**
     * A class used to show the progress of a story.
     */
    class ProgressCell extends TableCell<Object, String> {
        public Node popUp;
        public Backlog backlog;

        /**
         * Constructor
         * @param backlog The backlog of the story.
         */
        private ProgressCell(Backlog backlog) {
            this.backlog = backlog;
        }

        /**
         * Updates the item
         *
         * @param item the item to update to
         * @param empty if the cell is empty
         */
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            }
            else {
                this.popUp = new StoryCompletenessBar(getStory(), 220, 18);
                setGraphic(popUp);
            }
        }

        public Story getStory() {
            Story result = null;
            for (Story story : this.backlog.getStories()) {
                if (story.getShortName() == getItem()) {
                    result = story;
                }
            }
            return result;
        }

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
