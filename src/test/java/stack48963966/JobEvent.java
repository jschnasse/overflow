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