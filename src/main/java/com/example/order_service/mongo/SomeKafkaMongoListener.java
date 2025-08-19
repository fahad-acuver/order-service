package com.example.order_service.mongo;

import com.example.order_service.model.OrderTable;
import com.example.order_service.service.SomeMongoService;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

@Configuration
public class SomeKafkaMongoListener{
    private SomeMongoService someMongoService;

    public SomeKafkaMongoListener(SomeMongoService someMongoService){
        this.someMongoService=someMongoService;
    }
    @KafkaListener(topics = "someTopic",groupId = "someGroup")
    public void  consume(ConsumerRecord<String,GenericRecord> record){
        OrderTable order=new OrderTable(
                record.value().get("orderId").toString(),
                record.value().get("customerId").toString(),
                record.value().get("productId").toString(),
                Integer.parseInt(record.value().get("quantity").toString()),
                Double.parseDouble(record.value().get("price").toString())
        );
        someMongoService.send(order);
    }
}