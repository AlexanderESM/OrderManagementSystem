package itk.academy.orekhov.ordermanagementsystem.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testCustomerValid() {
        Customer customer = new Customer(1L, "John", "Doe", "john.doe@example.com", "1234567890");

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertTrue(violations.isEmpty(), "Customer should be valid");
    }

    @Test
    public void testFirstNameEmpty() {
        Customer customer = new Customer(1L, "", "Doe", "john.doe@example.com", "1234567890");

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertFalse(violations.isEmpty(), "First name should not be empty");
    }

    @Test
    public void testInvalidEmail() {
        Customer customer = new Customer(1L, "John", "Doe", "invalid-email", "1234567890");

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertFalse(violations.isEmpty(), "Email should be valid");
    }

    @Test
    public void testInvalidContactNumber() {
        Customer customer = new Customer(1L, "John", "Doe", "john.doe@example.com", "12345");

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        assertFalse(violations.isEmpty(), "Contact number should be between 10 and 15 digits");
    }
}
