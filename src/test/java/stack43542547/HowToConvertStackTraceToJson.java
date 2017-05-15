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
    package stack43542547;

    import java.io.StringWriter;
    import org.junit.Test;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.fasterxml.jackson.databind.SerializationFeature;

    public class HowToConvertStackTraceToJson {
    	@Test
    	public void convertStackTraceToJson() throws Exception {
    		try {
    			throw new NullPointerException();
    		} catch (Exception e) {
    			System.out.println(asJson(e));
    		}
    	}

    	private String asJson(Object obj) throws Exception {
    			StringWriter w = new StringWriter();
    			new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true).writeValue(w, obj);
    			String result = w.toString();
    			return result;	}
    }
