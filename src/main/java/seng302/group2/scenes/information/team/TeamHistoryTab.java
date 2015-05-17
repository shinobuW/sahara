package seng302.group2.scenes.information.team;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import seng302.group2.Global;
import seng302.group2.scenes.control.CustomComboBox;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.team.Allocation;
import seng302.group2.workspace.team.Team;

import java.time.format.DateTimeFormatter;

/**
 * A class for history allocation tab to display a table of the selected team's allocation
 * history
 * Created by swi67 on 15/05/15.
 */
public class TeamHistoryTab extends Tab
{
    /**
     * Constructor for team allocation tab
     * @param currentTeam currently selected project
     */
    Boolean isValidEdit;
    TeamHistoryTab(Team currentTeam)
    {
        this.setText("Allocation History");
        Pane historyPane = new VBox(10);  // The pane that holds the basic info
        historyPane.setBorder(null);
        historyPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(historyPane);
        this.setContent(wrapper);

        TableView<Allocation> historyTable = new TableView();
        historyTable.setEditable(true);
        historyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList<Allocation> data = currentTeam.getProjectAllocations();


        Label title = new TitleLabel("Allocation History");

        TableColumn teamCol = new TableColumn("Project");
        teamCol.setCellValueFactory(new PropertyValueFactory<Allocation, String>("Project"));
        teamCol.prefWidthProperty().bind(historyTable.widthProperty()
                .subtract(3).divide(100).multiply(40));
        teamCol.setResizable(false);

        TableColumn startDateCol = new TableColumn("Start Date");
        startDateCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Allocation, String>,
                        ObservableValue<String>>()
                {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Allocation,
                            String> alloc)
                    {
                        SimpleStringProperty property = new SimpleStringProperty();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        property.setValue(alloc.getValue().getStartDate().format(formatter));
                        return property;
                    }
                });
//        startDateCol.setOnEditCommit(
//                new EventHandler<TableColumn.CellEditEvent<Allocation, String>>()
//                {
//                    @Override
//                    public void handle(TableColumn.CellEditEvent<Allocation, String> event)
//                    {
//                        if (!event.getNewValue().isEmpty())
//                        {
//
//                            Allocation currentAllocation =
// (Allocation) event.getTableView().getItems()
//                                    .get(event.getTablePosition().getRow());
//                            Project proj = currentAllocation.getProject();
//
//                            LocalDate currentStartDate = currentAllocation.getStartDate();
//                            LocalDate newStartDate = LocalDate.parse(event.getNewValue(),
//                                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                            currentAllocation.setStartDate(newStartDate);
//
//                            if (validateAllocation(currentAllocation, proj))
//                            {
//                                isValidEdit = false;
//                            }
//                            else
//                            {
//                                currentAllocation.setStartDate(currentStartDate);
//
//                                isValidEdit = true;
//                            }
//                        }
//                    }
//                });
        //startDateCol.setCellFactory(cellFactory);
        startDateCol.prefWidthProperty().bind(historyTable.widthProperty()
                .subtract(3).divide(100).multiply(30));

        TableColumn endDateCol = new TableColumn("End Date");
        endDateCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Allocation, String>,
                        ObservableValue<String>>()
                {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Allocation,
                            String> alloc)
                    {
                        SimpleStringProperty property = new SimpleStringProperty();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        if (alloc.getValue().getEndDate() != null)
                        {
                            property.setValue(alloc.getValue().getEndDate().format(formatter));
                        }
                        else
                        {
                            property.setValue("");
                        }
                        return property;
                    }
                });
        endDateCol.prefWidthProperty().bind(historyTable.widthProperty()
                .subtract(3).divide(100).multiply(30));
//        endDateCol.setCellFactory(cellFactory);
//        endDateCol.setEditable(true);
//        endDateCol.setOnEditCommit(
//                new EventHandler<TableColumn.CellEditEvent<Allocation, String>>()
//                {
//                    @Override
//                    public void handle(TableColumn.CellEditEvent<Allocation, String> event)
//                    {
//                        if (!event.getNewValue().isEmpty())
//                        {
//
//                            Allocation currentAllocation = (Allocation) event.getTableView().
//                                    getItems().get(event.getTablePosition().getRow());
//                            Project proj = currentAllocation.getProject();
//
//                            LocalDate currentEndDate = currentAllocation.getEndDate();
//                            LocalDate newEndDate = LocalDate.parse(event.getNewValue(),
//                                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                            currentAllocation.setEndDate(newEndDate);
//
//                            if (validateAllocation(currentAllocation, proj))
//                            {
//                                isValidEdit = false;
//                            }
//                            else
//                            {
//                                currentAllocation.setEndDate(currentEndDate);
//                                isValidEdit = true;
//                            }
//
//                        }
//                    }
//                });

        Button addButton = new Button("Add");
        HBox newAllocationFields = new HBox(10);
        newAllocationFields.setAlignment(Pos.CENTER);
        CustomComboBox projectComboBox = new CustomComboBox("Project", true);
        Label startDateLabel = new Label("Start Date");
        DatePicker startDatePicker = new DatePicker();
        Label endDateLabel = new Label("End Date");
        DatePicker endDatePicker = new DatePicker();
        newAllocationFields.getChildren().addAll(projectComboBox, startDateLabel,
                startDatePicker, endDateLabel, endDatePicker);


        projectComboBox.getComboBox().setOnMouseClicked(event ->
            {
                projectComboBox.getComboBox().getItems().clear();
                for (Project proj : Global.currentWorkspace.getProjects())
                {
                    projectComboBox.getComboBox().getItems().add(proj);
                }
            });

        addButton.setOnAction((event) ->
            {
                //            if (startDatePicker.getValue() != null)
                //            {
                //                LocalDate endDate = endDatePicker.getValue();
                //                LocalDate startDate = startDatePicker.getValue();
                //                Team selectedTeam = null;
                //
                //                for (Team team : Global.currentWorkspace.getTeams())
                //                {
                //                    if (team.toString().equals(teamComboBox.getValue()))
                //                    {
                //                        selectedTeam = team;
                //                    }
                //                }
                //
                //                Allocation alloc = new Allocation(currentProject, selectedTeam,
                //                        startDate, endDate);
                //
                //                if (validateAllocation(alloc, currentProject))
                //                {
                //                    currentProject.add(alloc);
                //                }
                //                else
                //                {
                //                    event.consume();
                //                }
                //            }
                //            else
                //            {
                //                event.consume();
                //            }
            });
        historyTable.setItems(data);
        historyTable.getColumns().addAll(teamCol, startDateCol, endDateCol);
        historyPane.getChildren().addAll(title, historyTable, newAllocationFields, addButton);

    }
}
