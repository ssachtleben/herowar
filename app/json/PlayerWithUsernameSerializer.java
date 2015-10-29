package json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import models.entity.game.Player;

import java.io.IOException;


public class PlayerWithUsernameSerializer extends BaseSerializer<Player> {

   @Override
   public void serialize(Player player, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
      jgen.writeStartObject();
      writeNumberField(jgen, "id", player.getId());
      writeStringField(jgen, "username", player.getUser().getUsername());
      jgen.writeEndObject();
   }

}
