// OrderController.java
package itk.academy.orekhov.ordermanagementsystem.controller;

import itk.academy.orekhov.ordermanagementsystem.exception.ResourceNotFoundException;
import itk.academy.orekhov.ordermanagementsystem.exception.ValidationException;
import itk.academy.orekhov.ordermanagementsystem.model.Order;
import itk.academy.orekhov.ordermanagementsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Пример обработки запроса на получение заказа по id
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        Order order = orderService.findById(id);
        if (order == null) {
            throw new ResourceNotFoundException("Order with id " + id + " not found");
        }
        return order;
    }

    // Пример обработки создания нового заказа
    @PostMapping
    public Order createOrder(@Valid @RequestBody Order order, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                errors.append(error.getDefaultMessage()).append(", ");
            }
            throw new ValidationException("Validation failed: " + errors.toString());
        }
        return orderService.createOrder(order);
    }
}
