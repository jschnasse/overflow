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
package stack52761219;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class HowToMapXmlSequenceToList {

	@Test
	public void test2() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new XmlMapper();
		Name name = mapper.readValue("<name>\n" + "  <given>First</given>\n" + "  <given>Second</given>\n"
						+ "  <family>Lastname</family>\n" + "</name>", Name.class);
		System.out.println(toString(name));
	}

	public static class Name {
		@JacksonXmlElementWrapper(useWrapping = false)
		public List<String> given = new ArrayList<>();
		@XmlElement(required = true)
		public String family;
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
