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
package stack41365068;

import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
public class HowToParseCDATAWithStax {

	@Test
	public void test() {
		String yourSampleFile = "41365068.xml";
		XMLStreamReader r = null;
		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(yourSampleFile)) {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			r = factory.createXMLStreamReader(in);
			while (r.hasNext()) {
				switch (r.getEventType()) {
				case XMLStreamConstants.CHARACTERS:
				case XMLStreamConstants.CDATA:
					System.out.println(r.getText());
					break;
				default:
					break;
				}
				r.next();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	@Test
	public void testList(){

	    List<Integer> list1 =  Arrays.asList(1, 2, 2, 3,3);
	    List<Integer> list2 =  Arrays.asList(1, 2, 4,6, 5);
	    List<Integer> diff = list1.stream()
	                              .filter(e -> !list2.contains(e))
	                              .collect (Collectors.toList());
	    System.out.println(diff);
	}
}
