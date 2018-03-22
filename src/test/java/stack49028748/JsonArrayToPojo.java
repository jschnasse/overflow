package stack49028748;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
}
