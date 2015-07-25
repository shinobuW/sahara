package seng302.group2.scenes.menu;

import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;


/**
 * The main toolbar used in the main application window for fast-use, general functionality.
 */
public class MainToolbar extends ToolBar {

    static Button newButton = new Button("New");
    static Button openButton = new Button("Open");
    static Button saveButton = new Button("Save");
    static Button undoButton = new Button("Undo");
    static Button redoButton = new Button("Redo");
    static Button generateButton = new Button("Generate");


    /**
     * The constructor for the tool bar
     */
    public MainToolbar() {
        setActions();
        this.getItems().addAll(
                newButton,
                openButton,
                saveButton,
                new Separator(),
                undoButton,
                redoButton,
                new Separator(),
                generateButton
        );
    }

    private void setActions() {
        newButton.setOnAction(e -> MainMenuBar.newWorkspaceAction());
        openButton.setOnAction(e -> MainMenuBar.loadAction());
        saveButton.setOnAction(e -> MainMenuBar.saveAction());
        undoButton.setOnAction((event) -> MainMenuBar.undoAction());
        redoButton.setOnAction((event) -> MainMenuBar.redoAction());
        generateButton.setOnAction((event) -> MainMenuBar.reportAction());
    }
}
