package seng302.group2.util.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.controlsfx.dialog.Dialogs;
import seng302.group2.Global;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * Handles the loading and saving of Global configuration and settings of the application
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
            Global.setCurrentProjectUnchanged();
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
        /*catch (FileNotFoundException e)
        {
            Dialogs.create()
                    .title("File Not Found")
                    .message("The configuration file could not be found.")
                    .showWarning();
        }
        catch (IOException e)
        {
            Dialogs.create()
                    .title("Error Loading")
                    .message("An error occurred while trying to load the file.")
                    .showException(e);
        }*/
        catch (Exception e)
        {
            // Not too worried about the configuration load failures at the moment
        }
    }
}
