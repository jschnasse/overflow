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

    import java.util.List;

    import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
    import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

    public class Schedule {
    	@JacksonXmlProperty(isAttribute = true,localName="generated_at")
    	public String generatedAt;
    	@JacksonXmlProperty(isAttribute = true)
    	public String xmlns;
    	 @JacksonXmlElementWrapper(useWrapping = false)
    	public List<JobEvent> job_event;

    	@Override
        public String toString()
        {
            return "Schedule [generated_at = "+generatedAt+", xmlns = "+xmlns+", job_events = "+job_event.toString()+"]";
        }
    }