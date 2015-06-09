package seng302.group2.util.validation;

import seng302.group2.scenes.control.RequiredField;
import seng302.group2.workspace.project.backlog.Backlog;
import seng302.group2.workspace.project.story.Story;

/**
 * A class for validating the input on number only text fields
 * Created by drm127 on 20/05/15.
 */
public class PriorityFieldValidator {

    private static ValidationStatus validatePriorityField(String input, Backlog backlog, Integer currentPriority) {
        //System.out.print(input);
        if (input == null || input.equals("")) {
            return ValidationStatus.NULL;
        }
        else {
            try {
                Integer parsedInt = Integer.parseInt(input);
                if (currentPriority != null && currentPriority.equals(parsedInt)) {
                    return ValidationStatus.VALID;
                }
                if (backlog != null) {
                    for (Story currentStory : backlog.getStories()) {
                        if (currentStory.getPriority().equals(parsedInt)) {
                            return ValidationStatus.NON_UNIQUE;
                        }
                    }
                }
            }
            catch (NullPointerException ex) {
                return ValidationStatus.NULL;
            }
            catch (NumberFormatException ex) {
                return ValidationStatus.INVALID;
            }
            return ValidationStatus.VALID;
        }
    }

    /**
     * Checks whether the priority is valid.
     * @param numberField The nominated priority
     * @param backlog The current backlog of the story. null if a new story.
     * @param currentPriority the current priority. null if a new story.
     * @return If the priority is valid
     */
    public static boolean validatePriorityField(RequiredField numberField, Backlog backlog, Integer currentPriority) {
        switch (PriorityFieldValidator.validatePriorityField(numberField.getText(), backlog, currentPriority)) {
            case VALID:
                numberField.hideErrorField();
                return true;
            case INVALID:
                numberField.showErrorField("* You must enter integer values only");
                return false;
            case NULL:
                numberField.showErrorField("* You must enter an integer priority for this story");
                return false;
            case NON_UNIQUE:
                numberField.showErrorField("* Priorities must be unique within a backlog");
                return false;
            default:
                numberField.showErrorField("* Not a valid number");
                return false;
        }
    }


}
