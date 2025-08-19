package com.example.order_service.repo;

import com.example.order_service.model.OrderEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SomeMongoRepo extends MongoRepository<OrderEvent,String> {

}
