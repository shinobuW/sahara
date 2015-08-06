package seng302.group2.scenes.control.search;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import seng302.group2.scenes.JavaFxTestApp;


public class SearchableTextTest {

    /**
     * NOTE: This first block doesn't add to the tests, but allows us to text some JavaFX controls without running
     * inside a real application.
     * http://stackoverflow.com/questions/11385604/how-do-you-unit-test-a-javafx-controller-with-junit
     */
    @BeforeClass
    public static void initJFX() {
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(JavaFxTestApp.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
    }



    @Test
    public void testSetText() throws Exception {
        SearchableText sText = new SearchableText("some text");
        // Check the first text flow contains a text of the string we constructed with
        Assert.assertEquals("some text", ((Text) sText.texts.get(0).getChildren().get(0)).getText());

        sText.setText("another text");
        // Check the first text flow contains a text of the string we just set
        Assert.assertEquals("another text", ((Text)sText.texts.get(0).getChildren().get(0)).getText());
    }

    @Test
    public void testUpdateFlow() throws Exception {
        SearchableText sText = new SearchableText("some text");
        // Invalidate our use of the searchable text by adding new stuff that isn't searchable
        sText.getChildren().addAll(new Pane(), new Label("stuff"), new Text("that shouldn't be here"), new Label());

        sText.updateFlow();  // Update the text from the actual strings we use through the set

        Assert.assertEquals(1, sText.getChildren().size());
        Assert.assertEquals("some text", ((Text)sText.texts.get(0).getChildren().get(0)).getText());
    }

    @Test
    public void testQueryBasic() throws Exception {
        SearchableText sText = new SearchableText("some text");

        // Check the first text flow contains a text of the string we constructed with
        Assert.assertFalse(sText.query(""));  // empty query
        Assert.assertTrue(sText.query("sOmE t"));  // case
        Assert.assertTrue(sText.query("te"));  // second word
    }


    @Test
    public void testQueryIntermediate() throws Exception {
        SearchableText sText = new SearchableText("some text");

        // Break up of elements
        Assert.assertTrue(sText.query("text"));
        Assert.assertEquals(3, sText.getChildren().size());
        Assert.assertEquals("some ", ((Text)sText.texts.get(0).getChildren().get(0)).getText());  // before
        Assert.assertEquals("text", ((Text)sText.texts.get(1).getChildren().get(0)).getText());  // match
        Assert.assertEquals("", ((Text)sText.texts.get(2).getChildren().get(0)).getText());  // after
    }


    @Test
    public void testQueryAdvanced() throws Exception {
        SearchableText sText = new SearchableText("some text");

        // Multiple break up of elements
        Assert.assertTrue(sText.query("e"));
        Assert.assertEquals(5, sText.getChildren().size());
        Assert.assertEquals("som", ((Text)sText.texts.get(0).getChildren().get(0)).getText());  // before
        Assert.assertEquals("e", ((Text)sText.texts.get(1).getChildren().get(0)).getText());  // match
        Assert.assertEquals(" t", ((Text)sText.texts.get(2).getChildren().get(0)).getText());  // after
        Assert.assertEquals("e", ((Text)sText.texts.get(3).getChildren().get(0)).getText());  // match
        Assert.assertEquals("xt", ((Text)sText.texts.get(4).getChildren().get(0)).getText());  // after
    }
}