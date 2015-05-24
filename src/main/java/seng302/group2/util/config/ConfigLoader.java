package seng302.group2.util.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.controlsfx.dialog.Dialogs;

import java.io.*;

/**
 * Handles the loading and saving of Global configuration and settings of the application
 * TODO: When a change is made, move into using Java Preferences instead. See below link:
 * http://docs.oracle.com/javase/8/docs/technotes/guides/preferences/index.html
 * Created by Jordane on 13/04/2015.
 */
@SuppressWarnings("deprecation")
public class ConfigLoader
{
    private static String configLocation = "./config";

    /**
     * Save the current configuration
     */
    public static void saveConfig()
    {
        Configuration config = new Configuration();
        config.updateConfiguration();

        /* GSON SERIALIZATION */
        try (Writer writer = new FileWriter(configLocation))
        {
            Gson gson = new GsonBuilder().create();
            gson.toJson(config, writer);
            writer.close();
        }
        catch (IOException e)
        {
            Dialogs.create()
                    .title("Error Saving")
                    .message("An error occurred while trying to save Sahara's configuration "
                            + "settings")
                    .showException(e);
        }
    }


    /**
     * Load the current configuration, if found
     */
    public static void loadConfig()
    {
        /* GSON DESERIALIZATION */
        try (Reader reader = new FileReader(configLocation))
        {
            Gson gson = new GsonBuilder().create();
            Configuration config = gson.fromJson(reader, Configuration.class);
            reader.close();
            config.updateRuntime();
        }
        catch (Exception e)
        {
            // Not too worried about the configuration load failures at the moment
        }
    }
}
