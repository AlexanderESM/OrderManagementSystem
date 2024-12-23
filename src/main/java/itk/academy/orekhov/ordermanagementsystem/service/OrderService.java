// OrderService.java
package itk.academy.orekhov.ordermanagementsystem.service;

import itk.academy.orekhov.ordermanagementsystem.exception.ResourceNotFoundException;
import itk.academy.orekhov.ordermanagementsystem.exception.ValidationException;
import itk.academy.orekhov.ordermanagementsystem.model.Order;
import itk.academy.orekhov.ordermanagementsystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Поиск заказа по id
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    // Создание нового заказа
    public Order createOrder(Order order) {
        if (order.getTotalPrice() <= 0) {
            throw new ValidationException("Order total must be greater than zero");
        }
        return orderRepository.save(order);
    }
}
