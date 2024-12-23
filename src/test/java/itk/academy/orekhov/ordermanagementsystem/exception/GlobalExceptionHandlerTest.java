package itk.academy.orekhov.ordermanagementsystem.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RestControllerAdvice
public class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new GlobalExceptionHandler()).build();
    }

    @Test
    public void testHandleResourceNotFoundException() throws Exception {
        // Симуляция того, что возникает исключение ResourceNotFoundException
        mockMvc.perform(get("/some-url-that-throws-exception"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Resource not found"));  // Проверка текста ошибки
    }
}
