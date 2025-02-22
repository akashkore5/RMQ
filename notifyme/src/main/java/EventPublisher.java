import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {

    private static final String TOPIC = "notification_events";

    @Autowired
    private KafkaTemplate<String, EventPayload> kafkaTemplate;

    public void publishEvent(EventPayload payload) {
        kafkaTemplate.send(TOPIC, payload);
    }
}

// Payload class representing event data
class EventPayload {
    private String eventType;
    private String referenceId;
    // Constructors, Getters, and Setters
    public EventPayload() {}
    public EventPayload(String eventType, String referenceId) {
        this.eventType = eventType;
        this.referenceId = referenceId;
    }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getReferenceId() { return referenceId; }
    public void setReferenceId(String referenceId) { this.referenceId = referenceId; }
}
