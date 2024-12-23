package itk.academy.orekhov.ordermanagementsystem.service;

import itk.academy.orekhov.ordermanagementsystem.exception.ResourceNotFoundException;
import itk.academy.orekhov.ordermanagementsystem.exception.ValidationException;
import itk.academy.orekhov.ordermanagementsystem.model.Order;
import itk.academy.orekhov.ordermanagementsystem.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    // Инициализация мока перед каждым тестом
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Тест на поиск заказа по id (существующий заказ)
    @Test
    public void testFindByIdOrderExists() {
        // Создаем заказ для теста
        Order order = new Order(1L, null, null, "2024-12-23", "123 Street", 25.0, "Completed");

        // Мокаем поведение репозитория
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));

        // Вызов метода findById
        Order foundOrder = orderService.findById(1L);

        // Проверки
        assertNotNull(foundOrder, "Order should not be null");
        assertEquals(1L, foundOrder.getOrderId(), "Order ID should match");
        verify(orderRepository, times(1)).findById(1L);  // Проверка, что метод был вызван
    }

    // Тест на поиск заказа по id (заказ не найден)
    @Test
    public void testFindByIdOrderNotFound() {
        // Мокаем поведение репозитория на пустой результат
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Проверка, что метод выбрасывает исключение
        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.findById(1L);
        }, "Expected ResourceNotFoundException to be thrown");
    }

    // Тест на создание нового заказа с правильной ценой
    @Test
    public void testCreateOrderValid() {
        // Создаем заказ с правильной ценой
        Order order = new Order(1L, null, null, "2024-12-23", "123 Street", 25.0, "Completed");

        // Мокаем поведение репозитория
        when(orderRepository.save(order)).thenReturn(order);

        // Вызов метода createOrder
        Order createdOrder = orderService.createOrder(order);

        // Проверки
        assertNotNull(createdOrder, "Created order should not be null");
        assertEquals(25.0, createdOrder.getTotalPrice(), "Order total price should match");
        verify(orderRepository, times(1)).save(order);  // Проверка, что метод был вызван
    }

    // Тест на создание нового заказа с неправильной ценой
    @Test
    public void testCreateOrderInvalidTotalPrice() {
        // Создаем заказ с неправильной ценой
        Order order = new Order(1L, null, null, "2024-12-23", "123 Street", -5.0, "Completed");

        // Проверка, что метод выбрасывает исключение
        assertThrows(ValidationException.class, () -> {
            orderService.createOrder(order);
        }, "Expected ValidationException to be thrown");
    }
}
