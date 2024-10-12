package sv.edu.udb.inventoryapi.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import sv.edu.udb.inventoryapi.repository.domain.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
