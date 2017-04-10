package stack41365068;

import org.junit.Test;

import java.io.InputStream;

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

}
