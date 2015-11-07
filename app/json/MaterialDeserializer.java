package json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import models.entity.game.Material;
import models.entity.game.Texture;

import java.io.IOException;

public class MaterialDeserializer extends BaseDeserializer<Material> {

   @Override
   public Material deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
      ObjectCodec oc = jp.getCodec();
      JsonNode geometryNode = oc.readTree(jp);
      Material mat = this.parseObject(geometryNode, Texture.class);

      return mat;
   }

}
