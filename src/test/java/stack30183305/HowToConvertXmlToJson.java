package stack30183305;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class HowToConvertXmlToJson {

	@Test
	public void xmlToJson() {
		String xml = "<root><name>john</name><list><item>val1</item>val2<item>val3</item></list></root>";
		Map<String, Object> jsonResult = readXmlToMap(xml);
		String jsonString = toString(jsonResult);
		System.out.println(jsonString);
	}

	private Map<String, Object> readXmlToMap(String xml) {
		try {
			ObjectMapper xmlMapper = new XmlMapper();
			xmlMapper.registerModule(new SimpleModule().addDeserializer(Object.class, new UntypedObjectDeserializer() {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				protected Map<String, Object> mapObject(JsonParser jp, DeserializationContext ctxt) throws IOException {
					JsonToken t = jp.getCurrentToken();

					Multimap<String, Object> result = ArrayListMultimap.create();
					if (t == JsonToken.START_OBJECT) {
						t = jp.nextToken();
					}
					if (t == JsonToken.END_OBJECT) {
						return (Map) result.asMap();
					}
					do {
						String fieldName = jp.getCurrentName();
						jp.nextToken();
						result.put(fieldName, deserialize(jp, ctxt));
					} while (jp.nextToken() != JsonToken.END_OBJECT);

					return (Map) result.asMap();
				}
			}));
			return (Map) xmlMapper.readValue(xml, Object.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static public String toString(Object obj) {
		try {
			ObjectMapper jsonMapper = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true)
							.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, true);
			StringWriter w = new StringWriter();
			jsonMapper.writeValue(w, obj);
			return w.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
