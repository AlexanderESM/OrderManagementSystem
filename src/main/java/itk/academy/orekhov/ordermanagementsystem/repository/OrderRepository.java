package itk.academy.orekhov.ordermanagementsystem.repository;

import itk.academy.orekhov.ordermanagementsystem.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}