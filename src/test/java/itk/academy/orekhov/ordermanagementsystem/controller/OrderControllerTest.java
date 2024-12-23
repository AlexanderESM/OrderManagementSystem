package itk.academy.orekhov.ordermanagementsystem.controller;

import itk.academy.orekhov.ordermanagementsystem.exception.ResourceNotFoundException;
import itk.academy.orekhov.ordermanagementsystem.model.Order;
import itk.academy.orekhov.ordermanagementsystem.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        // Настройка MockMvc с контроллером
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void getOrderById_ShouldReturnOrder() throws Exception {
        // Создаем заказ для теста
        Order order = new Order();
        order.setOrderId(1L);
        order.setOrderStatus("PENDING");

        // Подготовка mock-объекта сервиса
        when(orderService.findById(1L)).thenReturn(order);

        // Выполнение GET-запроса и проверка ответа
        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())  // Ожидаем статус 200 OK
                .andExpect(jsonPath("$.orderId").value(1))  // Проверка ID заказа
                .andExpect(jsonPath("$.orderStatus").value("PENDING"));  // Проверка статуса заказа
    }

    @Test
    void getOrderById_ShouldReturnNotFound_WhenOrderNotExist() throws Exception {
        // Подготовка mock-объекта сервиса, чтобы вернуть null
        when(orderService.findById(1L)).thenReturn(null);

        // Выполнение GET-запроса и проверка ответа
        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isNotFound())  // Ожидаем статус 404 Not Found
                .andExpect(jsonPath("$.message").value("Order with id 1 not found"));  // Проверка сообщения об ошибке
    }

    @Test
    void createOrder_ShouldReturnCreatedOrder() throws Exception {
        // Создаем заказ для теста
        Order order = new Order();
        order.setOrderId(1L);
        order.setOrderStatus("PENDING");

        // Подготовка mock-объекта сервиса
        when(orderService.createOrder(order)).thenReturn(order);

        // Выполнение POST-запроса и проверка ответа
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderId\":1,\"orderStatus\":\"PENDING\"}"))
                .andExpect(status().isCreated())  // Ожидаем статус 201 Created
                .andExpect(jsonPath("$.orderId").value(1))  // Проверка ID заказа
                .andExpect(jsonPath("$.orderStatus").value("PENDING"));  // Проверка статуса заказа
    }

    @Test
    void createOrder_ShouldReturnBadRequest_WhenValidationFails() throws Exception {
        // Создаем заказ с ошибками валидации (например, обязательное поле отсутствует)
        Order order = new Order();
        order.setOrderStatus(null);  // Статус заказа не может быть null, если он обязательный

        // Выполнение POST-запроса и проверка ответа на ошибку валидации
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderStatus\":null}"))
                .andExpect(status().isBadRequest())  // Ожидаем статус 400 Bad Request
                .andExpect(jsonPath("$.message").value("Validation failed: Validation failed: order status must not be null"));  // Проверка сообщения об ошибке
    }
}
