package seng302.group2.scenes.control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * A text field with a limited number of characters
 * Created by Jordane on 24/03/2015.
 */
public class LimitedTextField extends TextField {
    /**
     * Creates a text field with a limited number of characters, given by maxCharacters
     *
     * @param maxCharacters The max allowed characters that can be entered into this
     *                      {@link TextField}.
     */
    public LimitedTextField(final int maxCharacters) {
        final TextField thisField = this;
        this.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                // Force correct length by deleting the last entered character if too long
                if (newValue.length() > maxCharacters) {
                    thisField.deleteNextChar();
                }
            }
        });
    }
}