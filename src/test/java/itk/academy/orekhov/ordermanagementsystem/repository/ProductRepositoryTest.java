package itk.academy.orekhov.ordermanagementsystem.repository;

import itk.academy.orekhov.ordermanagementsystem.model.Product;
import itk.academy.orekhov.ordermanagementsystem.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // Использовать PostgreSQL для тестов
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    // Тест на сохранение и поиск продукта по ID
    @Test
    public void testSaveAndFindById() {
        // Создаем новый продукт
        Product product = new Product("Test Product", 50.0, "Test Description", 100);

        // Сохраняем продукт в базе данных
        Product savedProduct = productRepository.save(product);

        // Проверяем, что продукт сохранен и его можно найти по ID
        assertNotNull(savedProduct.getId(), "Product ID should not be null after saving");

        // Ищем продукт по ID
        Product foundProduct = productRepository.findById(savedProduct.getId()).orElse(null);

        // Проверяем, что продукт был найден
        assertNotNull(foundProduct, "Product should be found by ID");
        assertEquals(savedProduct.getName(), foundProduct.getName(), "Product names should match");
        assertEquals(savedProduct.getPrice(), foundProduct.getPrice(), "Product prices should match");
    }

    // Тест на удаление продукта
    @Test
    public void testDeleteProduct() {
        // Создаем и сохраняем продукт
        Product product = new Product("Test Product", 50.0, "Test Description", 100);
        Product savedProduct = productRepository.save(product);

        // Проверяем, что продукт сохранен
        assertNotNull(savedProduct.getId(), "Product ID should not be null after saving");

        // Удаляем продукт
        productRepository.deleteById(savedProduct.getId());

        // Проверяем, что продукт больше не существует в базе данных
        assertFalse(productRepository.findById(savedProduct.getId()).isPresent(), "Product should be deleted");
    }

    // Тест на получение всех продуктов
    @Test
    public void testFindAllProducts() {
        // Создаем и сохраняем несколько продуктов
        Product product1 = new Product("Product 1", 10.0, "Description 1", 100);
        Product product2 = new Product("Product 2", 20.0, "Description 2", 200);
        productRepository.save(product1);
        productRepository.save(product2);

        // Получаем все продукты
        List<Product> products = (List<Product>) productRepository.findAll();

        // Проверяем, что оба продукта были сохранены и найдены
        assertEquals(2, products.size(), "There should be exactly two products in the repository");

        // Проверяем, что продукты содержат ожидаемые имена
        assertTrue(products.stream().anyMatch(p -> p.getName().equals("Product 1")));
        assertTrue(products.stream().anyMatch(p -> p.getName().equals("Product 2")));
    }
}
