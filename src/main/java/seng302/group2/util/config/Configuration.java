package seng302.group2.util.config;

import seng302.group2.Global;

/**
 * Holds global settings for config serialization.
 * Created by Jordane on 13/04/2015.
 */
public class Configuration
{
    private String lastSaveLocation = null;


    /**
     * Constructor that automatically updates itself on creation
     */
    public Configuration()
    {
        updateConfiguration();
    }


    /**
     * Updates the configuration members
     */
    public void updateConfiguration()
    {
        //System.out.println(lastSaveLocation); // Testing
        this.lastSaveLocation = Global.lastSaveLocation;
    }


    /**
     * Updates the application runtime with the configuration values.
     */
    public void updateRuntime()
    {
        Global.lastSaveLocation = this.lastSaveLocation;
    }


    /**
     * Gets the configuration's last save location
     * @return the last save location
     */
    public String getLastSaveLocation()
    {
        return lastSaveLocation;
    }


    /**
     * Sets the configuration's last save location
     * @param lastSaveLocation the last save location to set
     */
    public void setLastSaveLocation(String lastSaveLocation)
    {
        this.lastSaveLocation = lastSaveLocation;
    }
}
