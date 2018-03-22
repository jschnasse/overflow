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