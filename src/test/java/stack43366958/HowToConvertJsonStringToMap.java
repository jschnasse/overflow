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
