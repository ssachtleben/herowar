package json.excludes;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import json.PlayerWithUsernameSerializer;

@JsonSerialize(using = PlayerWithUsernameSerializer.class)
public class PlayerWithUsernameMixin {

}
