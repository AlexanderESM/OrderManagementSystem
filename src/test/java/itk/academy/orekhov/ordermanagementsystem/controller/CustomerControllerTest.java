package itk.academy.orekhov.ordermanagementsystem.controller;

import itk.academy.orekhov.ordermanagementsystem.model.Customer;
import itk.academy.orekhov.ordermanagementsystem.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        // Настройка MockMvc с контроллером
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void createCustomer_ShouldReturnCreatedCustomer() throws Exception {
        // Создаем тестового клиента
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setContactNumber("1234567890");

        // Подготовка mock-объекта сервиса
        when(customerService.createCustomer(customer)).thenReturn(customer);

        // Выполнение POST-запроса и проверка ответа
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"contactNumber\":\"1234567890\"}"))
                .andExpect(status().isCreated())  // Ожидаем статус 201
                .andExpect(jsonPath("$.firstName").value("John"))  // Проверка первого имени
                .andExpect(jsonPath("$.lastName").value("Doe"))    // Проверка фамилии
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))  // Проверка email
                .andExpect(jsonPath("$.contactNumber").value("1234567890")); // Проверка контактного номера
    }
}
