package seng302.group2.scenes;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * A test application to initialise the JavaFX Toolkit for usage in simple tests of custom FX controls and certain
 * controller classes that interact with GUI elements.
 * Created by jml168 on 7/08/15.
 */
public class JavaFxTestApp extends Application {

    /**
     * NOTE: This class doesn't add to the unit tests, but allows us to test some JavaFX controls without running
     * inside a real application.
     * http://stackoverflow.com/questions/11385604/how-do-you-unit-test-a-javafx-controller-with-junit
     */

    @Override
    public void start(Stage primaryStage) throws Exception {
        // noop
    }

    public static void initJFX() {
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(JavaFxTestApp.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
    }
}
