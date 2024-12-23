package itk.academy.orekhov.ordermanagementsystem.repository;


import itk.academy.orekhov.ordermanagementsystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}