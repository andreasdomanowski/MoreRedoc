package moreredoc.linguistics.processing;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Commons concerning linguistic analysis for this project.
 */
public class Commons {
    private Commons(){
    }

    public static final String GENITIVE_INDICATOR = "'";

    // relevant POS Tags
    public static final String POS_NOUN_SINGULAR_OR_MASS = "NN";
    public static final String POS_NOUN_PROPER_SINGULAR = "NNP";
    public static final String POS_NOUN_PLURAL = "NNS";
    public static final String POS_NOUN_PROPER_PLURAL = "NNPS";
    public static final String POS_MODAL = "MD";

    // List of verbs which should not be modelled when being the only verbs
    public static final List<String> VERBS_TO_NOT_MODEL_WHEN_ALONE = Collections.unmodifiableList(Arrays.asList("should", "be", "is", "are"));
}
