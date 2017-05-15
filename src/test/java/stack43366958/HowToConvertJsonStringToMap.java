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
package stack43366958;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HowToConvertJsonStringToMap {

	
	@Test
	public void json() throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "{\"complaint_Map\":{\"1000067730\":\"3011351597604397\",\"1000067730-06\":\"10582576134561065\"}}";
		Map<String,Object> pojo = mapper.readValue(jsonInString, Map.class);
		System.out.println(((Map<String,Object>)pojo.get("complaint_Map")).get("1000067730")+"");
	}

}
