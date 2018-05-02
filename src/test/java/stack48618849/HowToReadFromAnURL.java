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

package stack48618849;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

public class HowToReadFromAnURL {
	@Test
	public void readFromUrl() {
		try (InputStream in = getInputStreamFromUrl("http://jira.atlassian.com/rest/api/2/issue/JSWCLOUD-11658")) {
			System.out.println(convertInputStreamToString(in));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test(expected = RuntimeException.class)
	public void readFromUrlWithBasicAuth() {
		String user = "aUser";
		String passwd = "aPasswd";
		try (InputStream in = getInputStreamFromUrl(
						new URL("https://jira.atlassian.com/rest/api/2/issue/JSWCLOUD-11658"), user, passwd)) {
			System.out.println(convertInputStreamToString(in));
		} catch (Exception e) {
			System.out.println("If basic auth is provided, it should be correct: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Test
	public void readFromUrlWithNoHeaders() throws IOException {
		try (InputStream in = urlToInputStream(new URL("http://google.de"), null)) {
			System.out.println(convertInputStreamToString(in));
		}
	}

	private InputStream getInputStreamFromUrl(URL url, String user, String passwd) throws IOException {
		String encoded = Base64.getEncoder().encodeToString((user + ":" + passwd).getBytes(StandardCharsets.UTF_8));
		return urlToInputStream(url, mapOf("Accept", "application/json", "Authorization", "Basic " + encoded,
						"User-Agent", "myApplication"));
	}

	private InputStream getInputStreamFromUrl(String url) throws IOException {
		return urlToInputStream(new URL(url), mapOf("Accept", "application/json", "User-Agent", "myApplication"));
	}

	private String convertInputStreamToString(InputStream inputStream) throws IOException {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) != -1) {
			result.write(buffer, 0, length);
		}
		return result.toString("UTF-8");
	}

	private InputStream urlToInputStream(URL url, Map<String, String> args) {
		HttpURLConnection con = null;
		InputStream inputStream = null;
		try {
			con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(15000);
			con.setReadTimeout(15000);
			if (args != null) {
				for (Entry<String, String> e : args.entrySet()) {
					con.setRequestProperty(e.getKey(), e.getValue());
				}
			}
			con.connect();
			int responseCode = con.getResponseCode();
			/* By default the connection will follow redirects. The following
			 * block is only entered if the implementation of HttpURLConnection
			 * does not perform the redirect. The exact behavior depends to 
			 * the actual implementation (e.g. sun.net).
			 * !!! Attention: This block allows the connection to 
			 * switch protocols (e.g. HTTP to HTTPS), which is <b>not</b> 
			 * default behavior. See: https://stackoverflow.com/questions/1884230 
			 * for more info!!!
			 */
			if (responseCode < 400 && responseCode > 299) {
				String redirectUrl = con.getHeaderField("Location");
				try {
					URL newUrl = new URL(redirectUrl);
					return urlToInputStream(newUrl, args);
				} catch (MalformedURLException e) {
					URL newUrl = new URL(url.getProtocol() + "://" + url.getHost() + redirectUrl);
					return urlToInputStream(newUrl, args);
				}
			}
			/*!!!!!*/
			
			inputStream = con.getInputStream();
			return inputStream;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private <K, V> Map<K, V> mapOf(Object... keyValues) {
		Map<K, V> map = new HashMap<>();
		K key = null;
		for (int index = 0; index < keyValues.length; index++) {
			if (index % 2 == 0) {
				key = (K) keyValues[index];
			} else {
				map.put(key, (V) keyValues[index]);
			}
		}
		return map;
	}

}
