package seng302.group2.scenes.menu;

import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import seng302.group2.Global;
import seng302.group2.scenes.control.search.SearchBox;


/**
 * The main toolbar used in the main application window for fast-use, general functionality.
 */
public class MainToolbar extends ToolBar {



    Button newButton = new Button();
    Button openButton = new Button();
    Button saveButton = new Button();
    static Button undoButton = new Button();
    static Button redoButton = new Button();
    Button generateButton = new Button();


    SearchBox searchBox = new SearchBox();


    /**
     * The constructor for the tool bar
     */
    public MainToolbar() {
        setActions();

        HBox leftRightSeparator = new HBox();
        HBox.setHgrow(leftRightSeparator, Priority.ALWAYS);

        this.getItems().addAll(
                newButton,
                openButton,
                saveButton,
                new Separator(),
                undoButton,
                redoButton,
                new Separator(),
                generateButton,
                leftRightSeparator,
                searchBox
        );
    }

    private void setActions() {

        Image imageNew = new Image("icons/document-new.png");
        Image imageOpen = new Image("icons/document-open.png");
        Image imageSave = new Image("icons/document-save.png");
        Image imageUndo = new Image("icons/edit-undo.png");
        Image imageRedo = new Image("icons/edit-redo.png");
        Image imageGenerate = new Image("icons/document-export.png");

        newButton.setGraphic(new ImageView(imageNew));
        openButton.setGraphic(new ImageView(imageOpen));
        saveButton.setGraphic(new ImageView(imageSave));
        undoButton.setGraphic(new ImageView(imageUndo));
        redoButton.setGraphic(new ImageView(imageRedo));
        generateButton.setGraphic(new ImageView(imageGenerate));

        Tooltip toolNew = new Tooltip("New Workspace");
        Tooltip toolOpen = new Tooltip("Open Workspace");
        Tooltip toolSave = new Tooltip("Save Workspace");
        Tooltip toolUndo = new Tooltip("Undo");
        Tooltip toolRedo = new Tooltip("Redo");
        Tooltip toolGenerate = new Tooltip("Generate Report");

        Tooltip.install(newButton, toolNew);
        Tooltip.install(openButton, toolOpen);
        Tooltip.install(saveButton, toolSave);
        Tooltip.install(undoButton, toolUndo);
        Tooltip.install(redoButton, toolRedo);
        Tooltip.install(generateButton, toolGenerate);


        newButton.setOnAction(e -> MainMenuBar.newWorkspaceAction());
        openButton.setOnAction(e -> MainMenuBar.loadAction());
        saveButton.setOnAction(e -> MainMenuBar.saveAction());
        undoButton.setOnAction((event) -> MainMenuBar.undoAction());
        redoButton.setOnAction((event) -> MainMenuBar.redoAction());
        generateButton.setOnAction((event) -> MainMenuBar.reportAction());

        undoRedoToggle();
    }

    /**
     * Sets the focus to the search field of the toolbar
     */
    public void focusSearch() {
        searchBox.requestFocus();
    }

    /**
     * Disables / Enables the undo/redo button depending on whether the
     * commands are available.
     */
    public static void undoRedoToggle() {
        MainToolbar.undoButton.setDisable(!Global.commandManager.isUndoAvailable());
        MainToolbar.redoButton.setDisable(!Global.commandManager.isRedoAvailable());
    }
}
