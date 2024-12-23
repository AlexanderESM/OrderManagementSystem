package itk.academy.orekhov.ordermanagementsystem.exception;

import itk.academy.orekhov.ordermanagementsystem.exception.ResourceNotFoundException;
import itk.academy.orekhov.ordermanagementsystem.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    // Тест на исключение ResourceNotFoundException
    @Test
    public void whenResourceNotFound_thenReturnsNotFoundStatus() throws Exception {
        mockMvc.perform(get("/api/products/9999")) // Несуществующий ID
                .andExpect(status().isNotFound())
                .andExpect(content().string(is("Product with id 9999 not found")));
    }

    // Тест на исключение ValidationException
    @Test
    public void whenValidationException_thenReturnsBadRequestStatus() throws Exception {
        String invalidProductJson = "{ \"name\": \"\", \"price\": -10.0 }"; // Некорректные данные

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidProductJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(is("Validation failed: Product name cannot be empty, Product price must be greater than zero")));
    }

    // Тест на общее исключение (например, NullPointerException)
    @Test
    public void whenGeneralException_thenReturnsInternalServerErrorStatus() throws Exception {
        mockMvc.perform(get("/api/products/undefined")) // Некорректный запрос, вызывающий ошибку
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(is("An unexpected error occurred: java.lang.NullPointerException")));
    }
}
