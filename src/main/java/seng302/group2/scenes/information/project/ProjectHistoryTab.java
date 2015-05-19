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
import seng302.group2.scenes.control.CustomDatePicker;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.util.validation.ValidationStatus;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.team.Allocation;
import seng302.group2.workspace.team.Team;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static seng302.group2.util.validation.DateValidator.validAllocation;
import static seng302.group2.util.validation.DateValidator.validateAllocation;


/**
 * A class for history allocation tab to display a table of the selected project's allocation
 * history
 * Created by swi67 on 10/05/15.
 */
public class ProjectHistoryTab extends Tab
{
    private boolean isValidEdit = false;

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
        historyTable.setStyle("-fx-pref-width: 750;");
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
                public void handle(TableColumn.CellEditEvent<Allocation, String> event)
                {
                    if (!event.getNewValue().isEmpty())
                    {

                        Allocation currentAllocation = (Allocation) event.getTableView().getItems()
                                .get(event.getTablePosition().getRow());
                        Project proj = currentAllocation.getProject();

                        LocalDate currentStartDate = currentAllocation.getStartDate();
                        LocalDate newStartDate = LocalDate.parse(event.getNewValue(),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        currentAllocation.setStartDate(newStartDate);

                        if (validAllocation(currentAllocation, proj) == ValidationStatus.VALID)
                        {
                            isValidEdit = false;
                        }
                        else
                        {
                            currentAllocation.setStartDate(currentStartDate);
                            isValidEdit = true;
                        }
                    }
                }
            });
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
                    public void handle(TableColumn.CellEditEvent<Allocation, String> event)
                    {
                        if (!event.getNewValue().isEmpty())
                        {

                            Allocation currentAllocation = (Allocation) event.getTableView().
                                    getItems().get(event.getTablePosition().getRow());
                            Project proj = currentAllocation.getProject();

                            LocalDate currentEndDate = currentAllocation.getEndDate();
                            LocalDate newEndDate = LocalDate.parse(event.getNewValue(),
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            currentAllocation.setEndDate(newEndDate);

                            if (validateAllocation(currentAllocation, proj))
                            {
                                isValidEdit = false;
                            }
                            else
                            {
                                currentAllocation.setEndDate(currentEndDate);
                                isValidEdit = true;
                            }

                        }
                    }
                });

        Button addButton = new Button("Add");
        HBox newAllocationFields = new HBox(10);
        newAllocationFields.setAlignment(Pos.CENTER);
        CustomComboBox teamComboBox = new CustomComboBox("Team", true);
        CustomDatePicker startDatePicker = new CustomDatePicker("Start Date", true);
        CustomDatePicker endDatePicker = new CustomDatePicker("End Date", false);
        startDatePicker.setStyle("-fx-pref-width: 200;");
        endDatePicker.setStyle("-fx-pref-width: 200;");
        teamComboBox.prefWidthProperty().bind(historyTable.widthProperty()
                .subtract(3).divide(100).multiply(30));
        startDatePicker.prefWidthProperty().bind(historyTable.widthProperty()
                .subtract(3).divide(100).multiply(30));
        endDatePicker.prefWidthProperty().bind(historyTable.widthProperty()
                .subtract(3).divide(100).multiply(30));
        newAllocationFields.getChildren().addAll(teamComboBox,
                startDatePicker, endDatePicker);

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
                teamComboBox.hideErrorField();
                startDatePicker.hideErrorField();
                if (teamComboBox.getValue() != null && startDatePicker.getValue() != null)
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
                    if (teamComboBox.getValue() == null)
                    {
                        teamComboBox.showErrorField("Please select a team");
                        event.consume();
                    }
                    if (startDatePicker.getValue() == null)
                    {
                        startDatePicker.showErrorField("Please select a date");
                        event.consume();
                    }
                }
            });
        historyTable.setItems(data);
        historyTable.getColumns().addAll(teamCol, startDateCol, endDateCol);
        historyPane.getChildren().addAll(title, historyTable, newAllocationFields, addButton);
    }

    class EditingCell extends TableCell<Allocation, String>
    {

        public DatePicker datePicker;

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
                    if (datePicker == null)
                    {
                        setText(getString());
                    }
                    else
                    {
                        if (datePicker.getValue() == null)
                        {
                            setText(getString());
                        }
                        else if (!isValidEdit)
                        {
                            setText(datePicker.getValue().format(Global.dateFormatter));
                        }
                    }

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


