package stack49028748;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonArrayToPojo {
	@Test
	public void json() throws JsonParseException, JsonMappingException, IOException {
		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("49028748.json")) {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode matrix = mapper.readValue(in, JsonNode.class);
			for (JsonNode array : matrix) {
				System.out.println("Next Values:");
				System.out.println(array.at("/4").asDouble());
				System.out.println(array.at("/8").asInt());
			}
		}
	}

	@Test
	public void json_v1() throws JsonParseException, JsonMappingException, IOException {
		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("49028748.json")) {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode matrix = mapper.readValue(in, JsonNode.class);
			matrix.forEach(array -> {
				System.out.println("Next Values:");
				System.out.println(array.at("/4").asDouble());
				System.out.println(array.at("/8").asInt());
			});
		}
	}

	@Test
	public void json2() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readValue(
						"{\"TemplateArray\":[{\"Id\":16,\"Name\":\"Machine\",\"type\":\"PM\"}, {\"Id\":17,\"Name\":\"Ethernet\",\"type\":\"PM\"},{\"Id\":18,\"Name\":\"Hard Disk\",\"type\":\"PM\"}]}",
						JsonNode.class);
		node.at("/TemplateArray").forEach(a -> System.out.println(a.at("/Name")));
	}

	@Test
	public void json3() throws JsonParseException, JsonMappingException, IOException {
		Map<String, JsonNode> store = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode users = mapper.readValue(
						"[{\"name\":\"Peter\",\"ID\":\"bc6fe168-e73f-48c9-b421-ad3c4c424392\", \"Age\":\"23\",\"Comment\":\"I am a new User\"},{\"name\":\"jschnasse\",\"ID\":\"bc6fe168-e73f-48c9-b421-ad3c4c424393\", \"Age\":\"well\",\"Comment\":\"I am a fun User\"}]",
						JsonNode.class);
		users.forEach((user) -> {
			String id = user.at("/ID").asText();
			if (!store.containsKey(id)) {
				store.put(id, user);
			} else {
				/* Do something else */
			}
		});

		System.out.println(toString(store.values()));
	}

	public String toString(Object obj) throws JsonGenerationException, JsonMappingException, IOException {
		StringWriter w = new StringWriter();
		new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true).writeValue(w, obj);
		return w.toString();
	}

	@Test
	public void loadMapFromFileAndSaveIt(){
		Map<Object, Object> map = loadMap("map.json");
		map.put("8", "8th");
		map.remove("7");
		save(map,"/tmp/map2.txt");
	}

	private Map<Object, Object> loadMap(String string) {
		ObjectMapper mapper = new ObjectMapper();
		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("map.json")) {
			return mapper.readValue(in, HashMap.class);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void save(Map<Object, Object> map,String path) {
		try (PrintWriter out = new PrintWriter(path)) {
			out.println(toString(map));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
