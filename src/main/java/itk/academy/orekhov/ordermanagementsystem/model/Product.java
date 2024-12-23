package itk.academy.orekhov.ordermanagementsystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    @Column(length = 500) // Ограничиваем длину описания
    private String description;

    @Column(name = "quantity_in_stock")
    private Integer quantityInStock;

    // Конструктор без параметров
    public Product() {}

    // Конструктор с параметрами (с добавлением id)
    public Product(Long id, String name, Double price, String description, Integer quantityInStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantityInStock = quantityInStock;
    }

    // Конструктор без id (для удобства создания объектов без указания id)
    public Product(String name, Double price, String description, Integer quantityInStock) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantityInStock = quantityInStock;
    }

    // Удаленный конструктор, если он не используется
    // public Product(String s, double v) {}

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "', price=" + price + ", description='" + description + "', quantityInStock=" + quantityInStock + '}';
    }
}
