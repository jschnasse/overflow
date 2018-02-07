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
    import java.net.URL;
    import java.nio.charset.StandardCharsets;
    import java.util.Base64;
    
    import org.junit.Test;
    
    public class HowToReadFromAnURL {
    	@Test
    	public void readFromUrl() {
    		try (InputStream in = getInputStreamFromUrl("https://jira.atlassian.com/rest/api/2/issue/JSWCLOUD-11658")) {
    			System.out.println(convertInputStreamToString(in));
    		} catch (Exception e) {
    			throw new RuntimeException(e);
    		}
    	}
    	
    	@Test(expected = RuntimeException.class)
    	public void readFromUrlWithBasicAuth() {
    		String user="aUser";
    		String passwd="aPasswd";
    		try (InputStream in = getInputStreamFromUrl("https://jira.atlassian.com/rest/api/2/issue/JSWCLOUD-11658",user,passwd)) {
    			System.out.println(convertInputStreamToString(in));
    		} catch (Exception e) {
    			System.out.println("If basic auth is provided, it should be correct: "+e.getMessage());
    			throw new RuntimeException(e);
    		}
    	}

    	private InputStream getInputStreamFromUrl(String urlString,String user, String passwd) throws IOException {
    		URL url = new URL(urlString);
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		conn.setRequestMethod("GET");
    		conn.setRequestProperty("Accept", "application/json");
    		String encoded = Base64.getEncoder().encodeToString((user+":"+passwd).getBytes(StandardCharsets.UTF_8));  
    		conn.setRequestProperty("Authorization", "Basic "+encoded);
    		return conn.getInputStream();
    	}
    	
    	private InputStream getInputStreamFromUrl(String urlString) throws IOException {
    		URL url = new URL(urlString);
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		conn.setRequestMethod("GET");
    		conn.setRequestProperty("Accept", "application/json");
    		return conn.getInputStream();
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
    }
