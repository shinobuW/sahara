package seng302.group2.util.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.*;

/**
 * Handles the loading and saving of Global configuration and settings of the application
 * TODO: When a change is made, move into using Java Preferences instead. See below link:
 * http://docs.oracle.com/javase/8/docs/technotes/guides/preferences/index.html
 * Created by Jordane on 13/04/2015.
 */

public class ConfigLoader {
    private static String configLocation = "./config";

    /**
     * Save the current configuration
     */
    public static void saveConfig() {
        Configuration config = new Configuration();
        config.updateConfiguration();

        /* GSON SERIALIZATION */
        try (Writer writer = new FileWriter(configLocation)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(config, writer);
            writer.close();
        }
        catch (IOException e) {
//            Dialogs.create()
//                    .title("Error Saving")
//                    .message("An error occurred while trying to save Sahara's configuration "
//                            + "settings")
//                    .showException(e);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exception");
            alert.setHeaderText("Error Saving");
            alert.setContentText("An error occurred while trying to save Sahara's configuration settings");
// Create expandable Exception.
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionText = sw.toString();

            Label label = new Label("The exception stacktrace was:");

            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

            // Set expandable Exception into the dialog pane.
            alert.getDialogPane().setExpandableContent(expContent);

            alert.showAndWait();
        }
    }


    /**
     * Load the current configuration, if found
     */
    public static void loadConfig() {
        /* GSON DESERIALIZATION */
        try (Reader reader = new FileReader(configLocation)) {
            Gson gson = new GsonBuilder().create();
            Configuration config = gson.fromJson(reader, Configuration.class);
            reader.close();
            config.updateRuntime();
        }
        catch (Exception e) {
            // Not too worried about the configuration load failures at the moment
        }
    }
}
