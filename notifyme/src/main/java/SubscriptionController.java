import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity<Subscription> createSubscription(@RequestBody SubscriptionRequest request) {
        User user = userRepository.findById(request.getUserId())
                      .orElseThrow(() -> new RuntimeException("User not found"));
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setEventType(request.getEventType());
        subscription.setStatus("active");
        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return ResponseEntity.ok(savedSubscription);
    }

    @GetMapping("/{eventType}")
    public ResponseEntity<List<Subscription>> getSubscriptionsByEventType(@PathVariable String eventType) {
        List<Subscription> subscriptions = subscriptionRepository.findByEventTypeAndStatus(eventType, "active");
        return ResponseEntity.ok(subscriptions);
    }
}

// DTO for subscription request
class SubscriptionRequest {
    private Long userId;
    private String eventType;
    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
}
