import io.vertx.core.json.JsonObject;
import io.vertx.rabbitmq.RabbitMQOptions;

public class RMQOptionsFactory {

    public static RabbitMQOptions createOptions() {
//        JsonObject rmqConfig = ConfigManager.INSTANCE.getRMQConfig();
        JsonObject rmqConfig = new JsonObject();
        RabbitMQOptions options = new RabbitMQOptions();
        options.setHost(rmqConfig.getString("host", "b-cf8a0759-a7fe-4c32-9b01-f8a7e60de27f.mq.ap-south-1.amazonaws.com"));
        options.setPort(rmqConfig.getInteger("port", 5671));
        options.setUser(rmqConfig.getString("user", "dice"));
        options.setPassword(rmqConfig.getString("password", "dicetech@2025"));
        options.setVirtualHost(rmqConfig.getString("virtualHost", "/"));
        return options;
    }
}
