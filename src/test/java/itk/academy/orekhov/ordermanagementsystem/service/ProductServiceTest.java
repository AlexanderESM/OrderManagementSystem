package itk.academy.orekhov.ordermanagementsystem.service;

import itk.academy.orekhov.ordermanagementsystem.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    private ProductService productService;

    @BeforeEach
    public void setUp() {
        // Инициализация нового объекта сервиса перед каждым тестом
        productService = new ProductService();
    }

    @Test
    public void testGetAllProducts() {
        // Проверка метода getAllProducts
        Product product1 = new Product(1L, "Product 1", 100.0);
        Product product2 = new Product(2L, "Product 2", 200.0);

        productService.createProduct(product1);
        productService.createProduct(product2);

        assertEquals(2, productService.getAllProducts().size());
        assertTrue(productService.getAllProducts().contains(product1));
        assertTrue(productService.getAllProducts().contains(product2));
    }

    @Test
    public void testGetProduct() {
        // Проверка метода getProduct по id
        Product product = new Product(1L, "Product 1", 100.0);
        productService.createProduct(product);

        Optional<Product> retrievedProduct = productService.getProduct(1L);
        assertTrue(retrievedProduct.isPresent());
        assertEquals("Product 1", retrievedProduct.get().getName());
        assertEquals(100.0, retrievedProduct.get().getPrice());
    }

    @Test
    public void testGetProductNotFound() {
        // Проверка, что возвращается пустой Optional, если продукт не найден
        Optional<Product> product = productService.getProduct(99L);
        assertFalse(product.isPresent());
    }

    @Test
    public void testCreateProduct() {
        // Проверка метода createProduct
        Product product = new Product("New Product", 150.0);
        Product createdProduct = productService.createProduct(product);

        assertNotNull(createdProduct);
        assertEquals("New Product", createdProduct.getName());
        assertEquals(150.0, createdProduct.getPrice());
    }

    @Test
    public void testUpdateProduct() {
        // Проверка метода updateProduct
        Product product = new Product(1L, "Product 1", 100.0);
        productService.createProduct(product);

        Product updatedProduct = new Product("Updated Product", 120.0);
        Optional<Product> result = productService.updateProduct(1L, updatedProduct);

        assertTrue(result.isPresent());
        assertEquals("Updated Product", result.get().getName());
        assertEquals(120.0, result.get().getPrice());
    }

    @Test
    public void testUpdateProductNotFound() {
        // Проверка обновления несуществующего продукта
        Product updatedProduct = new Product("Updated Product", 120.0);
        Optional<Product> result = productService.updateProduct(99L, updatedProduct);

        assertFalse(result.isPresent());
    }

    @Test
    public void testDeleteProduct() {
        // Проверка метода deleteProduct
        Product product = new Product(1L, "Product to delete", 100.0);
        productService.createProduct(product);

        boolean isDeleted = productService.deleteProduct(1L);
        assertTrue(isDeleted);

        Optional<Product> deletedProduct = productService.getProduct(1L);
        assertFalse(deletedProduct.isPresent());
    }

    @Test
    public void testDeleteProductNotFound() {
        // Проверка удаления несуществующего продукта
        boolean isDeleted = productService.deleteProduct(99L);
        assertFalse(isDeleted);
    }
}
