package seng302.group2.scenes.control;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Created by jml168 on 10/05/15.
 */
public class TitleLabel extends Label
{
    public TitleLabel(String label)
    {
        this.setText(label);
        this.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
    }
}
