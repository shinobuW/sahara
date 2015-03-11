/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.project.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * A class for holding JavaFX scenes used in the project
 * @author Jordane Lew (jml168)
 */
public final class Scenes
{
    public static Scene getMainScene()
    {
        // The root window box
        VBox root = new VBox();
        
        
        // The menus and menu bar creation
        Menu fileMenu = new Menu("File");
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);
        root.getChildren().add(new StackPane(menuBar));
        
        
        // Set up a grid pane for everything in the window
        GridPane informationGrid = new GridPane();
        root.getChildren().add(informationGrid);
        informationGrid.setAlignment(Pos.CENTER);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);
        informationGrid.setPadding(new Insets(25,25,25,25));
        

        // Adds a title to the grid
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        informationGrid.add(scenetitle, 0, 0, 2, 1);
            // At top-left (0, 0), spanning 2 columns and 1 row

        // A button to switch to the demo scene
        Button btn = new Button("Switch to demo scene");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        informationGrid.add(hbBtn, 1, 4);
        
        //The button event handler
        btn.setOnAction((event) ->
            {
                /*Stage stage = (Stage) btn.getScene().getWindow();
                stage.setScene(Scenes.getStudentsTestScene());*/
            });

        
        return new Scene(root);
    }
    
    
    /**
     * Gets a new demo scene
     * @return A new demo scene
     * @author Jordane Lew (jml168)
     */
    /*public static Scene getDemoScene()
    {
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
        btn.setOnAction((event) ->
            {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Sign in button pressed");
                pwBox.setText("");
                userTextField.setText("");
            });
        
        // The back button
        Button btnBack = new Button("Back");
        //The back button event handler
        btnBack.setOnAction((event) ->
            {
                Stage stage = (Stage) btnBack.getScene().getWindow();
                stage.setScene(Scenes.getInitialScene());
            });
        
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btnBack);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
          
        return new Scene(grid, 400, 275);
    }*/
    
    
    /**
     * Gets a new demo scene
     * @return A new demo scene
     * @author Jordane Lew (jml168)
     */
    /*public static Scene getStudentsTestScene()
    {
        // Set up the grid pane
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));
        
        // The title
        Text scenetitle = new Text("Students");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        // An observable list of students for display
	ObservableList<Person> students = FXCollections.observableArrayList();
	
	ListView StudentsView = new ListView();
	StudentsView.setItems(students);
        grid.add(StudentsView, 0, 1);
	
        // The sign in button
        Button btnAdd = new Button("Add Student");
        //The button event handler
        btnAdd.setOnAction((event) ->
            {
                students.add(new Person());
            });
	
	// The sign in button
        Button btnDelete = new Button("Delete Student");
        //The button event handler
        btnDelete.setOnAction((event) ->
            {
                students.remove((Person)
                        StudentsView.getSelectionModel().getSelectedItem());
            });
        
        // The save button
        Button btnSave = new Button("Load");
        //The save button event handler
        btnSave.setOnAction((event) ->
            {
                /*unserialize arraylist */
                /*System.out.println("unserializing list");
                try
                {
                    FileInputStream fin = new FileInputStream("list.dat");
                    ObjectInputStream ois = new ObjectInputStream(fin);
                    ArrayList<Person> list = (ArrayList) ois.readObject();

                    students.clear();
                    list.stream().forEach((student) ->
                        {
                            students.add(student);
                        });
                    ois.close();
                }
                catch (IOException | ClassNotFoundException e)
                { 
                    e.printStackTrace(); 
                }
            });
        
        // The load button
        Button btnLoad = new Button("Save");
        //The save button event handler
        btnLoad.setOnAction((event) ->
            {
                /*serialize arraylist*/
                /*try
                {
                    System.out.println("serializing list");
                    FileOutputStream fout = new FileOutputStream("list.dat");
                    ObjectOutputStream oos = new ObjectOutputStream(fout);

                    ArrayList<Person> list = new ArrayList<>();
                    students.stream().forEach((student) ->
                        {
                            list.add(student);
                        });

                    oos.writeObject(list);
                    oos.close();
                }
                catch (FileNotFoundException e)
                {		
                    e.printStackTrace(); 
                }
                catch (IOException e)
                { 
                    e.printStackTrace(); 
                }
            });
        
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btnSave);
        hbBtn.getChildren().add(btnLoad);
        hbBtn.getChildren().add(btnAdd);
	hbBtn.getChildren().add(btnDelete);
        grid.add(hbBtn, 0, 4, 2, 1);
          
        return new Scene(grid, 400, 275);
    } */
}
