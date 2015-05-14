package seng302.group2.scenes.information.project;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static seng302.group2.util.validation.DateValidator.validateAllocation;


/**
 * A class for history allocation tab to display a table of the selected project's allocation
 * history
 * Created by swi67 on 10/05/15.
 */
public class ProjectHistoryTab extends Tab
{
    /**
     * Constructor for project allocation tab
     * @param currentProject currently selected project
     */
    public ProjectHistoryTab(Project currentProject)
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
        ObservableList<Allocation> data = currentProject.getTeamAllocations();

        Callback<TableColumn, TableCell> cellFactory = new Callback<TableColumn, TableCell>()
        {
            public TableCell call(TableColumn col)
            {
                return new EditingCell();
            }
        };

        Label title = new TitleLabel("Allocation History");

        TableColumn teamCol = new TableColumn("Team");
        teamCol.setCellValueFactory(new PropertyValueFactory<Allocation, String>("Team"));
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
        startDateCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Allocation, String>>()
                {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Allocation, String> tableCol)
                    {
                        ((Allocation) tableCol.getTableView().getItems().get(
                                tableCol.getTablePosition().getRow())
                        ).setStartDate(LocalDate.parse(tableCol.getNewValue(),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    }
                }
        );
        startDateCol.setCellFactory(cellFactory);
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
        endDateCol.setCellFactory(cellFactory);
        endDateCol.setEditable(true);
        endDateCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Allocation, String>>()
                {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Allocation, String> col)
                    {
                        if (!col.getNewValue().isEmpty())
                        {
                            ((Allocation) col.getTableView().getItems().get(
                                    col.getTablePosition().getRow())
                            ).setEndDate(LocalDate.parse(col.getNewValue(),
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        }
                    }
                }
        );

        Button addButton = new Button("Add");
        HBox newAllocationFields = new HBox(10);
        newAllocationFields.setAlignment(Pos.CENTER);
        CustomComboBox teamComboBox = new CustomComboBox("Team", true);
        Label startDateLabel = new Label("Start Date");
        DatePicker startDatePicker = new DatePicker();

        Label endDateLabel = new Label("End Date");
        DatePicker endDatePicker = new DatePicker();
        newAllocationFields.getChildren().addAll(teamComboBox, startDateLabel,
                startDatePicker, endDateLabel, endDatePicker);

        teamComboBox.getComboBox().setOnMouseClicked(event ->
            {
                teamComboBox.getComboBox().getItems().clear();
                for (Team team : Global.currentWorkspace.getTeams())
                {
                    teamComboBox.getComboBox().getItems().add(team);
                }
                // Remove the unassigned team
                teamComboBox.getComboBox().getItems().remove(Global.getUnassignedTeam());
            });

        addButton.setOnAction((event) ->
            {
                if (startDatePicker.getValue() != null)
                {
                    LocalDate endDate = endDatePicker.getValue();
                    LocalDate startDate = startDatePicker.getValue();
                    Team selectedTeam = null;

                    for (Team team : Global.currentWorkspace.getTeams())
                    {
                        if (team.toString().equals(teamComboBox.getValue()))
                        {
                            selectedTeam = team;
                        }
                    }

                    Allocation alloc = new Allocation(currentProject, selectedTeam,
                            startDate, endDate);

                    if (validateAllocation(alloc, currentProject))
                    {
                        currentProject.add(alloc);
                    }
                    else
                    {
                        event.consume();
                    }
                }
                else
                {
                    event.consume();
                }
            });
        historyTable.setItems(data);
        historyTable.getColumns().addAll(teamCol, startDateCol, endDateCol);
        historyPane.getChildren().addAll(title, historyTable, newAllocationFields, addButton);
    }

    class EditingCell extends TableCell<Allocation, String>
    {

        private DatePicker datePicker;

        public EditingCell()
        {
        }

        @Override
        public void startEdit()
        {
            if (!isEmpty())
            {
                super.startEdit();
                createTextField();
                setGraphic(datePicker);

                if (!getText().isEmpty())
                {
                    datePicker.setValue(LocalDate.parse(getText(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
                else
                {
                    datePicker.setValue(null);
                }
                Platform.runLater(() ->
                    {
                        datePicker.requestFocus();
                    });

            }
        }

        @Override
        public void cancelEdit()
        {
            super.cancelEdit();
            setGraphic(null);
        }

        @Override
        public void updateItem(String item, boolean empty)
        {
            super.updateItem(item, empty);
            if (empty)
            {
                setText(null);
                setGraphic(null);
            }
            else
            {
                if (isEditing())
                {
                    if (!getItem().toString().isEmpty())
                    {
                        datePicker.setValue(LocalDate.parse(getItem(),
                                DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    }
                    setGraphic(datePicker);
                }
                else
                {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }

        private void createTextField()
        {
            datePicker = new DatePicker();
            datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            datePicker.focusedProperty().addListener(new ChangeListener<Boolean>()
            {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0,
                                    Boolean arg1, Boolean arg2)
                {
                    if (!arg2)
                    {
                        if (datePicker.getValue() != null)
                        {
                            commitEdit(datePicker.getValue().toString());
                        }
                        else
                        {
                            commitEdit("");
                        }
                    }
                    else
                    {
                        updateItem(getItem(), false);
                    }
                }
            });
        }

        private String getString()
        {
            LocalDate date;

            if (getItem().toString().isEmpty())
            {
                return getItem().toString();
            }
            else
            {
                if (getItem().toString().matches("([0-9]{2})/([0-9]{2})/([0-9]{4})"))
                {
                    date = LocalDate.parse(getItem().toString(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                }
                else
                {
                    date = LocalDate.parse(getItem().toString(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
                String dateString = date.format(Global.dateFormatter);
                return getItem() == null ? "" : dateString;
            }
        }
    }

}


