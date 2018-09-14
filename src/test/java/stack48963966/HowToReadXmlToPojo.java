    package stack48963966;

    import java.util.List;

    import org.junit.Test;

    import com.fasterxml.jackson.core.type.TypeReference;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.fasterxml.jackson.dataformat.xml.XmlMapper;

    import stack47711679.Anime;

    public class HowToReadXmlToPojo {
    	@Test
    	public void readXmlToPojo() throws Exception {
    		ObjectMapper mapper = new XmlMapper();
    		Anime pojo = mapper.readValue(
    						Thread.currentThread().getContextClassLoader().getResourceAsStream("47711679.xml"),
    						Anime.class);
    		System.out.println(pojo + "");
    	}

    	@Test
    	public void readXmlToListOfPojo() throws Exception {
    		ObjectMapper mapper = new XmlMapper();
    		List<Anime> pojos = mapper.readValue(
    						Thread.currentThread().getContextClassLoader().getResourceAsStream("47711679_v2.xml"),
    						new TypeReference<List<Anime>>() {
    						});
    		System.out.println(pojos + "");
    	}

    	@Test
    	public void readXmlToSelfDefinedPojo() throws Exception {

    		ObjectMapper mapper = new XmlMapper();
    		List<JobEvent> pojo = mapper.readValue(
    						Thread.currentThread().getContextClassLoader().getResourceAsStream("48963966.xml"),
    						new TypeReference<List<JobEvent>>(){});
    		System.out.println(pojo + "");
    	}
    	
    	@Test
    	public void readXmlToSelfDefinedPojo2() throws Exception {

    		ObjectMapper mapper = new XmlMapper();
    		Schedule pojo = mapper.readValue(
    						Thread.currentThread().getContextClassLoader().getResourceAsStream("48963966.xml"),
    						Schedule.class);
    		System.out.println(pojo + "");
    	}
    	
    	
    }
