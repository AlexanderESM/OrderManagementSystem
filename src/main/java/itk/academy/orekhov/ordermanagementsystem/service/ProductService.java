package itk.academy.orekhov.ordermanagementsystem.service;

import itk.academy.orekhov.ordermanagementsystem.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private List<Product> products = new ArrayList<>();

    // Получить все продукты
    public List<Product> getAllProducts() {
        return products;
    }

    // Получить продукт по ID
    public Optional<Product> getProduct(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    // Создать новый продукт
    public Product createProduct(Product product) {
        products.add(product);
        return product;
    }

    // Обновить продукт
    public Optional<Product> updateProduct(Long id, Product product) {
        Optional<Product> existingProduct = getProduct(id);
        if (existingProduct.isPresent()) {
            existingProduct.get().setName(product.getName());
            existingProduct.get().setPrice(product.getPrice());
            return Optional.of(existingProduct.get());
        }
        return Optional.empty();
    }

    // Удалить продукт
    public boolean deleteProduct(Long id) {
        return products.removeIf(product -> product.getId().equals(id));
    }
}
