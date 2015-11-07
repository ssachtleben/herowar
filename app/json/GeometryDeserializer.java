package json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import dao.GeoMatId;
import models.entity.game.GeoMetaData;
import models.entity.game.Geometry;
import models.entity.game.Material;

import java.io.IOException;


/**
 * Custom Deserializer for Geometry entity. We have to parse the arrays to strings.
 * 
 * @author Sebastian Sachtleben
 */
public class GeometryDeserializer extends BaseDeserializer<Geometry> {

	@Override
	public Geometry deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException,
									JsonProcessingException {
		ObjectCodec oc = jsonParser.getCodec();
		JsonNode geometryNode = oc.readTree(jsonParser);
		Geometry geo = this.parseObject(geometryNode, GeoMetaData.class, Material.class, GeoMatId.class);
		// geo.setType(GeometryType.ENVIRONMENT);
		if (geo.getMaterials() != null) {
			for (Material mat : geo.getMaterials()) {
				// Here we have got uploaded Materials, so any name is missing
				if (mat.getName() == null)
					mat.setName(mat.getDbgName());

			}
		}
		return geo;
	}
}
