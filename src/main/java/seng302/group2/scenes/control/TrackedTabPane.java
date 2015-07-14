package seng302.group2.scenes.control;

import javafx.scene.control.TabPane;

import java.util.HashMap;
import java.util.Map;

/**
 * A Tab Pane extension that keeps a tracked record of the last open tabs for each of its extending
 * classes.
 * Created by Jordane on 10/05/2015.
 */
public class TrackedTabPane extends TabPane {
    /**
     * An enumeration of scenes in the project
     */
    public enum ContentScene {
        PERSON,
        PERSON_EDIT,
        PERSON_CATEGORY,
        PROJECT,
        PROJECT_EDIT,
        PROJECT_CATEGORY,
        WORKSPACE,
        WORKSPACE_EDIT,
        TEAM,
        TEAM_EDIT,
        TEAM_CATEGORY,
        ROLE,
        ROLE_EDIT,
        ROLE_CATEGORY,
        SKILL,
        SKILL_EDIT,
        SKILL_CATEGORY,
        RELEASE,
        RELEASE_EDIT,
        RELEASE_CATEGORY,
        STORY,
        STORY_EDIT,
        STORY_CATEGORY,
        BACKLOG,
        BACKLOG_EDIT,
        BACKLOG_CATEGORY
    }

    /**
     * A map to keep of each of the last selected tabs in the scenes
     */
    public static Map<ContentScene, Integer> contentTabs = new HashMap<>();

    private ContentScene scene;

    /**
     * Constructor that takes the content scene type to use for storing tracking information
     *
     * @param scene The scene of the tracked tab pane
     */
    public TrackedTabPane(ContentScene scene) {
        this.scene = scene;
        this.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        openLastTab();

        this.getSelectionModel().selectedItemProperty().addListener((event) -> {
                contentTabs.put(scene,
                        this.getSelectionModel().getSelectedIndex());
                //System.out.println("saving last tab");
            });
    }


    /**
     * Opens the last tab stored for the type of scene
     */
    public void openLastTab() {
        Integer lastTab = 0;
        if (scene != null) {
            //System.out.println("opening last tab from " + scene.toString());
            lastTab = contentTabs.get(scene);
        }
        if (lastTab != null) {
            this.getSelectionModel().select(lastTab);
        }
    }
}