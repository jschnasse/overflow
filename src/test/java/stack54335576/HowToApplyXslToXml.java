package stack54335576;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class HowToApplyXslToXml {

	@Test
	public void useMultipleXmlSourcesInOneXsl() {
		InputStream xml = Thread.currentThread().getContextClassLoader().getResourceAsStream("stack54335576/repo.xml");
		InputStream xsl = Thread.currentThread().getContextClassLoader().getResourceAsStream("stack54335576/join.xsl");
		String booksXmlPath = "src/test/java/stack54335576/books.xml";
		String articlesXmlPath = "src/test/java/stack54335576/articles.xml";
		Document xslDocument = readXml(xsl);
		insertFileSourceIntoXsl(xslDocument,"bookFile", booksXmlPath);
		insertFileSourceIntoXsl(xslDocument,"articleFile", articlesXmlPath);
		xsl(xml, new DOMSource(xslDocument));
	}

	private void insertFileSourceIntoXsl(Document xslDocument, String name,String xmlPath) {
		Element param = xslDocument.createElement("xsl:param");
		param.setAttribute("name", name);
		param.setAttribute("select", "document('"+xmlPath+"')");
		xslDocument.getFirstChild().appendChild(param);
	}

	public void xsl(InputStream xml, Source xsl) {
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

	private Document readXml(InputStream xmlin) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			return db.parse(xmlin);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
