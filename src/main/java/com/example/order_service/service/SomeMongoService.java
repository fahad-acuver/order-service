package com.example.order_service.service;

import com.example.order_service.model.OrderEvent;
import com.example.order_service.model.OrderTable;
import com.example.order_service.repo.SomeMongoRepo;
import org.springframework.stereotype.Service;

@Service
public class SomeMongoService {
    private final SomeMongoRepo someMongoRepo;

    SomeMongoService(SomeMongoRepo someMongoRepo){
        this.someMongoRepo=someMongoRepo;
    }

    public void send(OrderTable order){
        someMongoRepo.save(new OrderEvent(order));
        System.out.println("Sent Successfully");
    }
}
