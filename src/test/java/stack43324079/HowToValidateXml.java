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
package stack43324079;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.xerces.dom.DOMInputImpl;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.ls.LSInput;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class HowToValidateXml {

	@Test
	public void validate1() throws SAXException, IOException {
		URL schemaFile = new URL("https://schema.datacite.org/meta/kernel-4.1/metadata.xsd");
		Source xmlFile = new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
						"schemas/datacite/kernel-4.1/example/datacite-example-complicated-v4.1.xml"));
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		Schema schema = schemaFactory.newSchema(schemaFile);
		Validator validator = schema.newValidator();
		validator.validate(xmlFile);
		System.out.println(xmlFile.getSystemId() + " is valid");
	}

	@Test(expected = SAXParseException.class)
	public void validate1_must_fail() throws SAXException, IOException {
		URL schemaFile = new URL("https://schema.datacite.org/meta/kernel-4.1/metadata.xsd");
		Source xmlFile = new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
						"schemas/datacite/kernel-4.1/example/datacite-example-complicated-v4.1-fail.xml"));
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		Schema schema = schemaFactory.newSchema(schemaFile);
		Validator validator = schema.newValidator();
		validator.validate(xmlFile);
		System.out.println(xmlFile.getSystemId() + " is valid");
	}

	@Test(expected = SAXParseException.class)
	public void validate1_must_fail_2() throws SAXException, IOException {
		StreamSource schemaFile = new StreamSource(Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("schemas/datacite/kernel-4.1/metadata.xsd"));
		Source xmlFile = new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
						"schemas/datacite/kernel-4.1/example/datacite-example-complicated-v4.1-fail-2.xml"));
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		Schema schema = schemaFactory.newSchema(schemaFile);
		Validator validator = schema.newValidator();
		validator.validate(xmlFile);
		System.out.println(xmlFile.getSystemId() + " is valid");
	}

	@Test
	public void validate2() throws Exception {
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		f.setNamespaceAware(true);
		DocumentBuilder parser = f.newDocumentBuilder();
		Document document = parser
						.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("43324079.xml"));
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Source schemaFile = new StreamSource(
						Thread.currentThread().getContextClassLoader().getResourceAsStream("tincan.xsd"));
		Schema schema = factory.newSchema(schemaFile);
		Validator validator = schema.newValidator();
		validator.validate(new DOMSource(document));
	}

	@Test
	public void validate3() throws Exception {
		Source xmlFile = new StreamSource(
						Thread.currentThread().getContextClassLoader().getResourceAsStream("43324079.xml"));
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = factory.newSchema(Thread.currentThread().getContextClassLoader().getResource("tincan.xsd"));
		Validator validator = schema.newValidator();
		validator.validate(xmlFile);
	}

	@Test(expected = SAXParseException.class)
	public void validate3_must_fail() throws Exception {
		InputStream xmlStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
						"schemas/datacite/kernel-4.1/example/datacite-example-complicated-v4.1-fail.xml");
		InputStream schemaStream = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("schemas/datacite/kernel-4.1/metadata.xsd");
		Source xmlFile = new StreamSource(xmlStream);
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = factory.newSchema(new StreamSource(schemaStream));
		Validator validator = schema.newValidator();
		validator.validate(xmlFile);
	}

	@Test(expected = SAXParseException.class)
	public void validate3_must_fail3() throws Exception {
		InputStream xmlStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
						"schemas/datacite/kernel-4.1/example/datacite-example-complicated-v4.1-fail-2.xml");
		InputStream schemaStream = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("schemas/datacite/kernel-4.1/metadata.xsd");
		Source xmlFile = new StreamSource(xmlStream);
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = factory.newSchema(new StreamSource(schemaStream));
		Validator validator = schema.newValidator();
		validator.validate(xmlFile);
	}

	@Test
	public void validate4() throws Exception {
		InputStream xmlStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
						"schemas/datacite/kernel-4.1/example/datacite-example-complicated-v4.1.xml");
		InputStream schemaStream = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("schemas/datacite/kernel-4.1/metadata.xsd");
		new XmlSchemaValidator().validate(xmlStream, schemaStream, "https://schema.datacite.org/meta/kernel-4.1/",
						"schemas/datacite/kernel-4.1/");
	}
	
	@Test(expected = SAXParseException.class)
	public void validate5() throws Exception {
		InputStream xmlStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
						"48985313.xml");
		InputStream schemaStream = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("eventos31.xsd");
		new XmlSchemaValidator().validate(xmlStream, schemaStream, "",
						"/");
	}

	@Test(expected = SAXParseException.class)
	public void validate4_must_fail() throws Exception {
		InputStream xmlStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
						"schemas/datacite/kernel-4.1/example/datacite-example-complicated-v4.1-fail.xml");
		InputStream schemaStream = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("schemas/datacite/kernel-4.1/metadata.xsd");
		new XmlSchemaValidator().validate(xmlStream, schemaStream, "https://schema.datacite.org/meta/kernel-4.1/",
						"schemas/datacite/kernel-4.1/");
	}

	@Test(expected = SAXParseException.class)
	public void validate4_must_fail_2() throws Exception {
		InputStream xmlStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
						"schemas/datacite/kernel-4.1/example/datacite-example-complicated-v4.1-fail-2.xml");
		InputStream schemaStream = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("schemas/datacite/kernel-4.1/metadata.xsd");
		new XmlSchemaValidator().validate(xmlStream, schemaStream, "https://schema.datacite.org/meta/kernel-4.1/",
						"schemas/datacite/kernel-4.1/");
	}

	public class XmlSchemaValidator {

		/**
		 * @param xmlStream
		 *            xml data as a stream
		 * @param schemaStream
		 *            schema as a stream
		 * @param baseUri
		 *            to search for relative pathes on the web
		 * @param localPath
		 *            to search for schemas on a local directory
		 * @throws SAXException
		 *             if validation fails
		 * @throws IOException
		 *             not further specified
		 */
		public void validate(InputStream xmlStream, InputStream schemaStream, String baseUri, String localPath)
						throws SAXException, IOException {
			Source xmlFile = new StreamSource(xmlStream);
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			factory.setResourceResolver((type, namespaceURI, publicId, systemId, baseURI) -> {
				LSInput input = new DOMInputImpl();
				input.setPublicId(publicId);
				input.setSystemId(systemId);
				input.setBaseURI(baseUri);
				input.setCharacterStream(new InputStreamReader(
								getSchemaAsStream(input.getSystemId(), input.getBaseURI(), localPath)));
				return input;
			});
			Schema schema = factory.newSchema(new StreamSource(schemaStream));
			javax.xml.validation.Validator validator = schema.newValidator();
			validator.validate(xmlFile);
		}

		private InputStream getSchemaAsStream(String systemId, String baseUri, String localPath) {
			InputStream in = getSchemaFromClasspath(systemId, localPath);
			// You could just return in; , if you are sure that everything is on
			// your machine. Here I call getSchemaFromWeb as last resort.
			return in == null ? getSchemaFromWeb(baseUri, systemId) : in;
		}

		private InputStream getSchemaFromClasspath(String systemId, String localPath) {
			System.out.println("Try to get stuff from localdir: " + localPath + systemId);
			return Thread.currentThread().getContextClassLoader().getResourceAsStream(localPath + systemId);
		}

		/*
		 * You can leave out the webstuff if you are sure that everything is
		 * available on your machine
		 */
		private InputStream getSchemaFromWeb(String baseUri, String systemId) {
			try {
				URI uri = new URI(systemId);
				if (uri.isAbsolute()) {
					System.out.println("Get stuff from web: " + systemId);
					return urlToInputStream(uri.toURL(), "text/xml");
				}
				System.out.println("Get stuff from web: Host: " + baseUri + " Path: " + systemId);
				return getSchemaRelativeToBaseUri(baseUri, systemId);
			} catch (Exception e) {
				// maybe the systemId is not a valid URI or
				// the web has nothing to offer under this address
			}
			return null;
		}

		private InputStream urlToInputStream(URL url, String accept) {
			HttpURLConnection con = null;
			InputStream inputStream = null;
			try {
				con = (HttpURLConnection) url.openConnection();
				con.setConnectTimeout(15000);
				con.setRequestProperty("User-Agent", "Name of my application.");
				con.setReadTimeout(15000);
				con.setRequestProperty("Accept", accept);
				con.connect();
				int responseCode = con.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_MOVED_PERM
								|| responseCode == HttpURLConnection.HTTP_MOVED_TEMP || responseCode == 307
								|| responseCode == 303) {
					String redirectUrl = con.getHeaderField("Location");
					try {
						URL newUrl = new URL(redirectUrl);
						return urlToInputStream(newUrl, accept);
					} catch (MalformedURLException e) {
						URL newUrl = new URL(url.getProtocol() + "://" + url.getHost() + redirectUrl);
						return urlToInputStream(newUrl, accept);
					}
				}
				inputStream = con.getInputStream();
				return inputStream;
			} catch (SocketTimeoutException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		private InputStream getSchemaRelativeToBaseUri(String baseUri, String systemId) {
			try {
				URL url = new URL(baseUri + systemId);
				return urlToInputStream(url, "text/xml");
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
}
