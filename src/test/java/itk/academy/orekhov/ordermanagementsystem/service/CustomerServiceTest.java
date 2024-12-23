package itk.academy.orekhov.ordermanagementsystem.service;

import itk.academy.orekhov.ordermanagementsystem.model.Customer;
import itk.academy.orekhov.ordermanagementsystem.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    // Инициализация мока перед тестами
    public CustomerServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    // Тест на создание нового клиента
    @Test
    public void testCreateCustomer() {
        // Создаем новый объект Customer с правильным конструктором
        Customer customer = new Customer(1L, "John", "Doe", "john.doe@example.com", "1234567890");

        // Мокаем поведение репозитория, чтобы метод save возвращал клиента
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Вызов метода createCustomer
        Customer createdCustomer = customerService.createCustomer(customer);

        // Проверки
        assertNotNull(createdCustomer, "Created customer should not be null");
        assertEquals(1L, createdCustomer.getCustomerId(), "Customer ID should match");
        assertEquals("John", createdCustomer.getFirstName(), "First name should match");
        assertEquals("Doe", createdCustomer.getLastName(), "Last name should match");
        assertEquals("john.doe@example.com", createdCustomer.getEmail(), "Email should match");
        assertEquals("1234567890", createdCustomer.getContactNumber(), "Contact number should match");

        // Проверка, что метод save был вызван один раз
        verify(customerRepository, times(1)).save(customer);
    }
}
