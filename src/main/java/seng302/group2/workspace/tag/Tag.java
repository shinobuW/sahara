package seng302.group2.workspace.tag;

import org.w3c.dom.Element;
import seng302.group2.workspace.SaharaItem;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * Created by btm38 on 10/09/15.
 */
public class Tag extends SaharaItem {

    /**
     * SaharaItems 'belonging' to the Tag
     *
     * @return Tag empty set as tags do not have any items belonging to
     */
    @Override
    public Set<SaharaItem> getItemsSet() {
        return new HashSet<>();
    }

    /**
     * Method for creating an XML element for the Log within report generation
     *
     * @return element for XML generation
     */
    @Override
    public Element generateXML() {
        return null;
    }
}