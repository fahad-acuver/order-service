package com.example.order_service.avro;

import com.example.order_service.model.OrderTable;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

// 1. Change to @Service - its job is to perform an action.
@Service
public class SomeAvroSerializer {

    // 2. Inject the KafkaTemplate managed by Spring. No more manual producer creation!
    private final KafkaTemplate<Object, Object> kafkaTemplate;

    private final Schema schema;

    private final String orderSchema = "{\n" +
            "  \"type\": \"record\",\n" +
            "  \"namespace\": \"com.example.order_service.model\",\n" +
            "  \"name\": \"OrderTable\",\n" +
            "  \"fields\": [\n" +
            "    { \"name\": \"orderId\", \"type\": \"string\" },\n" +
            "    { \"name\": \"customerId\", \"type\": \"string\" },\n" +
            "    { \"name\": \"productId\", \"type\": \"string\" },\n" +
            "    { \"name\": \"quantity\", \"type\": \"int\" },\n" +
            "    { \"name\": \"price\", \"type\": \"double\" }\n" +
            "  ]\n" +
            "}";

    // 3. Use constructor injection to get the KafkaTemplate.
    // Also parse the schema once during initialization, not every time.
    public SomeAvroSerializer(KafkaTemplate<Object, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.schema = new Schema.Parser().parse(orderSchema);
    }

    public String send(OrderTable order) {
        // Create the generic record
        GenericRecord record = new GenericData.Record(schema);
        record.put("orderId", order.getOrderId());
        record.put("customerId", order.getCustomerId());
        record.put("productId", order.getProductId());
        record.put("quantity", order.getQuantity());
        record.put("price", order.getPrice());

        // 4. The send method is now a single, clean line.
        // Spring manages the producer's lifecycle (connections, flushing, closing).
        try {
            kafkaTemplate.send("someTopic", "someKey", record).get();
            return "Success";
        } catch (Exception e) {
            // It's better to log the error and re-throw or handle it appropriately
            e.printStackTrace();
            throw new RuntimeException("Failed to send Avro message", e);
        }
    }

    // 5. The manual getProducer() method is no longer needed and should be deleted.
}