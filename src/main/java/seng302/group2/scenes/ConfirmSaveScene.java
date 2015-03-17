package seng302.group2.scenes;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static seng302.group2.project.Project.saveCurrentProject;

/**
 *
 * @author David Moseley drm127
 */
public class ConfirmSaveScene
{
    public static int confirmSave(Stage confirmSaveBox) 
    {
        int cancelled = 0;
        //Stage confirmSaveBox = new Stage();
        confirmSaveBox.setTitle("Save Current Project?");
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Scene stageScene = new Scene(grid, 500, 250);
        confirmSaveBox.setScene(stageScene);
        
        Text sceneTitle = new Text("You can only have one project open at a time.\n"
                + "Do you wish to save the current project?");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);
        
        Button saveButton = new Button("Save");
        Button dontSaveButton = new Button("Don't Save");
        Button cancelButton = new Button("Cancel");
        
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(saveButton);
        hbBtn.getChildren().add(dontSaveButton);
        hbBtn.getChildren().add(cancelButton);
        grid.add(hbBtn, 1, 7);
        
        confirmSaveBox.show();
        confirmSaveBox.setAlwaysOnTop(true);
        confirmSaveBox.toFront();
        
        saveButton.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event)
            {
                try
                {
                    saveCurrentProject();
                    confirmSaveBox.close();
                }
                catch (IOException ex)
                {
                    Logger.getLogger(ConfirmSaveScene.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        dontSaveButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                confirmSaveBox.close();
            }
        });
        
        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                confirmSaveBox.close();
            }
        });
        return cancelled;
    }
}
