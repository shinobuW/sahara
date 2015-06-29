package seng302.group2.workspace.project.story.acceptanceCriteria;

import javafx.util.StringConverter;
import org.apache.commons.lang.WordUtils;

/**
 * Created by jml168 on 29/06/15.
 */
public class AcEnumStringConverter extends StringConverter<AcceptanceCriteria.AcState> {

    public AcceptanceCriteria.AcState fromString(String value) {
        String result = value.replace(' ', '_');
        result = result.toUpperCase();
        return AcceptanceCriteria.AcState.valueOf(result);
    }

    @Override
    public String toString(AcceptanceCriteria.AcState value) {
        String result = value.name().replace("_", " ");
        return WordUtils.capitalizeFully(result);
    }
}
