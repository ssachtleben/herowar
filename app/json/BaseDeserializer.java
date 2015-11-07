package json;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import play.Logger;
import utils.JsonUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

public abstract class BaseDeserializer<T> extends JsonDeserializer<T> {
	private static final Logger.ALogger log = Logger.of(BaseDeserializer.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T parseObject(JsonNode node, Class<?>... parseClasses) {
		T result = null;
		List<Class<?>> classes = Arrays.asList(parseClasses);
		try {
			result = (T) ((Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
			JsonUtils.parse(result, node, classes);
		} catch (Exception e) {
			log.error("", e);
		}
		return result;

	}
}
