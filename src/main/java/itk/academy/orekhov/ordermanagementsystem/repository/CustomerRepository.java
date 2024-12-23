package itk.academy.orekhov.ordermanagementsystem.repository;


import itk.academy.orekhov.ordermanagementsystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
