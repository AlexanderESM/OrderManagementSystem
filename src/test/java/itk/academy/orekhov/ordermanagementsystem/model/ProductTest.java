package itk.academy.orekhov.ordermanagementsystem.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    public void testConstructorWithId() {
        Product product = new Product(1L, "Test Product", 99.99, "Test Description", 100);

        assertEquals(1L, product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals(99.99, product.getPrice());
        assertEquals("Test Description", product.getDescription());
        assertEquals(100, product.getQuantityInStock());
    }

    @Test
    public void testConstructorWithoutId() {
        Product product = new Product("Test Product", 99.99, "Test Description", 100);

        assertNull(product.getId());  // id should be null as it was not provided
        assertEquals("Test Product", product.getName());
        assertEquals(99.99, product.getPrice());
        assertEquals("Test Description", product.getDescription());
        assertEquals(100, product.getQuantityInStock());
    }

    @Test
    public void testSettersAndGetters() {
        Product product = new Product();

        product.setId(1L);
        product.setName("Updated Product");
        product.setPrice(149.99);
        product.setDescription("Updated Description");
        product.setQuantityInStock(200);

        assertEquals(1L, product.getId());
        assertEquals("Updated Product", product.getName());
        assertEquals(149.99, product.getPrice());
        assertEquals("Updated Description", product.getDescription());
        assertEquals(200, product.getQuantityInStock());
    }

    @Test
    public void testToString() {
        Product product = new Product(1L, "Test Product", 99.99, "Test Description", 100);

        String expectedString = "Product{id=1, name='Test Product', price=99.99, description='Test Description', quantityInStock=100}";
        assertEquals(expectedString, product.toString());
    }
}
