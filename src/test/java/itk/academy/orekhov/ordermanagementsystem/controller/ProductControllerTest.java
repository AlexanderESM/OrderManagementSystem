package itk.academy.orekhov.ordermanagementsystem.controller;

import itk.academy.orekhov.ordermanagementsystem.model.Product;
import itk.academy.orekhov.ordermanagementsystem.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        // Создаем тестовый продукт
        product = new Product(1L, "Test Product", 99.99, "Test Description", 100);
    }

    @Test
    public void testGetAllProducts() throws Exception {
        // Подготовка мока
        when(productService.getAllProducts()).thenReturn(List.of(product));

        // Выполнение запроса и проверка ответа
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Product"));
    }

    @Test
    public void testGetProductById() throws Exception {
        // Подготовка мока
        when(productService.getProductById(anyLong())).thenReturn(product);

        // Выполнение запроса и проверка ответа
        mockMvc.perform(get("/api/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    public void testGetProductByIdNotFound() throws Exception {
        // Подготовка мока
        when(productService.getProductById(anyLong())).thenThrow(new RuntimeException("Product not found"));

        // Выполнение запроса и проверка ответа
        mockMvc.perform(get("/api/products/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateProduct() throws Exception {
        // Подготовка мока
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        // Выполнение запроса и проверка ответа
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test Product\", \"price\": 99.99, \"description\": \"Test Description\", \"quantityInStock\": 100}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        // Подготовка мока
        when(productService.updateProduct(anyLong(), any(Product.class))).thenReturn(product);

        // Выполнение запроса и проверка ответа
        mockMvc.perform(put("/api/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Product\", \"price\": 149.99, \"description\": \"Updated Description\", \"quantityInStock\": 200}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.price").value(149.99));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        // Подготовка мока для метода, который не возвращает значение
        doNothing().when(productService).deleteProduct(anyLong());

        // Выполнение запроса и проверка ответа
        mockMvc.perform(delete("/api/products/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
