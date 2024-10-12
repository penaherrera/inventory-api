package sv.edu.udb.inventoryapi.service;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.udb.inventoryapi.repository.ProductRepository;
import sv.edu.udb.inventoryapi.repository.domain.Product;
import sv.edu.udb.inventoryapi.controller.request.ProductRequest;
import sv.edu.udb.inventoryapi.controller.response.ProductResponse;
import sv.edu.udb.inventoryapi.service.implementation.ProductServiceImpl;
import sv.edu.udb.inventoryapi.service.mapper.ProductMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    private Product product;
    @BeforeEach
    void init() {
        this.product = Product
                .builder()
                .id(1L)
                .nombre("testing product")
                .precio(1.50)
                .cantidad(5)
                .fechaExpiracion(LocalDate.of(2024, 12, 28))
                .build();
    }

    @Test
    @DisplayName("Find all the products when we have data")
    void shouldGetAllProductResponse_When_FindAllTheProduct() {
        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productMapper.toProductResponseList(anyList()))
                .thenReturn(
                        List.of(
                                ProductResponse
                                        .builder()
                                        .nombre(this.product.getNombre())
                                        .precio(this.product.getPrecio())
                                        .cantidad(this.product.getCantidad())
                                        .fechaExpiracion(this.product.getFechaExpiracion())
                                        .build()));

        final List<ProductResponse> productResponseList = productService.findAll();

        assertNotNull(productResponseList);
        assertEquals(1, productResponseList.size());
        verify(productRepository, times(1))
                .findAll();
        verifyNoMoreInteractions(productRepository);
        verify(productMapper, times(1))
                .toProductResponseList(anyList());
        verifyNoMoreInteractions(productMapper);
    }

    @Test
    @DisplayName("Find Product by id")
    void shouldGetProduct_When_ExistProductWithId() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(this.product));
        when(productMapper.toProductResponse(any(Product.class))).thenReturn(ProductResponse
                .builder()
                .nombre(this.product.getNombre())
                .precio(this.product.getPrecio())
                .cantidad(this.product.getCantidad())
                .fechaExpiracion(this.product.getFechaExpiracion())
                .build());
        final ProductResponse productResponse = productService.findById(1L);
        final String expectedTitle = "testing product";
        assertNotNull(productResponse);
        assertEquals(expectedTitle, productResponse.getNombre());
        verify(productRepository, times(1))
                .findById(anyLong());
        verifyNoMoreInteractions(productRepository);
        verify(productMapper, times(1))
                .toProductResponse(any(Product.class));
        verifyNoMoreInteractions(productMapper);
    }

    @Test
    @DisplayName("Find a no existed id then throws an exception")
    void shouldGetEntityNotFoundException_When_NoExistProductWithId() {

        when(productRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> {
            productService.findById(2L);
        });
        verify(productMapper, never())
                .toProductResponse(any(Product.class));
        verifyNoMoreInteractions(productMapper);
    }

    @Test
    @DisplayName("Save Product when the Product request is valid")
    void shouldSaveProductEntity_When_ProductRequestIsValid() {
        when(productMapper.toProduct(any(ProductRequest.class))).thenReturn(this.product);
        when(productRepository.save(any(Product.class))).thenReturn(this.product);
        when(productMapper.toProductResponse(any(Product.class)))
                .thenReturn(ProductResponse
                        .builder()
                        .nombre(this.product.getNombre())
                        .precio(this.product.getPrecio())
                        .cantidad(this.product.getCantidad())
                        .fechaExpiracion(this.product.getFechaExpiracion())
                        .build());
        final ProductResponse productResponse = productService.save(ProductRequest
                .builder()
                .nombre(this.product.getNombre())
                .precio(this.product.getPrecio())
                .cantidad(this.product.getCantidad())
                .fechaExpiracion(this.product.getFechaExpiracion())
                .build());
        final String expectedTitle = "testing product";
        assertNotNull(productResponse);
        assertEquals(expectedTitle, productResponse.getNombre());
        verify(productMapper, times(1))
                .toProduct(any(ProductRequest.class));
        verify(productMapper, times(1))
                .toProductResponse(any(Product.class));
        verifyNoMoreInteractions(productMapper);
        verify(productRepository, times(1))
                .save(any(Product.class));
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    @DisplayName("Should update Product When Product Request and Id are valid")
    void shouldUpdateProduct_When_ProductRequestAndIdAreValid() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(this.product));
        final String newNombre = "";
        final double newPrecio = 2.50;
        final Integer newCantidad = 2;
        final LocalDate newFechaExpiracion = LocalDate.of(2024, 10, 27);
        final Product newProduct = Product
                .builder()
                .nombre(newNombre)
                .precio(newPrecio)
                .cantidad(newCantidad)
                .fechaExpiracion(newFechaExpiracion)
                .build();
        when(productRepository.save(any(Product.class))).thenReturn(newProduct);
        when(productMapper.toProductResponse(any(Product.class)))
                .thenReturn(ProductResponse
                        .builder()
                        .nombre(newNombre)
                        .precio(newPrecio)
                        .cantidad(newCantidad)
                        .fechaExpiracion(newFechaExpiracion)
                        .build());
        final ProductRequest productRequest = ProductRequest
                .builder()
                .nombre(newNombre)
                .precio(newPrecio)
                .cantidad(newCantidad)
                .fechaExpiracion(newFechaExpiracion)
                .build();
        final ProductResponse productResponse = productService.update(1L, productRequest);
        assertNotNull(productResponse);
        assertEquals(newNombre, productResponse.getNombre());
        assertEquals(newPrecio, productResponse.getPrecio());
        assertEquals(newCantidad, productResponse.getCantidad());
        assertEquals(newFechaExpiracion, productResponse.getFechaExpiracion());
        verify(productRepository, times(1))
                .findById(anyLong());
        verify(productRepository, times(1))
                .save(any(Product.class));
        verifyNoMoreInteractions(productRepository);
        verify(productMapper, times(1))
                .toProductResponse(any(Product.class));
        verifyNoMoreInteractions(productMapper);
    }

    @Test
    @DisplayName("Delete Product when product id exist")
    void shouldDeleteProduct_When_ProductIdExist() {
        doNothing().when(productRepository).deleteById(anyLong());
        productService.delete(1L);
        verify(productRepository, times(1))
                .deleteById(anyLong());
        verifyNoMoreInteractions(productRepository);
    }
}
