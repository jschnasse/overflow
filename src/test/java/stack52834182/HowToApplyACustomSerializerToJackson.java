package stack52834182;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class HowToApplyACustomSerializerToJackson {

	@Test
	public void test2() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new XmlMapper();
		Event event=mapper.readValue("<Event><EventID>248739296</EventID><Event>1709</Event></Event>", Event.class);
		System.out.println(toString(event));
	}

	public String toString(Object obj) {
		try {
			StringWriter w = new StringWriter();
			new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true).writeValue(w, obj);
			return w.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
