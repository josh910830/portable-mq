# PortableMQ

Agile, Not Hasty.

An asynchronous messaging framework for a Spring framework.  
Wouldn't it be nice if the broker change was as easy as moving from MySQL to Postgres?  
Supports Lazy & Agile MSA through SpringEvent and Kafka's Portable Service Abstraction.

by josh910830@gmail.com

---

![concept](./doc/concept.jpg)

---

- [Install](#Install)
- [Usage](#Usage)
- [Architecture](#Architecture)
- [Premise](#Premise)

---

## Install

### build.gradle

```groovy
// build.gradle
```

### @Configuration

```java
// @EnablePortableMQ
```

### application.yml

```yml
spring:
  kafka:
    consumer:
      group-id: groupId # always. SpringListener also has groupId.
      bootstrap-servers: localhost:9092 # on use Kafka.
    producer:
      bootstrap-servers: localhost:9092 # on use Kafka.
    listener:
      ack-mode: manual # on use Kafka. Should be manual.
```

## Usage

@see [PortableMQ-Demo](https://github.com/josh910830/portable-mq-demo)

### Producer

```java
// public class XxxService {
// private final BrokerProducer<XxxMessage> producer;
// }
```

```java
// @Producer("example-message")
// public class XxxSpringProducer implements SpringProducer<XxxMessage> {}
```

```java
// @Producer("example-message")
// public class XxxKafkaProducer implements KafkaProducer<XxxMessage> {}
```

### Consumer

```java
// @Consumer
// public class ExampleSpringConsumer {
//    @Consume(useDeadletter=false)
//    @SpringListener("example-message")
//    fun consume(exampleMessage:ExampleMessage) {
// IMPL
//    }
// }
```

```java
// @Consumer
// class ExampleKafkaConsumer {
//     @Consume(useBadletter = true)
//     @KafkaListener(topics = ["example-message"])
//     fun consume(data: String, ack: Acknowledgment) {}
//
//     @Handle
//     fun handle(message: ExampleMessage) {
// IMPL
//     }
// }
```

### Adapter

```java
// public class JpaDeadletterStore extends AbstractDeadletterStore {
//   @Scheduled
//   @Override
//   public void clear() {
//   }
// }
```

### API

**Deadletter**

<details>
<summary>find</summary>

```http request
GET /portable-mq/deadletter/
    ?topic=topic
    &redriven=false
```

```json
[
  {
    "id": "deadletterId",
    "topic": "topic",
    "message": {
      "id": "messageId",
      "etc": "data"
    },
    "broker": "KAFKA",
    "redriven": false
  }
]
```

</details>

<details>
<summary>findById</summary>

```http request
GET /portable-mq/deadletter/{deadletterId}
```

```json
{
  "id": "deadletterId",
  "topic": "topic",
  "message": {
    "id": "messageId",
    "etc": "data"
  },
  "broker": "KAFKA",
  "redriven": false
}
```

</details>

<details>
<summary>redrive</summary>

```http request
POST /portable-mq/deadletter/redrive
    ?deadletterId=deadletterId
```

```json
{
  "deadletterId": "deadletterId",
  "success": true,
  "error": null
}
```

</details>


<details>
<summary>redriveBatch</summary>

```http request
POST /portable-mq/deadletter/redrive-batch
    ?deadletterIds=id1,id2
```

```json
[
  {
    "deadletterId": "id1",
    "success": true,
    "error": null
  },
  {
    "deadletterId": "id2",
    "success": false,
    "error": "errorMessage"
  }
]
```

</details>

<details>
<summary>redriveAll</summary>

```http request
POST /portable-mq/deadletter/redrive-all
```

```json
[
  {
    "deadletterId": "id1",
    "success": true,
    "error": null
  }
]
```

</details>

<details>
<summary>redriveToken</summary>

```http request
GET /portable-mq/deadletter/redrive-token
    ?deadletterId=deadletterId
    &redriveToken=redriveToken
```

```json
{
  "deadletterId": "deadletterId",
  "success": true,
  "error": null
}
```

</details>

<details>
<summary>drop</summary>

```http request
DELETE /portable-mq/deadletter/drop
    ?deadletterId=deadletterId
```

```json
{
  "deadletterId": "deadletterId",
  "success": true,
  "error": null
}
```

</details>

<details>
<summary>dropBatch</summary>

```http request
DELETE /portable-mq/deadletter/drop-batch
    ?deadletterIds=id1,id2
```

```json
[
  {
    "deadletterId": "id1",
    "success": true,
    "error": null
  },
  {
    "deadletterId": "id2",
    "success": false,
    "error": "errorMessage"
  }
]
```

</details>

<details>
<summary>dropRedriven</summary>

```http request
DELETE /portable-mq/deadletter/drop-redriven
```

```json
[
  {
    "deadletterId": "id1",
    "success": true,
    "error": null
  }
]
```

</details>

<details>
<summary>clear</summary>

```http request
DELETE /portable-mq/deadletter/clear
```

</details>


**Badletter**

<details>
<summary>find</summary>

```http request
GET /portable-mq/badletter/
    ?topic=topic
```

```json
[
  {
    "id": "badletterId",
    "topic": "topic",
    "data": "rawMessage",
    "broker": "KAFKA"
  }
]
```

</details>

<details>
<summary>findById</summary>

```http request
GET /portable-mq/badletter/{badletterId}
```

```json
{
  "id": "badletterId",
  "topic": "topic",
  "data": "rawMessage",
  "broker": "KAFKA"
}
```

</details>

<details>
<summary>drop</summary>

```http request
DELETE /portable-mq/badletter/drop
    ?badletterId=badletterId
```

```json
{
  "badletterId": "badletterId",
  "success": true,
  "error": null
}
```

</details>

<details>
<summary>dropBatch</summary>

```http request
DELETE /portable-mq/badletter/drop-batch
    ?badletterIds=id1,id2
```

```json
[
  {
    "badletterId": "id1",
    "success": true,
    "error": null
  },
  {
    "badletterId": "id2",
    "success": false,
    "error": "errorMessage"
  }
]
```

</details>

<details>
<summary>clear</summary>

```http request
DELETE /portable-mq/badletter/clear
```

</details>

## Architecture

![architecture](./doc/architecture.jpg)

## Premise

### Stable Broker

Asynchronous message queues leverage brokers to reduce the runtime coupling of producers and consumers.  
However, if the broker's stability is not guaranteed, the overall system coupling will rather increase.  
Since Kafka is composed of clusters to provide sufficient reliability, PortableMQ not consider broker failure.

### Not Partitioned

To prevent message loss, commit must be made after the consumer has finished working.  
However, if there are messages that cannot be processed, clogging will occur and operation will be down.  
So, PortableMQ utilized the deadletter, this means that outstanding messages will be processed later.  
Therefore, the order between messages within the partition is not guaranteed.
