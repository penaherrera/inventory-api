package sv.edu.udb.inventoryapi.service.implementation;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sv.edu.udb.inventoryapi.controller.request.ProductRequest;
import sv.edu.udb.inventoryapi.controller.response.ProductResponse;
import sv.edu.udb.inventoryapi.repository.ProductRepository;
import sv.edu.udb.inventoryapi.repository.domain.Product;
import sv.edu.udb.inventoryapi.service.ProductService;
import sv.edu.udb.inventoryapi.service.mapper.ProductMapper;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @NonNull
    private final ProductRepository productRepository;

    @NonNull
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponse> findAll() {
        return productMapper.toProductResponseList(productRepository.findAll());
    }

    @Override
    public ProductResponse findById(final Long id) {
        return productMapper.toProductResponse(
                productRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException("Resource not found id " + id)));
    }

    @Override
    public ProductResponse save(final ProductRequest productRequest) {
        final Product product = productMapper.toProduct(productRequest);
        return productMapper.toProductResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse update(final Long id, final ProductRequest productRequest) {
        final Product productToUpdate = productRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Resource not found id " + id));
        productToUpdate.setNombre(productRequest.getNombre());
        productToUpdate.setFechaExpiracion(productRequest.getFechaExpiracion());
        productToUpdate.setCantidad(productRequest.getCantidad());
        productToUpdate.setPrecio(productRequest.getPrecio());
        productRepository.save(productToUpdate);
        return productMapper.toProductResponse(productToUpdate);
    }

    @Override
    public void delete(final Long id) {
        productRepository.deleteById(id);
    }

}