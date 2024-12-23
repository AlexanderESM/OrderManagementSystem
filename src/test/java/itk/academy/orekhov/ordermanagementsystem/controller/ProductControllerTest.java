package itk.academy.orekhov.ordermanagementsystem.controller;

import itk.academy.orekhov.ordermanagementsystem.model.Product;
import itk.academy.orekhov.ordermanagementsystem.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)  // Указываем только контроллер для тестирования
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;  // MockMvc автоматически инжектируется

    @MockBean
    private ProductService productService;  // Мокируем сервис с помощью @MockBean

    @Test
    public void testGetAllProducts() throws Exception {
        // Подготовка данных для теста
        Product product1 = new Product("Product 1", 10.0);
        Product product2 = new Product("Product 2", 15.0);
        when(productService.getAllProducts()).thenReturn(List.of(product1, product2));

        mockMvc.perform(get("/products/"))
                .andExpect(status().isOk()) // Ожидаем статус 200 OK
                .andExpect(jsonPath("$[0].name").value("Product 1")) // Проверяем первый продукт
                .andExpect(jsonPath("$[0].price").value(10.0))
                .andExpect(jsonPath("$[1].name").value("Product 2")) // Проверяем второй продукт
                .andExpect(jsonPath("$[1].price").value(15.0));
    }

    @Test
    public void testGetProduct() throws Exception {
        // Подготовка данных для теста
        Product product = new Product(1L, "Product 1", 10.0);
        when(productService.getProduct(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk()) // Ожидаем статус 200 OK
                .andExpect(jsonPath("$.name").value("Product 1"))
                .andExpect(jsonPath("$.price").value(10.0));
    }

    @Test
    public void testGetProductNotFound() throws Exception {
        // Подготовка данных для теста
        when(productService.getProduct(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/products/99"))
                .andExpect(status().isNotFound()) // Ожидаем статус 404 NOT FOUND
                .andExpect(content().string("")); // Ожидаем пустое тело
    }

    @Test
    public void testCreateProduct() throws Exception {
        // Подготовка данных для теста
        Product product = new Product("New Product", 20.0);
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Product\",\"price\":20.0}"))
                .andExpect(status().isCreated()) // Ожидаем статус 201 CREATED
                .andExpect(jsonPath("$.name").value("New Product"))
                .andExpect(jsonPath("$.price").value(20.0));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        // Подготовка данных для теста
        Product existingProduct = new Product(1L, "Product 1", 10.0);
        Product updatedProduct = new Product("Updated Product", 25.0);
        when(productService.updateProduct(1L, updatedProduct)).thenReturn(Optional.of(updatedProduct));

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Product\",\"price\":25.0}"))
                .andExpect(status().isOk()) // Ожидаем статус 200 OK
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.price").value(25.0));
    }

    @Test
    public void testUpdateProductNotFound() throws Exception {
        // Подготовка данных для теста
        Product updatedProduct = new Product("Updated Product", 25.0);
        when(productService.updateProduct(99L, updatedProduct)).thenReturn(Optional.empty());

        mockMvc.perform(put("/products/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Product\",\"price\":25.0}"))
                .andExpect(status().isNotFound()); // Ожидаем статус 404 NOT FOUND
    }

    @Test
    public void testDeleteProduct() throws Exception {
        // Подготовка данных для теста
        when(productService.deleteProduct(1L)).thenReturn(true);

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent()); // Ожидаем статус 204 NO CONTENT
    }

    @Test
    public void testDeleteProductNotFound() throws Exception {
        // Подготовка данных для теста
        when(productService.deleteProduct(99L)).thenReturn(false);

        mockMvc.perform(delete("/products/99"))
                .andExpect(status().isNotFound()); // Ожидаем статус 404 NOT FOUND
    }
}
