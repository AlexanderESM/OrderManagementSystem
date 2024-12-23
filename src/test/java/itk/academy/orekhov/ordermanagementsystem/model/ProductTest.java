package itk.academy.orekhov.ordermanagementsystem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private Product product;

    @BeforeEach
    public void setUp() {
        // Используем конструктор с id, name и price
        product = new Product(1L, "Product Name", 100.0);
    }

    @Test
    public void testProductConstructorAndGetters() {
        assertEquals(1L, product.getId(), "Product ID should be 1");
        assertEquals("Product Name", product.getName(), "Product name should be 'Product Name'");
        assertEquals(100.0, product.getPrice(), "Product price should be 100.0");
    }

    @Test
    public void testProductSetters() {
        product.setId(2L);
        product.setName("Updated Product");
        product.setPrice(200.0);

        assertEquals(2L, product.getId(), "Product ID should be updated to 2");
        assertEquals("Updated Product", product.getName(), "Product name should be updated to 'Updated Product'");
        assertEquals(200.0, product.getPrice(), "Product price should be updated to 200.0");
    }

    @Test
    public void testEqualsAndHashCode() {
        Product product1 = new Product(1L, "Product Name", 100.0);
        Product product2 = new Product(1L, "Product Name", 100.0);
        Product product3 = new Product(2L, "Different Product", 150.0);

        assertEquals(product1, product2, "Products with the same ID should be equal");
        assertNotEquals(product1, product3, "Products with different IDs should not be equal");

        assertEquals(product1.hashCode(), product2.hashCode(), "Hash codes for equal products should be the same");
        assertNotEquals(product1.hashCode(), product3.hashCode(), "Hash codes for different products should be different");
    }

    @Test
    public void testDefaultConstructor() {
        Product defaultProduct = new Product();
        assertNull(defaultProduct.getId(), "Default product ID should be null");
        assertNull(defaultProduct.getName(), "Default product name should be null");
        assertNull(defaultProduct.getPrice(), "Default product price should be null");
    }
}
