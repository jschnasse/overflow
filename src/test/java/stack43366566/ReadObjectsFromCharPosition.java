/*******************************************************************************
 * Copyright 2017 Jan Schnasse
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
package stack43366566;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class ReadObjectsFromCharPosition<T> {
	private Class<T> typeOfT;

	public ReadObjectsFromCharPosition(Class<T> typeParameterClass) {
		this.typeOfT = typeParameterClass;
	}

	private List<T> extractObjects(String filename, List<Integer>offsets) {
		List<T> objects = new ArrayList<>();
		for (Integer offset : offsets) {
			T object = readPage(offset, filename);
			objects.add(object);
		}
		return objects;
	}

	private T readPage(Integer offset, String filename) {
		try (Reader in = new FileReader(filename)) {
			in.skip(offset);
			ObjectMapper mapper = new XmlMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			T object = mapper.readValue(in, typeOfT);
			return object;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String objectToString(Object object) {
		try {
			return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(object);
		} catch (Exception e) {
			return "To String failed " + e.getMessage();
		}
	}

	public static void main(String[] args) {
		String filename = "/home/jan/dewiki-20170501-pages-articles-multistream.xml";
		String elementName = "page";

		List<Integer> offsets = new FindXmlOffset().createOffsets(filename, elementName);
		System.out.println("Found "+offsets.size()+" pages");
		System.out.println(objectToString(offsets));
		
		ReadObjectsFromCharPosition<Page> c = new ReadObjectsFromCharPosition<>(Page.class);
		List<Page> objects = c.extractObjects(filename, offsets);
		System.out.println("Found "+objects.size()+" pages");
		System.out.println(objectToString(objects));
	}

}