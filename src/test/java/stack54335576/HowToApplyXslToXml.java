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
package stack54335576;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.tools.javac.util.Pair;

public class HowToApplyXslToXml {

	@Test
	public void useMultipleXmlSourcesInOneXsl3() {
		System.out.println("Variant 3 - Provide a call back method for the transformer to lookup DOMs--\n");
		InputStream xml = Thread.currentThread().getContextClassLoader().getResourceAsStream("stack54335576/repo.xml");
		InputStream xsl = Thread.currentThread().getContextClassLoader().getResourceAsStream("stack54335576/join3.xsl");
		InputStream booksXml = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("stack54335576/books.xml");
		InputStream articlesXml = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("stack54335576/articles.xml");
		Document booksDom = readXml(booksXml);
		Document articlesDom = readXml(articlesXml);
		Map<String, Document> parameters = new HashMap<>();
		parameters.put("bookFile", booksDom);
		parameters.put("articleFile", articlesDom);
		xslt3(xml, xsl, parameters);
		System.out.println("\n-----------------------------------------------------------------------\n");
	}

	public final void xslt3(InputStream xml, InputStream xsl, Map<String, Document> parameters) {
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(xsl));
			transformer.setURIResolver((href, base) -> new DOMSource(parameters.get(href)));
			transformer.transform(new StreamSource(xml), new StreamResult(System.out));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Document readXml(InputStream xmlin) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			return db.parse(xmlin);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void useMultipleXmlSourcesInOneXsl2() {
		System.out.println("Variant 2 - Use setParameter method --\n");
		InputStream xml = Thread.currentThread().getContextClassLoader().getResourceAsStream("stack54335576/repo.xml");
		InputStream xsl = Thread.currentThread().getContextClassLoader().getResourceAsStream("stack54335576/join2.xsl");
		String booksXmlPath = "src/test/java/stack54335576/books.xml";
		String articlesXmlPath = "src/test/java/stack54335576/articles.xml";
		xslt2(xml, xsl, new Pair<String, String>("bookFile", booksXmlPath),
						new Pair<String, String>("articleFile", articlesXmlPath));
		System.out.println("\n-----------------------------------------------------------------------\n");
	}

	@SafeVarargs
	public final void xslt2(InputStream xml, InputStream xsl, Pair<String, String>... params) {
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(xsl));
			Stream.of(params).forEach((p) -> transformer.setParameter(p.fst, p.snd));
			transformer.transform(new StreamSource(xml), new StreamResult(System.out));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void useMultipleXmlSourcesInOneXsl1() {
		System.out.println("Variant 1 - Modify XSL Dom --------------------------------------------\n");
		InputStream xml = Thread.currentThread().getContextClassLoader().getResourceAsStream("stack54335576/repo.xml");
		InputStream xsl = Thread.currentThread().getContextClassLoader().getResourceAsStream("stack54335576/join.xsl");
		String booksXmlPath = "src/test/java/stack54335576/books.xml";
		String articlesXmlPath = "src/test/java/stack54335576/articles.xml";
		Document xslDocument = readXml(xsl);
		insertFileSourceIntoXsl(xslDocument, "bookFile", booksXmlPath);
		insertFileSourceIntoXsl(xslDocument, "articleFile", articlesXmlPath);
		xslt1(xml, new DOMSource(xslDocument));
		System.out.println("\n-----------------------------------------------------------------------\n");
	}

	private void insertFileSourceIntoXsl(Document xslDocument, String name, String xmlPath) {
		Element param = xslDocument.createElementNS("http://www.w3.org/1999/XSL/Transform", "xsl:param");
		param.setAttribute("name", name);
		param.setAttribute("select", "document('" + xmlPath + "')");
		xslDocument.getFirstChild().appendChild(param);
	}

	public void xslt1(InputStream xml, Source xsl) {
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Templates template = factory.newTemplates(xsl);
			Transformer xformer = template.newTransformer();
			Source source = new StreamSource(xml);
			Result result = new StreamResult(System.out);
			xformer.transform(source, result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
