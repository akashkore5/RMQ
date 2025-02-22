import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.rabbitmq.RabbitMQClient;
import io.vertx.rabbitmq.RabbitMQOptions;

public class ConsumerVerticle extends AbstractVerticle {

//    private RabbitMQClient client;
    private final String queueName = "testQueue";

    @Override
    public void start(Promise<Void> startPromise) {
        RabbitMQOptions config = new RabbitMQOptions()
                .setHost("b-010732be-9267-4aa2-bf28-d84ca0c0ecf8.mq.ap-south-1.amazonaws.com")  // Change this to Amazon RMQ URL if needed
                .setPort(5671)
                .setUser("piyush")
                .setPassword("Piyush@12345")
                .setSsl(true);

        RabbitMQClient client = RabbitMQClient.create(vertx, config);

        client.start(ar -> {
            if (ar.succeeded()) {
                System.out.println("Consumer: Connected to RabbitMQ!");
                // Optionally declare the queue to ensure it exists
                client.queueDeclare(queueName, true, false, false, new JsonObject(), queueAr -> {
                    if (queueAr.succeeded()) {
                        System.out.println("Consumer: Queue declared: " + queueName);
                        client.basicConsumer(queueName)
                                .onSuccess(consumer -> {
                                    consumer.handler(message -> {
                                        System.out.println("Consumer: Received -> " + message.body().toString());
                                    });
                                    System.out.println("Consumer: Listening on queue...");
                                    startPromise.complete();
                                })
                                .onFailure(err -> {
                                    System.out.println("Consumer: Failed to consume messages: " + err.getMessage());
                                    startPromise.fail(err);
                                });
                    } else {
                        System.out.println("Consumer: Failed to declare queue: " + queueAr.cause().getMessage());
                        startPromise.fail(queueAr.cause());
                    }
                });
            } else {
                startPromise.fail(ar.cause());
            }
        });
    }
}
