package com.example.order_service.avro;

import com.example.order_service.model.OrderTable;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.checkerframework.checker.units.qual.K;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class SomeAvroSerializer {
    String orderSchema="{\n" +
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

    public String send(OrderTable order){

        GenericRecord record=new GenericData.Record(new Schema.Parser().parse(orderSchema));
        record.put("orderId", order.getOrderId());
        record.put("customerId", order.getCustomerId());
        record.put("productId", order.getProductId());
        record.put("quantity", order.getQuantity());
        record.put("price", order.getPrice());
        KafkaProducer prod=getProducer();
        ProducerRecord<Object,Object> producerRecord=new ProducerRecord<>("someTopic","someKey",record);
        try{
            prod.send(producerRecord).get();
            return "Success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally{
            prod.flush();
            prod.close();
        }
    }

    private KafkaProducer getProducer() {
      Properties props=new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                io.confluent.kafka.serializers.KafkaAvroSerializer.class);
        props.put("schema.registry.url", "http://localhost:8081");
        return new KafkaProducer(props);
    }
}
