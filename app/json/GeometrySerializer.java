package json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import dao.GeoMatId;
import models.entity.game.GeoMetaData;
import models.entity.game.Geometry;
import models.entity.game.Material;

import java.io.IOException;

/**
 * @author Sebastian Sachtleben
 */
public class GeometrySerializer extends BaseSerializer<Geometry> {

   @Override
   public void serialize(Geometry geometry, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

      writeAll(jgen, geometry, GeoMetaData.class, Material.class, GeoMatId.class);

   }
}
