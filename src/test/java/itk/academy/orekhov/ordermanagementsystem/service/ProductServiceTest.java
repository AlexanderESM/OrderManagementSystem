package itk.academy.orekhov.ordermanagementsystem.service;

import itk.academy.orekhov.ordermanagementsystem.exception.ResourceNotFoundException;
import itk.academy.orekhov.ordermanagementsystem.model.Product;
import itk.academy.orekhov.ordermanagementsystem.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Тест на получение всех продуктов
    @Test
    public void testGetAllProducts() {
        // Создаем тестовые данные
        Product product1 = new Product(1L, "Product 1", 10.0, "Description 1", 100);
        Product product2 = new Product(2L, "Product 2", 20.0, "Description 2", 200);

        // Мокаем поведение репозитория
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        // Вызов метода
        List<Product> products = productService.getAllProducts();

        // Проверки
        assertNotNull(products, "Product list should not be null");
        assertEquals(2, products.size(), "There should be 2 products");
        verify(productRepository, times(1)).findAll(); // Проверка, что метод был вызван
    }

    // Тест на получение продукта по ID (существующий продукт)
    @Test
    public void testGetProductByIdExists() {
        // Создаем продукт для теста
        Product product = new Product(1L, "Product 1", 10.0, "Description 1", 100);

        // Мокаем поведение репозитория
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Вызов метода
        Product foundProduct = productService.getProductById(1L);

        // Проверки
        assertNotNull(foundProduct, "Product should not be null");
        assertEquals(1L, foundProduct.getId(), "Product ID should match");
        verify(productRepository, times(1)).findById(1L); // Проверка, что метод был вызван
    }

    // Тест на получение продукта по ID (продукт не найден)
    @Test
    public void testGetProductByIdNotFound() {
        // Мокаем поведение репозитория на пустой результат
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Проверка, что метод выбрасывает исключение
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductById(1L);
        }, "Expected ResourceNotFoundException to be thrown");
    }

    // Тест на создание нового продукта
    @Test
    public void testCreateProduct() {
        // Создаем продукт для теста
        Product product = new Product(1L, "Product 1", 10.0, "Description 1", 100);

        // Мокаем поведение репозитория
        when(productRepository.save(product)).thenReturn(product);

        // Вызов метода
        Product createdProduct = productService.createProduct(product);

        // Проверки
        assertNotNull(createdProduct, "Created product should not be null");
        assertEquals("Product 1", createdProduct.getName(), "Product name should match");
        verify(productRepository, times(1)).save(product); // Проверка, что метод был вызван
    }

    // Тест на обновление продукта
    @Test
    public void testUpdateProduct() {
        // Создаем старый продукт для теста
        Product existingProduct = new Product(1L, "Product 1", 10.0, "Description 1", 100);
        Product updatedProductDetails = new Product(1L, "Updated Product", 15.0, "Updated Description", 120);

        // Мокаем поведение репозитория
        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(updatedProductDetails);

        // Вызов метода
        Product updatedProduct = productService.updateProduct(1L, updatedProductDetails);

        // Проверки
        assertNotNull(updatedProduct, "Updated product should not be null");
        assertEquals("Updated Product", updatedProduct.getName(), "Product name should match");
        assertEquals("Updated Description", updatedProduct.getDescription(), "Product description should match");
        assertEquals(15.0, updatedProduct.getPrice(), "Product price should match");
        assertEquals(120, updatedProduct.getQuantityInStock(), "Product quantity should match");
        verify(productRepository, times(1)).save(existingProduct); // Проверка, что метод был вызван
    }

    // Тест на удаление продукта
    @Test
    public void testDeleteProduct() {
        // Создаем продукт для теста
        Product product = new Product(1L, "Product 1", 10.0, "Description 1", 100);

        // Мокаем поведение репозитория
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Вызов метода
        productService.deleteProduct(1L);

        // Проверки
        verify(productRepository, times(1)).delete(product); // Проверка, что метод был вызван
    }

    // Тест на удаление продукта (продукт не найден)
    @Test
    public void testDeleteProductNotFound() {
        // Мокаем поведение репозитория на пустой результат
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Проверка, что метод выбрасывает исключение
        assertThrows(ResourceNotFoundException.class, () -> {
            productService.deleteProduct(1L);
        }, "Expected ResourceNotFoundException to be thrown");
    }
}
