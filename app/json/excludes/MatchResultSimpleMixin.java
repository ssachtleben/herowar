package json.excludes;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.MatchResultSimpleSerializer;

/**
 * @author Sebastian Sachtleben
 */
@JsonSerialize(using = MatchResultSimpleSerializer.class)
public class MatchResultSimpleMixin {

}
