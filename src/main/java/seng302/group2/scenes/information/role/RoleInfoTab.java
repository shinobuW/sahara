package seng302.group2.scenes.information.role;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import seng302.group2.scenes.control.TitleLabel;
import seng302.group2.workspace.role.Role;


/**
 * The workspace information tab
 * Created by jml168 on 11/05/15.
 */
public class RoleInfoTab extends Tab
{
    public RoleInfoTab(Role currentRole)
    {
        this.setText("Basic Information");

        Pane basicInfoPane = new VBox(10);  // The pane that holds the basic info
        basicInfoPane.setBorder(null);
        basicInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ScrollPane wrapper = new ScrollPane(basicInfoPane);
        this.setContent(wrapper);


        Label title = new TitleLabel(currentRole.getShortName());

        ListView skillsBox = new ListView(currentRole.getRequiredSkills());
        skillsBox.setPrefHeight(192);
        skillsBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        skillsBox.setMaxWidth(450);

        basicInfoPane.getChildren().add(title);
        basicInfoPane.getChildren().add(new Label("Role Description: "
                + currentRole.getDescription()));

        basicInfoPane.getChildren().add(new Label("Required Skills: "));
        basicInfoPane.getChildren().add(skillsBox);
    }
}
