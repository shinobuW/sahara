package seng302.group2.scenes.information;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.workspace.person.Person;

import static seng302.group2.scenes.MainScene.informationGrid;

/**
 * A class for displaying the Person Scene
 * @author crw73
 */
public class PersonScene
{
    
    /**
     * Gets the Person information scene
     * @param currentPerson the person to display the information of
     * @return The Person information scene
     */
    public static Pane getPersonScene(Person currentPerson)
    {
        informationGrid = new VBox(10);

        /*informationGrid.setAlignment(Pos.TOP_LEFT);
        informationGrid.setHgap(10);
        informationGrid.setVgap(10);*/
        informationGrid.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentPerson.getFirstName() + " " + currentPerson.getLastName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnEdit = new Button("Edit");
    
        ListView personSkillsBox = new ListView(currentPerson.getSkills());
        personSkillsBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        final Separator separator = new Separator();
        
        informationGrid.getChildren().add(title);
        informationGrid.getChildren().add(new Label("Short Name: " + currentPerson.getShortName()));
        informationGrid.getChildren().add(new Label("Email Address: " + currentPerson.getEmail()));
        informationGrid.getChildren().add(new Label("Birth Date: "
                + currentPerson.getDateString()));
        informationGrid.getChildren().add(new Label("Person Description: "
                + currentPerson.getDescription()));
        informationGrid.getChildren().add(new Label("Team: " + currentPerson.getTeamName()));

        String roleString = currentPerson.getRole() == null ? "" :
                currentPerson.getRole().toString();
        informationGrid.getChildren().add(new Label("Role: " + roleString));

        informationGrid.getChildren().add(separator);
        informationGrid.getChildren().add(new Label("Skills: "));
        informationGrid.getChildren().add(personSkillsBox);

        informationGrid.getChildren().add(btnEdit);
        
        btnEdit.setOnAction((event) ->
            {
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.PERSON_EDIT, currentPerson);
            });

        return MainScene.informationGrid;
    }

    
    public static void refreshPersonScene(Person person)
    {
        SceneSwitcher.changeScene(SceneSwitcher.ContentScene.PERSON, person);
    }
}
