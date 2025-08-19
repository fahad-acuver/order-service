package com.example.order_service.service;

import com.example.order_service.avro.SomeAvroSerializer;
import com.example.order_service.model.OrderTable;
import com.example.order_service.repo.SomeRepo;
import org.springframework.stereotype.Service;

@Service
public class SomeService {
    private final SomeAvroSerializer someAvroSerializer;
    private final SomeRepo someRepo;

    public SomeService(SomeRepo someRepo, SomeAvroSerializer someAvroSerializer){
        this.someRepo=someRepo;
        this.someAvroSerializer=someAvroSerializer;
    }
    public void send(OrderTable order) {
        someAvroSerializer.send(order);
        someRepo.save(order);
    }
}
