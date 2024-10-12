package sv.edu.udb.inventoryapi.service;
import sv.edu.udb.inventoryapi.controller.request.ProductRequest;
import sv.edu.udb.inventoryapi.controller.response.ProductResponse;
import java.util.List;

public interface ProductService {

    List<ProductResponse> findAll();

    ProductResponse findById(final Long id);

    ProductResponse save(final ProductRequest productRequest);

    ProductResponse update(final Long id, final ProductRequest productRequest);

    void delete(final Long id);

}
