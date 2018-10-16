package stack52834182;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JsonDeserialize(using = EventDeserializer.class)
@JacksonXmlRootElement(localName = "Event")
public class Event {
    @JsonProperty("EventID")
    private String eventID;
    @JsonProperty("Event")
    private int event;
	public String getEventID() {
		return eventID;
	}
	public void setEventID(String eventID) {
		this.eventID = eventID;
	}
	public int getEvent() {
		return event;
	}
	public void setEvent(int event) {
		this.event = event;
	}
    
}