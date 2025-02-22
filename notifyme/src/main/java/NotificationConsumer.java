import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationConsumer {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @KafkaListener(topics = "notification_events", groupId = "notify_group")
    public void listen(EventPayload payload) {
        // Retrieve subscriptions for the event type
        List<Subscription> subscriptions = subscriptionRepository
                .findByEventTypeAndStatus(payload.getEventType(), "active");

        // For each subscription, simulate sending a notification
        for (Subscription subscription : subscriptions) {
            sendNotification(subscription.getUser(), payload);
        }
    }

    private void sendNotification(User user, EventPayload payload) {
        // Here, integrate with your notification channels (Email, SMS, etc.)
        System.out.println("Notifying " + user.getEmail() + " about event " + payload.getEventType());
        // e.g., emailService.sendEmail(user.getEmail(), subject, message);
    }
}
