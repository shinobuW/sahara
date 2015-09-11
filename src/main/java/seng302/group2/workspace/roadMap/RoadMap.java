package seng302.group2.workspace.roadMap;

import javafx.collections.ObservableList;
import org.w3c.dom.Element;
import seng302.group2.scenes.sceneswitch.switchStrategies.workspace.RoadMapInformationSwitchStrategy;
import seng302.group2.scenes.sceneswitch.switchStrategies.workspace.SkillInformationSwitchStrategy;
import seng302.group2.util.reporting.ReportGenerator;
import seng302.group2.workspace.SaharaItem;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cvs20 on 11/09/15.
 */
public class RoadMap extends SaharaItem implements Serializable, Comparable<RoadMap> {
    private String shortName;
    private String longName;

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
     * Gets a roadmap short name
     *
     * @return The short name of the roadmap short
     */
    public String getShortName() {
        return this.shortName;
    }



    /**
     * Returns the items held by the RoadMap, blank as the RoadMap has no child items.
     * @return a blank hash set
     */
    @Override
    public Set<SaharaItem> getItemsSet() {
        return new HashSet<>();
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



}
