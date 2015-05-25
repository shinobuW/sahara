package seng302.group2.scenes.control;

import javafx.scene.control.TabPane;
import seng302.group2.scenes.SceneSwitcher;

import java.util.HashMap;
import java.util.Map;

/**
 * A Tab Pane extension that keeps a tracked record of the last open tabs for each of its extending
 * classes.
 * Created by Jordane on 10/05/2015.
 */
public class TrackedTabPane extends TabPane
{
    /**
     * A map to keep of each of the last selected tabs in the scenes
     */
    public static Map<SceneSwitcher.ContentScene, Integer> contentTabs = new HashMap<>();

    private SceneSwitcher.ContentScene scene;

    /**
     * Constructor that takes the content scene type to use for storing tracking information
     * @param scene The scene of the tracked tab pane
     */
    public TrackedTabPane(SceneSwitcher.ContentScene scene)
    {
        this.scene = scene;
        this.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        openLastTab();

        this.getSelectionModel().selectedItemProperty().addListener((event) ->
            {
                contentTabs.put(scene,
                        this.getSelectionModel().getSelectedIndex());
                ////System.out.println("saving last tab");
            });
    }


    /**
     * Opens the last tab stored for the type of scene
     */
    public void openLastTab()
    {
        Integer lastTab = 0;
        if (scene != null)
        {
            ////System.out.println("opening last tab from " + scene.toString());
            lastTab = contentTabs.get(scene);
        }
        if (lastTab != null)
        {
            this.getSelectionModel().select(lastTab);
        }
    }
}
