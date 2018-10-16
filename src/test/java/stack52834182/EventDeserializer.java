package stack52834182;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class EventDeserializer extends StdDeserializer<Event> {
	 
    public EventDeserializer() {
        this(null);
    }

    public EventDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Event deserialize(JsonParser jp, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
        JsonNode eventNode = jp.getCodec().readTree(jp);
        Event event = new Event();
        event.setEventID(eventNode.get("EventID").textValue()+" My Own Blabla");
        event.setEvent(eventNode.get("Event").asInt());
        return event;
    }
}