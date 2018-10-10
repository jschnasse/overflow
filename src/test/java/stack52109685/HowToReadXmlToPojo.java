package stack52109685;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
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
		System.out.println(asXml(pojo));
		asXml2(pojo);
	}

	private String asXml(ExtAdress pojo) throws JsonProcessingException {
		XmlMapper xmlMapper = new XmlMapper();
		return xmlMapper.writeValueAsString(pojo);
	}

	private void asXml2(ExtAdress pojo) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(ExtAdress.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(pojo, System.out);
	}

	public String toString(Object obj) throws JsonGenerationException, JsonMappingException, IOException {
		StringWriter w = new StringWriter();
		new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true).writeValue(w, obj);
		return w.toString();
	}
}
