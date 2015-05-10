package seng302.group2.scenes.information.Person;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import seng302.group2.scenes.MainScene;
import seng302.group2.scenes.SceneSwitcher;
import seng302.group2.workspace.person.Person;

import static seng302.group2.scenes.MainScene.informationPane;

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
    public static ScrollPane getPersonScene(Person currentPerson)
    {
        informationPane = new VBox(10);

        /*informationPane.setAlignment(Pos.TOP_LEFT);
        informationPane.setHgap(10);
        informationPane.setVgap(10);*/
        informationPane.setPadding(new Insets(25,25,25,25));
        Label title = new Label(currentPerson.getFirstName() + " " + currentPerson.getLastName());
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));

        Button btnEdit = new Button("Edit");
    
        ListView personSkillsBox = new ListView(currentPerson.getSkills());
        personSkillsBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        final Separator separator = new Separator();
        
        informationPane.getChildren().add(title);
        informationPane.getChildren().add(new Label("Short Name: " + currentPerson.getShortName()));
        informationPane.getChildren().add(new Label("Email Address: " + currentPerson.getEmail()));
        informationPane.getChildren().add(new Label("Birth Date: "
                + currentPerson.getDateString()));
        informationPane.getChildren().add(new Label("Person Description: "
                + currentPerson.getDescription()));
        informationPane.getChildren().add(new Label("Team: " + currentPerson.getTeamName()));

        String roleString = currentPerson.getRole() == null ? "" :
                currentPerson.getRole().toString();
        informationPane.getChildren().add(new Label("Role: " + roleString));

        informationPane.getChildren().add(separator);
        informationPane.getChildren().add(new Label("Skills: "));
        informationPane.getChildren().add(personSkillsBox);

        informationPane.getChildren().add(btnEdit);
        
        btnEdit.setOnAction((event) ->
            {
                SceneSwitcher.changeScene(SceneSwitcher.ContentScene.PERSON_EDIT, currentPerson);
            });

        return new ScrollPane(informationPane);
    }

    
    public static void refreshPersonScene(Person person)
    {
        SceneSwitcher.changeScene(SceneSwitcher.ContentScene.PERSON, person);
    }
}
