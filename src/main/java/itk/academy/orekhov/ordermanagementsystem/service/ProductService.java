package itk.academy.orekhov.ordermanagementsystem.service;

import itk.academy.orekhov.ordermanagementsystem.exception.ResourceNotFoundException;
import itk.academy.orekhov.ordermanagementsystem.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import itk.academy.orekhov.ordermanagementsystem.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Получить все продукты
    public List<Product> getAllProducts() {
        return productRepository.findAll(); // Работаем с репозиторием для получения всех продуктов
    }

    // Получить продукт по ID
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found")); // Используем репозиторий для поиска по ID
    }

    // Создать новый продукт
    public Product createProduct(Product product) {
        return productRepository.save(product); // Сохраняем продукт в базе данных
    }

    // Обновить продукт
    public Product updateProduct(Long id, Product productDetails) {
        Product existingProduct = getProductById(id); // Получаем продукт или выбрасываем исключение
        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setQuantityInStock(productDetails.getQuantityInStock());
        return productRepository.save(existingProduct); // Обновляем продукт в базе данных
    }

    // Удалить продукт
    public void deleteProduct(Long id) {
        Product product = getProductById(id); // Получаем продукт или выбрасываем исключение
        productRepository.delete(product); // Удаляем продукт из базы данных
    }
}
