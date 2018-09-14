package stack52109685;


import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;


public class HowToReadXmlToPojo {

	@Test
	public void readXmlToSelfDefinedPojo2() throws Exception {

		ObjectMapper mapper = new XmlMapper();
		ExtAdress pojo = mapper.readValue(
						Thread.currentThread().getContextClassLoader().getResourceAsStream("52109685.xml"),
						ExtAdress.class);
		System.out.println(toString(pojo));
	}
	
	public String toString(Object obj) throws JsonGenerationException, JsonMappingException, IOException{
		StringWriter w = new StringWriter();
		new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true).writeValue(w, obj);
		return w.toString();
	}
}
