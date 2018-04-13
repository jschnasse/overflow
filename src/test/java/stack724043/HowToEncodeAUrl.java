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
						String encodedUrl = encodeUrl(unencodedUrl);
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

	@Test
	public void testUrl() {
		try (InputStream in = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("url-succeding-tests.json")) {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode testdata = mapper.readValue(in, JsonNode.class).at("/tests");
			for (JsonNode test : testdata) {
				String url = test.at("/in").asText();
				String expected = test.at("/out").asText();
				String encodedUrl = encodeUrl(url);

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
					encodedUrl = encodeUrl(url);
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

	public String encodeUrl(String url) {
		try {
			URL u = new URL(url);
			URI uri = new URI(u.getProtocol(), u.getUserInfo(), IDN.toASCII(u.getHost()), u.getPort(), u.getPath(),
							u.getQuery(), u.getRef());
			String correctEncodedURL = uri.toASCIIString();
			return correctEncodedURL;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String decodeUrl(String url) {
		return new String(decode(url.getBytes()));
	}

	/** Copy
	 * https://android.googlesource.com/platform/frameworks/base/+/a5408e6/core/java/android/webkit/URLUtil.java
	 * Copyright (C) 2006 The Android Open Source Project
	 *
	 * Licensed under the Apache License, Version 2.0 (the "License"); you may
	 * not use this file except in compliance with the License. You may obtain a
	 * copy of the License at
	 *
	 * http://www.apache.org/licenses/LICENSE-2.0
	 *
	 * Unless required by applicable law or agreed to in writing, software
	 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
	 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
	 * License for the specific language governing permissions and limitations
	 * under the License.
	 */
	public byte[] decode(byte[] url) throws IllegalArgumentException {
		if (url.length == 0) {
			return new byte[0];
		}
		// Create a new byte array with the same length to ensure capacity
		byte[] tempData = new byte[url.length];
		int tempCount = 0;
		for (int i = 0; i < url.length; i++) {
			byte b = url[i];
			if (b == '%') {
				if (url.length - i > 2) {
					b = (byte) (parseHex(url[i + 1]) * 16 + parseHex(url[i + 2]));
					i += 2;
				} else {
					throw new IllegalArgumentException("Invalid format");
				}
			}
			tempData[tempCount++] = b;
		}
		byte[] retData = new byte[tempCount];
		System.arraycopy(tempData, 0, retData, 0, tempCount);
		return retData;
	}

	/** Copy
	 * https://android.googlesource.com/platform/frameworks/base/+/a5408e6/core/java/android/webkit/URLUtil.java
	 * * Copyright (C) 2006 The Android Open Source Project
	 *
	 * Licensed under the Apache License, Version 2.0 (the "License"); you may
	 * not use this file except in compliance with the License. You may obtain a
	 * copy of the License at
	 *
	 * http://www.apache.org/licenses/LICENSE-2.0
	 *
	 * Unless required by applicable law or agreed to in writing, software
	 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
	 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
	 * License for the specific language governing permissions and limitations
	 * under the License.
	 */
	private int parseHex(byte b) {
		if (b >= '0' && b <= '9')
			return (b - '0');
		if (b >= 'A' && b <= 'F')
			return (b - 'A' + 10);
		if (b >= 'a' && b <= 'f')
			return (b - 'a' + 10);
		throw new IllegalArgumentException("Invalid hex char '" + b + "'");
	}
}
