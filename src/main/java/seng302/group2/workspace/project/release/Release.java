/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seng302.group2.workspace.project.release;

import javafx.collections.ObservableList;
import seng302.group2.Global;
import seng302.group2.scenes.listdisplay.TreeViewItem;
import seng302.group2.scenes.sceneswitch.switchStrategies.workspace.project.ReleaseInformationSwitchStrategy;
import seng302.group2.util.undoredo.Command;
import seng302.group2.workspace.project.Project;

import java.time.LocalDate;
import java.util.Collections;

/**
 * A release is a sub-member of project and contains information about a release of a project
 *
 * @author Shinobu, Jordane
 */
public class Release extends TreeViewItem implements Comparable<Release> {
    private String shortName;
    private String description;
    private LocalDate estimatedDate;
    private Project project;


    /**
     * Basic constructor
     */
    public Release() {
        this.shortName = "Untitled Release";
        this.description = "Release without project assigned should not exist";
        this.estimatedDate = LocalDate.now();
        this.project = new Project();

        setInformationSwitchStrategy(new ReleaseInformationSwitchStrategy());
    }


    /**
     * Required Constructor
     *
     * @param shortName short name to be set
     * @param project   project to be set
     */
    public Release(String shortName, Project project) {
        this.shortName = shortName;
        this.project = project;

        setInformationSwitchStrategy(new ReleaseInformationSwitchStrategy());
    }


    /**
     * Complete Constructor
     *
     * @param shortName   short name to be set
     * @param description description to be set
     * @param releaseDate release date to be set
     * @param project     project to be set
     */
    public Release(String shortName, String description, LocalDate releaseDate, Project project) {
        this.shortName = shortName;
        this.description = description;
        this.estimatedDate = releaseDate;
        this.project = project;

        setInformationSwitchStrategy(new ReleaseInformationSwitchStrategy());
    }


    // <editor-fold defaultstate="collapsed" desc="Getters"> 


    /**
     * Gets short name of the release
     *
     * @return short name of the release
     */
    public String getShortName() {
        return this.shortName;
    }

    /**
     * Sets the short Name of the release
     *
     * @param shortName short name to set
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Gets the description of the release
     *
     * @return the description of the release
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the Description of the release
     *
     * @param description The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the estimated release date for the release
     *
     * @return the estimated release date of the release
     */
    public LocalDate getEstimatedDate() {
        return this.estimatedDate;
    }


    //</editor-fold>


    // <editor-fold defaultstate="collapsed" desc="Setters"> 

    /**
     * Sets the estimated Release Date of the release
     *
     * @param releaseDate The release date to set
     */
    public void setEstimatedDate(LocalDate releaseDate) {
        this.estimatedDate = releaseDate;
    }

    /**
     * Gets the project the release belongs to
     *
     * @return project
     */
    public Project getProject() {
        return this.project;
    }

    /**
     * Sets the project the release belongs to
     *
     * @param project project to set
     */
    public void setProject(Project project) {
        //this.project.removeWithoutUndo(this);
        this.project = project;
        //project.add(this);
    }

    /**
     * Gets the persons birth date as a string
     *
     * @return The persons birth date as a string
     */
    public String getDateString() {
        if (this.estimatedDate == null) {
            return "";
        }
        else {
            try {
                return this.getEstimatedDate().format(Global.dateFormatter);
            }
            catch (Exception e) {
                System.out.println("Error parsing date");
                return "";
            }
        }
    }

    /**
     * Sets the project the release belongs to without an undoable command
     *
     * @param project project to set
     */
    public void setProjectWithoutUndo(Project project) {
        this.project = project;
        project.addWithoutUndo(this);
    }

    //</editor-fold>

    /**
     * Compares the release to another release based on their short names
     *
     * @param compareRelease The release to compare to
     * @return The string comparison or the releases short names
     */
    @Override
    public int compareTo(Release compareRelease) {
        String release1ShortName = this.getShortName();
        String release2ShortName = compareRelease.getShortName();
        return release1ShortName.compareTo(release2ShortName);
    }


    /**
     * Deletes a release from the given project.
     */
    public void deleteRelease() {
        Command command = new DeleteReleaseCommand(this);
        Global.commandManager.executeCommand(command);
    }


    @Override
    public ObservableList<TreeViewItem> getChildren() {
        return null;
    }

    @Override
    public String toString() {
        return this.shortName;
    }

    /**
     * Creates a Release edit command and executes it with the Global Command Manager, updating
     * the release with the new parameter values.
     *
     * @param newShortName     The new short name
     * @param newDescription   The new description
     * @param newEstimatedDate The new estimated date
     * @param newProject       The new project
     */
    public void edit(String newShortName, String newDescription, LocalDate newEstimatedDate,
                     Project newProject) {
        Command relEdit = new ReleaseEditCommand(this, newShortName, newDescription,
                newEstimatedDate, newProject);
        Global.commandManager.executeCommand(relEdit);
    }


    /**
     * A command class that allows the executing and undoing of release edits
     */
    private class ReleaseEditCommand implements Command {
        private Release release;
        private String shortName;
        private String description;
        private LocalDate estimatedDate;
        private Project project;
        private String oldShortName;
        private String oldDescription;
        private LocalDate oldEstimatedDate;
        private Project oldProject;

        private ReleaseEditCommand(Release release, String newShortName, String newDescription,
                                   LocalDate newEstimatedDate, Project newProject) {
            this.release = release;
            this.shortName = newShortName;
            this.description = newDescription;
            this.estimatedDate = newEstimatedDate;
            this.project = newProject;
            this.oldShortName = release.shortName;
            this.oldDescription = release.description;
            this.oldEstimatedDate = release.estimatedDate;
            this.oldProject = release.project;
        }

        /**
         * Executes/Redoes the changes of the release edit
         */
        public void execute() {
            release.shortName = shortName;
            release.description = description;
            release.estimatedDate = estimatedDate;
            release.project = project;
            Collections.sort(project.getReleases());

        }

        /**
         * Undoes the changes of the release edit
         */
        public void undo() {
            release.shortName = oldShortName;
            release.description = oldDescription;
            release.estimatedDate = oldEstimatedDate;
            release.project = oldProject;
            Collections.sort(project.getReleases());
        }
    }

    private class DeleteReleaseCommand implements Command {
        private Release release;
        private Project proj;

        DeleteReleaseCommand(Release release) {
            this.release = release;
            this.proj = release.getProject();
        }

        public void execute() {
            System.out.println("Exec Release Delete");
            proj.getReleases().remove(release);
            //release.setProject(null);
        }

        public void undo() {
            System.out.println("Undone Release Delete");
            proj.getReleases().add(release);
            //release.setProject(proj);
        }
    }
}
