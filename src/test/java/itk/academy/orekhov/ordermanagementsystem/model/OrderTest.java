package itk.academy.orekhov.ordermanagementsystem.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testOrderValid() {
        Customer customer = new Customer(1L, "John", "Doe", "john.doe@example.com", "1234567890");
        Product product1 = new Product(1L, "Product 1", 10.0);
        Product product2 = new Product(2L, "Product 2", 15.0);
        Order order = new Order(1L, customer, Arrays.asList(product1, product2), "2024-12-23", "123 Street, City", 25.0, "Completed");

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertTrue(violations.isEmpty(), "Order should be valid");
    }

    @Test
    public void testOrderInvalidWithoutCustomer() {
        Product product1 = new Product(1L, "Product 1", 10.0);
        Product product2 = new Product(2L, "Product 2", 15.0);
        Order order = new Order(1L, null, Arrays.asList(product1, product2), "2024-12-23", "123 Street, City", 25.0, "Completed");

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertFalse(violations.isEmpty(), "Order should not be valid without customer");
    }

    @Test
    public void testOrderInvalidWithoutProducts() {
        Customer customer = new Customer(1L, "John", "Doe", "john.doe@example.com", "1234567890");
        Order order = new Order(1L, customer, null, "2024-12-23", "123 Street, City", 25.0, "Completed");

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertFalse(violations.isEmpty(), "Order should not be valid without products");
    }

    @Test
    public void testInvalidOrderDate() {
        Customer customer = new Customer(1L, "John", "Doe", "john.doe@example.com", "1234567890");
        Product product1 = new Product(1L, "Product 1", 10.0);
        Product product2 = new Product(2L, "Product 2", 15.0);
        Order order = new Order(1L, customer, Arrays.asList(product1, product2), "", "123 Street, City", 25.0, "Completed");

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertFalse(violations.isEmpty(), "Order should not be valid without order date");
    }

    @Test
    public void testInvalidShippingAddress() {
        Customer customer = new Customer(1L, "John", "Doe", "john.doe@example.com", "1234567890");
        Product product1 = new Product(1L, "Product 1", 10.0);
        Product product2 = new Product(2L, "Product 2", 15.0);
        Order order = new Order(1L, customer, Arrays.asList(product1, product2), "2024-12-23", "", 25.0, "Completed");

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertFalse(violations.isEmpty(), "Order should not be valid without shipping address");
    }

    @Test
    public void testInvalidTotalPrice() {
        Customer customer = new Customer(1L, "John", "Doe", "john.doe@example.com", "1234567890");
        Product product1 = new Product(1L, "Product 1", 10.0);
        Product product2 = new Product(2L, "Product 2", 15.0);
        Order order = new Order(1L, customer, Arrays.asList(product1, product2), "2024-12-23", "123 Street, City", -5.0, "Completed");

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertFalse(violations.isEmpty(), "Order should not be valid with negative total price");
    }

    @Test
    public void testInvalidOrderStatus() {
        Customer customer = new Customer(1L, "John", "Doe", "john.doe@example.com", "1234567890");
        Product product1 = new Product(1L, "Product 1", 10.0);
        Product product2 = new Product(2L, "Product 2", 15.0);
        Order order = new Order(1L, customer, Arrays.asList(product1, product2), "2024-12-23", "123 Street, City", 25.0, "");

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertFalse(violations.isEmpty(), "Order should not be valid without order status");
    }

    @Test
    public void testEqualsAndHashCode() {
        Customer customer1 = new Customer(1L, "John", "Doe", "john.doe@example.com", "1234567890");
        Product product1 = new Product(1L, "Product 1", 10.0);
        Product product2 = new Product(2L, "Product 2", 15.0);

        Order order1 = new Order(1L, customer1, Arrays.asList(product1, product2), "2024-12-23", "123 Street, City", 25.0, "Completed");
        Order order2 = new Order(1L, customer1, Arrays.asList(product1, product2), "2024-12-23", "123 Street, City", 25.0, "Completed");
        Order order3 = new Order(2L, customer1, Arrays.asList(product1, product2), "2024-12-23", "123 Street, City", 25.0, "Completed");

        assertEquals(order1, order2, "Orders with same id should be equal");
        assertNotEquals(order1, order3, "Orders with different id should not be equal");

        assertEquals(order1.hashCode(), order2.hashCode(), "Orders with same id should have same hashCode");
        assertNotEquals(order1.hashCode(), order3.hashCode(), "Orders with different id should have different hashCode");
    }
}
