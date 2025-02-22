import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.rabbitmq.RabbitMQClient;
import io.vertx.rabbitmq.RabbitMQOptions;

public class ProducerVerticle extends AbstractVerticle {


    private final String queueName = "testQueue";

    @Override
    public void start(Promise<Void> startPromise) {
        RabbitMQOptions config = new RabbitMQOptions()
                .setHost("b-010732be-9267-4aa2-bf28-d84ca0c0ecf8.mq.ap-south-1.amazonaws.com")  // Change this to Amazon RMQ URL if needed
                .setPort(5671)
                .setUser("piyush")
                .setPassword("Piyush@12345")
                .setSsl(true);

        config.setVirtualHost("/");


        RabbitMQClient client = RabbitMQClient.create(vertx, config);
        System.out.println("Produer !");

        client.start(ar -> {
            if (ar.succeeded()) {
                System.out.println("Producer: Connected to RabbitMQ!");
                // Optionally declare the queue to ensure it exists
                client.queueDeclare(queueName, true, false, false, new JsonObject(), queueAr -> {
                    if (queueAr.succeeded()) {
                        // Send a message (or use a periodic timer if you want several messages)
                        String message = "Hello Vert.x RMQ! " + System.currentTimeMillis();
                        client.basicPublish("", queueName, Buffer.buffer(message), res -> {
                            if (res.succeeded()) {
                                System.out.println("Producer: Sent -> " + message);
                                startPromise.complete();
                            } else {
                                System.out.println("Producer: Failed to send message " + res.cause());
                                startPromise.fail(res.cause());
                            }
                        });
                    } else {
                        System.out.println("Producer: Failed to declare queue: " + queueAr.cause().getMessage());
                        startPromise.fail(queueAr.cause());
                    }
                });
            } else {
                startPromise.fail(ar.cause());
            }
        });
    }
}
