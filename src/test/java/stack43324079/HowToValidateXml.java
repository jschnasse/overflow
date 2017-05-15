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

    import javax.xml.XMLConstants;
    import javax.xml.parsers.DocumentBuilder;
    import javax.xml.parsers.DocumentBuilderFactory;
    import javax.xml.transform.Source;
    import javax.xml.transform.dom.DOMSource;
    import javax.xml.transform.stream.StreamSource;
    import javax.xml.validation.Schema;
    import javax.xml.validation.SchemaFactory;
    import javax.xml.validation.Validator;

    import org.junit.Test;
    import org.w3c.dom.Document;

    public class HowToValidateXml {
    	@Test
    	public void validate1() throws Exception {
    		DocumentBuilderFactory f=DocumentBuilderFactory.newInstance();
    		f.setNamespaceAware(true);
    		DocumentBuilder parser = f.newDocumentBuilder();
    		Document document = parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("43324079.xml"));
    		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    		Source schemaFile = new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream("tincan.xsd"));
    		Schema schema = factory.newSchema(schemaFile);
    		Validator validator = schema.newValidator();
    		validator.validate(new DOMSource(document));
    	}
    	
    	@Test
    	public void validate2() throws Exception {
    		Source xmlFile = new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream("43324079.xml"));
    		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    		Schema schema = factory.newSchema(Thread.currentThread().getContextClassLoader().getResource("tincan.xsd"));
    		Validator validator = schema.newValidator();
    		validator.validate(xmlFile);
    	}
    }
