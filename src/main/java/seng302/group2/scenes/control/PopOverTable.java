package seng302.group2.scenes.control;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableRow;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;
import seng302.group2.scenes.control.search.*;
import seng302.group2.scenes.information.project.story.task.LoggingEffortPane;
import seng302.group2.workspace.project.story.tasks.Task;

import java.util.Collection;

/**
 * Created by crw73 on 26/08/15.
 */
public class PopOverTable<T> extends SearchableTable<T> {
    /**
     * Basic constructor.
     */
    public PopOverTable() {
        super();
        updateRows();
    }


    /**
     * Basic constructor. Sets the data of the table to that provided in the parameter.
     * @param data the new data of the table
     */
    public PopOverTable(ObservableList<T> data) {
        super();
        updateRows();
        setData(data);
    }


    /**
     * Basic constructor. Sets the data of the table to that provided in the parameter.
     * @param data the new data of the table
     * @param rowFactory the table row factory that is used for the table. Default is SearchableTableRow
     */
    public PopOverTable(ObservableList<T> data, SearchableTableRow<T> rowFactory) {
        super();
        setData(data);
    }


    /**
     * Basic constructor. Sets the data of the table to that provided in the parameter.
     * @param data the new data of the table
     * @param searchableControls The collection of searchable controls to add this control to
     */
    public PopOverTable(Collection<T> data, Collection<SearchableControl> searchableControls) {
        super();
        searchableControls.add(this);
        updateRows();
        setData(data);
    }

    /**
     * Highlights a row if a matching query is found within that row. If there is no matching query,
     * the row's style is set to null (default).
     */
    private void updateRows() {
        setRowFactory(tv -> new TableRow<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                HBox content = new HBox();
                PopOver taskPopover = new PopOver();



                VBox taskContent = new VBox();
                taskContent.setPadding(new Insets(8, 8, 8, 8));
                if (item != null) {

                    if (item == null) {
                        SearchableText noTaskLabel = new SearchableText("No tasks selected.");
                        taskContent.getChildren().add(noTaskLabel);
                    }
                    else {
                        Task currentTask = (Task) item;
                        taskPopover.setDetachedTitle(currentTask.toString());
                        VBox taskInfo = new VBox();

                        taskInfo.setBorder(null);
                        taskInfo.setPadding(new Insets(25, 25, 25, 25));

                        SearchableText title = new SearchableTitle(currentTask.getShortName());
                        SearchableText description = new SearchableText("Task Description: "
                                + currentTask.getDescription());
                        SearchableText impediments = new SearchableText("Impediments: " + currentTask.getImpediments());
                        SearchableText effortLeft = new SearchableText("Effort Left: "
                                + currentTask.getEffortLeftString());
                        SearchableText effortSpent = new SearchableText("Effort Spent: "
                                + currentTask.getEffortSpentString());
                        SearchableText taskState = new SearchableText("Task State: " + currentTask.getState());
                        SearchableText assignedPerson;
                        if (currentTask.getAssignee() == null) {
                            assignedPerson = new SearchableText("Assigned Person: ");
                        }
                        else {
                            assignedPerson = new SearchableText("Assigned Person: " + currentTask.getAssignee());
                        }

                        taskInfo.getChildren().addAll(
                                title,
                                description,
                                impediments,
                                effortLeft,
                                effortSpent,
                                taskState,
                                assignedPerson
                        );



                        ScrollPane taskWrapper = new ScrollPane();
                        taskWrapper.setContent(new LoggingEffortPane((Task) item,
                                taskPopover));

                        TitledPane collapsableInfoPane = new TitledPane("Task Info", taskInfo);
                        collapsableInfoPane.setPrefHeight(30);
                        collapsableInfoPane.setExpanded(true);
                        collapsableInfoPane.setAnimated(true);

                        TitledPane collapsableLoggingPane = new TitledPane("Task Logging", taskWrapper);
                        collapsableLoggingPane.setExpanded(true);
                        collapsableLoggingPane.setAnimated(true);

                        taskContent.getChildren().addAll(collapsableInfoPane, collapsableLoggingPane);
                    }

                    taskPopover.setContentNode(taskContent);
                    if (matchingItems.contains(item)) {
                        setStyle("-fx-background-color: " + SearchableControl.highlightColourString + "; ");
                    }
                    else {
                        setStyle(null);
                        //setStyle("-fx-background-color: " + Color.TRANSPARENT + ";");
                    }
                    content.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                            System.out.println("Is clicked");
                            if (taskPopover.isShowing()) {
                                taskPopover.hide();
                            }
                            else {
                                taskPopover.show(content);
                            }
                            event.consume();
                        });
                }

                setGraphic(content);
            }
        });
    }
}
