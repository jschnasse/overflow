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
    package stack44164695;

    import java.util.HashMap;
    import java.util.Map;

    import org.junit.Test;

    public class HowToCountWordOccurrences {
    	@Test
    	public void countWords() {
    		String[] queries = { "question", "This", "is", "a" };
    		String data = "a foo This bar is This foo question a bar question foo";

    		Map<String, Integer> index= new HashMap<>();
    		for (String w : data.split(" ")) {
    			Integer count=index.get(w);
    			if(count==null){
    				index.put(w, 1);
    			}else{
    				index.put(w, count+=1);
    			}
    		}
    		for(String w:queries){
    			int i=index.get(w);
    			System.out.println(String.format("%d\t%s", i,w));
    		}
    	}
    }
