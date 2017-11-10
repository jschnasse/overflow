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
    package stack43844461;

    import java.io.IOException;
    import java.util.Map;

    import org.junit.Test;

    import com.fasterxml.jackson.core.JsonParseException;
    import com.fasterxml.jackson.databind.JsonMappingException;
    import com.fasterxml.jackson.databind.ObjectMapper;

    public class HowToConvertEmbeddedJsonStringToMap {

    	@Test
    	public void json() throws JsonParseException, JsonMappingException, IOException {

    		String jsonInString = "{\"someMap\":"
    						+ " {\"one_value\": \"1\","
    						+ "\"another_value\": \"2\"},"
    						+ "\"anotherMap\": "
    						+ "\"{\\\"100000000\\\": 360000,"
    						+ "\\\"100000048\\\": 172800,"
    						+ "\\\"100000036\\\": 129600,"
    						+ "\\\"100000024\\\": 86400,"
    						+ "\\\"100000012\\\": 43200}\"}";
    		
    		ObjectMapper mapper = new ObjectMapper();
    		// Step 1: Read everything into one object. 
    		Map<String, Object> all = mapper.readValue(jsonInString, Map.class);
    		// Step 2: Get your "normal" data into one object
    		Map<String, Object> someMap=(Map<String, Object>) all.get("someMap");
    		// Step 3: Get your "embedded" data from your object
    		String anotherMapStr = (String) all.get("anotherMap");
    		// Step 4: Deserialize embedded data
    		Map<String, Object> anotherMap = mapper.readValue(anotherMapStr, Map.class);
    		System.out.println(anotherMap);
    		System.out.println(someMap);
    	}

    }
