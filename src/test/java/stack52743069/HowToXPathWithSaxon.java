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
package stack52743069;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.function.Consumer;

import javax.xml.transform.stream.StreamSource;

import org.junit.Test;

import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.XdmItem;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XdmValue;

public class HowToXPathWithSaxon {

	Processor processor = new Processor(false);
	
	@Test
	public void xpathWithSaxon() {
		String xml = "<root><tagA attA=\"VAL1\">text</tagA>\n" + "<tagB attB=\"VAL333\">text</tagB>\n"
						+ "<tagA attA=\"VAL2\">text</tagA>\n" + "<tagA attA=\"V2\">text</tagA>\n" + "</root>";
		try (InputStream in = new ByteArrayInputStream(xml.getBytes("utf-8"));) {
			processFilteredXmlWith(in, "//root/tagA[matches(@attA,'^VAL\\d*$')]", (node) -> {
				printItem(node, System.out);
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void printItem(XdmItem node, PrintStream out) {
		out.println(node);
	}

	public void processFilteredXmlWith(InputStream in, String xpath, Consumer<XdmItem> process) {
		XdmNode doc = readXmlWith(in);
		XdmValue list = filterNodesByXPathWith(doc, xpath);
		list.forEach((node) -> {
			process.accept(node);
		});

	}

	private XdmNode readXmlWith(InputStream xmlin) {
		try {
			return processor.newDocumentBuilder().build(new StreamSource(xmlin));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private XdmValue filterNodesByXPathWith(XdmNode doc, String xpathExpr) {
		try {
			return processor.newXPathCompiler().evaluate(xpathExpr, doc);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
