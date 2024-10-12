package sv.edu.udb.inventoryapi.repository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sv.edu.udb.inventoryapi.repository.domain.Product;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @BeforeEach
    void init() {
        final String expectedNombre = "Yogurt Yessss";
        final double expectedPrecio = 1.25;
        final Integer expectedCantidad = 100;
        final LocalDate expectedFechaExpiracion = LocalDate.of(2024, 12, 24);
        final Product newProduct = Product
                .builder()
                .nombre(expectedNombre)
                .precio(expectedPrecio)
                .cantidad(expectedCantidad)
                .fechaExpiracion(expectedFechaExpiracion)
                .build();
        productRepository.save(newProduct);
    }
    @AfterEach
    void clean() {
        productRepository.deleteAll();
    }

    @Test
    void shouldHasOneProduct_When_FindAll() {
        int expectedProductNumber = 1;
        final List<Product> actualProductList = productRepository.findAll();
        assertNotNull(actualProductList);
        assertEquals(expectedProductNumber, actualProductList.size());
    }

    @Test
    void shouldGetProduct_When_IdExist() {
        final Long expectedId = 1L;
        final String expectedNombre = "Yogurt Yessss";
        final double expectedPrecio = 1.25;
        final Integer expectedCantidad = 100;
        final LocalDate expectedFechaExpiracion = LocalDate.of(2024, 12, 24);

        final Product actualProduct = productRepository.findById(expectedId).orElse(null);

        assertNotNull(actualProduct);
        assertEquals(expectedId, actualProduct.getId());
        assertEquals(expectedNombre, actualProduct.getNombre());
        assertEquals(expectedPrecio, actualProduct.getPrecio());
        assertEquals(expectedCantidad, actualProduct.getCantidad());
        assertEquals(expectedFechaExpiracion, actualProduct.getFechaExpiracion());
    }


    @Test
    void shouldSaveProduct_When_ProductIsNew() {
        final String expectedNombre = "Churros Diana";
        final double expectedPrecio = 0.25;
        final Integer expectedCantidad = 200;
        final LocalDate expectedFechaExpiracion = LocalDate.of(2024, 12, 31);
        final Product newProduct = Product
                .builder()
                .nombre(expectedNombre)
                .precio(expectedPrecio)
                .cantidad(expectedCantidad)
                .fechaExpiracion(expectedFechaExpiracion)
                .build();

        Product savedProduct = productRepository.save(newProduct);

        final Long actualId = savedProduct.getId();
        final Product actualProduct = productRepository.findById(actualId).orElse(null);
        assertNotNull(actualProduct);
        assertEquals(actualId, actualProduct.getId());
        assertEquals(expectedNombre, actualProduct.getNombre());
        assertEquals(expectedPrecio, actualProduct.getPrecio());
        assertEquals(expectedCantidad, actualProduct.getCantidad());
        assertEquals(expectedFechaExpiracion, actualProduct.getFechaExpiracion());
    }

    @Test
    void shouldDeleteProduct_When_ProductExist() {
        final double expectedPrecio = 0.25;
        final Integer expectedCantidad = 200;
        final String expectedNombre = "Churros Diana";
        final LocalDate expectedFechaExpiracion = LocalDate.of(2024, 12, 31);
        final Product newProduct = Product
                .builder()
                .nombre(expectedNombre)
                .precio(expectedPrecio)
                .cantidad(expectedCantidad)
                .fechaExpiracion(expectedFechaExpiracion)
                .build();

        productRepository.saveAndFlush(newProduct);

        final Long expectedId = newProduct.getId();
        final Product actualProduct = productRepository.findById(expectedId).orElse(null);
        assertNotNull(actualProduct);
        productRepository.deleteById(expectedId);
        final Product deletedProduct = productRepository.findById(expectedId).orElse(null);
        assertNull(deletedProduct);
    }
}
