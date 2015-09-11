package seng302.group2.workspace.roadMap;

import javafx.collections.ObservableList;
import org.w3c.dom.Element;
import seng302.group2.scenes.sceneswitch.switchStrategies.workspace.RoadMapInformationSwitchStrategy;
import seng302.group2.scenes.sceneswitch.switchStrategies.workspace.SkillInformationSwitchStrategy;
import seng302.group2.util.reporting.ReportGenerator;
import seng302.group2.workspace.SaharaItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static javafx.collections.FXCollections.observableArrayList;
import seng302.group2.Global;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.project.Project;
import seng302.group2.workspace.project.release.Release;
import seng302.group2.workspace.project.sprint.Sprint;
import seng302.group2.workspace.project.story.Story;

/**
 * RoadMap Model class.
 * 
 * Created by cvs20 on 11/09/15.
 */
public class RoadMap extends SaharaItem implements Serializable, Comparable<RoadMap> {
    private String shortName;
    private String longName;
    private transient ObservableList<Release> releases = observableArrayList();
    private List<Release> serializableReleases = new ArrayList<>();

    
    

    /**
     * Basic RoadMap constructor
     */
    public RoadMap() {
        super("Untitled RoadMap");
        this.shortName = "Untitled RoadMap";
        this.longName = "Untitled Long RoadMap";

        setInformationSwitchStrategy(new RoadMapInformationSwitchStrategy());
    }
    
    /**
     * Basic RoadMap constructor
     */
    public RoadMap(String shortName, String longName) {
        super(shortName);
        this.shortName = shortName;
        this.longName = longName;

        setInformationSwitchStrategy(new RoadMapInformationSwitchStrategy());
    }

    /**
     * Gets a roadmap short name
     *
     * @return The short name of the roadmap
     */
    public String getShortName() {
        return this.shortName;
    }
    
    /**
     * Gets a roadmap long name
     *
     * @return The long name of the roadmap
     */
    public String getLongName() {
        return this.longName;
    }
    
    /**
     * Gets the releases of the RoadMap
     *
     * @return list of releases
     */
    public ObservableList<Release> getReleases() {
        return this.releases;
    }
    
    /**
     * Adds a Release to the RoadMaps's list of Releases.
     *
     * @param release The release to add
     */
    public void add(Release release) {
        Command command = new AddReleaseCommand(this, release);
        Global.commandManager.executeCommand(command);
    }
    
    /**
     * Adds a Release to the RoadMaps's list of Releases.
     *
     * @param release The release to add
     */
    public void edit(String shortName, Collection<Release> releases) {
        Command command = new RoadMapEditCommand(this, shortName, releases);
        Global.commandManager.executeCommand(command);
    }
    
    /**
     * Deletes the RoadMap 
     */
    public void deleteRoadMap() {
        Command command = new DeleteRoadMapCommand(this);
        Global.commandManager.executeCommand(command);
    }

    

    /**
     * Returns the items held by the RoadMap, blank as the RoadMap has no child items.
     * @return a blank hash set
     */
    @Override
    public Set<SaharaItem> getItemsSet() {
        Set<SaharaItem> items = new HashSet<>();
        for (Release release : releases) {
            items.addAll(release.getItemsSet());
        }
        items.addAll(releases);
        return items;
    }

    /**
     * Gets the children of the SaharaItem
     *
     * @return The items of the SaharaItem
     */
    @Override
    public ObservableList<SaharaItem> getChildren() {
        return null;
    }

    /**
     * Compares the RoadMap to another RoadMap by their short names
     *
     * @param compareRoadmap The skill to compare to
     * @return The result of the string comparison between the teams' short names
     */
    @Override
    public int compareTo(RoadMap compareRoadmap) {
        String roadMap1ShortName = this.getShortName();
        String roadMap2ShortName = compareRoadmap.getShortName();
        return roadMap1ShortName.compareTo(roadMap2ShortName);
    }

    /**
     * An overridden version for the String representation of a RoadMap
     *
     * @return The short name of the RoadMap
     */
    @Override
    public String toString() {
        return this.shortName;
    }

    /**
     * Method for creating an XML element for the Skill within report generation
     * @return element for XML generation
     */
    @Override
    public Element generateXML() {
        return null;
    }
 
    
    /**
     * A command class for allowing the adding of Releases to RoadMaps.
     */
    private class AddReleaseCommand implements Command {
        private Release release;
        private RoadMap roadMap;
        
        /**
         * Constructor for the release addition command.
         * @param roadMap The roadMap to which the release is being added.
         * @param release The release to be added.
         */
        AddReleaseCommand(RoadMap roadMap, Release release) {
            this.roadMap = roadMap;
            this.release = release;
        }

        /**
         * Executes the release addition command.
         */
        public void execute() {
            roadMap.getReleases().add(release);
            
        }

        /**
         * Undoes the release addition command.
         */
        public void undo() {
            roadMap.getReleases().remove(release);
        }

        /**
         * Searches the stateObjects to find an equal model class to map to
         * @param stateObjects A set of objects to search through
         * @return If the item was successfully mapped
         */
        @Override
        public boolean map(Set<SaharaItem> stateObjects) {
            boolean mapped = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(roadMap)) {
                    this.roadMap = (RoadMap) item;
                    mapped = true;
                }
            }
            boolean mapped_release = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(release)) {
                    this.release = (Release) item;
                    mapped_release = true;
                }
            }
            return mapped && mapped_release;
        }
    }

    /**
     * A command class for allowing the editting of RoadMaps.
     */
    private class RoadMapEditCommand implements Command {
        private RoadMap roadMap;
        
        private String shortName;
        private Collection<Release> releases = new HashSet<>();
        
        private String oldShortName;
        private Collection<Release> oldReleases = new HashSet<>();
        
        /**
         * Constructor for the release addition command.
         * @param roadMap The roadMap to which the release is being added.
         * @param release The release to be added.
         */
        RoadMapEditCommand(RoadMap roadMap, String shortName, Collection<Release> releases) {
            this.roadMap = roadMap;
            this.releases = releases;
            this.shortName = shortName;
            
            this.oldShortName = roadMap.getShortName();
            this.oldReleases = roadMap.getReleases();
        }

        /**
         * Executes the release addition command.
         */
        public void execute() {
            roadMap.shortName = shortName;
            
            roadMap.releases.removeAll(oldReleases);
            roadMap.releases.addAll(releases);
            
        }

        /**
         * Undoes the release addition command.
         */
        public void undo() {
            roadMap.shortName = oldShortName;
            
            roadMap.releases.removeAll(releases);
            roadMap.releases.addAll(oldReleases);
        }

        /**
         * Searches the stateObjects to find an equal model class to map to
         * @param stateObjects A set of objects to search through
         * @return If the item was successfully mapped
         */
        @Override
        public boolean map(Set<SaharaItem> stateObjects) {
            boolean mapped = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(roadMap)) {
                    this.roadMap = (RoadMap) item;
                    mapped = true;
                }
            }
            for (Release release : releases) {
                for (SaharaItem item : stateObjects) {
                    if (item.equivalentTo(release)) {
                        releases.remove(release);
                        releases.add((Release)item);
                        break;
                    }
                }
            }

            for (Release release : oldReleases) {
                for (SaharaItem item : stateObjects) {
                    if (item.equivalentTo(release)) {
                        oldReleases.remove(release);
                        oldReleases.add((Release)item);
                        break;
                    }
                }
            }
            return mapped;
        }
    }


    /**
     * A command class for allowing the deletion of RoadMaps from a Workspace.
     */
    private class DeleteRoadMapCommand implements Command {
        private RoadMap roadMap;

        /**
         * Constructor for the roadMap deletion command
         * @param roadMap The roadMap to be deleted
         */
        DeleteRoadMapCommand(RoadMap roadMap) {
            this.roadMap = roadMap;
        }

        /**
         * Executes the roadMap deletion command
         */
        public void execute() {
            Global.currentWorkspace.getRoadMaps().remove(roadMap);
        }

        /**
         * Undoes the roadMap deletion command
         */
        public void undo() {
            Global.currentWorkspace.getRoadMaps().add(roadMap);
        }

        /**
         * Searches the stateObjects to find an equal model class to map to
         * @param stateObjects A set of objects to search through
         * @return If the item was successfully mapped
         */
        @Override
        public boolean map(Set<SaharaItem> stateObjects) {
            boolean mapped = false;
            for (SaharaItem item : stateObjects) {
                if (item.equivalentTo(roadMap)) {
                    this.roadMap = (RoadMap) item;
                    mapped = true;
                }
            }
            return mapped;
        }
    }
}
