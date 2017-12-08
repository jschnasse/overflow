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
package stack47711679;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Anime{
	@JacksonXmlProperty(isAttribute = true)
	public String  id;
	public String name;
	public String altname;
	public String seasons;
	public String episodes;
	public String status;
	public String DS;
	public String have;
	public String left;
	public String plot;
	public String connect;
	public String image;
	public String sound;
    public Anime(){
    }
    
    @Override
    public String toString() {
        return "Anime [ID=" + id + 
               ", name=" + name + 
               ", altname=" + altname + 
               ", seasons=" + seasons + 
               ", episodes=" + episodes + 
               ", status=" + status + 
               ", DS=" + DS + 
               ", have=" + have + 
               ", left=" + left + 
               ", plot=" + plot + 
               ", connect=" + connect + 
               ", sound=" + sound + 
               ", image=" + image + "]";
    }
}