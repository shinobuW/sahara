/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * A class for holding JavaFX scenes used in the project
 * @author Jordane
 */
public final class Scenes {
    public static Scene getInitialScene() {
        // Set up a grid pane for everything in the window
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));
        
        // Adds a title to the grid
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1); // At top-left (0, 0), spanning 2 columns and 1 row

        // A button to switch to the demo scene
        Button btn = new Button("Switch to demo scene");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        
        //The button event handler
        btn.setOnAction((event) -> {
            // Gets the stage of the scene of the button
            Stage stage = (Stage) btn.getScene().getWindow();
            // Set the new scene of the stage
            stage.setScene(Scenes.getDemoScene());
        });

        return new Scene(grid, 400, 275);
    }
    
    
    public static Scene getDemoScene() {
        // Set up the grid pane
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));
        
        // The title
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        // The username label and field
        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        // The password label and field
        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        
        // The sign in button red text message
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 5);
        
        // The sign in button
        Button btn = new Button("Sign in");
        //The button event handler
        btn.setOnAction((event) -> {
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Sign in button pressed");
            pwBox.setText("");
            userTextField.setText("");
        });
        
        // The back button
        Button btnBack = new Button("Back");
        //The back button event handler
        btnBack.setOnAction((event) -> {
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(Scenes.getInitialScene());
        });
        
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btnBack);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
          
        return new Scene(grid, 400, 275);
    }
}
