/*******************************************************************************
 * Copyright 2019 Jan Schnasse
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
package stack48963966;

    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class JobEvent {
    	@JacksonXmlProperty(isAttribute = true)
    	private String id;
    	@JacksonXmlProperty(isAttribute = true)
    	private String scheduled;
    	@JacksonXmlProperty(isAttribute = true, localName = "start_time_tbd")
    	private String startTimeTbd;
    	@JacksonXmlProperty(isAttribute = true)
    	private String status;

    	@Override
    	public String toString() {
    		return id + " " + scheduled + " " + " " + startTimeTbd + " " + status;
    	}
    }