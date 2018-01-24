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
package stack43324079;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
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
import org.xml.sax.SAXParseException;

public class HowToValidateXml {
	@Test
	public void validate1() throws Exception {
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
	public void validate2() throws Exception {
		Source xmlFile = new StreamSource(
						Thread.currentThread().getContextClassLoader().getResourceAsStream("43324079.xml"));
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = factory.newSchema(Thread.currentThread().getContextClassLoader().getResource("tincan.xsd"));
		Validator validator = schema.newValidator();
		validator.validate(xmlFile);
	}

	@Test
	public void validate3() throws Exception {
		Source xmlFile = new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
						"schemas/datacite/kernel-4.1/example/datacite-example-complicated-v4.1.xml"));
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		factory.setResourceResolver((type, namespaceURI, publicId, systemId, baseURI) -> {
			LSInput input = new DOMInputImpl();
			input.setPublicId(publicId);
			input.setSystemId(systemId);
			input.setBaseURI("https://schema.datacite.org/meta/kernel-4.1/");
			input.setCharacterStream(new InputStreamReader(
							getSchemaAsStream(input.getSystemId(), input.getPublicId(), input.getBaseURI())));
			return input;
		});
		Schema schema = factory.newSchema(new StreamSource(Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("schemas/datacite/kernel-4.1/metadata.xsd")));
		javax.xml.validation.Validator validator = schema.newValidator();
		validator.validate(xmlFile);
	}

	@Test(expected = SAXParseException.class)
	public void validate_must_fail() throws Exception {
		Source xmlFile = new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
						"schemas/datacite/kernel-4.1/example/datacite-example-complicated-v4.1-fail.xml"));
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		factory.setResourceResolver((type, namespaceURI, publicId, systemId, baseURI) -> {
			LSInput input = new DOMInputImpl();
			input.setPublicId(publicId);
			input.setSystemId(systemId);
			input.setBaseURI("https://schema.datacite.org/meta/kernel-4.1/");
			input.setCharacterStream(new InputStreamReader(
							getSchemaAsStream(input.getSystemId(), input.getPublicId(), input.getBaseURI())));
			return input;
		});
		Schema schema = factory.newSchema(new StreamSource(Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("schemas/datacite/kernel-4.1/metadata.xsd")));
		javax.xml.validation.Validator validator = schema.newValidator();
		validator.validate(xmlFile);
	}

	public static InputStream urlToInputStream(URL url, String accept) {
		HttpURLConnection con = null;
		InputStream inputStream = null;
		try {
			con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(15000);
			con.setRequestProperty("User-Agent", "Regal Webservice");
			con.setReadTimeout(15000);
			con.setRequestProperty("Accept", accept);
			con.connect();
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_MOVED_TEMP
							|| responseCode == 307 || responseCode == 303) {
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

	public static InputStream getSchemaAsStream(String systemId, String publicId, String baseUri) {
		InputStream in = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("schemas/datacite/kernel-4.1/" + systemId);
		if (in != null) {
			return in;
		} else {
			try {
				URL url = new URL(systemId);
				return urlToInputStream(url, "text/xml");
			} catch (Exception e2) {
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
}
