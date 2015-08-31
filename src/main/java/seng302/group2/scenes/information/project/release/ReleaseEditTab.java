package seng302.group2.scenes.information.project.release;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import seng302.group2.App;
import seng302.group2.scenes.control.CustomDatePicker;
import seng302.group2.scenes.control.CustomTextArea;
import seng302.group2.scenes.control.RequiredField;
import seng302.group2.scenes.control.search.SearchableControl;
import seng302.group2.scenes.control.search.SearchableTab;
import seng302.group2.scenes.validation.ValidationStyle;
import seng302.group2.util.validation.DateValidator;
import seng302.group2.util.validation.ShortNameValidator;
import seng302.group2.workspace.project.release.Release;
import seng302.group2.workspace.project.sprint.Sprint;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A class for displaying a tab used to edit releases.
 * Created by btm38 on 30/07/15.
 */
public class ReleaseEditTab extends SearchableTab {

    List<SearchableControl> searchControls = new ArrayList<>();
    /**
     * Constructor for the ReleaseEditTab class. This constructor creates a JavaFX ScrollPane
     * which is populated with relevant controls then shown.
     *
     * @param currentRelease the release being edited
     */
    public ReleaseEditTab(Release currentRelease) {
        // Tab settings
        this.setText("Edit Release");
        Pane editPane = new VBox(10);
        editPane.setBorder(null);
        editPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(editPane);
        this.setContent(wrapper);

        // Create controls
        RequiredField shortNameCustomField = new RequiredField("Short Name:");
        CustomTextArea descriptionTextArea = new CustomTextArea("Release Description:", 300);
        CustomDatePicker releaseDatePicker = new CustomDatePicker("Estimated Release Date:", false);


        shortNameCustomField.setPrefWidth(300);
        descriptionTextArea.setPrefWidth(300);
        releaseDatePicker.setPrefWidth(300);

        Button btnCancel = new Button("Cancel");
        Button btnDone = new Button("Done");

        HBox buttons = new HBox();
        buttons.spacingProperty().setValue(10);
        buttons.alignmentProperty().set(Pos.TOP_LEFT);
        buttons.getChildren().addAll(btnDone, btnCancel);

        shortNameCustomField.setText(currentRelease.getShortName());
        descriptionTextArea.setText(currentRelease.getDescription());
        releaseDatePicker.setValue(currentRelease.getEstimatedDate());

        final Callback<DatePicker, DateCell> releaseDateCellFactory =
            new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            LocalDate lastSprintEnd = LocalDate.now();
                            for (Sprint sprint : currentRelease.getProject().getSprints()) {
                                if (sprint.getRelease() == currentRelease
                                        && sprint.getEndDate().isAfter(lastSprintEnd)) {
                                    lastSprintEnd = sprint.getEndDate();
                                }
                            }
                            if (item.isBefore(lastSprintEnd)) {
                                setDisable(true);
                                setStyle("-fx-background-color: #ffc0cb;");
                            }
                        }
                    };
                }
            };
        releaseDatePicker.getDatePicker().setDayCellFactory(releaseDateCellFactory);

        releaseDatePicker.getDatePicker().valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable,
                                LocalDate oldValue, LocalDate newValue) {
                LocalDate lastSprintEnd = LocalDate.now();
                for (Sprint sprint : currentRelease.getProject().getSprints()) {
                    if (sprint.getRelease() == currentRelease && sprint.getEndDate().isAfter(lastSprintEnd)) {
                        lastSprintEnd = sprint.getEndDate();
                    }
                }
                if (newValue.isBefore(lastSprintEnd)) {
                    ValidationStyle.borderGlowRed(releaseDatePicker.getDatePicker());
                    ValidationStyle.showMessage("The estimated date of release cannot be in the past, or before the end"
                            + " date of any sprint that exists for this release", releaseDatePicker.getDatePicker());
                    btnDone.setDisable(true);
                }
                else {
                    ValidationStyle.borderGlowNone(releaseDatePicker.getDatePicker());
                    btnDone.setDisable(false);
                }
            }
        });


        btnDone.setOnAction((event) -> {
                boolean shortNameUnchanged = shortNameCustomField.getText().equals(
                        currentRelease.getShortName());
                boolean descriptionUnchanged = descriptionTextArea.getText().equals(
                        currentRelease.getDescription());
                if (shortNameUnchanged && descriptionUnchanged) {
                    // No fields have been changed
                    currentRelease.switchToInfoScene();
                    return;
                }

                LocalDate releaseDate = releaseDatePicker.getValue();

                if (releaseDatePicker.getValue() == null) {
                    releaseDate = null;
                }
                else {
                    if (!DateValidator.isFutureDate(releaseDate)) {
                        ValidationStyle.borderGlowRed(releaseDatePicker.getDatePicker());
                        ValidationStyle.showMessage("Date must be a future date", releaseDatePicker.getDatePicker());
                    }
                }

                boolean correctShortName = ShortNameValidator.validateShortName(shortNameCustomField,
                        currentRelease.getShortName());
                // The short name is the same or valid
                if (correctShortName) {

                    currentRelease.edit(shortNameCustomField.getText(),
                            descriptionTextArea.getText(),
                            releaseDate
                    );

                    Collections.sort(currentRelease.getProject().getReleases());
                    currentRelease.switchToInfoScene();
                    App.mainPane.refreshTree();
                }
                else {
                    // One or more fields incorrectly validated, stay on the edit scene
                    event.consume();
                }
            });

        btnCancel.setOnAction((event) -> {
                currentRelease.switchToInfoScene();
            });

        // Add items to pane & search collection
        editPane.getChildren().addAll(
                shortNameCustomField,
                descriptionTextArea,
                releaseDatePicker,
                buttons
        );

        Collections.addAll(searchControls,
                shortNameCustomField,
                descriptionTextArea,
                releaseDatePicker
        );
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

