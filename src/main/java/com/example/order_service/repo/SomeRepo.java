package com.example.order_service.repo;

import com.example.order_service.model.OrderTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SomeRepo extends JpaRepository<OrderTable,String> {

}
