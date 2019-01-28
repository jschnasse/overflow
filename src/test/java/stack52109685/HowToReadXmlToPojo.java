/*******************************************************************************
 * Copyright 2019 Jan Schnasse
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

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
