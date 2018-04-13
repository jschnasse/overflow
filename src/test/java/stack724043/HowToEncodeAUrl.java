package stack724043;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.IDN;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
/**
 * https://url.spec.whatwg.org/#parsers
 * https://stackoverflow.com/a/49778055/1485527
 * @author jan
 *
 */
public class HowToEncodeAUrl {
	
	@Test
	public void testUrl() {
		try (InputStream in = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("url-succeding-tests.json")) {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode testdata = mapper.readValue(in, JsonNode.class).at("/tests");
			for (JsonNode test : testdata) {
				String url = test.at("/in").asText();
				String expected = test.at("/out").asText();
				String encodedUrl = URLUtil.encode(url);
				org.junit.Assert.assertTrue(expected.equals(encodedUrl));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void failingUrls() {
		try (InputStream in = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("url-failing-tests.json")) {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode testdata = mapper.readValue(in, JsonNode.class).at("/tests");
			for (JsonNode test : testdata) {
				String url = test.at("/in").asText();
				String expected = test.at("/out").asText();
				String encodedUrl = "ERROR";
				try {
					encodedUrl = URLUtil.encode(url);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
				System.out.println("In:\t" + url);
				System.out.println("Expect:\t" + expected);
				System.out.println("Actual:\t" + encodedUrl);
				System.out.println("");
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String asJson(Object obj) throws Exception {
		StringWriter w = new StringWriter();
		new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true).writeValue(w, obj);
		String result = w.toString();
		return result;
	}

	
	
	
	public void generateTestData() {
		 generateTestData("urls-local.json");
		 generateTestData("urls.json");
	}

	public void generateTestData(String name) {
		List<Map<String, Object>> success = new ArrayList<>(200);

		List<Map<String, Object>> failing = new ArrayList<>(200);
		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(name)) {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode urlData = mapper.readValue(in, JsonNode.class);
			JsonNode groups = urlData.at("/tests/group");
			for (JsonNode group : groups) {
				JsonNode tests = group.at("/test");
				for (JsonNode test : tests) {

					Map<String, Object> testData = new TreeMap<>();

					String expectedUrl = test.at("/encoded").asText();
					String unencodedUrl = test.at("/unencoded").asText();
					try {
						String encodedUrl = URLUtil.encode(unencodedUrl);
						if (!expectedUrl.equals(encodedUrl))
							throw new RuntimeException("Unexpected comparison!");
						testData.put("in", unencodedUrl);
						testData.put("out", expectedUrl);
						success.add(testData);
					} catch (Exception e) {
						testData.put("in", unencodedUrl);
						testData.put("out", expectedUrl);
						failing.add(testData);
					}
				}
			}
			System.out.println("Working URLs --------------");
			System.out.println(asJson(success));
			System.out.println("Not Working URLs-----------");
			System.out.println(asJson(failing));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
