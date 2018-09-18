package stack50738973;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class HowToCreateJsonLdFromNestedObjectStructure {

	/*
	 * Just skip the jackson-jsonld part and do it manually
	 * 
	 * Create JSON - just introduce a field for type and id into your java
	 * classes. Create a JSON-LD context - map your id and type fields in an
	 * additional @context object Combine context and data - e.g. just add
	 * your @context object after your 'normal' json serialization using
	 * standard jackson API.
	 * 
	 */
	@Test
	public void createJsonFromPojo() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		// Create object structure
		Venue venue = new Venue();
		venue.location = new Location();
		venue.id = "12";
		venue.uri = "http://localhost:8080/kangarooEvents/venue/12";
		venue.name = "Joondalup Library - Ground Floor Meeting Room";
		venue.location.address = "102 Boas Avenue";
		venue.location.city = "Joondalup";
		venue.location.state = "WA";
		venue.location.country = "Australia";
		venue.location.zipcode = "6027";

		// 1. Create JSON
		ObjectNode myData = mapper.valueToTree(venue);

		// 2. Create a JSON-LD context
		ArrayNode context = mapper.createArrayNode();
		context.add("http://schema.org/");
		ObjectNode myContext = mapper.createObjectNode();
		myContext.put("id", "@id");
		myContext.put("type", "@type");
		context.add(myContext);

		// 3. Combine context and data
		myData.set("@context", context);

		// 4. Print
		StringWriter w = new StringWriter();
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true).writeValue(w, myData);
		String result = w.toString();
		System.out.println(result);
	}

}
