package com.example.order_service.controller;

import com.example.order_service.model.OrderTable;
import com.example.order_service.service.SomeService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class SomeController {

    private final SomeService service;

    public SomeController(SomeService service){
        this.service=service;
    }
    @PostMapping
    public ResponseEntity<Object> sendData(@RequestBody OrderTable order){
        try{
            service.send(order);
            return ResponseEntity.ok(order);
        }
        catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
