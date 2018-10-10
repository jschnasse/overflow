/*******************************************************************************
 * Copyright 2018 Jan Schnasse
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
package stack52720162;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.function.Consumer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HowToReadPartsOfXmlUsingXPath {
	@Test
	public void printXml() {
		try (InputStream in = readFile("52720162.xml")) {
			processFilteredXml(in, "//root/Entity", (node) -> {
				printNode(node, System.out);
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private InputStream readFile(String yourSampleFile) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(yourSampleFile);
	}

	private void processFilteredXml(InputStream in, String xpath, Consumer<Node> process) {
		Document doc = readXml(in);
		NodeList list = filterNodesByXPath(doc, xpath);
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			process.accept(node);
		}
	}

	public Document readXml(InputStream xmlin) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			return db.parse(xmlin);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private NodeList filterNodesByXPath(Document doc, String xpathExpr) {
		try {
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xpath = xPathFactory.newXPath();
			XPathExpression expr = xpath.compile(xpathExpr);
			Object eval = expr.evaluate(doc, XPathConstants.NODESET);
			return (NodeList) eval;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void printNode(Node node, PrintStream out) {
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(node);
			transformer.transform(source, result);
			String xmlString = result.getWriter().toString();
			out.println(xmlString);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
