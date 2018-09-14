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


public class HowToReadXmlToPojo {

	@Test
	public void createJsonFromPojo() throws Exception {
		ObjectMapper mapper=new ObjectMapper();
		Venue venue = new Venue();
		venue.location = new Location();
		venue.id="12";
		venue.uri="http://localhost:8080/kangarooEvents/venue/12";
		venue.name="Joondalup Library - Ground Floor Meeting Room";
		venue.location.address="102 Boas Avenue";
		venue.location.city="Joondalup";
		venue.location.state="WA";
		venue.location.country="Australia";
		venue.location.zipcode="6027";
		
		ObjectNode myData = mapper.valueToTree(venue);
		ArrayNode context = mapper.createArrayNode();
		context.add("http://schema.org/");
		ObjectNode myContext=mapper.createObjectNode();
		myContext.put("id", "@id");
		myContext.put("type", "@type");
		context.add(myContext);
		myData.set("@context",context);
		
		StringWriter w = new StringWriter();
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true).writeValue(w, myData);
		String result= w.toString();
		System.out.println(result);
	}
	

}
