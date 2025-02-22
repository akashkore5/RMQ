import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        // Deploy consumer verticle so it's always active.
        vertx.deployVerticle(new ConsumerVerticle(), ar -> {
            if (ar.succeeded()) {
                System.out.println("ConsumerVerticle deployed successfully");
            } else {
//                start();
                System.err.println("Failed to deploy ConsumerVerticle: " + ar.cause());
            }
        });

        // Set up a simple HTTP server to trigger the producer
        vertx.createHttpServer().requestHandler(req -> {
            if ("/send".equals(req.path())) {
                // Deploy the ProducerVerticle on-demand
                vertx.deployVerticle(new ProducerVerticle(), prodAr -> {
                    if (prodAr.succeeded()) {
                        req.response().end("Message sent!");
                    } else {
                        req.response().setStatusCode(500).end("Failed to send message: " + prodAr.cause());
                    }
                });
            } else {
                req.response().end("Use /send to send a message.");
            }
        }).listen(8888, httpAr -> {
            if (httpAr.succeeded()) {
                System.out.println("HTTP server started on port 8888");
            } else {
                System.err.println("Failed to start HTTP server: " + httpAr.cause());
            }
        });
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVerticle());
    }
}
