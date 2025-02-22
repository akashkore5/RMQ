# Notify Me Functionality

## Overview
The "Notify Me" functionality is designed for e-commerce, stock tracking, or any system where users need to be alerted when an event occurs, such as an item restocking, a price drop, or service availability.

## High-Level Design (HLD)

### **Actors**
- **User**: Requests notifications for specific events.
- **System**: Manages notification subscriptions and event triggers.
- **Notification Service**: Sends notifications via different channels (Email, SMS, Push).

### **Components**
- **Frontend/UI**: Allows users to subscribe for notifications.
- **Backend Service**: Handles subscription requests and notification logic.
- **Database**: Stores user subscriptions and event data.
- **Message Queue**: Ensures reliable event processing.
- **Notification Service**: Sends notifications using various channels.

### **Flow**
1. The user subscribes to a notification.
2. The system stores the subscription details.
3. When an event occurs, a message is sent to a queue.
4. The Notification Service picks up the event and sends notifications to the user.

## Low-Level Design (LLD)

### **Database Schema**

#### **User Table**
| Column    | Type    | Description                |
|-----------|--------|----------------------------|
| id        | INT    | Primary Key                |
| name      | STRING | User's name                |
| email     | STRING | User's email address       |
| phone     | STRING | User's phone number        |
| preferences | JSON | Notification preferences   |

#### **Subscription Table**
| Column    | Type    | Description                |
|-----------|--------|----------------------------|
| id        | INT    | Primary Key                |
| user_id   | INT    | Foreign Key (User Table)   |
| event_type | STRING | Type of event subscribed to |
| status    | STRING | Subscription status        |

#### **Event Table**
| Column      | Type    | Description                |
|------------|--------|----------------------------|
| id         | INT    | Primary Key                |
| event_type | STRING | Type of event              |
| reference_id | INT  | Associated entity ID       |
| created_at | TIMESTAMP | Event creation timestamp |

## **Technology Stack**
- **Backend**: Java (Spring Boot, Vert.x)
- **Database**: MySQL/PostgreSQL (for structured data)
- **Event Queue**: Redis/Kafka (for event processing)
- **Message Queue**: RabbitMQ (for reliable messaging)
- **Notification Service**: Firebase, Twilio, AWS SES (for sending notifications)

## **Scalability Considerations**
- **Event Processing**: Use Kafka or RabbitMQ for distributed event handling.
- **Caching**: Store recent events in Redis to minimize database queries.
- **Scaling**: Deploy services with Kubernetes for horizontal scalability.
- **Rate Limiting**: Implement rate limits to prevent notification spamming.

## **Java Implementation**
- Subscription Service
- Event Listener (Kafka/RabbitMQ Consumer)
- Notification Sender (Email, SMS, Push)

  # Notify Me Functionality

## Overview
The "Notify Me" functionality is designed for e-commerce, stock tracking, or any system where users need to be alerted when an event occurs, such as an item restocking, a price drop, or service availability.

## High-Level Design (HLD)

### Actors
- **User**: Requests notifications for specific events.
- **System**: Manages notification subscriptions and event triggers.
- **Notification Service**: Sends notifications via different channels (Email, SMS, Push).

### Components
- **Frontend/UI**: Allows users to subscribe for notifications.
- **Backend Service**: Handles subscription requests and notification logic.
- **Database**: Stores user subscriptions and event data.
- **Message Queue**: Ensures reliable event processing.
- **Notification Service**: Sends notifications using various channels.

### Flow
1. The user subscribes to a notification.
2. The system stores the subscription details.
3. When an event occurs, a message is sent to a queue.
4. The Notification Service picks up the event and sends notifications to the user.

## Workflow

### Subscription
- User subscribes through the UI (choosing event type and preferred channel).
- API call to backend service → persist subscription in the database.

### Event Triggering
- An external system or internal process generates an event (e.g., a product is back in stock).
- The event is published to a message queue.

### Notification Processing
- The Notification Service consumes the event.
- It retrieves the list of users subscribed to this event type.
- It dispatches notifications via configured channels.

### Feedback & Monitoring
- The system logs each notification and monitors for failures.
- Metrics (latency, delivery success, rate limits) are collected for system monitoring.

## Low-Level Design (LLD)

### Database Schema

#### User Table
```sql
CREATE TABLE User (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(20),
    preferences JSON -- e.g., preferred channels
);

```CREATE TABLE Subscription (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    event_type VARCHAR(100),
    status VARCHAR(50), -- active, inactive, etc.
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(id)
);
```CREATE TABLE Event (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    event_type VARCHAR(100),
    reference_id VARCHAR(255), -- e.g., product id
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


