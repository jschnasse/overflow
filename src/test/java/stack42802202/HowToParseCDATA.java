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
package stack42802202;

import javax.xml.stream.XMLStreamReader;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;

public class HowToParseCDATA {

	@Test
	public void useDom() {
		String yourSampleFile = "42802202.xml";
		String cdataNode = "extendedinfo";
		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(yourSampleFile)) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(in);
			NodeList elements = doc.getElementsByTagName(cdataNode);
			for (int i = 0; i < elements.getLength(); i++) {
				Node e = elements.item(i);
				System.out.println(e.getTextContent());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void useStax() {
		String yourSampleFile = "42802202.xml";
		String cdataNode = "extendedinfo";
		XMLStreamReader r = null;
		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(yourSampleFile)) {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			r = factory.createXMLStreamReader(in);
			while (r.hasNext()) {
				switch (r.getEventType()) {
				case XMLStreamConstants.START_ELEMENT:
					if (cdataNode.equals(r.getName().getLocalPart())) {
						System.out.println(r.getElementText());
					}
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
	public void getText() {
		String yourSampleFile = "44167076.xml";
		StringBuilder result = new StringBuilder();
		XMLStreamReader r = null;
		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(yourSampleFile)) {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			r = factory.createXMLStreamReader(in);
			while (r.hasNext()) {
				switch (r.getEventType()) {
				case XMLStreamConstants.CHARACTERS:
					result.append(r.getText());
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
		System.out.println(result.toString().replaceAll("(?m)^[ \t]*\r?\n", ""));
	}

	@Test
	public void getText2() {
		String yourSampleFile = "44167076.xml";
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(yourSampleFile)) {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(in);
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xpath = xPathFactory.newXPath();
			XPathExpression expr = xpath.compile("//string/text()");
			Object eval = expr.evaluate(doc, XPathConstants.NODESET);
			List<String> textValues = new ArrayList<String>();
			if (eval != null && eval instanceof NodeList) {
				NodeList list = (NodeList) eval;
				for (int i = 0; i < list.getLength(); i++) {
					Node node = list.item(i);
					String text = node.getNodeValue().trim();
					if (!text.isEmpty()) {
						System.out.println(text);
						textValues.add(text);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
